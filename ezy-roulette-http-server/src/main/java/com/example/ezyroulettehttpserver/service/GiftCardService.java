package com.example.ezyroulettehttpserver.service;

import com.example.ezyroulettehttpserver.entity.GiftCard;

public interface GiftCardService {
    GiftCard getGiftCard(String serial);
    public void updateGiftCard(GiftCard giftCard);
}
