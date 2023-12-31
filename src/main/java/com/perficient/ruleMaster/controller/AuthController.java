package com.perficient.ruleMaster.controller;

import com.perficient.ruleMaster.dto.LoginDTO;
import com.perficient.ruleMaster.dto.TokenDTO;
import com.perficient.ruleMaster.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class AuthController {

    private final TokenService tokenService;

    private final AuthenticationManager authenticatorManager;

    @PostMapping("/token")
    public TokenDTO token(@RequestBody LoginDTO loginDTO){

        Authentication authentication = authentication = authenticatorManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password()));;

        return tokenService.generateToken(authentication);
    }
}
