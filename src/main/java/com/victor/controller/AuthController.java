package com.victor.controller;

import com.victor.domain.Auth;
import com.victor.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vvtan on 16/10/19.
 */
@RestController
@RequestMapping(path = "/")
public class AuthController {
    @Autowired
    AuthService authService;

    @RequestMapping(method = RequestMethod.GET, path = "/hello")
    public Auth hello(){
        Auth auth = authService.getAuth(1L);
        System.out.println(auth);
        auth.setShare_resources("分享资源");
        auth.setImage_matrix("图片矩阵");
        auth.setRelation_check("关联验证");
        authService.saveAuth(auth);
        return auth;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleException(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("msg", e.getMessage());
        return response;
    }
}