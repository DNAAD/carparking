package com.coalvalue.configuration.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessDeniedExceptionFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                 FilterChain fc) throws ServletException, IOException {
        try {
            fc.doFilter(req, res);
        } catch (AccessDeniedException e) {
            // log error if needed here then redirect
/*     RequestDispatcher requestDispatcher =
             getServletContext().getRequestDispatcher(redirecturl);
     requestDispatcher.forward(request, response);*/
        }

    }
}