package org.cs3219.project.peerprep.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.cs3219.project.peerprep.common.api.CommonResponse;
import org.cs3219.project.peerprep.model.dto.authentication.LoginSuccessResponse;
import org.cs3219.project.peerprep.model.entity.User;
import org.cs3219.project.peerprep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

@Component
@AllArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("utf-8");

        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        LoginSuccessResponse successResponse = new LoginSuccessResponse(
                user.getId(),
                email,
                user.getNickname()
        );

        CommonResponse<Object> resp = new CommonResponse<>(HttpServletResponse.SC_OK, "success", successResponse);
        ObjectMapper mapper = new ObjectMapper();
        OutputStream out = httpServletResponse.getOutputStream();
        mapper.writeValue(out, resp);
        out.flush();
    }
}
