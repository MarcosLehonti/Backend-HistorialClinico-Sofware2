package com.HistorialClinico.Backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Agregar los orígenes permitidos: localhost y Vercel
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "https://frontend-historial-clinico.vercel.app"));

        // Métodos HTTP permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Cabeceras permitidas en las solicitudes
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin"));

        // Exponer cabeceras que pueden ser leídas en las respuestas
        configuration.setExposedHeaders(List.of("Authorization", "Content-Disposition"));

        // Permitir credenciales como cookies o tokens
        configuration.setAllowCredentials(true);

        // Aplicar la configuración a todas las rutas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Activa CORS
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF solo para pruebas
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/api/usuarios/**").permitAll() // Permitir todas las rutas de usuarios
                        //.requestMatchers("/api/usuarios/login").permitAll()
                        .requestMatchers("/api/usuarios/**").permitAll() // Permitir registro y login
                        .requestMatchers("/api/especialidades/**", "/api/horarios/**").permitAll() // Permitir especialidades y horarios
                        .requestMatchers("/api/roles/**").permitAll() // Permitir registro y login
                        .requestMatchers("/api/horarios").permitAll() // Permitir especialidades y horarios
                        .requestMatchers("/api/asignaciones/**").permitAll()//permitir acceso a todos los usuarios
                        .requestMatchers("/api/citas/**").permitAll()// permitir el acceso a hoarios para todos
                        .requestMatchers("/api/triajes/**").permitAll()
                        .requestMatchers("/api/antecedentes/**").permitAll()
                        .requestMatchers("/api/diagnosticos/**").permitAll()
                        .requestMatchers("/api/bitacora/**").permitAll()
                        .requestMatchers("/api/medicos/**").permitAll()//permitir las rutas de medicos a todos
                        .requestMatchers("/api/usuarios/perfil").authenticated() // Solo usuarios autenticados pueden acceder al perfil
                        .anyRequest().authenticated() // Cualquier otra solicitud requiere autenticación

                );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
