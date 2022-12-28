package com.sid.gl.manageemployee.security;

import com.sid.gl.manageemployee.constants.SecurityConstant;
import com.sid.gl.manageemployee.tools.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest request;

    @Override
    public Optional<String> getCurrentAuditor() {

        String username = "";
        if (request.getHeader(SecurityConstant.HEADER_NAME) != null) {
            String token = request.getHeader(SecurityConstant.HEADER_NAME).split(" ")[1];
            if (!token.isEmpty())
                username = jwtTokenUtil.extractUsername(token);
        }
        return Optional.ofNullable(username).filter(s -> !s.isEmpty());
    }
}
