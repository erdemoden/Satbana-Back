package com.woh.satbana.Business.Manegers;

import com.woh.satbana.Business.Services.JWTService;
import com.woh.satbana.Business.Services.UsersSevice;
import com.woh.satbana.DTO.CreateUserDTO;
import com.woh.satbana.Entities.User;
import com.woh.satbana.Repositories.UserRepository;
import com.woh.satbana.Responses.ErrorSuccess;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersManager implements UsersSevice {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final JWTService jwtService;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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
    public User saveUser(CreateUserDTO createUserDTO) {
        User user = mapper.map(createUserDTO,User.class);
        user.setJwt(jwtService.generateJwtTokenForDatabase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
