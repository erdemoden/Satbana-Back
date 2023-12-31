package com.woh.satbana.Controllers;

import com.woh.satbana.DTO.CreateUserDTO;
import com.woh.satbana.Entities.User;
import com.woh.satbana.Services.JWTService;
import com.woh.satbana.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JWTService jwtService;
    private final ModelMapper mapper;
    @PostMapping("/login")
    public void createUser(@RequestBody CreateUserDTO createUserDTO){
        boolean checkUser = userService.checkUser(createUserDTO);
        if (!checkUser){
            User user = mapper.map(createUserDTO,User.class);
        }
    }

}
