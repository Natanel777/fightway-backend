package spring.natanel.fightwaybackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@RequiredArgsConstructor
public class StoreSecurityConfig {

    private final JwtAuthenticationFilter filter;
    @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            var configuration = new CorsConfiguration();

            //configuration.setAllowedOriginPatterns(
            // List.of("http://localhost:3000", "http://localhost:8080", "http://127.0.0.1:3000")
            //);
            configuration.setAllowedOriginPatterns(List.of("*"));

            //configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
            configuration.setAllowedMethods(List.of("*"));

            //configuration.setAllowedHeaders(List.of(HttpHeaders.AUTHORIZATION));
            configuration.setAllowedHeaders(List.of("*"));

            var source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);

            return source;
        }
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception{
        return http
                .cors(Customizer.withDefaults())
                .addFilterBefore(filter, BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->{
                    auth.requestMatchers("/api/v1/auth/**").permitAll(); //not secured
                    auth.requestMatchers(HttpMethod.GET,"/api/v1/**").permitAll();
                    auth.requestMatchers("/api/v1/**").authenticated();//secured
                    auth.anyRequest().permitAll(); //anything else not secured (Must Be Last! there is importance to order)
                })
                .httpBasic(basic-> basic.authenticationEntryPoint(new StoreBasicAuthenticationEntryPoint())).build();
    }
}
