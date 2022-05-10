package org.youngmonkeys.common.service;

import org.youngmonkeys.common.entity.User;

public interface UserService {
	User getUser(String username);
	User getUserByToken(String token);
	User createUser(String username, String password);
	User updateUser(String username, float balance);

}
