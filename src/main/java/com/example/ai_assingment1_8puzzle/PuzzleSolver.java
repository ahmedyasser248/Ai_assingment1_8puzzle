package com.example.ai_assingment1_8puzzle;

import java.util.*;

public class PuzzleSolver {
  BitSet goalState;
  HashMap<Integer,BitSet> goalMap;
  HashMap<BitSet,Integer> valueMap;
  public PuzzleSolver(){
    valueMap=generateValueMap();
    goalMap=generateGoalMap();
    goalState=generateGoal();
  }
  private BitSet generateGoal(){//Generate the goal State
    BitSet b=new BitSet(36);
    for(int i=0;i<9;i++){
      BitSet s=goalMap.get(i);
      for(int m=0;m<4;m++){
         if(s.get(m)){
           b.set(i*4+m);
         }
      }
    }
    return b;
  }
  //Generate a hashmap with Bitset as key and integer as value for example 1000 = 1 , 0100 =2 and so on
  public HashMap<BitSet,Integer> generateValueMap(){
    HashMap<BitSet,Integer> valueMap=new HashMap<>();
    valueMap.put(generateValue(0),0);//0000
    valueMap.put(generateValue(1),1);//1000
    valueMap.put(generateValue(2),2);//0100
    valueMap.put(generateValue(3),3);//1100
    valueMap.put(generateValue(4),4);//0010
    valueMap.put(generateValue(5),5);//1010
    valueMap.put(generateValue(6),6);//0110
    valueMap.put(generateValue(7),7);//1110
    valueMap.put(generateValue(8),8);//0001
    return valueMap;
  }
  //Generate a hashmap with integer as key and bitset as value for example 1 = 1000 , 2=0100 and so on
  private HashMap<Integer,BitSet> generateGoalMap(){
    HashMap<Integer,BitSet> valueMap=new HashMap<>();
    valueMap.put(0,generateValue(0));//0000
    valueMap.put(1,generateValue(1));//1000
    valueMap.put(2,generateValue(2));//0100
    valueMap.put(3,generateValue(3));//1100
    valueMap.put(4,generateValue(4));//0010
    valueMap.put(5,generateValue(5));//1010
    valueMap.put(6,generateValue(6));//0110
    valueMap.put(7,generateValue(7));//1110
    valueMap.put(8,generateValue(8));//0001
    return valueMap;
  }
  private BitSet generateValue(int number){//generate bitset corresponding to the given number
    BitSet b=new BitSet(4);
    if(number==0){
      return b;
    }
    else if( number==2|| number==4){
      b.set(number/2);
      return b;
    }
    else if (number==8){
      b.set(3);
      return b;
    }
    else if(number==1 || number==3||number==5){
      b.set(0);
      b.set((number-1)/2);
      return b;
    }
    else if(number==6){
      b.set(1,3);
      return b;
    }
    else {//number==7
      b.set(0,3);
      return b;
    }
  }

  ArrayList<BitSet> findNeighbors(BitSet state){
    ArrayList<BitSet> neighbors = new ArrayList<>();
    BitSet zero = new BitSet(4);
    int i=0,j;
    for (int k = 0; k < 9; k++) {
      BitSet current = state.get(4*k,4*k+4);
      if(current.equals(zero) ){
        i = k/3; j = k%3;
        if( j >= 0 && j != 2){    // 0 is in left or middle column
          int oldLocation = (i*3+j);
          int newLocation = (i*3+j+1);
          BitSet neighbor1 = (BitSet) state.clone();
          BitSet temp = neighbor1.get(newLocation*4,newLocation*4+4);
          neighbor1.clear(newLocation*4,newLocation*4+4);
          for (int l = 0; l < 4 ; l++) {
            if(temp.get(l)){
              neighbor1.set(oldLocation*4+l);
            }
          }
          neighbors.add(neighbor1);
        }
        if( i >= 0 && i != 2){    // 0 is in upper or middle row
          int oldLocation = (i*3+j);
          int newLocation = ((i+1)*3+j);
          BitSet neighbor1 = (BitSet) state.clone();
          BitSet temp = neighbor1.get(newLocation*4,newLocation*4+4);
          neighbor1.clear(newLocation*4,newLocation*4+4);
          for (int l = 0; l < 4 ; l++) {
            if(temp.get(l)){
              neighbor1.set(oldLocation*4+l);
            }
          }
          neighbors.add(neighbor1);
        }
        if( j <= 2 && j != 0){    // 0 is in right or middle column
          int oldLocation = (i*3+j);
          int newLocation = (i*3+j-1);
          BitSet neighbor1 = (BitSet) state.clone();
          BitSet temp = neighbor1.get(newLocation*4,newLocation*4+4);
          neighbor1.clear(newLocation*4,newLocation*4+4);
          for (int l = 0; l < 4 ; l++) {
            if(temp.get(l)){
              neighbor1.set(oldLocation*4+l);
            }
          }
          neighbors.add(neighbor1);
        }
        if( i <= 2 && i != 0){    // 0 is in lower or middle row
          int oldLocation = (i*3+j);
          int newLocation = ((i-1)*3+j);
          BitSet neighbor1 = (BitSet) state.clone();
          BitSet temp = neighbor1.get(newLocation*4,newLocation*4+4);
          neighbor1.clear(newLocation*4,newLocation*4+4);
          for (int l = 0; l < 4 ; l++) {
            if(temp.get(l)){
              neighbor1.set(oldLocation*4+l);
            }
          }
          neighbors.add(neighbor1);
        }
        break;
      }
    }
    return neighbors;
  }


