package org.youngmonkeys.common.service.impl;

import com.tvd12.ezydata.database.repository.EzyMaxIdRepository;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import org.youngmonkeys.common.entity.User;
import org.youngmonkeys.common.repo.UserRepo;
import org.youngmonkeys.common.service.UserService;

@EzySingleton("userService")
public class UserServiceImpl implements UserService {
	
	@EzyAutoBind
	private UserRepo userRepo;
	
	@EzyAutoBind
	private EzyMaxIdRepository maxIdRepo;
	
	@Override
	public User getUser(String username) {
		return userRepo.findByField("username", username);
	}
	
	@Override
	public User createUser(String username, String password) {
		User user = new User();
		user.setId(maxIdRepo.incrementAndGet("user"));
		user.setUsername(username);
		user.setPassword(password);
		user.setBalance(10f);
		userRepo.save(user);
		return user;
	}

	@Override
	public User updateUser(String username, float balance) {
		User user = userRepo.findByField("username", username);
		user.setBalance(user.getBalance()+balance);
		userRepo.save(user);
		return user;
	}

}
