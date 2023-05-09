//package com.isc.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class MyBasicConfiguration {
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(8);
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((requests) -> requests
//                .requestMatchers("/course/add").hasRole("USER")
//                .requestMatchers("/course/add").hasRole("ADMIN")
//                .requestMatchers("/course/loadAll").permitAll()
//                .anyRequest().authenticated()
//        );
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withUsername("user")
//                        .password(passwordEncoder().encode("password"))
//                        .roles("USER")
//                        .build();
//        User.withUsername("admin")
//                .password("admin")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//}