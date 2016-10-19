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
            for (int j=0;j<100;j++){
                if (data1[i][j]==1){
                    System.out.print("*");
                }else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        SignComparator comparator = SignComparator.build(data1,data2);
        System.out.println(comparator.simpleMatch());
        System.out.println(comparator.moveMatch());
    }
}
