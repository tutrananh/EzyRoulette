package org.youngmonkeys.app.request;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import lombok.Data;

@Data
@EzyObjectBinding
public class BetRequest {
    private float amount;
    private int side;
}
