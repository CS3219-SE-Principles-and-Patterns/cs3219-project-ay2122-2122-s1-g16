package org.cs3219.project.peerprep.controller;

import lombok.AllArgsConstructor;
import org.cs3219.project.peerprep.common.api.CommonResponse;
import org.cs3219.project.peerprep.model.dto.profile.UpdateUserProfileRequest;
import org.cs3219.project.peerprep.model.dto.profile.UserProfileResponse;
import org.cs3219.project.peerprep.model.entity.User;
import org.cs3219.project.peerprep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(value = "/api/v1/profile")
@AllArgsConstructor
@Validated
public class UserProfileController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/id")
    public ResponseEntity<CommonResponse<UserProfileResponse>> getProfileById(@RequestParam("id")
                                                                              @Positive(message = "id must be a positive integer")
                                                                                      int id) {
        User user = userService.getUserById(id);

        UserProfileResponse profileResponse = new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );

        CommonResponse<UserProfileResponse> resp = new CommonResponse<>(HttpStatus.OK.value(), "success", profileResponse);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping(path = "/email")
    public ResponseEntity<CommonResponse<UserProfileResponse>> getProfileByEmail(@RequestParam("email")
                                                                                 @Email(message = "email must a valid email address")
                                                                                 @NotBlank(message = "email cannot be blank") String email) {
        User user = userService.getUserByEmail(email);

        UserProfileResponse profileResponse = new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );

        CommonResponse<UserProfileResponse> resp = new CommonResponse<>(HttpStatus.OK.value(), "success", profileResponse);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<UserProfileResponse>> updateNickname(@RequestBody @Valid UpdateUserProfileRequest request) {
        User user = userService.updateNickname(request.getId(), request.getNickname());
        UserProfileResponse profileResponse = new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );

        CommonResponse<UserProfileResponse> resp = new CommonResponse<>(HttpStatus.OK.value(), "success", profileResponse);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
