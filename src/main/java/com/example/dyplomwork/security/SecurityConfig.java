package com.example.dyplomwork.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authorization.AuthorizationDecision;

import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Відключення CSRF (якщо потрібно)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll() // Відкрита зона
                        .requestMatchers("/admin/**").access((authenticationSupplier, context) ->
                                new AuthorizationDecision(isAdmin(authenticationSupplier))
                        ) // Перевірка адміністратора
                        .anyRequest().authenticated() // Усі інші запити потребують авторизації
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(this.oidcUserService()) // Кастомний OIDC User Service
                        )
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // URL для виходу з акаунту
                        .logoutSuccessUrl("/login") // Перенаправлення після успішного виходу
                        .invalidateHttpSession(true) // Видалення сесії
                        .clearAuthentication(true)  // Очищення автентифікації
                );

        return http.build();
    }

    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
                OidcUser oidcUser = super.loadUser(userRequest);

                // Отримання email користувача
                String email = oidcUser.getAttribute("email");
                if (email == null || !email.endsWith("@ukd.edu.ua")) {
                    throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token"),
                            "Доступ заборонено: лише для студентів із доменом @ukd.edu.ua");
                }

                return oidcUser;
            }
        };
    }

    private static boolean isAdmin(Supplier<Authentication> authenticationSupplier) {
        Authentication authentication = authenticationSupplier.get();
        if (authentication == null || authentication.getName() == null) {
            return false;
        }
        return authentication.getName().equals("oleksandr.v.basarab@ukd.edu.ua");
    }
}
