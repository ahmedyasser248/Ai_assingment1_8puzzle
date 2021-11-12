package com.example.ai_assingment1_8puzzle;

import java.util.BitSet;
import java.util.PriorityQueue;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {



        int[][] currentArray = {{4,3,2},//432650781
                                {6,5,0},
                                {7,8,1}};
        BitSet outputBitSet = new BitSet();
        int index = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                for (int i = 0; i < 4; i++) {
                    if (currentArray[j][k] % 2 != 0) {
                        outputBitSet.set(index);
                    }
                    ++index;
                    currentArray[j][k] = currentArray[j][k] >>> 1;

                }
            }
        }

        MainRunner mr=new MainRunner();
        Object[] result=mr.AE(outputBitSet);
        if(result[0]==null){
            System.out.println("Path Not Found");
        }
        else System.out.println("Path was found");
        System.out.println("Path Cost " +result[1]);
        System.out.println("Number of nodes expanded "+result[2]);
        System.out.println("Search depth "+result[3]);
        System.out.println("Running Time "+ (Long)result[4]/1000000+" MilliSeconds");



    }
}
