package org.youngmonkeys.app.service;

import com.tvd12.ezydata.database.repository.EzyMaxIdRepository;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import org.youngmonkeys.app.entity.Result;
import org.youngmonkeys.app.repo.ResultRepo;

@EzySingleton
public class ResultService {
	
	@EzyAutoBind
	private ResultRepo resultRepo;
	
	@EzyAutoBind
	private EzyMaxIdRepository maxIdRepository;
	
	public Result createResult(int resultRecord) {
		Result result = new Result();
		result.setId(maxIdRepository.incrementAndGet("result"));
		result.setPrize(resultRecord);
		resultRepo.save(result);
		return result;
	}
}
