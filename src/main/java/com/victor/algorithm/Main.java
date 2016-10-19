package com.victor.algorithm;

import java.util.Arrays;

public class Main {


    private static byte[][] data1= new byte[100][100];
    private static byte[][] data2= new byte[100][100];

    public static void main(String[] args) {
        for (int i=0;i<100;i++){
            for (int j=0;j<100;j++){
                if (i==12){
                    data1[i][j] = 1;
                }
            }
        }
        for (int i=0;i<100;i++){
            for (int j=0;j<100;j++){
                if (i==15){
                    data2[i][j] = 1;
                }
            }
        }
        for (int i=0;i<100;i++){
            System.out.println(Arrays.toString(data1[i]));
        }
        System.out.println(CompareTools.simpleMatch(data1,data2));
        System.out.println(CompareTools.moveMatch(data1,data2));
    }
}
