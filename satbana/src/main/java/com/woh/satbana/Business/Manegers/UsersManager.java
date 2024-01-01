package com.woh.satbana.Business.Manegers;

import com.woh.satbana.Business.Services.UsersSevice;
import com.woh.satbana.DTO.CreateUserDTO;
import com.woh.satbana.Entities.User;
import com.woh.satbana.Repositories.UserRepository;
import com.woh.satbana.Responses.ErrorSuccess;
import org.springframework.stereotype.Service;

@Service
public class UsersManager implements UsersSevice {
    private final UserRepository userRepository;

    public UsersManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean checkUser(CreateUserDTO createUserDTO) {
        User user = null;
        user =  userRepository.findByEmail(createUserDTO.getEmail());
        if(user != null){
            return true;
        }
        return false;
    }

    @Override
    public ErrorSuccess saveUserCreateJWT(CreateUserDTO createUserDTO) {

        ErrorSuccess errorSuccess = new ErrorSuccess();
        if(!checkUser(createUserDTO)){
            errorSuccess.setSuccess("Please check your email and enter the 6-digit number");

            return errorSuccess;
        }
        errorSuccess.setError("The user is already registered in the system. Please try to log in.");
        return errorSuccess;
    }
}
