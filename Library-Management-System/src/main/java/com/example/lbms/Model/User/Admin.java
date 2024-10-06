package com.example.lbms.Model.User;

import com.example.lbms.Utils.Enum.UserType;

public class Admin extends User{
    public Admin(){
        super(UserType.ADMIN);
    }
}
