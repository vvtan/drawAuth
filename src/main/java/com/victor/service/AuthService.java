package com.victor.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.victor.algorithm.SignComparator;
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

    public float getAuthRate(String jsonData){
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        String drawTarget = jsonObject.getString("draw");
        Long id = jsonObject.getLong("id");
        Auth authSource = getAuth(id);
        String drawSource = authSource.getImage_matrix();
        byte[][] src = convert(drawSource);
        byte[][] target = convert(drawTarget);
        SignComparator signComparator = SignComparator.build(src, target);
        float rate = signComparator.match();
        return rate;
    }

    private byte[][] convert(String draw){
        JSONArray drawArray = JSONObject.parseArray(draw);
        byte matrix[][] = new byte[100][100];
        for (int i = 0; i < 100; i++) {
            JSONArray tmp = drawArray.getJSONArray(i);
            for (int j = 0; j < 100; j++) {
                matrix[i][j] = tmp.getByte(j);
            }
        }
        return matrix;
    }
}