  Object[] solveWithBFS(BitSet startState) {
    int maxDepth=0;
    HashSet<BitSet> explored=new HashSet<>();
    Queue<BitSet> frontier = new LinkedList<>();
    HashSet<BitSet> frontierSet=new HashSet<>();
    HashMap<BitSet,stateWithCost> parentMap=new HashMap<>();//Cost is needed to find the maximum search depth.
    frontier.add(startState);
    frontierSet.add(startState);
    stateWithCost s=new stateWithCost(startState,0);
    parentMap.put(startState,s);
    while (!frontier.isEmpty()) {
      BitSet currentState = frontier.poll();
      int currentDepth=(int)parentMap.get(currentState).cost;
      if(currentDepth>maxDepth){
        maxDepth=currentDepth;
      }
      if ((currentState).equals(goalState)) {

        Stack<BitSet> path=getPath(parentMap,currentState);
        return new Object []{path,path.size()-1,explored.size(),maxDepth,0};//Running time will be set outside of the function
      }
      explored.add(currentState);
      ArrayList<BitSet> neighbors = findNeighbors(currentState);
      int pathCost=(int)parentMap.get(currentState).cost+1;//cost to reach this neighbour
      stateWithCost parentState=new stateWithCost(currentState,pathCost);

      for (BitSet neighbor : neighbors) {
        if(!explored.contains(neighbor) && ! frontierSet.contains(neighbor)) {
          frontier.add(neighbor);
          frontierSet.add(neighbor);
          parentMap.put(neighbor,parentState);
        }
      }
    }

    return new Object []{null,-1,explored.size(),maxDepth,0};
  }

  Object[] solveWithDFS(BitSet startState) {
    int maxDepth=0;
    HashSet<BitSet> explored=new HashSet<>();
    Stack<BitSet> frontier = new Stack<>();
    HashSet<BitSet> frontierSet=new HashSet<>();
    HashMap<BitSet,stateWithCost> parentMap=new HashMap<>();
    frontier.add(startState);
    frontierSet.add(startState);
    stateWithCost s=new stateWithCost(startState,0);
    parentMap.put(startState,s);
    while (!frontier.isEmpty()) {
      BitSet currentState = frontier.pop();
      int currentDepth=(int)parentMap.get(currentState).cost;
      if(currentDepth>maxDepth){
        maxDepth=currentDepth;
      }
      if ((currentState).equals(goalState)) {
        Stack<BitSet> path=getPath(parentMap,currentState);
        return new Object []{path,path.size()-1,explored.size(),maxDepth,0};
      }
      explored.add(currentState);
      ArrayList<BitSet> neighbors = findNeighbors(currentState);

      int pathCost=(int)parentMap.get(currentState).cost+1;//cost to reach this neighbour
      stateWithCost parentState=new stateWithCost(currentState,pathCost);
      for (BitSet neighbor : neighbors) {
        if(!explored.contains(neighbor) && !frontierSet.contains(neighbor)) {
          frontier.add(neighbor);
          frontierSet.add(neighbor);
          parentMap.put(neighbor,parentState);
        }
      }
    }

    return new Object []{null,-1,explored.size(),maxDepth,0};
  }





  int manhattanDistance(BitSet state) {
    int sum=0;
    for(int k=0;k<9;k++){
      BitSet current=state.get(4*k,4*k+4);
      int value=valueMap.get(current);
      int i=k/3;
      int j=k%3;
      if(value!=0){
        sum+=Math.abs(value/3-i)+Math.abs(value%3-j);
      }
    }
    return sum;
  }

  double euclideanDistance(BitSet state) {
    double sum=0;
    for(int k=0;k<9;k++){
      BitSet current=state.get(4*k,4*k+4);
      int value=valueMap.get(current);
      int i=k/3;
      int j=k%3;
      if(value!=0){
        sum+=Math.sqrt(Math.pow((value/3-i),2)+Math.pow((value%3-j),2));
      }
    }
    return sum;
  }

