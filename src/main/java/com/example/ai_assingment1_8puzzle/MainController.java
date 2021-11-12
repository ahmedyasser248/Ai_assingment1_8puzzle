package com.example.ai_assingment1_8puzzle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;

public class MainController {
    int [] arr;
    @FXML
    private Label welcomeText;
    @FXML
    TextField field1;
    @FXML
    TextField field2;
    @FXML
    TextField field3;
    @FXML
    TextField field4;
    @FXML
    TextField field5;
    @FXML
    TextField field6;
    @FXML
    TextField field7;
    @FXML
    TextField field8;
    @FXML
    TextField field9;
    @FXML
    Button dfsButton;
    @FXML
    Button bfsButton;
    @FXML
    Button aStarButton;
    @FXML
    Button aStarEuc;
    @FXML
    Label alertLabel;




    @FXML
    public void pressedDfsButton()  {
        if(!checkFields()){
            showAlert();
            return;
        }
        openNewStage("DFS Process",1);
    }
    @FXML
    public void pressedBFSButton(){
        if(!checkFields()){
            showAlert();
            return;
        }
        openNewStage("BFS Process",2);

    }
    @FXML
    public void pressedAStarManButton(){
        if(!checkFields()){
            showAlert();
            return;
        }
       openNewStage("A*ManProcess",3);

    }
    @FXML
    public void pressedAStarEucButton(){
        if(!checkFields()){
            showAlert();
            return;
        }
        openNewStage("A*EucProcess",4);

    }
    public void showAlert(){
        alertLabel.setText("make a valid input that contains 0 and no duplicate");
    }
    public void openNewStage(String s,int buttonClicked){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("process_view.fxml"));
        try {
            ProcessController.arr=arr;
            ProcessController.buttonClicked=buttonClicked;
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle(s);
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean checkFields(){
        alertLabel.setText("");
        arr = new int[9];
        arr[0]=Integer.parseInt(field1.getText());
        arr[1]=Integer.parseInt(field2.getText());
        arr[2]=Integer.parseInt(field3.getText());
        arr[3]=Integer.parseInt(field4.getText());
        arr[4]=Integer.parseInt(field5.getText());
        arr[5]=Integer.parseInt(field6.getText());
        arr[6]=Integer.parseInt(field7.getText());
        arr[7]=Integer.parseInt(field8.getText());
        arr[8]=Integer.parseInt(field9.getText());
        boolean  foundedZero = false;
        for(int i = 0 ; i< 9 ; i++){
            if(arr[i]==0){
                foundedZero = true;
            }
            if(arr[i]>8){
                return false;
            }

        }
        int[] arrayToCheckDuplicates= new int[9];
        for (int  i = 0 ; i <  9 ; i++){
            arrayToCheckDuplicates[arr[i]]++;
            if(arrayToCheckDuplicates[arr[i]]>1){
                return false;
            }
        }

        return foundedZero;
    }
}