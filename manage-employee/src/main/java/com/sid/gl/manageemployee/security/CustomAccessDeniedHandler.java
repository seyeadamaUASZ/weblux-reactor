package com.sid.gl.manageemployee.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.gl.manageemployee.tools.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private DispatcherServlet dispatcherServlet;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException,
            ServletException {
        if (!response.isCommitted()) {
            List<HandlerMapping> handlerMappings = dispatcherServlet.getHandlerMappings();
            if (handlerMappings != null) {
                HandlerExecutionChain handler = null;
                for (HandlerMapping handlerMapping : handlerMappings) {
                    try {
                        handler = handlerMapping.getHandler(request);
                    } catch (Exception e) {
                    }
                    if (handler != null)
                        break;
                }
                if (handler != null && handler.getHandler() instanceof HandlerMethod) {
                    HandlerMethod method = (HandlerMethod) handler.getHandler();
                    PreAuthorize methodAnnotation = method.getMethodAnnotation(PreAuthorize.class);
                    if (methodAnnotation != null) {
                        BasicResponse basicResponse = new BasicResponse("Unauthorized access !!");
                        response.setStatus(basicResponse.getHttpStatus());
                        response.getWriter().write(convertObjectToJson(basicResponse));

                        return;
                    }
                }
            }
            response.sendError(HttpStatus.FORBIDDEN.value(),
                    HttpStatus.FORBIDDEN.getReasonPhrase());
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Autowired
    public void setDispatcherServlet(DispatcherServlet dispatcherServlet) {
        this.dispatcherServlet = dispatcherServlet;
    }
}