  Object[] solveWithAManhattan(BitSet state) {


    int maxDepth=0;//This integers holds the max search depth
    stateWithCost s = new stateWithCost(state, 0);//Goal State has cost 0 to be reached
    HashMap<BitSet, stateWithCost> parentMap = new HashMap<>();
    parentMap.put(state,s);//The parent of the initial state is the state itself
    s.cost=manhattanDistance(state);//Compute the manhattan distance from the state s to the goal state
    PriorityQueue<stateWithCost> frontier = new PriorityQueue(new stateWithCost());
    frontier.add(s);
    HashSet<stateWithCost> frontierSet=new HashSet<>();
    frontierSet.add(s);
    HashSet<BitSet> explored = new HashSet<>();



    while (!frontier.isEmpty()) {
      stateWithCost currentState = frontier.poll();//get state with minimum estimated cost
      int currentDepth=(int)parentMap.get(currentState.state).cost;
      if(currentDepth>maxDepth){
        maxDepth=currentDepth;
      }
      if(!explored.contains(currentState.state)) {
        if ((currentState.state).equals(goalState)) {//goal is found

          Stack<BitSet> path=getPath(parentMap,currentState.state);
          return new Object []{path,path.size()-1,explored.size(),maxDepth,0};
        }
        explored.add(currentState.state);
        ArrayList<BitSet> neighbours = findNeighbors(currentState.state);
        for (BitSet neighbour : neighbours) {

          int pathCost=(int)parentMap.get(currentState.state).cost+1;//cost to reach this neighbour
          double cost=pathCost+manhattanDistance(neighbour);//f(node)=g(node)+h(node)
          stateWithCost neighbourWithCost = new stateWithCost(neighbour,cost);
          stateWithCost parentState=new stateWithCost(currentState.state,pathCost);

          if (!explored.contains(neighbour) && !frontierSet.contains(neighbourWithCost)) {
            frontier.add(neighbourWithCost);
            frontierSet.add(neighbourWithCost);
            parentMap.put(neighbour, parentState);
          } else if (frontierSet.contains(neighbourWithCost)) {
            int oldCost=(int)parentMap.get(neighbour).cost;
            if(oldCost>pathCost){
              parentMap.replace(neighbour,parentState);
              frontier.add(neighbourWithCost);
              frontierSet.add(neighbourWithCost);
            }
          }
        }
      }
    }

    return new Object []{null,-1,explored.size(),maxDepth,0};
  }

  Object[] solveWithAEuclidean(BitSet state) {
    int maxDepth=0;//This integer holds the max search depth
    stateWithCost s = new stateWithCost(state, 0);//Goal State has cost 0 to be reached
    HashMap<BitSet, stateWithCost> parentMap = new HashMap<BitSet, stateWithCost>();
    parentMap.put(state,s);//The parent of the initial state is the state itself
    s.cost=euclideanDistance(state);//Compute the euclidean distance from the state s to the goal state
    PriorityQueue<stateWithCost> frontier = new PriorityQueue(new stateWithCost());
    frontier.add(s);
    HashSet<stateWithCost> frontierSet=new HashSet<>();
    frontierSet.add(s);
    HashSet<BitSet> explored = new HashSet<>();



    while (!frontier.isEmpty()) {
      stateWithCost currentState = frontier.poll();//get state with minimum estimated cost
      int currentDepth=(int)parentMap.get(currentState.state).cost;
      if(currentDepth>maxDepth){
        maxDepth=currentDepth;
      }
      if(!explored.contains(currentState.state)) {//Used to handle a special case where 2 equal states are present in the queue each of them having different cost
        if ((currentState.state).equals(goalState)) {//goal is found

          Stack<BitSet> path=getPath(parentMap,currentState.state);
          return new Object []{path,path.size()-1,explored.size(),maxDepth,0};
        }
        explored.add(currentState.state);
        ArrayList<BitSet> neighbours = findNeighbors(currentState.state);
        for (BitSet neighbour : neighbours) {

          int pathCost=(int)parentMap.get(currentState.state).cost+1;//cost to reach this neighbour
          double cost=pathCost+euclideanDistance(neighbour);//f(node)=g(node)+h(node)
          stateWithCost neighbourWithCost = new stateWithCost(neighbour,cost);
          stateWithCost parentState=new stateWithCost(currentState.state,pathCost);

          if (!explored.contains(neighbour) && !frontierSet.contains(neighbourWithCost)) {
            frontier.add(neighbourWithCost);
            frontierSet.add(neighbourWithCost);
            parentMap.put(neighbour, parentState);
          } else if (frontierSet.contains(neighbourWithCost)) {
            int oldCost=(int)parentMap.get(neighbour).cost;
            if(oldCost>pathCost){
              parentMap.replace(neighbour,parentState);
              frontier.add(neighbourWithCost);
              frontierSet.add(neighbourWithCost);
            }
          }
        }
      }
    }
    return new Object []{null,-1,explored.size(),maxDepth,0};
  }



  public Stack<BitSet> getPath(HashMap<BitSet, stateWithCost> parentMap, BitSet goalState) {
    Stack<BitSet> path = new Stack<>();
    path.add(goalState);
    BitSet currentState = goalState;
    while (!parentMap.get(currentState).state.equals(currentState)) {
      path.add(parentMap.get(currentState).state);
      currentState = parentMap.get(currentState).state;
    }
    return path;
  }
  public void printPath(Stack<BitSet> path){
    while(!path.isEmpty()){
      printState(path.pop());
    }
  }
  private void printState(BitSet state){
    for (int i = 0; i < 9; i++) {

      System.out.print(valueMap.get(state.get(4 * i, 4 * i + 4)));
      System.out.print(" ");
      if((i+1)%3==0){
        System.out.println();
      }
    }
    System.out.println();
  }
}
