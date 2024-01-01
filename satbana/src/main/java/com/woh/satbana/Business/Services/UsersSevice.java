package com.woh.satbana.Business.Services;

import com.woh.satbana.DTO.CreateUserDTO;
import com.woh.satbana.Responses.ErrorSuccess;

public interface UsersSevice {
    boolean checkUser(CreateUserDTO createUserDTO);
    ErrorSuccess saveUserCreateJWT(CreateUserDTO createUserDTO);

}
