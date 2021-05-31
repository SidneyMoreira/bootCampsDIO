package com.avanade.security.app.repository;

import com.avanade.security.app.model.UserModel;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends CrudRepository<UserModel, String>{
    
}
