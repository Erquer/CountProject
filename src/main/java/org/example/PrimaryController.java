package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class PrimaryController {


    @FXML
    Button liczButton;
    @FXML
    TextField koszt;

    @FXML
    RadioButton wycinanie;
    @FXML
    VBox wycinanieBox;
    @FXML
    TextField dlugosc;
    @FXML
    ChoiceBox<String > grubosc;
    Map<Integer,Double> czasyGrubosci = new HashMap<>();

    ToggleGroup grupa = new ToggleGroup();

    @FXML
    RadioButton grawerowanie;
    @FXML
    VBox grawerowanieBox;
    @FXML
    TextField grawerSzerokosc;
    @FXML
    TextField grawerDlugosc;

    public void initialize(){
        grupa.getToggles().addAll(grawerowanie,wycinanie);
        wycinanie.setSelected(true);
        if(wycinanie.selectedProperty().getValue()){
            grawerowanieBox.setDisable(true);
            wycinanieBox.setDisable(false);
        }
        wycinanie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(wycinanie.selectedProperty().getValue()){
                    grawerowanieBox.setDisable(true);
                    wycinanieBox.setDisable(false);
                }else{
                    grawerowanieBox.setDisable(false);
                    wycinanieBox.setDisable(true);
                }
            }
        });
        grawerowanie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(grawerowanie.selectedProperty().getValue()){
                    grawerowanieBox.setDisable(false);
                    wycinanieBox.setDisable(true);
                }
            }
        });

        //inicjalizacja grubosci i dodanie do choiceboxa
        czasyGrubosci.put(10,4.0);
        czasyGrubosci.put(8,2.5);
        czasyGrubosci.put(6,2.0);
        czasyGrubosci.put(5,1.5);
        czasyGrubosci.put(4,1.16);
        czasyGrubosci.put(3,0.58);
        czasyGrubosci.put(2,0.43);
        for(Integer e: czasyGrubosci.keySet()){
            grubosc.getItems().add(e.toString());
        }
        grubosc.getSelectionModel().selectFirst();
        dlugosc.textProperty().addListener((ov, oldVal, newVal)->{
            String nV = dlugosc.getText();
            if(!newVal.matches("[.,0-9]")){
                //if(newVal.matches(".*,.*")) newVal.replaceAll(",",".");
                    dlugosc.setText(newVal.replaceAll("[^,.0-9]",""));

            }
        });
        grawerDlugosc.textProperty().addListener((ov, oldVal, newVal)->{
           // String nV = dlugosc.getText();
            if(!newVal.matches("[.,0-9]")){
                //if(newVal.matches(".*,.*")) newVal.replaceAll(",",".");
                grawerDlugosc.setText(newVal.replaceAll("[^,.0-9]",""));

            }
        });
        grawerSzerokosc.textProperty().addListener((ov, oldVal, newVal)->{
           // String nV = dlugosc.getText();
            if(!newVal.matches("[.,0-9]")){
                //if(newVal.matches(".*,.*")) newVal.replaceAll(",",".");
                grawerSzerokosc.setText(newVal.replaceAll("[^,.0-9]",""));

            }
        });



    }

    public void countCost(){
        if(wycinanie.selectedProperty().getValue()) {
            Double time = czasyGrubosci.get(Integer.parseInt(grubosc.getSelectionModel().getSelectedItem()));
            System.out.println(time);
            String temp = dlugosc.getText();
            temp = temp.replace(',', '.');
            Double dl = Double.parseDouble(temp) /100 ;
            System.out.println(dl);
            Double reqTime = dl * time * 60 / 3600;

            Double cost = 200.0 * reqTime;
            System.out.println(cost);
            cost = Double.valueOf(Math.round(cost * 100)) / 100;
            System.out.println(cost);
            koszt.setText(cost.toString());
        }else if(grawerowanie.selectedProperty().getValue()){
            String temp = grawerSzerokosc.getText();
            temp = temp.replace(',', '.');
            Double szer = Double.parseDouble(temp);
            temp = grawerDlugosc.getText();
            temp = temp.replace(',', '.');
            Double dlu = Double.parseDouble(temp);
            /*
                pole = x * y

             */
            Double pole = szer * dlu; //
            Double stala = 1000.0; //mm^2
            System.out.println(pole);
            pole = pole/stala; // obliczenie ile pól znajduje się w naszym stałym polu.
            Double czas = pole * 47/60;
            System.out.println(czas);
            Double koszty = Double.valueOf(Math.round(200 * czas * 100))/100;
            System.out.println(koszty);
            koszt.setText(koszty.toString());

        }


    }



    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
