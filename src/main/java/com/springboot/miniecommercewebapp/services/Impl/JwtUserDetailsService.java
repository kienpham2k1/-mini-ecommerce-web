package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.config.WebSecurityConfig;
import com.springboot.miniecommercewebapp.models.AdminsEntity;
import com.springboot.miniecommercewebapp.models.UsersEntity;
import com.springboot.miniecommercewebapp.repositories.AdminRepository;
import com.springboot.miniecommercewebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<UsersEntity> foundUser = userRepository.findById(userId);
        Optional<AdminsEntity> foundAdmin = adminRepository.findById(userId);

        List<GrantedAuthority> authories = new ArrayList<>();

        if (foundAdmin.isPresent()) {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
            authories.add(authority);
            return new User(foundAdmin.get().getUserId(), foundAdmin.get().getPassword(), authories);
        }else {
            if (foundUser.isPresent()) {
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
                authories.add(authority);
                return new User(foundUser.get().getUserId(), foundUser.get().getPassword(), authories);
            } else throw new UsernameNotFoundException("User is not exist!");
        }
    }
}