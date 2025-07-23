package com.welldev.ams.config;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.welldev.ams.repositories.UserRepository;
import com.welldev.ams.service.JWTService;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
  private final JWTService jwtService;
  private final UserRepository authUserRepository;

  public JwtFilter(@Lazy JWTService jwtService, @Lazy UserRepository authUserRepository) {
    this.jwtService = jwtService;
    this.authUserRepository = authUserRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException
  {
//    final String authHeader = request.getHeader("Authorization");
//    final String jwt;
//    final String username;
//
//    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//      filterChain.doFilter(request, response); // If no token, pass to the next filter
//      return;
//    }
//
//    jwt = authHeader.substring(7); // Extract token from "Bearer "
//    username = jwtService.extractUsername(jwt);
//
//    // Check if username is valid and user is not already authenticated
//    if (username != null && SecurityContextHolder.getContext()
//        .getAuthentication() == null)
//    {
//      UserDetails userDetails = userDetailsService().loadUserByUsername(username);
//
//      if (jwtService.validateToken(jwt, userDetails)) {
//        // If token is valid, create an Authentication token and set it in the Security Context
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
//            userDetails.getAuthorities());
//        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext()
//            .setAuthentication(authToken);
//      }
//    }
    filterChain.doFilter(request, response);
  }

  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) {
        return (UserDetails) authUserRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
      }
    };
  }
}