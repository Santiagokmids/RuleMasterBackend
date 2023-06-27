package com.perficient.ruleMaster.api;

import com.perficient.ruleMaster.dto.UserDTO;
import com.perficient.ruleMaster.exceptions.RuleMasterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserAPI {

    String BASE_USER_URL = "/users";

    @PostMapping("/addUser")
    UserDTO createUser(@RequestBody UserDTO userDTO);

    @GetMapping("/{userEmail}")
    UserDTO getUser(@PathVariable String userEmail) throws RuleMasterException;

    @GetMapping("/getUsers")
    List<UserDTO> getAllUsers();
}