package com.stc.exporter.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            int userIdx = jwtService.extractUserIdx(token);

            if (jwtService.validateToken(token, userIdx)) {
                authenticateUser(userIdx, request);

            } else if (jwtService.isTokenExpired(token)) {
                logger.info("Token Expired");
                String refreshToken = jwtService.getRefreshToken(request);

                if (refreshToken != null) {
                    jwtService.checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
                    authenticateUser(userIdx, request);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateUser(int userIdx, HttpServletRequest request) {
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUserIdx(userIdx);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
