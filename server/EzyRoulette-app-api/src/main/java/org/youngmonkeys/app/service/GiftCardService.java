package org.youngmonkeys.app.service;

import com.tvd12.ezydata.database.repository.EzyMaxIdRepository;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import org.youngmonkeys.app.entity.GiftCard;
import org.youngmonkeys.app.repo.GiftCardRepo;

@EzySingleton
public class GiftCardService {
    @EzyAutoBind
    private GiftCardRepo giftCardRepo;

    @EzyAutoBind
    private EzyMaxIdRepository maxIdRepository;

    public GiftCard getGiftCard(String serial) {
        return giftCardRepo.findByField("serial", serial);
    }

    public GiftCard createGiftCard(String serial, String code, int amount) {
        GiftCard giftCard = new GiftCard();
        giftCard.setId(maxIdRepository.incrementAndGet("giftCard"));
        giftCard.setSerial(serial);
        giftCard.setCode(code);
        giftCard.setAmount(amount);
        giftCardRepo.save(giftCard);
        return giftCard;
    }
    public void updateGiftCard(GiftCard giftCard){
        giftCardRepo.save(giftCard);
        return;
    }
}
