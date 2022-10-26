package com.capstone_project.hbts.resource;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class JwtResponse {

    private String jwttoken;

    private int type;

}
