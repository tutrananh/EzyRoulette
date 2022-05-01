package org.youngmonkeys.app.repo;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import org.youngmonkeys.app.entity.Result;

@EzyRepository("resultRepo")
public interface ResultRepo extends EzyMongoRepository<Long, Result> {
}
