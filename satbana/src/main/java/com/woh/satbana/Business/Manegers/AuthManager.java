package com.woh.satbana.Business.Manegers;

import com.woh.satbana.Business.Services.AuthService;
import com.woh.satbana.Business.Services.JWTService;
import com.woh.satbana.Business.Services.UsersSevice;
import com.woh.satbana.Client.MailServiceClient;
import com.woh.satbana.DTO.CreateUserDTO;
import com.woh.satbana.Entities.User;
import com.woh.satbana.Responses.ErrorSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Service
@RequiredArgsConstructor
public class AuthManager implements AuthService {

    private final UsersSevice usersSevice;
    private final JWTService jwtService;
    private final MailServiceClient mailServiceClient;
    @Override
    public ErrorSuccess checkUserSendMail(CreateUserDTO createUserDTO) {
        ErrorSuccess errorSuccess = new ErrorSuccess();
        if(!usersSevice.checkUser(createUserDTO)){
            errorSuccess.setSuccess("Please check your email and enter the 6-digit number");
            mailServiceClient.sendCodeMail(createUserDTO.getEmail());
            return errorSuccess;
        }
        errorSuccess.setError("The user is already registered in the system. Please try to log in.");
        return errorSuccess;
    }

    @Override
    public ErrorSuccess checkMailSaveUser(CreateUserDTO createUserDTO) throws NoSuchAlgorithmException, NoSuchProviderException {
        ErrorSuccess errorSuccess = new ErrorSuccess();
        if(mailServiceClient.checkCodeMail(createUserDTO.getEmail(),createUserDTO.getMailCode()).getBody()){
            errorSuccess.setSuccess("User Created Successfully");
            User user = usersSevice.saveUser(createUserDTO);
            errorSuccess.setJwt(jwtService.generateJwtToken(user.getId()));
            return errorSuccess;
        }
        errorSuccess.setError("The Code You Send Is Wrong ");
        return errorSuccess;
    }
}
