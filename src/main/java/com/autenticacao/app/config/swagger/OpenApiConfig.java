package com.autenticacao.app.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        List<Tag> tags = Arrays.asList(
                new Tag()
                        .name("Public API")
                        .description("""
                                Endpoints públicos (não exigem autenticação).
                                Inclui:
                                - Login
                                - Registro de usuário
                                - Recuperação de senha
                                """),
                new Tag()
                        .name("Private API")
                        .description("""
                                Endpoints privados (exigem autenticação JWT).
                                Inclui:
                                - Verificação de perfil de usuário/admin
                                - Exclusão de usuário
                                """),
                new Tag()
                        .name("register-controller")
                        .description("Controle de registro de novos usuários (público)"),
                new Tag()
                        .name("forgot-password-controller")
                        .description("Controle de recuperação de senha (público)"),
                new Tag()
                        .name("login-controller")
                        .description("Controle de login de usuário (público)"),
                new Tag()
                        .name("verify-controller")
                        .description("Verificação de permissões de usuários (privado)"),
                new Tag()
                        .name("user-controller")
                        .description("Controle de usuários (privado)")
        );

        SecurityScheme bearerAuthScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        return new OpenAPI()
                .info(new Info()
                        .title("Autentica ai Fabiano - API Documentation")
                        .version("1.0")
                        .description("""
                                # Sumário
                                Esta documentação descreve os endpoints da API de autenticação.

                                ## Público
                                - Login de usuário
                                - Registro de usuário
                                - Recuperação de senha

                                ## Privado (Autenticação JWT necessária)
                                - Verificação de papéis de usuário
                                - Administração de usuários

                                **Autenticação:**  
                                Os endpoints privados requerem Bearer Token JWT no cabeçalho Authorization.
                                """)
                        .contact(new Contact()
                                .name("Iago e companhia(Mateus Teixeira e João Godoi")
                                .email("meda10ai@porfavor.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .tags(tags)
                .addSecurityItem(securityRequirement)
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("Bearer Authentication", bearerAuthScheme));
    }
}