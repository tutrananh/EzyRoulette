package org.youngmonkeys.app.entity;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import lombok.Data;

@Data
@EzyObjectBinding
public class WheelFragment {
	private int index;
	private float ratio;
	private WheelPrizeType prizeType;
}
