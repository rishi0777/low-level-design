package com.example.lbms.Model.User;

import com.example.lbms.Utils.Enum.UserType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name="User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "userName", nullable = false, unique = true)
    private String userName;

    private UserType userType;
    private String password;

    public User(UserType userType){
        this.userType = userType;
    }
}
