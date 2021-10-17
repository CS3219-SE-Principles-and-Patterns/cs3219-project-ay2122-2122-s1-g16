package org.cs3219.project.peerprep.service;

import lombok.AllArgsConstructor;
import org.cs3219.project.peerprep.model.entity.User;
import org.cs3219.project.peerprep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationService {
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final int LIFETIME = 30;

    public User createNewUser(User user) throws IllegalArgumentException {
        String email = user.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User savedUser = optionalUser.get();
            if (savedUser.isEnabled()) {
                throw new IllegalArgumentException(String.format("email %s is taken", email));
            }

            user.setId(savedUser.getId());
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String token = UUID.randomUUID().toString();
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(LIFETIME);
        user.setActivationToken(token);
        user.setActivationTokenExpireTime(expireTime);

        userRepository.save(user);

        return user;
    }

    public User activate(String email, String token) {
        User user = userService.getUserByEmail(email);
        if (user.isEnabled()) {
            throw new IllegalArgumentException("account is already activated");
        }

        if (!token.equals(user.getActivationToken())) {
            throw new IllegalArgumentException("incorrect token received");
        }

        LocalDateTime expireTime = user.getActivationTokenExpireTime();
        if (expireTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("token expired");
        }

        LocalDateTime now = LocalDateTime.now();
        user.setEnabled(true);
        user.setActivationTime(now);

        userRepository.updateActivationStatus(email, now);

        return user;
    }

    public String generatePasswordToken(String email) {
        if (!userRepository.existsUserByEmail(email)) {
            throw new IllegalArgumentException(String.format("user with email %s is not found", email));
        }
        String token = UUID.randomUUID().toString();
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(LIFETIME);
        userRepository.updatePasswordToken(email, token, expireTime);

        return token;
    }

    public User resetPassword(String email, String password, String token) {
        User user = userService.getUserByEmail(email);

        if (!token.equals(user.getPasswordToken())) {
            throw new IllegalArgumentException("incorrect token received");
        }

        LocalDateTime expireTime = user.getPasswordTokenExpireTime();
        if (expireTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("token expired");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(password);
        userRepository.updatePassword(email, encodedPassword);
        return user;
    }
}
