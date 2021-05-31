package com.avanade.dio.api.services;

import com.avanade.dio.api.models.User;
import com.avanade.dio.api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
	private UserRepository userRepository;
	
	public Iterable<User> findAll(){
        return userRepository.findAll( );
    }
	

    public void inserir(User user){
        userRepository.save( user );
    	System.out.println("INSERIDO: " + user);
    }
    
}
