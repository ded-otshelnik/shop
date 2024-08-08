package com.example.shop.config;

import com.example.shop.controller.CartController;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class.getName());

    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");

        String login = null, jwt = null;
        logger.info("Getting login from JWT Token");
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            jwt = authHeader.substring(7);
            try{
                login = jwtTokenService.extractUserName(jwt);
            }
            catch (ExpiredJwtException e){
                logger.debug("The token is expired");
            }
        }
        logger.info("Setting Authentication token into context");
        if (login != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(login);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userDetails.getAuthorities(),
                    userDetails.getUsername());
            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        filterChain.doFilter(request, response);
    }
}
