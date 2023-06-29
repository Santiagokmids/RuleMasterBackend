package com.perficient.ruleMaster.unit.service;

import com.perficient.ruleMaster.dto.UserDTO;
import com.perficient.ruleMaster.error.exception.RuleMasterException;
import com.perficient.ruleMaster.maper.UserMapper;
import com.perficient.ruleMaster.maper.UserMapperImpl;
import com.perficient.ruleMaster.model.RuleMasterUser;
import com.perficient.ruleMaster.repository.UserRepository;
import com.perficient.ruleMaster.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;

    private UserMapper userMapper;

    private UserService userService;

    @BeforeEach
    private void init(){
        userRepository = mock(UserRepository.class);
        userMapper = spy(UserMapperImpl.class);
        userService = new UserService(userRepository, userMapper);
    }

    @Test
    public void testCreateUser(){

        when(userRepository.findByEmail(defaultUser().getEmail())).thenReturn(Optional.empty());

        userService.createUser(defaultUser());

        verify(userRepository, times(1)).save(any());
        verify(userRepository, times(1)).findByEmail(any());
        verify(userMapper, times(1)).fromUserDTO(any());
    }

    @Test
    public void testCreateUserWithNameThatAlreadyExists(){

        UserDTO user = defaultUser();
        RuleMasterUser userToCreate = userMapper.fromUserDTO(user);

        when(userRepository.findByEmail(defaultUser().getEmail())).thenReturn(Optional.of(userToCreate));

        var exception = assertThrows(RuleMasterException.class, () -> userService.createUser(user));

        assertEquals(409, exception.getStatus().value());
        assertEquals("User with email "+user.getEmail()+" already exists", exception.getMessage());
    }

    @Test
    public void testGetUser(){

        UserDTO userToCreate = defaultUser();
        RuleMasterUser user = userMapper.fromUserDTO(userToCreate);
        when(userRepository.findByEmail(defaultUser().getEmail())).thenReturn(Optional.of(user));

        UserDTO userToGet = userService.getUser(user.getEmail());

        assertEquals("John", userToGet.getName());
        assertEquals("Doe", userToGet.getLastName());
        assertEquals("johndoe@email.com", userToGet.getEmail());
        assertEquals("Admin", userToGet.getRole());
    }

    @Test
    public void testGetUserThatNotExists(){

        when(userRepository.findByEmail(defaultUser().getEmail())).thenReturn(Optional.empty());

        var exception = assertThrows(RuleMasterException.class, () -> userService.getUser(defaultUser().getEmail()));

        assertEquals(404, exception.getStatus().value());
        assertEquals("User with email "+defaultUser().getEmail()+" not exists", exception.getMessage());
    }

    private UserDTO defaultUser(){
        return UserDTO.builder()
                .name("John")
                .lastName("Doe")
                .email("johndoe@email.com")
                .password("password")
                .role("Admin")
                .build();
    }

}
