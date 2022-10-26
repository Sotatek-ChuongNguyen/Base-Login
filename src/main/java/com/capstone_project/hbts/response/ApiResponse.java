package com.capstone_project.hbts.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ApiResponse<T> {

    private int status;

    private T data;

    private String error_message;

}
