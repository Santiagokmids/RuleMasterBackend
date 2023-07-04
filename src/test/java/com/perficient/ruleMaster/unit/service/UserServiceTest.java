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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;

    private UserMapper userMapper;

    private UserService userService;

    //@Autowired
    //PasswordEncoder passwordEncoder;

    @BeforeEach
    private void init(){
        userRepository = mock(UserRepository.class);
        userMapper = spy(UserMapperImpl.class);
        //passwordEncoder = mock(PasswordEncoder.class);

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

        var error = exception.getRuleMasterError();
        var details = error.getDetails();
        assertEquals(1, details.size());
        var detail = details.get(0);
        assertEquals("ERR_DUPLICATED", detail.getErrorCode(), "Code doesn't match");
        assertEquals("User with email "+ user.getEmail()+" already exists", detail.getErrorMessage(), "Error message doesn't match");
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

        var error = exception.getRuleMasterError();
        var details = error.getDetails();
        assertEquals(1, details.size());
        var detail = details.get(0);
        assertEquals("ERR_404", detail.getErrorCode(), "Code doesn't match");
        assertEquals("User with email "+ defaultUser().getEmail()+" not found", detail.getErrorMessage(), "Error message doesn't match");
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
