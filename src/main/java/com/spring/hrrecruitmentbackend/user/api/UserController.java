package com.spring.hrrecruitmentbackend.user.api;

import com.spring.hrrecruitmentbackend.user.exceptions.EmptyUserListException;
import com.spring.hrrecruitmentbackend.user.model.response.UserResponse;
import com.spring.hrrecruitmentbackend.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

    private final UsersService usersService;

    @RequestMapping("/users")
    public List<UserResponse> getUsersList() {
        if (usersService.getAllUsers().isEmpty()) {
            throw new EmptyUserListException();
        }
        return usersService.getAllUsers();
    }
}
