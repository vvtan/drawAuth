package com.victor.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.URLCodec;
import com.victor.domain.Auth;
import com.victor.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Arrays;
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
        short a[][] = new short[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (i==j){
                    a[i][j] = 1;
                }
            }
        }
        System.out.println(Arrays.toString(a[0]));
        System.out.println(Arrays.asList(a[0]));
        return auth;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/share")
    public Map<String, Object> share(@RequestBody String jsonData){
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        Auth auth = new Auth();
        String draw = jsonObject.getString("draw");
        String message = jsonObject.getString("message");
        auth.setImage_matrix(draw);
        auth.setMessage(message);
        Auth authNew = authService.saveAuth(auth);
        String shareURL = "http://192.168.1.202:8099/receiver.html?id=" + authNew.getId();
        authNew.setShare_resources(shareURL);
        String redirect = "http://127.0.0.1:8099/success.html";
        Map<String, Object> result = new HashMap<>();
        result.put("status", "ok");
        result.put("shareURL", redirect + "?url="+ shareURL);
//        return "{\"status\":\"ok\",\"shareURL\":\""+ redirect + "?url="+ shareURL +"\"}";
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/auth")
    public Map<String, Object> auth(@RequestBody String jsonData){
        float rate = authService.getAuthRate(jsonData);
        Map<String, Object> result = new HashMap<>();
        result.put("status", "ok");
        result.put("rate", rate);
//        return "{\"status\":\"ok\",\"rate\":\""+ rate +"\"}";
        return result;
    }






    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleException(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("msg", e.getMessage());
        e.printStackTrace();
        return response;
    }
}