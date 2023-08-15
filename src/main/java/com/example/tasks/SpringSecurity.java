package com.example.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HttpSecurity user =
        http.authorizeHttpRequests((authorize) -> authorize.
                    requestMatchers("/register/**").permitAll()
                  .requestMatchers("/").permitAll()
                   .requestMatchers("/tasks").permitAll().
                                    requestMatchers("/add").hasRole("USER").
        requestMatchers("/completed").hasRole("USER").
        requestMatchers("/Update")
        .hasRole("USER").requestMatchers("/delete").hasRole("USER").
        requestMatchers("/category").hasRole("USER").
        requestMatchers("/search").hasRole("USER").
        requestMatchers("/categorizied").hasRole("USER").
                        requestMatchers("/get-task/{taskId}").hasRole("USER")
                        .requestMatchers("/profile").hasRole("USER")
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/loginsave")
                               .defaultSuccessUrl("/tasks")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );


        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}


//                    .requestMatchers("/add").hasRole("USER").
//                  requestMatchers("/completed") .hasRole("USER").
//                    requestMatchers("/update")
//                    .hasRole("USER").requestMatchers("/delete").hasRole("USER").
//                    requestMatchers("/category").hasRole("USER").
//                    requestMatchers("/search").hasRole("USER").
//                    requestMatchers("/categorizied").hasRole("USER")

////////

//                    requestMatchers("/add").hasRole("USER").
//        requestMatchers("/completed").hasRole("USER").
//        requestMatchers("/update")
//        .hasRole("USER").requestMatchers("/delete").hasRole("USER").
//        requestMatchers("/category").hasRole("USER").
//        requestMatchers("/search").hasRole("USER").
//        requestMatchers("/categorizied").hasRole("USER")