package com.gpcf.expense_tracker_api.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {


        @Autowired
        private JwtService jwtService;


        @Autowired
        private UserDetailsService userDetailsService;


        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {

                String authentication = request.getHeader("Authorization");


                if(authentication == null || !authentication.startsWith("Bearer ")){
                    filterChain.doFilter(request,response);
                    return;
                }


                String token = authentication.substring(7);

                String username = jwtService.extractUsername(token);


                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    List<String> roles = jwtService.extractRoles(token);

                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    authorities
                            );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                filterChain.doFilter(request,response);


        }
    }