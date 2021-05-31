package com.avanade.dio.api.repository;

import java.util.List;

import com.avanade.dio.api.models.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String>{
    
    public List<User> findByUsername(String username);
    
}