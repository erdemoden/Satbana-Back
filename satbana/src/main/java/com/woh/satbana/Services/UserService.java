package com.woh.satbana.Services;

import com.woh.satbana.DTO.CreateUserDTO;
import com.woh.satbana.Entities.User;
import com.woh.satbana.Repositories.UserRepository;
import com.woh.satbana.Responses.ErrorSuccess;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
private final UserRepository userRepository;
private final JWTService jwtService;
private final ModelMapper mapper;
public ErrorSuccess saveUserCreateJWT(CreateUserDTO createUserDTO){
    ErrorSuccess errorSuccess = new ErrorSuccess();
    if(!checkUser(createUserDTO)){
        errorSuccess.setSuccess("Please check your email and enter the 6-digit number");

        return errorSuccess;
    }
    errorSuccess.setError("The user is already registered in the system. Please try to log in.");
    return errorSuccess;
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
