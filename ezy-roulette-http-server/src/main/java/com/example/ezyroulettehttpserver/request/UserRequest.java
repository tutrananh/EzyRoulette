package com.example.ezyroulettehttpserver.request;

import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private String password;
}