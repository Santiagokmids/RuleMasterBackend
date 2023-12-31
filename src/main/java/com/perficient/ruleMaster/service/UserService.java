package com.perficient.ruleMaster.service;

import com.perficient.ruleMaster.dto.UserDTO;
import com.perficient.ruleMaster.error.exception.DetailBuilder;
import com.perficient.ruleMaster.error.exception.ErrorCode;
import com.perficient.ruleMaster.maper.UserMapper;
import com.perficient.ruleMaster.model.RuleMasterUser;
import com.perficient.ruleMaster.repository.UserRepository;
import com.perficient.ruleMaster.security.RuleMasterSecurityContext;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.perficient.ruleMaster.error.util.RuleMasterExceptionBuilder.createRuleMasterException;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UserDTO createUser(UserDTO userDTO){

        verifyUserByEmail(userDTO.getEmail());

        RuleMasterUser user = userMapper.fromUserDTO(userDTO);

        user.setUserId(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.fromUser(userRepository.save(user));
    }

    private void verifyUserByEmail(String email){

        if (userRepository.findByEmail(email).isPresent()){
            throw createRuleMasterException(
                    "Email duplicated",
                    HttpStatus.CONFLICT,
                    new DetailBuilder(ErrorCode.ERR_DUPLICATED, "User with email", email)
            ).get();
        }
    }

    public UserDTO getUser(String email) {

        if (userRepository.findByEmail(email).isEmpty()){
            throw createRuleMasterException(
                    "User not found",
                    HttpStatus.NOT_FOUND,
                    new DetailBuilder(ErrorCode.ERR_404, "User with email", email)
            ).get();
        }
        return userMapper.fromUser(userRepository.findByEmail(email).get());
    }

    public List<UserDTO> getAllUsers() {

        return userRepository.findAll().stream()
                .map(userMapper::fromUser)
                .toList();
    }

    public UserDTO getCurrentUser(){
        return userMapper.fromUser(userRepository.findById(UUID.fromString(RuleMasterSecurityContext.getCurrentUserId())).get());
    }
}
