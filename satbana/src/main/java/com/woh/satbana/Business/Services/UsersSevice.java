package com.woh.satbana.Business.Services;

import com.woh.satbana.DTO.CreateUserDTO;
import com.woh.satbana.Entities.User;

import java.util.Optional;

public interface UsersSevice {
    boolean checkUserForSignUp(CreateUserDTO createUserDTO);
    boolean checkUserForLogin(CreateUserDTO createUserDTO);
    User saveUser(CreateUserDTO createUserDTO);
    Optional<User> getUserById(String id);
    Optional<User> findByEmail(String email);
}
