package com.autenticacao.app.config.security;

import com.autenticacao.app.config.service.JwtServiceImpl;
import com.autenticacao.app.config.service.SecurityUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtService;

    private final SecurityUserDetailsService userDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer")) {
            String token = authorization.split(" ")[1];

            if (jwtService.isTokenValido(token)) {
                String login = jwtService.obterLoginUsuario(token);

                var userAuth = userDetailService.loadUserByUsername(login);
                if (userAuth != null) {
                    UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userAuth, null,
                            userAuth.getAuthorities());
                    user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(user);
                }
            }
            else {
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().write("{\"error\": \"Invalid token\"}");
                return;
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
