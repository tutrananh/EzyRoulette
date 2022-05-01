package org.youngmonkeys.app.entity;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.database.annotation.EzyCollection;
import lombok.Data;

import java.util.Date;

@Data
@EzyCollection
public class Result {
	@EzyId
	private Long id;
	
	private Date date = new Date();
	private int prize;
}
