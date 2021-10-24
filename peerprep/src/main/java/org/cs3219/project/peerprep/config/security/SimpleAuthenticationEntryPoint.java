package org.cs3219.project.peerprep.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cs3219.project.peerprep.common.api.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

@Component
public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("utf-8");

        CommonResponse<Object> resp = new CommonResponse<>(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage(), null);

        ObjectMapper mapper = new ObjectMapper();
        OutputStream out = httpServletResponse.getOutputStream();
        mapper.writeValue(out, resp);
        out.flush();
    }
}
