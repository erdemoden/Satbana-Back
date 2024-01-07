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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsersSevice usersService;
    private final AuthService authService;
    @PostMapping("/signUp")
    public ErrorSuccess sendMail(@RequestBody CreateUserDTO createUserDTO){
        return authService.checkUserSendMail(createUserDTO);
    }
    @SneakyThrows
    @PostMapping("/checkMailSave")
    public ErrorSuccess createUser(@RequestBody CreateUserDTO createUserDTO){
        return authService.checkMailSaveUser(createUserDTO);
    }

    @GetMapping("/deneme")
    public String deneme(){
        return "NE VAR LAN PUŞT !";
    }

}
