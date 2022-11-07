package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.config.WebSecurityConfig;
import com.springboot.miniecommercewebapp.dto.request.UserRequestModel;
import com.springboot.miniecommercewebapp.exceptions.ItemExistException;
import com.springboot.miniecommercewebapp.exceptions.NotFoundException;
import com.springboot.miniecommercewebapp.models.UsersEntity;
import com.springboot.miniecommercewebapp.models.enums.EUserStatus;
import com.springboot.miniecommercewebapp.repositories.UserRepository;
import com.springboot.miniecommercewebapp.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public Optional<UsersEntity> getUser(String userId) {
        Optional<UsersEntity> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()) return foundUser;
        throw new NotFoundException("Not found User");
    }

//    @Override
//    public UserUpdateRequestModel getUserDto(String userId) {
//        Optional<UsersEntity> foundUser = userRepository.findById(userId);
//        if (foundUser.isPresent()) {
//            UserUpdateRequestModel userdto = mapper.map(foundUser.get(), UserUpdateRequestModel.class);
//            return  userdto;
//        }
//        throw new NotFoundException("Not found User");
//    }

    @Override
    public List<UsersEntity> getAllUser() {
        List<UsersEntity> usersEntityList = userRepository.findAll();
        if (usersEntityList.size() > 0) return usersEntityList;
        throw new NotFoundException("User list empty");
    }

    public UsersEntity register(UserRequestModel newUser) {
        Optional<UsersEntity> foundUser = userRepository.findById(newUser.getUserId());
        if (foundUser.isEmpty()) {
            newUser.setPassword(WebSecurityConfig.passwordEncoder().encode(newUser.getPassword()));
            newUser.setRoleId(2);
            newUser.setStatus(EUserStatus.IS_USING);
            UsersEntity usersEntity = mapper.map(newUser, UsersEntity.class);
            usersEntity.setUserId(newUser.getUserId());
            return userRepository.save(usersEntity);
        }
        throw new ItemExistException("User id has already exist!");
    }

    public UsersEntity updateUser(String id, UserRequestModel newUser) {
        Optional<UsersEntity> foundUser = userRepository.findById(id);
        if (foundUser.isPresent()) {
            UsersEntity updateUser = mapper.map(newUser, UsersEntity.class);
            updateUser.setUserId(id);
            return userRepository.save(updateUser);
        }
        throw new NotFoundException("Not found Product to update");
    }

    public boolean deleteUser(String userId) {
        Optional<UsersEntity> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()) {
            if (foundUser.get().getStatus() == EUserStatus.IS_USING)
                foundUser.get().setStatus(EUserStatus.IS_DELETED);
            else foundUser.get().setStatus(EUserStatus.IS_USING);
            userRepository.save(foundUser.get());
            return true;
        }
        throw new NotFoundException(("Not found user to delete"));
    }
}
