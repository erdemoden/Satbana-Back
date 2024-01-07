package com.woh.satbana.Business.Services;

import com.woh.satbana.DTO.CreateUserDTO;
import com.woh.satbana.Responses.ErrorSuccess;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public interface AuthService {

    ErrorSuccess checkUserSendMail(CreateUserDTO createUserDTO);
    ErrorSuccess checkMailSaveUser(CreateUserDTO createUserDTO) throws NoSuchAlgorithmException, NoSuchProviderException;
}
