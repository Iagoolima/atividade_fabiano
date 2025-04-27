package com.autenticacao.app.config.security;

import com.autenticacao.app.config.service.JwtServiceImpl;
import com.autenticacao.app.config.service.SecurityUserDetailsService;
import com.autenticacao.app.domain.business.Business;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.split(" ")[1];

            try {
                if (jwtService.isTokenValidated(token)) {
                    String login = jwtService.getLoginUser(token);
                    String typeToken = jwtService.getRefreshToken(token);

                    CustomUserDetails userAuth = (CustomUserDetails) userDetailService.loadUserByUsername(login);
                    if (userAuth != null) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userAuth, null, userAuth.getAuthorities()
                        );
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        if(typeToken.equals("refresh"))
                            Business.init(userAuth.getUser(), jwtService.generateToken(userAuth.getUser()), typeToken);

                        Business.init(userAuth.getUser(), null, typeToken);
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Token invalido\"}");
                    return;
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Erro ao processar token\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}