package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.actor.UserDTO;
import com.capstone_project.hbts.entity.Role;
import com.capstone_project.hbts.entity.Users;
import com.capstone_project.hbts.repository.RoleRepository;
import com.capstone_project.hbts.repository.UserRepository;
import com.capstone_project.hbts.request.UserRequest;
import com.capstone_project.hbts.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public void register(UserRequest userRequest) {
        // type 0 is normal user, 1 is manager and 2 admin, register is always user
        userRequest.setType(0);
        // set active for new user: 1-active, 0-deleted
        userRequest.setStatus(1);
        // name prefix for user table
        userRequest.setUsername("u-" + userRequest.getUsername());
        // set vip status auto 1 for new user
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Users newUser = modelMapper.map(userRequest, Users.class);
        newUser.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        userRepository.save(newUser);
        Role role = new Role(newUser, "ROLE_USER");
        roleRepository.save(role);
    }

    @Override
    public UserDTO loadUserByEmail(String email) {
        Users users = userRepository.getUsersByEmail(email);
        if(users == null){
            return null;
        }else {
            return modelMapper.map(users, UserDTO.class);
        }
    }

    @Override
    public List<UserDTO> searchUserByUsername(String text) {
        return userRepository.searchUserByUsername(text).stream().map(item -> modelMapper.map(item, UserDTO.class))
                .collect(Collectors.toList());
    }

}
