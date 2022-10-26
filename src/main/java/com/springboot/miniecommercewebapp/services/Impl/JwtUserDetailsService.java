package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.config.WebSecurityConfig;
import com.springboot.miniecommercewebapp.enums.ERoleName;
import com.springboot.miniecommercewebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        var foundUser = userRepository.findById(userid);
        if (foundUser.isPresent()){
            String roleName = ERoleName.getRoleName(foundUser.get().getRoleId());
            return new User(foundUser.get().getUserId(),
                    WebSecurityConfig.passwordEncoder().encode(foundUser.get().getPassword()),
                    Collections.singleton(new SimpleGrantedAuthority(roleName))
            );
        } else {
            throw new UsernameNotFoundException("User not found with userId: " + userid);
        }
    }
}