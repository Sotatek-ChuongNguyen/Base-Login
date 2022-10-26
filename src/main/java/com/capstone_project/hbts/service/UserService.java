package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.actor.UserDTO;
import com.capstone_project.hbts.request.UserRequest;

import java.util.List;

public interface UserService {

    /**
     * Register an user
     */
    void register(UserRequest userRequest);

    /**
     * Load detail user by email
     */
    UserDTO loadUserByEmail(String email);

    /**
     * Get page of all user
     */
    List<UserDTO> searchUserByUsername(String text);

}
