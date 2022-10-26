package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.dto.actor.UserDTO;
import com.capstone_project.hbts.request.UserRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("api/v1")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * @apiNote for only user register, admin and manager account cannot be registered
     */
    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        log.info("REST request to register a new user : {}", userRequest);
        try {
            userService.register(userRequest);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, null));
        }
    }

    @GetMapping("/search-user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> searchUserByUsername(@RequestParam String text) {
        log.info("REST request to search user by username for admin");
        try {
            List<UserDTO> userDTOList = userService.searchUserByUsername(text);
            return ResponseEntity.ok().body(new ApiResponse<>(200, userDTOList, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, null));
        }
    }

}
