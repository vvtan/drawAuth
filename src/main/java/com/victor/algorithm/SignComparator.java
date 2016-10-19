package com.victor.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 比较图片
 * Created by Caixinning on 2016/10/19.
 */
public class SignComparator {

    private byte[][] src;
    private byte[][] target;

    private List<Byte> compactSrc;
    private List<Byte> compactTarget;

    private float srcDots;
    private float tarDots;

    private SignComparator(byte[][] src,byte[][] target){
        this.src = src;
        this.target = target;
        for (int i=0;i<100;i++){
            for (int j=0;j<100;j++){
                if (src[i][j]==1){
                    srcDots++;
                }
                if (target[i][j]==1){
                    tarDots++;
                }
            }
        }
    }

    public static SignComparator build(byte[][] src,byte[][] target){
        return new SignComparator(src,target);
    }

    public float match(){
        float result;
        result = simpleMatch();

        return result;
    }

    public float simpleMatch(){
        if (!isSizeNear()){
            return 0;
        }
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

    public float moveMatch(){
        if (!isSizeNear()){
            return 0;
        }
        float match_1 = 0.00f;
        float match_0 = 0.00f;
        float total_1 = 0.00f;
        float total_0 = 0.00f;
        compactSrc = compact(src);
        compactTarget = compact(target);
//        System.out.println(compactSrc.size());
//        System.out.println(compactTarget.size());
        int min = compactSrc.size()<=compactTarget.size()?compactSrc.size():compactTarget.size();
        int max = compactSrc.size()>=compactTarget.size()?compactSrc.size():compactTarget.size();
//        System.out.println(min);
        for (int i=0;i<min;i++){
            if (compactSrc.get(i).equals(compactTarget.get(i))){
                if (compactSrc.get(i)==1){
                    match_1++;
                }else {
                    match_0++;
                }
            }
        }
        for (int i=0;i<compactSrc.size();i++){
            if (compactSrc.get(i)==1){
                total_1++;
            }else {
                total_0++;
            }
        }
        for (int i=0;i<compactTarget.size();i++){
            if (compactTarget.get(i)==1){
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

    public float smallMatch(){
        List<Integer> shapeA = new ArrayList<>();
        List<Integer> shapeB = new ArrayList<>();

        return 0;
    }

    private void getShape(List<Byte> data){
        List<Integer> shape = new ArrayList<>();
        boolean flag = true;
        int line=0;
        for (int i=0;i<compactSrc.size();i++){


        }

    }


    public List<Byte> compact(byte[][] data){
        boolean not_find_head = true;
        List<Byte> bytes = new ArrayList<>();
        for (int i=0;i<100;i++){
            for (int j=0;j<100;j++){
                if (not_find_head){
                    if (data[i][j] != 0){
                        not_find_head = false;
                        bytes.add(data[i][j]);
                    }
                }else {
                    bytes.add(data[i][j]);
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

    public boolean isSizeNear(){
        return 0.66f<srcDots/tarDots && srcDots/tarDots<1.66f;
    }
}
