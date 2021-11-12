package com.example.ai_assingment1_8puzzle;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.*;

public class ProcessController implements Initializable {
    public static int[]arr;
    static int buttonClicked=0;
    MainRunner puzzleSolver = new MainRunner();
    Stack<BitSet>finalPath;
    @FXML
    Label label1;
    @FXML
    Label label2;
    @FXML
    Label label3;
    @FXML
    Label label4;
    @FXML
    Label label5;
    @FXML
    Label label6;
    @FXML
    Label label7;
    @FXML
    Label label8;
    @FXML
    Label label9;
    @FXML
    Label unsolvableLabel;
    @FXML
    Label pathCostLabel;
    @FXML
    Label numberOfNodesLabel;
    @FXML
    Label searchDepthLabel;
    @FXML
    Label RunningTimeLabel;

    Label [] labels = new Label[9];
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labels[0]=label1;
        labels[1]=label2;
        labels[2]=label3;
        labels[3]=label4;
        labels[4]=label5;
        labels[5]=label6;
        labels[6]=label7;
        labels[7]=label8;
        labels[8]=label9;
        for(int  i = 0 ; i < labels.length;i++){
            if(arr[i]==0){
                labels[i].setTextFill(Color.color(1,0,0));
            }else{
                labels[i].setTextFill(Color.color(0,0,0));
            }
            labels[i].setText(arr[i]+"");
        }
    }
    @FXML
    private void updateBoard(){
    if (!finalPath.isEmpty()){
        BitSet bitSet = finalPath.pop();
        int[]array = convertBitSetToArray(bitSet);
        for(int  i = 0 ; i < labels.length;i++){
            if(array[i]==0){
                labels[i].setTextFill(Color.color(1,0,0));
            }else{
                labels[i].setTextFill(Color.color(0,0,0));
            }
            labels[i].setText(array[i]+"");
        }
    }


    }

    public Object[] chooseWhichMethod(){
        Object[] results;
        BitSet initialBitSet = convertIntArrayToBitSet(arr);
        if(buttonClicked==1){
            results= puzzleSolver.DFS(initialBitSet);
        }else if(buttonClicked == 2){
            results = puzzleSolver.BFS(initialBitSet);
        }else if(buttonClicked == 3){
            results = puzzleSolver.AM(initialBitSet);
        }else{
            results = puzzleSolver.AE(initialBitSet);
        }
        return results;
    }
    @FXML
    public void startProcess(){
        Object[] results =chooseWhichMethod();
        if(results[0]==null){
         unsolvableLabel.setText("Cannot be solved");
         finalPath = new Stack<BitSet>();
            long runningTime=(long)results[4];
            int searchDepth=(int)results[3];
            int numberOfNodes =(int)results[2];
            numberOfNodesLabel.setText(numberOfNodesLabel.getText()+""+numberOfNodes);
            searchDepthLabel.setText(searchDepthLabel.getText()+""+searchDepth);
            RunningTimeLabel.setText(RunningTimeLabel.getText()+""+runningTime);
        }else {
            finalPath = (Stack<BitSet>) results[0];
            int pathCost =(int) results[1];
            int numberOfNodes =(int)results[2];
            int searchDepth=(int)results[3];
            long runningTime=(long)results[4];
            pathCostLabel.setText(pathCostLabel.getText()+""+pathCost);
            numberOfNodesLabel.setText(numberOfNodesLabel.getText()+""+numberOfNodes);
            searchDepthLabel.setText(searchDepthLabel.getText()+""+searchDepth);
            RunningTimeLabel.setText(RunningTimeLabel.getText()+""+runningTime);

        }


    }
    public BitSet convertIntArrayToBitSet (int [] array){
        BitSet bitSet = new BitSet(36);
        for (int  i = 0 ; i < 9 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                if(array[i]%2!=0){
                    bitSet.set(i*4+j);
                }
                array[i]=array[i]>>>1;
            }
        }
        return bitSet;
    }
    public int[] convertBitSetToArray(BitSet bitSet){
        int []array = new int[9];
        PuzzleSolver ps =new PuzzleSolver();
        HashMap<BitSet,Integer>intValues = ps.generateValueMap();
        for (int  i = 0 ; i < 9 ; i++){
            array[i]=intValues.get(bitSet.get(i*4,i*4+4));
        }
        return array;
    }

}
