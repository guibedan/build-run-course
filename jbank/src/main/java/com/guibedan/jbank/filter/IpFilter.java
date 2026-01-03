package com.guibedan.jbank.filter;

import com.guibedan.jbank.constants.HeadersConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class IpFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var ipAddress = request.getRemoteAddr();
        response.setHeader(HeadersConstants.USER_IP_HEADER, ipAddress);
        request.setAttribute(HeadersConstants.USER_IP_HEADER, ipAddress);

        chain.doFilter(request, response);
    }

}
