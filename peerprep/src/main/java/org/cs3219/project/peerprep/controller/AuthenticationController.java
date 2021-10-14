package org.cs3219.project.peerprep.controller;

import lombok.AllArgsConstructor;
import org.cs3219.project.peerprep.common.utils.AuthenticationUtil;
import org.cs3219.project.peerprep.model.dto.authentication.*;
import org.cs3219.project.peerprep.model.entity.User;
import org.cs3219.project.peerprep.model.entity.UserGroup;
import org.cs3219.project.peerprep.service.AuthenticationService;
import org.cs3219.project.peerprep.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(value = "/api/v1/account")
@AllArgsConstructor
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final EmailService emailService;

    @PostMapping(path = "/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody @Valid RegistrationRequest request) {
        User user = authenticationService.createNewUser(new User(request.getEmail(),
                request.getNickname(),
                request.getPassword(),
                UserGroup.SIMPLE_USER)
        );
        RegistrationResponse resp = new RegistrationResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getActivationToken()
        );

        // send email
        String url = AuthenticationUtil.generateActivationUrl(user.getEmail(), user.getActivationToken());
        String subject = AuthenticationUtil.generateActivationEmailSubject();
        String recepientEmail = user.getEmail();
        String senderEmail = AuthenticationUtil.generateEmailSenderAddress();
        String emailContent = AuthenticationUtil.buildActivationEmailContent(url);
        emailService.send(subject, recepientEmail, senderEmail, emailContent);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    // TODO: need to configure CORS for this endpoint to allow request from anywhere
    @GetMapping(path = "/activate")
    public ResponseEntity<RegistrationResponse> activate(@RequestParam("email") @Email(message = "email must a valid email address") String email,
                                                         @RequestParam("token") @NotBlank(message = "token cannot be blank") String token
                                                         ) {
        User user = authenticationService.activate(email, token);
        RegistrationResponse resp = new RegistrationResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getActivationToken()
        );
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(path = "/password/pre-reset")
    public ResponseEntity<PasswordPreResetResponse> preResetPassword(@RequestParam("email") @Email String email) {
        String token = authenticationService.generatePasswordToken(email);
        PasswordPreResetResponse resp = new PasswordPreResetResponse(email, token);

        // send email
        String url = AuthenticationUtil.generatePasswordResetUrl(email, token);
        String subject = AuthenticationUtil.generatePasswordResetEmailSubject();
        String recipientEmail = email;
        String senderEmail = AuthenticationUtil.generateEmailSenderAddress();
        String emailContent = AuthenticationUtil.buildPasswordResetEmailContent(url);
        emailService.send(subject, recipientEmail, senderEmail, emailContent);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }


    @PostMapping(path = "/password/reset")
    public ResponseEntity<PasswordResetResponse> resetPassord(@RequestBody @Valid PasswordResetRequest request) {
        User user = authenticationService.resetPassword(request.getEmail(), request.getPassword(), request.getToken());
        PasswordResetResponse resp = new PasswordResetResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
