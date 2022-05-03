package org.youngmonkeys.app.request;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import lombok.Data;

@Data
@EzyObjectBinding
public class AddBalanceByGiftCardRequest {
    private String serial;
    private String code;
}
