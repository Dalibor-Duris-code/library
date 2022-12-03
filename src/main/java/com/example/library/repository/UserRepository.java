package com.example.library.repository;

import com.example.library.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User getByUsername(String username);
}
