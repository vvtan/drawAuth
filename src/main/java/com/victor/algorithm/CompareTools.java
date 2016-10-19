package com.victor.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 比对工具
 * Created by Caixinning on 2016/10/19.
 */
public class CompareTools {

    public static float match(byte[][] src, byte[][] target){
        float result;
        result = simpleMatch(src,target);

        return result;
    }

    public static float simpleMatch(byte[][] src, byte[][] target){
        float match_1 = 0;
        float match_0 = 0;
        float total_1 = 0;
        float total_0 = 0;

        for (int i=0;i<100;i++){
            for (int j=0;j<100;j++){
                if (src[i][j]==target[i][j]){
                    if (src[i][j]==1){
                        match_1++;
                    }else {
                        match_0++;
                    }
                }
                if (src[i][j]==1){
                    total_1++;
                }else{
                    total_0++;
                }
                if (target[i][j]==1){
                    total_1++;
                }else{
                    total_0++;
                }
            }
        }
        return match_1/total_1+match_0/total_0;
    }

    public static float moveMatch(byte[][] src, byte[][] target){
        float match_1 = 0.00f;
        float match_0 = 0.00f;
        float total_1 = 0.00f;
        float total_0 = 0.00f;
        List<Byte> srcCut = compact(src);
        List<Byte> targetCut = compact(target);
        System.out.println(srcCut.size());
        System.out.println(targetCut.size());
        int min = srcCut.size()<=targetCut.size()?srcCut.size():targetCut.size();
        int max = srcCut.size()>=targetCut.size()?srcCut.size():targetCut.size();
        System.out.println(min);
        for (int i=0;i<min;i++){
            if (srcCut.get(i).equals(targetCut.get(i))){
                if (srcCut.get(i)==1){
                    match_1++;
                }else {
                    match_0++;
                }
            }
        }
        for (int i=0;i<srcCut.size();i++){
            if (srcCut.get(i)==1){
                total_1++;
            }else {
                total_0++;
            }
        }
        for (int i=0;i<targetCut.size();i++){
            if (targetCut.get(i)==1){
                total_1++;
            }else {
                total_0++;
            }
        }

        float result;
        if (total_1==0){
            result = 2*match_0/total_0;
        }else if (total_0==0){
            result = 2*match_1/total_1;
        }else {
            result = match_1/total_1+match_0/total_0;
        }

        return result;
    }


    public static List<Byte> compact(byte[][] src){
        int length = 0 ;
        boolean not_find_head = true;
        List<Byte> bytes = new ArrayList<Byte>();
        for (int i=0;i<100;i++){
            for (int j=0;j<100;j++){
                if (not_find_head){
                    if (src[i][j] != 0){
                        not_find_head = false;
                        bytes.add(src[i][j]);
                    }
                }else {
                    bytes.add(src[i][j]);
                }
            }
        }
        for (int i=bytes.size()-1;i>=0;i--){
            if (bytes.get(i)==1){
                break;
            }else {
                bytes.remove(i);
            }
        }
        return bytes;
    }


}
