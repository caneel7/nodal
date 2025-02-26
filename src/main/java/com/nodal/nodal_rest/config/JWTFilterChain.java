package com.nodal.nodal_rest.config;

import com.nodal.nodal_rest.service.CustomUserDetailService;
import com.nodal.nodal_rest.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilterChain extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomUserDetailService userDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {

        final String header = request.getHeader("Authorization");
        String username,token;

        if(header != null && header.startsWith("Bearer "))
        {
            try{

                token = header.split(" ")[1];

                username = jwtService.extractUsername(token);

                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
                {

                    UserDetails userDetails = userDetailService.loadUserById(Integer.parseInt(username));
                    boolean tokenValid = jwtService.isTokenValid(token,userDetails);
                    if(tokenValid)
                    {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }

            }catch (Exception e){
                throw new ServletException(e.getMessage());
            }
        }

        filterChain.doFilter(request,response);

    }
}
