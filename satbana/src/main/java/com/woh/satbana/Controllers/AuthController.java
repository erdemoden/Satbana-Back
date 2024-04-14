package com.woh.satbana.Controllers;

import com.woh.satbana.Business.Services.AuthService;
import com.woh.satbana.Business.Services.UsersSevice;
import com.woh.satbana.Client.MailServiceClient;
import com.woh.satbana.DTO.CreateUserDTO;
import com.woh.satbana.Responses.ErrorSuccess;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsersSevice usersService;
    private final AuthService authService;
    @PostMapping("/signUp")
    public ErrorSuccess sendMail(@RequestBody CreateUserDTO createUserDTO){
        return authService.checkUserForSignUpSendMail(createUserDTO);
    }
    @SneakyThrows
    @PostMapping("/checkMailSave")
    public ErrorSuccess createUser(@RequestBody CreateUserDTO createUserDTO){
        return authService.checkMailSaveUser(createUserDTO);
    }

    @PostMapping("/login")
    public ErrorSuccess login(@RequestBody CreateUserDTO createUserDTO){
        return authService.checkUserForLoginSendMail(createUserDTO);
    }

    @PostMapping("/checkMailForLogin")
    public ErrorSuccess checkMailForLogin(@RequestBody CreateUserDTO createUserDTO) throws NoSuchAlgorithmException, NoSuchProviderException {
        return authService.checkMailForLogin(createUserDTO);
    }

    @GetMapping("/deneme")
    public String deneme(){
        return "NE VAR LAN PUŞT !";
    }

}
