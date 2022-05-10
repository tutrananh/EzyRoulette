package com.example.ezyroulettehttpserver.repo;

import com.example.ezyroulettehttpserver.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, Long> {
    @Query("{username:'?0'}")
    User findUserByUsername(String username);
}
