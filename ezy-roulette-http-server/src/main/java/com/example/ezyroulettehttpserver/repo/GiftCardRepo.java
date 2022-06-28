package com.example.ezyroulettehttpserver.repo;

import com.example.ezyroulettehttpserver.entity.GiftCard;
import com.example.ezyroulettehttpserver.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCardRepo extends MongoRepository<GiftCard, Long> {
    @Query("{serial:'?0'}")
    GiftCard findGiftCardBySerial(String serial);
}
