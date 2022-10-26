package com.capstone_project.hbts.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
public class UserRequest {

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String email;

    private int status; // 1-active, 0-account deleted, not required

    private String phone;

    private String address;

    private String avatar;

    private BigDecimal spend;

    private int type; // not required

}
