package com.voinesti.versuriapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                // 1. Permitem accesul la resursele vizuale și pagina de login
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                
                // 2. Protejăm rutele de administrare (trebuie să fie IDENTICE cu cele din Controller)
                .requestMatchers("/adauga", "/salveaza", "/edit/**", "/delete/**").hasRole("ADMIN")
                
                // 3. Toate celelalte pagini (lista, detaliile) cer logare (USER sau ADMIN)
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout") // URL-ul pe care îl apelează butonul nostru
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // ADMIN: Tu (Flavius) - ai acces la editare/ștergere
        UserDetails admin = User.builder()
            .username("flavius")
            .password(passwordEncoder().encode("1234")) 
            .roles("ADMIN")
            .build();

        // USER: Membrii corului - pot doar să vadă versurile
        UserDetails user = User.builder()
            .username("membru")
            .password(passwordEncoder().encode("1234"))
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}