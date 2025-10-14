package com.microservice.user.services;

import com.microservice.user.models.UserModel;
import com.microservice.user.producers.UserProducer;
import com.microservice.user.repositories.IUserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    final IUserRepository userRepository;
    final UserProducer userProducer;

    public UserService(
            IUserRepository userRepository,
            UserProducer userProducer
    ) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    @Transactional
    public UserModel save(UserModel userModel) {
        var user = userRepository.save(userModel);

        userProducer.publishMessageEmail(user);

        return user;
    }

}
