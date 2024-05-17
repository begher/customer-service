package com.begh.customerservice.auth;

import com.begh.customerservice.service.CustomerService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class FirebaseTokenFilter extends OncePerRequestFilter {
    CustomerService customerService;
    public FirebaseTokenFilter(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String idToken = authorizationHeader.substring(7);
            try {
                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
                boolean isNewCustomer = customerService.addCustomerIfNotExist(decodedToken.getEmail(), request);
                FirebaseAuthenticationToken authentication = new FirebaseAuthenticationToken(decodedToken.getUid(), idToken,
                        authorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                response.setHeader("New-Customer", String.valueOf(isNewCustomer));

            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
