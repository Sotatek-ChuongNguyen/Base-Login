package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.entity.Users;
import com.capstone_project.hbts.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get credential from db and authenticationManager compare credential that user sent
            Users user = userRepository.getUsersByUsername(username);
            if(user == null){
                throw new UsernameNotFoundException("can't find account");
            }
            Set<GrantedAuthority> grantedAuthoritySet = user.getListRole().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
            return new User(user.getUsername(), user.getPassword(), grantedAuthoritySet);
    }

}
