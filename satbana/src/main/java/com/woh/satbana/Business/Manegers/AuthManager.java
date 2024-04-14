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
    public ErrorSuccess checkUserForSignUpSendMail(CreateUserDTO createUserDTO) {
        ErrorSuccess errorSuccess = new ErrorSuccess();
        if(!usersSevice.checkUserForSignUp(createUserDTO)){
            errorSuccess.setSuccess("Please check your email and enter the 6-digit number");
            mailServiceClient.sendCodeMail(createUserDTO.getEmail());
            return errorSuccess;
        }
        errorSuccess.setError("The user is already registered in the system. Please try to log in.");
        return errorSuccess;
    }
    public ErrorSuccess checkUserForLoginSendMail(CreateUserDTO createUserDTO){
        ErrorSuccess errorSuccess = new ErrorSuccess();
        if(usersSevice.checkUserForLogin(createUserDTO)){
            errorSuccess.setSuccess("Please check your email and enter the 6-digit number");
            mailServiceClient.sendCodeMail(createUserDTO.getEmail());
            return errorSuccess;
        }
        errorSuccess.setError("The user is not registered in the system. Please try to sign up.");
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

    @Override
    public ErrorSuccess checkMailForLogin(CreateUserDTO createUserDTO) throws NoSuchAlgorithmException, NoSuchProviderException {
        ErrorSuccess errorSuccess = new ErrorSuccess();
        if(mailServiceClient.checkCodeMail(createUserDTO.getEmail(),createUserDTO.getMailCode()).getBody()){
            errorSuccess.setSuccess("User Created Successfully");
            User user = usersSevice.findByEmail(createUserDTO.getEmail()).orElseGet(()->null);
            if(user!=null) {
                errorSuccess.setJwt(jwtService.generateJwtToken(user.getId()));
                return errorSuccess;
            }
            else {
                errorSuccess.setError("The user is not registered in the system. Please try to sign up.");
                return errorSuccess;
            }
        }
        errorSuccess.setError("The Code You Send Is Wrong ");
        return errorSuccess;
    }

}
