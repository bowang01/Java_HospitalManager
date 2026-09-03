package com.bo.hospital.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.bo.hospital.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,Object> map = new HashMap<>();
        // Get the token from the request header
        String token = request.getHeader("token");
        try {
            JwtUtil.verify(token);// verify token
            return true;
        }catch (SignatureVerificationException e){
            e.printStackTrace();
            map.put("msg", "Invalid signature");
        }catch (TokenExpiredException e){
            e.printStackTrace();
            map.put("msg", "Token expired");
        }catch (AlgorithmMismatchException e){
            e.printStackTrace();
            map.put("msg", "Token algorithm mismatch");
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg", "Invalid token");
        }
        map.put("state", false);
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
