package com.example.demo.config;

import com.example.demo.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(MemberRepository memberRepository) {
        return username -> {
            var member = memberRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException(username + " 없는 사용자입니다"));
            return User.builder()
                    .username(username)
                    .password(member.getPassword())
                    .authorities(member.getAuthority()).build();
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/product/list").authenticated()
                        .requestMatchers("/product/add", "/product/edit", "/product/delete").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/css/**", "/js/**", "/image/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .build();
    }

}
