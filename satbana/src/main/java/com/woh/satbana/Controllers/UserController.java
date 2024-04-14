package com.woh.satbana.Controllers;


import com.woh.satbana.Business.Services.UsersSevice;
import com.woh.satbana.DTO.CreateUserDTO;
import com.woh.satbana.Entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UsersSevice usersService;

    @PostMapping("/save")
    public void saveUser(@RequestBody CreateUserDTO createUserDTO){

    }
    @GetMapping("/getUserById")
    public User getUserById(@RequestParam("id")String id){
        Optional<User> user = usersService.getUserById(id);
        if(!user.isEmpty()){
            return user.get();
        }
        return null;
    }
    @GetMapping("/hello")
    public String hello(){
        return  "Hello World";
    }

}
