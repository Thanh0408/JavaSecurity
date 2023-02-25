package vn.com.thanhlq.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MultipleEntryPointsSecurityConfig {

    @Value("${logging.info.admin.username}")
    private String admin;

    @Value("${logging.info.admin.password}")
    private String passAdmin;

    @Value("${logging.info.user.username}")
    private String user;

    @Value("${logging.info.user.password}")
    private String passUser;

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
                .withUsername(user)
                .password(encoder().encode(passUser))
                .roles("USER").build());
        manager.createUser(User
                .withUsername(admin)
                .password(encoder().encode(passAdmin))
                .roles("ADMIN", "USER").build());
        return manager;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests(
                    autho->autho
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .antMatchers("/api/v1/**").hasRole("USER")
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
