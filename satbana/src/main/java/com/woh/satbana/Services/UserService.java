package com.woh.satbana.Services;

import com.woh.satbana.DTO.CreateUserDTO;
import com.woh.satbana.Entities.User;
import com.woh.satbana.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
private final UserRepository userRepository;

public void saveUser(CreateUserDTO createUserDTO){
    if(checkUser(createUserDTO)){

    }
    User user = new User();
    user.setEmail(createUserDTO.getEmail());
    user.setUsername(createUserDTO.getUsername());
    userRepository.save(user);
}
public boolean checkUser(CreateUserDTO createUserDTO){
    User user = null;
   user =  userRepository.findByEmail(createUserDTO.getEmail());
   if(user != null){
       return true;
   }
   return false;
}

}
