package com.filtro.FILTRO_SPRINGBOOT.configuration.security;

import com.filtro.FILTRO_SPRINGBOOT.configuration.security.filter.JwtAuthenticationFilter;
import com.filtro.FILTRO_SPRINGBOOT.configuration.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class SpringSecurityConfig {

    //CONFIGURACION DEL FILTRO DEL JWT
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    //METODO QUE MANEJARA LA CONFUGRUACION DEL JWT
    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //CREAMOS UN COMPONENTE PARA ENCRIPTAR CONTRASEÑAS
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests((auth)->auth

                        .requestMatchers(HttpMethod.GET,"/api/users").permitAll()
                        .requestMatchers( "/v3/api-docs/**",      // Para OpenAPI 3.0
                                "/swagger-ui/**",       // Swagger UI
                                "/swagger-ui.html",     // Página principal de Swagger UI
                                "/swagger-resources/**",
                                "/webjars/**")
                        .permitAll()
                        .anyRequest().authenticated()) //resto de rutas privadas
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(config -> config.disable())
                .cors(cors-> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    //CONFIGURACION DE CORS, SE AÑADE AL FILTRO Y A LA CONFIGURACION DE SECURITY
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET","POST", "DELETE", "PUT", "PATCH")); //Indicamos metodos permitidos
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        //Hacemos la implementacion concreta
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    //CREAMOS COMPONENTE FILTRO QUE SE EJECUTE SIEMPRE CON LA CONFIGUTACION DE ARRIBA
    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }
}
