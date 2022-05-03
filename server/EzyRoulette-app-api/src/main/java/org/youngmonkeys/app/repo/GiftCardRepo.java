package org.youngmonkeys.app.repo;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import org.youngmonkeys.app.entity.GiftCard;


@EzyRepository("giftCardRepo")
public interface GiftCardRepo extends EzyMongoRepository<Long, GiftCard> {
}
