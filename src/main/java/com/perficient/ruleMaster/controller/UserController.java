package com.perficient.ruleMaster.controller;

import com.perficient.ruleMaster.api.UserAPI;
import com.perficient.ruleMaster.dto.UserDTO;
import com.perficient.ruleMaster.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.perficient.ruleMaster.api.UserAPI.BASE_USER_URL;

@RestController
@RequestMapping(BASE_USER_URL)
@AllArgsConstructor
public class UserController implements UserAPI {

    private final UserService userService;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @Override
    public UserDTO getUser(String userEmail) {
        return userService.getUser(userEmail);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }
}
