package com.perficient.ruleMaster.service;

import com.perficient.ruleMaster.dto.UserDTO;
import com.perficient.ruleMaster.maper.UserMapper;
import com.perficient.ruleMaster.model.RuleMasterUser;
import com.perficient.ruleMaster.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserDTO createUser(UserDTO userDTO){

        verifyUserByEmail(userDTO.getEmail());

        RuleMasterUser user = userMapper.fromUserDTO(userDTO);

        user.setUserId(UUID.randomUUID());

        return userMapper.fromUser(userRepository.save(user));
    }

    private void verifyUserByEmail(String email){

        if (userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("User with email "+email+" already exists");
        }
    }

    public UserDTO getUser(String email){

        return userMapper.fromUser(userRepository.findByEmail(email).
                orElseThrow(() -> new RuntimeException("User with email "+email+" not exists")));
    }

    public List<UserDTO> getAllUsers() {

        return userRepository.findAll().stream()
                .map(userMapper::fromUser)
                .toList();
    }
}
