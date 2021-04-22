package com.azure.springboot.service;

import com.azure.springboot.document.UserDocument;
import com.azure.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
public class DummyDataService {

    @Autowired
    UserRepository userRepository;

    //@PostConstruct
    public void createUsers(){

        userRepository.deleteAll()
                .thenMany(
                        Flux.just("Michelli", "Sidney", "Kaka Curti",
                                "Tatiane", "Marcia")
                                .map(nome -> new UserDocument(UUID.randomUUID().toString(), nome))
                                .flatMap(userRepository::save))
                .subscribe(System.out::println);

    }
}
