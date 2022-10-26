package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.dto.actor.UserDTO;
import com.capstone_project.hbts.request.UserRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.security.jwt.JwtTokenUtil;
import com.capstone_project.hbts.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("api/v1")
public class JwtAuthenticationResource {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserDetailsService userDetailsService;

    private final UserService userService;

    public JwtAuthenticationResource(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                                     @Qualifier("customUserDetailsService") UserDetailsService userDetailsService,
                                     UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    /**
     * @apiNote for admin/manager/user can authenticate
     */
    @PostMapping("/authenticate/user")
    public ResponseEntity<?> createJsonWebTokenKeyForUser(@RequestBody UserRequest userRequest) {
        log.info("REST request to authenticate user request : {}", userRequest);
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();
        UserDTO userDTO = userService.loadUserByEmail(email);
        if (userDTO == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, null));
        }
        if (userDTO.getStatus() == 0) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, null));
        }
        try {
            // also call to method loadUserByUsername of customUserDetailsService (in web config)
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), password));
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, null));
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDTO.getId() + "", userDetails);

        JwtResponse jwtResponse = new JwtResponse(jwt, userDTO.getType());

        return ResponseEntity.ok().body(new ApiResponse<>(200, jwtResponse, null));
    }

}
