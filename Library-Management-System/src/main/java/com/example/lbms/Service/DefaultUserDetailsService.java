package com.example.lbms.Service;

import com.example.lbms.Model.User.User;
import com.example.lbms.Model.User.UserPrincipal;
import com.example.lbms.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        try {
            User user = userRepository.findByUserName(username);

            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            return new UserPrincipal(user);
        } catch(Exception e){
            System.out.println("Exception Occurred: " + e.getMessage());
            return null;
        }
    }
}
