package com.victor.service;

import com.victor.domain.Auth;
import com.victor.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vvtan on 16/10/19.
 */
@Service
public class AuthService {
    @Autowired
    AuthRepository authRepository;

    public Auth getAuth(Long id){
        return authRepository.findOne(id);
    }

    public Auth saveAuth(Auth auth){
        return authRepository.save(auth);
    }
}
