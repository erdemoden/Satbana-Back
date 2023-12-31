package com.woh.satbana.Controllers;

import com.woh.satbana.DTO.CreateUserDTO;
import com.woh.satbana.Entities.User;
import com.woh.satbana.Responses.ErrorSuccess;
import com.woh.satbana.Services.JWTService;
import com.woh.satbana.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    @PostMapping("/signUp")
    public ErrorSuccess createUser(@RequestBody CreateUserDTO createUserDTO){
        return userService.saveUserCreateJWT(createUserDTO);
    }

}
