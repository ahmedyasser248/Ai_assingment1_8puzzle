package com.example.ai_assingment1_8puzzle;

import java.util.BitSet;

public class MainRunner {
    private PuzzleSolver ps;
    public MainRunner(){
        ps=new PuzzleSolver();
    }
    public Object [] BFS(BitSet startState){
        long start =System.nanoTime();
         Object [] result=ps.solveWithBFS(startState);
         long end=System.nanoTime();
         long runningTime=end-start;
         result[4]=runningTime;
         return result;
    }
    public Object [] DFS(BitSet startState){
        long start =System.nanoTime();
        Object [] result=ps.solveWithDFS(startState);
        long end=System.nanoTime();
        long runningTime=end-start;
        result[4]=runningTime;
        return result;
    }
    public Object [] AM(BitSet startState){
        long start =System.nanoTime();
        Object [] result=ps.solveWithAManhattan(startState);
        long end=System.nanoTime();
        long runningTime=end-start;
        result[4]=runningTime;
        return result;
    }
    public Object [] AE(BitSet startState){
        long start =System.nanoTime();
        Object [] result=ps.solveWithAEuclidean(startState);
        long end=System.nanoTime();
        long runningTime=end-start;
        result[4]=runningTime;
        return result;
    }

}
