package com.example.ai_assingment1_8puzzle;

import java.util.BitSet;
import java.util.Comparator;

public class stateWithCost implements Comparator<stateWithCost> {
    BitSet state;
    double cost;
    stateWithCost(){}

    stateWithCost(BitSet state,double cost){
        this.state=state;
        this.cost=cost;
    }

    @Override
    public int compare(stateWithCost s1, stateWithCost s2) {
       return Double.compare(s1.cost,s2.cost);
    }
    @Override
    public boolean equals(Object obj){
        stateWithCost s=(stateWithCost) obj;
        return this.state.equals(s.state);
    }
}
