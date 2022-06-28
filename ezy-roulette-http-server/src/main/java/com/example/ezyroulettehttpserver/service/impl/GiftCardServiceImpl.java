package com.example.ezyroulettehttpserver.service.impl;

import com.example.ezyroulettehttpserver.entity.GiftCard;
import com.example.ezyroulettehttpserver.repo.GiftCardRepo;
import com.example.ezyroulettehttpserver.service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiftCardServiceImpl implements GiftCardService {

    @Autowired
    private GiftCardRepo giftCardRepo;

    @Override
    public GiftCard getGiftCard(String serial) {
        return giftCardRepo.findGiftCardBySerial(serial);
    }

    @Override
    public void updateGiftCard(GiftCard giftCard) {
        giftCardRepo.save(giftCard);
    }
}
