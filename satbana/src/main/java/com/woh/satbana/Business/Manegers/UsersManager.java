package com.woh.satbana.Business.Manegers;

import com.woh.satbana.Business.Services.JWTService;
import com.woh.satbana.Business.Services.UsersSevice;
import com.woh.satbana.DTO.CreateUserDTO;
import com.woh.satbana.Entities.User;
import com.woh.satbana.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersManager implements UsersSevice {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public boolean checkUserForSignUp(CreateUserDTO createUserDTO) {
        User user = this.findByEmail(createUserDTO.getEmail()).orElseGet(()->null);
        if(user != null){
            return true;
        }
        return false;
    }
    public boolean checkUserForLogin(CreateUserDTO createUserDTO){
        User user = this.findByEmail(createUserDTO.getEmail()).orElseGet(()->null);
        if(user != null){
            if(passwordEncoder.matches(createUserDTO.getPassword(),user.getPassword())){
                return true;
            }
        }
        return false;
    }
    @Override
    public User saveUser(CreateUserDTO createUserDTO) {
        User user = mapper.map(createUserDTO,User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(UUID.fromString(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
