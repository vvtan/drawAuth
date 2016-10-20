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

    public static final int bitMapSize = 16;

    private SignComparator(byte[][] src,byte[][] target){
        this.src = src;
        this.target = target;
        compactSrc = compact(src);
        compactTarget = compact(target);
    }

    public static SignComparator build(byte[][] src,byte[][] target){
//        System.out.println("src: ");
//        print(src);
//        System.out.println("target: ");
//        print(target);

        return new SignComparator(src,target);
    }

    public float match(){

        return Math.max(Math.max(simpleMatch(),moveMatch()),smallMatch());
    }

    public float simpleMatch(){
//        if (!isSizeNear()){
//            return 0;
//        }
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
        float rs = match_1/total_1+match_0/total_0;
        System.out.println("simple:"+rs);
        return rs;
    }

    public float moveMatch(){
        float match_1 = 0.00f;
        float match_0 = 0.00f;
        float total_1 = 0.00f;
        float total_0 = 0.00f;
        int min = compactSrc.size()<=compactTarget.size()?compactSrc.size():compactTarget.size();
        int max = compactSrc.size()>=compactTarget.size()?compactSrc.size():compactTarget.size();
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
        System.out.println("move:"+result);
        return result;
    }

    public float smallMatch(){
        float match_1 = 0.00f;
        float match_0 = 0.00f;
        float total_1 = 0.00f;
        float total_0 = 0.00f;
        byte[][] src_16 = toBit(src);
        byte[][] target_16 = toBit(target);
        for (int i=0;i<bitMapSize;i++){
            for (int j=0;j<bitMapSize;j++){
                if (src_16[i][j]==target_16[i][j]){
                    if (src_16[i][j]==1){
                        match_1++;
                    }else {
                        match_0++;
                    }
                }
            }
        }
        for (int i=0;i<bitMapSize;i++){
            for (int j=0;j<bitMapSize;j++){
                if (src_16[i][j]==1){
                    total_1++;
                }else {
                    total_0++;
                }
                if (target_16[i][j]==1){
                    total_1++;
                }else {
                    total_0++;
                }
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
        print(src_16);
        print(target_16);

        System.out.println("small:"+result);

        return result;
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

    /**
     * 将图片压缩为16*16的格式
     */
    public static byte[][] toBit(byte[][] data){
        int left = 100;
        int right = 0;
        int top = 100;
        int bottom = 0;

        for (int i=0;i<100;i++) {
            for (int j = 0; j < 100; j++) {
                if (data[i][j]==1){
                    left = left<=j?left:j;
                    right = right>=j?right:j;
                    top = top<=i?top:i;
                    bottom = bottom>=i?bottom:i;
                }
            }
        }
        if (left>right || top>bottom){
            throw new ArithmeticException("计算边界异常");
        }
        int width = right-left;
        int height = bottom-top;
        int bigSize = tableSizeFor(Math.max(width,height));
        if (bigSize<bitMapSize){
            bigSize = bitMapSize;
        }
        byte[][] bigMap = new byte[bigSize][bigSize];
        float widthFactor = ((float) bigSize)/width;
        float heightFactor = ((float) bigSize)/height;

        for (int i=top;i<=bottom;i++){
            for (int j=left;j<=right;j++){
                if (data[i][j]==1){
                    bigMap[Math.round((i-top)*widthFactor)][Math.round((j-left)*heightFactor)] = 1;
                }
            }
        }
        print(bigMap);
        if (bigSize==bitMapSize){
            return bigMap;
        }

        byte[][] map_16 = new byte[bitMapSize][bitMapSize];
        int mergeFactor = bigSize/bitMapSize; //合并因子
        for (int i=0;i<bitMapSize;i++){
            for (int j=0;j<bitMapSize;j++){
                List<Byte> sample = new ArrayList<>();
                for (int k=i*mergeFactor;k<(i+1)*mergeFactor;k++){
                    for (int p=j*mergeFactor;p<(j+1)*mergeFactor;p++){
                        sample.add(bigMap[k][p]);
                    }
                }
                map_16[i][j] = merge(sample);
            }
        }

        return map_16;
    }

    static byte merge(List<Byte> list){
        int len = list.size();
        int shot = 0;
        for (Byte b:list){
            if (b==1){
                shot++;
            }
        }
        if (Math.round(((float) shot)/len)>0.05){
            return 1;
        }
        return 0;
    }


    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= Integer.MAX_VALUE) ? Integer.MAX_VALUE : n + 1;
    }


    static void print(byte[][] data){
        for (int i=0;i<data.length;i++){
            for (int j=0;j<data.length;j++){
                if (data[i][j]==1){
                    System.out.print("*");
                }else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
}
