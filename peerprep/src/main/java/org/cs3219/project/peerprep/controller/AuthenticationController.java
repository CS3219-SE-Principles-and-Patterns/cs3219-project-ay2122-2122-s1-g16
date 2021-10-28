package org.cs3219.project.peerprep.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cs3219.project.peerprep.common.api.CommonResponse;
import org.cs3219.project.peerprep.common.utils.AuthenticationUtil;
import org.cs3219.project.peerprep.model.dto.authentication.*;
import org.cs3219.project.peerprep.model.entity.User;
import org.cs3219.project.peerprep.model.entity.UserGroup;
import org.cs3219.project.peerprep.service.AuthenticationService;
import org.cs3219.project.peerprep.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(value = "/api/v1/account")
@Validated
@Slf4j
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final EmailService emailService;
    @Autowired
    private final AuthenticationUtil authenticationUtil;
    @Value("${custom.email.enable}")
    private boolean emailEnabled;

    public AuthenticationController(AuthenticationService authenticationService, EmailService emailService, AuthenticationUtil authenticationUtil) {
        this.authenticationService = authenticationService;
        this.emailService = emailService;
        this.authenticationUtil = authenticationUtil;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<CommonResponse<RegistrationResponse>> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException {

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
        String url = authenticationUtil.generateActivationUrl(user.getEmail(), user.getActivationToken());
        String subject = authenticationUtil.generateActivationEmailSubject();
        String recepientEmail = user.getEmail();
        String senderEmail = authenticationUtil.generateEmailSenderAddress();
        String emailContent = authenticationUtil.buildActivationEmailContent(url);

        if (emailEnabled) {
            emailService.send(subject, recepientEmail, senderEmail, emailContent);
        }

        CommonResponse<RegistrationResponse> commonResponse = new CommonResponse<>(HttpStatus.CREATED.value(), "created", resp);
        return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
    }

    // TODO: need to configure CORS for this endpoint to allow request from anywhere
    @GetMapping(path = "/activate")
    public ResponseEntity<String> activate(@RequestParam("email")
                                                                         @Email(message = "email must a valid email address")
                                                                         @NotBlank(message = "email cannot be blank")
                                                                                 String email,
                                                                         @RequestParam("token") @NotBlank(message = "token cannot be blank") String token) {
        User user = authenticationService.activate(email, token);
        RegistrationResponse resp = new RegistrationResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getActivationToken()
        );
        CommonResponse<RegistrationResponse> CommonResponse = new CommonResponse<>(HttpStatus.OK.value(), "success", resp);
        String successMsg = "Activation success! Please go back to Peerprep to login: " + authenticationUtil.generateFrontendLandingUrl();
        return new ResponseEntity<>(successMsg, HttpStatus.OK);
    }

    @PostMapping(path = "/password/pre-reset")
    public ResponseEntity<CommonResponse<PasswordPreResetResponse>> preResetPassword(@RequestParam("email")
                                                                                     @Email(message = "email must a valid email address")
                                                                                     @NotBlank(message = "email cannot be blank")
                                                                                             String email) throws MessagingException {
        String token = authenticationService.generatePasswordToken(email);
        PasswordPreResetResponse resp = new PasswordPreResetResponse(email, token);

        // send email
        String url = authenticationUtil.generatePasswordResetUrl(email, token);
        String subject = authenticationUtil.generatePasswordResetEmailSubject();
        String senderEmail = authenticationUtil.generateEmailSenderAddress();
        String emailContent = authenticationUtil.buildPasswordResetEmailContent(url);

        if (emailEnabled) {
            emailService.send(subject, email, senderEmail, emailContent);
        }

        CommonResponse<PasswordPreResetResponse> CommonResponse = new CommonResponse<>(HttpStatus.OK.value(), "success", resp);
        return new ResponseEntity<>(CommonResponse, HttpStatus.OK);
    }


    @PutMapping(path = "/password/reset")
    public ResponseEntity<CommonResponse<PasswordResetResponse>> resetPassword(@RequestBody @Valid PasswordResetRequest request) {
        User user = authenticationService.resetPassword(request.getEmail(), request.getPassword(), request.getToken());
        PasswordResetResponse resp = new PasswordResetResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );
        CommonResponse<PasswordResetResponse> CommonResponse = new CommonResponse<>(HttpStatus.OK.value(), "success", resp);
        return new ResponseEntity<>(CommonResponse, HttpStatus.OK);
    }
}
