package com.woh.satbana.Controllers;

import com.woh.satbana.Business.Services.UsersSevice;
import com.woh.satbana.DTO.CreateUserDTO;
import com.woh.satbana.Responses.ErrorSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsersSevice usersService;
    @PostMapping("/signUp")
    public ErrorSuccess createUser(@RequestBody CreateUserDTO createUserDTO){
        return usersService.saveUserCreateJWT(createUserDTO);
    }

}
