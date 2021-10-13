package org.cs3219.project.peerprep.service;

import lombok.AllArgsConstructor;
import org.cs3219.project.peerprep.model.entity.User;
import org.cs3219.project.peerprep.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final int LIFETIME = 30;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO: check if we use username or email
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                                String.format("User not found with username %s", email)
                        )
                );
    }

    public User createNewUser(User user) {
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
        LocalDateTime createTime = LocalDateTime.now();
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(LIFETIME);
        user.setToken(token);
        user.setTokenCreateTime(createTime);
        user.setTokenExpireTime(expireTime);

        userRepository.save(user);

        return user;
    }

}
