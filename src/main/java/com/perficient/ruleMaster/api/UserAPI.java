package com.perficient.ruleMaster.api;

import com.perficient.ruleMaster.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserAPI {

    String BASE_USER_URL = "/users";

    @PostMapping
    UserDTO createUser(@RequestBody UserDTO userDTO);

    @GetMapping("/{userEmail}")
    UserDTO getUser(@PathVariable String userEmail);

    @GetMapping
    List<UserDTO> getAllUsers();
}
