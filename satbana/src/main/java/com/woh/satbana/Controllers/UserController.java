package com.woh.satbana.Controllers;

import com.woh.satbana.DTO.CreateUserDTO;
import com.woh.satbana.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    public void saveUser(@RequestBody CreateUserDTO createUserDTO){
    userService.saveUser(createUserDTO);
    }
    @GetMapping("/hello")
    public String hello(){
        return  "Hello World";
    }

}
