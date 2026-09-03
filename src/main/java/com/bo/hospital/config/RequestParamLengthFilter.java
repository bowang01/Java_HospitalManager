package com.bo.hospital.config;

import com.bo.hospital.utils.InputLengthValidator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestParamLengthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String contentType = request.getContentType();
        boolean multipart = contentType != null && contentType.toLowerCase().startsWith("multipart/");
        int contentLength = request.getContentLength();
        if (!multipart && contentLength > InputLengthValidator.BODY_BYTES) {
            writeFail(response, "Request body exceeds maximum length");
            return;
        }

        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String[] values = request.getParameterValues(name);
            if (values == null) {
                continue;
            }
            for (String value : values) {
                if (value != null && value.length() > InputLengthValidator.PARAM) {
                    writeFail(response, "Request parameter exceeds maximum length of " + InputLengthValidator.PARAM);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void writeFail(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"status\":400,\"msg\":\"" + message + "\",\"data\":null}");
    }
}
