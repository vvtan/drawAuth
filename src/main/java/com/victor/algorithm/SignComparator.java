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
        System.out.println("src: ");
        for (int i=0;i<100;i++){
            for (int j=0;j<100;j++){
                if (src[i][j]==1){
                    System.out.print("*");
                }else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println("target: ");
        for (int i=0;i<100;i++){
            for (int j=0;j<100;j++){
                if (target[i][j]==1){
                    System.out.print("*");
                }else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }

        return new SignComparator(src,target);
    }

    public float match(){
//        float result;
//        result = simpleMatch();
//        if (result>0.7){
//            return result;
//        }
//        result = moveMatch();
//        if (result>=0.7){
//            return result;
//        }
//        result = smallMatch();

        return Math.max(Math.max(simpleMatch(),moveMatch()),smallMatch());
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
        List<Integer> shapeSrc = getShape(compactSrc);
        List<Integer> shapeTarget = getShape(compactTarget);
        System.out.println(shapeSrc);
        System.out.println(shapeTarget);

        return compareShape(shapeSrc,shapeTarget);
    }

    public float compareShape(List<Integer> shapeSrc, List<Integer> shapeTarget){
        int sizeA = shapeSrc.size();
        int sizeB = shapeTarget.size();
        float count = 0;
        float result = 0.00f;
        int differ = sizeB-sizeA;
        if (differ>0){
            for (int off=0;off<=differ;off++){
                int tmp = 0;
                for (int i=0;i<sizeA;i++){
                    float per = (float) (shapeSrc.get(i))/shapeTarget.get(i+off);
                    if (per>=0.75 && per<=1.3){
                        tmp++;
                    }
                }
                if (tmp>=count){
                    count=tmp;
                }
            }
            result = count/sizeA;
        }else{
            differ = -differ;
            for (int off=0;off<=differ;off++){
                int tmp = 0;
                for (int i=0;i<sizeB;i++){
                    float per = (float) (shapeTarget.get(i))/shapeSrc.get(i+off);
                    if (per>=0.75 && per<=1.3){
                        tmp++;
                    }
                }
                if (tmp>=count){
                    count=tmp;
                }
            }
            result = count/sizeB;
        }

        return result;
    }

    private List<Integer> getShape(List<Byte> data){
        List<Integer> shape = new ArrayList<>();
        boolean flag = false;
        Integer line=0;
        for (int i=0;i<compactSrc.size();i++){
            if (flag){
                if (compactSrc.get(i)==1){
                    line++;
                }else {
                    shape.add(line);
                    flag = false;
                }
            }else {
                if (compactSrc.get(i)==1){
                    line = 0;
                    line++;
                    flag = true;
                }
            }
            if (flag && i==compactSrc.size()-1){
                shape.add(line);
            }
        }
        return shape;
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
