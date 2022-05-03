package org.youngmonkeys.app.entity;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.database.annotation.EzyCollection;
import lombok.Data;

import java.util.Date;

@Data
@EzyCollection
public class GiftCard {
    @EzyId
    private Long id;

    private String serial;
    private String code;
    private int amount;
    private boolean activated;
}
