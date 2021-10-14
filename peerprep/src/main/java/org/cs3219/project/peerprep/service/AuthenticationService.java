package org.cs3219.project.peerprep.service;

import lombok.AllArgsConstructor;
import org.cs3219.project.peerprep.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    @Autowired
    private final UserService userService;

    public User register(User user) {
        user = userService.createNewUser(user);

        // TODO: send email

        return user;
    }
}
