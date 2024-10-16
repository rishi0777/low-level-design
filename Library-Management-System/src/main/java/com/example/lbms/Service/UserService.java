package com.example.lbms.Service;

import com.example.lbms.Model.User.User;
import com.example.lbms.Repository.UserRepository;
import com.example.lbms.Utils.DTO.BookCopyDTO;
import com.example.lbms.Utils.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    public boolean registerUser(User user){
        try {
            User fetchedUser = userRepository.findByUserName(user.getUserName());

            if (fetchedUser != null) {
                throw new Exception("Username already exists");
            }

            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.saveAndFlush(user);
            return  true;
        } catch (Exception e){
            System.out.println("Register User: " + e.getMessage());
            return false;
        }
    }

    public String loginUser(UserDTO userDto){
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDto.getUserName(),userDto.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(userDto.getUserName());
        }

        return "Login Unsuccessful";
    }
}
