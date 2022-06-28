package com.example.ezyroulettehttpserver.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("gift_card")
public class GiftCard {
    @Id
    private Long id;

    private String serial;
    private String code;
    private int amount;
    private boolean activated;
}
