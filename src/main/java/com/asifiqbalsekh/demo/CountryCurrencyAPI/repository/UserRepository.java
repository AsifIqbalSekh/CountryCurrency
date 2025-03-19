package com.asifiqbalsekh.demo.CountryCurrencyAPI.repository;

import com.asifiqbalsekh.demo.CountryCurrencyAPI.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
