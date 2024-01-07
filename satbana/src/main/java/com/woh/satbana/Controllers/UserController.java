package com.woh.satbana.Controllers;


import com.woh.satbana.Business.Services.UsersSevice;
import com.woh.satbana.DTO.CreateUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UsersSevice usersService;

    @PostMapping("/save")
    public void saveUser(@RequestBody CreateUserDTO createUserDTO){

    }
    @GetMapping("/hello")
    public String hello(){
        return  "Hello World";
    }

}
