package com.capstone_project.hbts.dto.actor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private int status;

    private String phone;

    private String address;

    private int type;

    private String avatar;

    private BigDecimal spend;

}
