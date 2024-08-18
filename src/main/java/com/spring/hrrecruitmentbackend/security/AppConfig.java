package com.spring.hrrecruitmentbackend.security;

import com.spring.hrrecruitmentbackend.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

@RequiredArgsConstructor
public class AppConfig {

    private final UsersService usersService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(usersService);
    }

}
