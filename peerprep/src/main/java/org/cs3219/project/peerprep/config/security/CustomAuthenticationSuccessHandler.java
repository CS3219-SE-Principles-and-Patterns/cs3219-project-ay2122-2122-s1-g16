package org.cs3219.project.peerprep.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
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
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    // TODO: modify according to CommonResponse
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("utf-8");

        HashMap<String, String> map = new HashMap<>();
        map.put("code", HttpServletResponse.SC_OK + "");
        map.put("message", "login success!");

        ObjectMapper mapper = new ObjectMapper();
        OutputStream out = httpServletResponse.getOutputStream();
        mapper.writeValue(out, map);
        out.flush();
    }
}
