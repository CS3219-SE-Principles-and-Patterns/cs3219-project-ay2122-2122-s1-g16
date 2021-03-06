package org.cs3219.project.peerprep.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cs3219.project.peerprep.common.api.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("utf-8");

        CommonResponse<Object> resp = new CommonResponse<>(HttpServletResponse.SC_OK, "success", null);
        ObjectMapper mapper = new ObjectMapper();
        OutputStream out = httpServletResponse.getOutputStream();
        mapper.writeValue(out, resp);
        out.flush();
    }
}
