package com.korniykom.testtask.configuration

import com.korniykom.testtask.repository.UserRepository
import com.korniykom.testtask.ui.login.LoginView
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationSuccessHandler


@EnableWebSecurity
@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .with(VaadinSecurityConfigurer.vaadin()) { configurer ->
                configurer.loginView(LoginView::class.java)
            }

        return http.build()
    }
}

