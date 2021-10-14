package org.cs3219.project.peerprep.controller;

import lombok.AllArgsConstructor;
import org.cs3219.project.peerprep.model.dto.RegistrationRequest;
import org.cs3219.project.peerprep.model.dto.RegistrationResponse;
import org.cs3219.project.peerprep.model.entity.User;
import org.cs3219.project.peerprep.model.entity.UserGroup;
import org.cs3219.project.peerprep.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping(value = "/api/v1/authenticate")
@AllArgsConstructor
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping(path = "/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {

        // TODO: input validation
//        boolean isValidEmail = emailValidationService.test(request.getEmail());
//
//        if (!isValidEmail) {
//            throw new IllegalArgumentException("invalid email address");
//        }

        // TODO: check empty nickname

        User user = authenticationService.register(new User(request.getEmail(),
                        request.getNickname(),
                        request.getPassword(),
                        UserGroup.SIMPLE_USER)
        );
        RegistrationResponse resp = new RegistrationResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }
}
