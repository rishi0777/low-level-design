package com.example.lbms.Controller;

import com.example.lbms.Model.User.User;
import com.example.lbms.Service.UserService;
import com.example.lbms.Utils.DTO.BookCopyDTO;
import com.example.lbms.Utils.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public boolean registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserDTO userDto){
        return userService.loginUser(userDto);
    }
}
