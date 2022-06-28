package com.example.ezyroulettehttpserver.request;

import lombok.Data;

@Data
public class GiftCardRequest {
    private String username;
    private String serial;
    private String code;
}
