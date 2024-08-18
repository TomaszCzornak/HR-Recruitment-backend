package com.spring.hrrecruitmentbackend.Utils;

import com.spring.hrrecruitmentbackend.user.model.entity.User;
import com.spring.hrrecruitmentbackend.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


import java.util.Objects;

@Component
@AllArgsConstructor
public class UserContextHelper {

    private final UserRepository users;

    public static User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.equals(authentication.getPrincipal(), "anonymousUser")) {
            return User.builder().build();
        } else {
            return (User) authentication.getPrincipal();
        }
    }

}
