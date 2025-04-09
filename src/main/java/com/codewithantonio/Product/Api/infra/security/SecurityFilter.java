package com.codewithantonio.Product.Api.infra.security;

import com.codewithantonio.Product.Api.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UsuarioRepository userRepository;

    private final List<String> publicPaths = List.of("/auth/login", "/auth/register");

    private boolean isPublic(HttpServletRequest request) {
        return publicPaths.contains(request.getRequestURI()) && request.getMethod().equals("POST");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        if (isPublic(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = recoverToken(request);
        if (isInvalidToken(token)) {
            sendUnauthorizedResponse(response, mapper, "Token não enviado.");
            return;
        }

        String subject = tokenService.validateToken(token);
        if (isInvalidSubject(subject)) {
            sendUnauthorizedResponse(response, mapper, "Token inválido ou expirado.");
            return;
        }

        UserDetails user = userRepository.findByEmail(subject);
        if (user == null) {
            sendUnauthorizedResponse(response, mapper, "Usuário não encontrado.");
            return;
        }

        setAuthentication(user);
        filterChain.doFilter(request, response);
    }

    private boolean isInvalidToken(String token) {
        return token == null || token.isBlank();
    }

    private boolean isInvalidSubject(String subject) {
        return subject == null || subject.isBlank();
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, ObjectMapper mapper, String message)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(Map.of("message", message)));
    }

    private void setAuthentication(UserDetails user) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}