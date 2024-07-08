package com.example.esifolio2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

public class RechercheComboBox implements EventHandler<KeyEvent> {
   private ComboBox combo;
   final private ObservableList liste;
   private Integer i;

   public RechercheComboBox (final ComboBox combo){
       this.combo=combo;
       this.liste=combo.getItems();
       this.doRecherche();
   }

   public RechercheComboBox(final ComboBox combo , Integer i){
       this.combo=combo;
       this.liste=combo.getItems();
       this.i=i;
       this.doRecherche();
   }

    private void deplacerCaret(int longueur){
        this.combo.getEditor().positionCaret(longueur);
    }

   private void doRecherche(){
       this.combo.setEditable(true);
       this.combo.getEditor().focusedProperty().addListener((observable, oldValue, newValue) ->{
           if(newValue){
               this.combo.show();
           }
       } );
       this.combo.getEditor().setOnMouseClicked(mouseEvent -> {
           if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
               if(mouseEvent.getClickCount()==2){
                   return;
               }
           }
           this.combo.show();
       });
       this.combo.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->{
           deplacerCaret(this.combo.getEditor().getText().length());
       });
       this.combo.setOnKeyReleased(RechercheComboBox.this);
       if(this.i!=null){
           this.combo.getSelectionModel().select(this.i);
       }
   }

    private void setItems(){
        ObservableList observableList = FXCollections.observableArrayList();
        for(Object data : this.liste){
            String ch = this.combo.getEditor().getText().toLowerCase();
            if(data.toString().toLowerCase().contains(ch.toLowerCase())){
                observableList.add(data.toString());
            }
        }
        this.combo.setItems(observableList);
        this.combo.show();
    }

    @Override
    public void handle(KeyEvent event) {
        if(event.getCode()== KeyCode.UP || event.getCode()==KeyCode.DOWN || event.getCode()==KeyCode.RIGHT || event.getCode()==KeyCode.LEFT || event.getCode()==KeyCode.TAB){
            return;
        }
        if(event.getCode()==KeyCode.BACK_SPACE){
            String chaine = this.combo.getEditor().getText();
            if(chaine!=null){
                this.combo.getEditor().setText(chaine);
                deplacerCaret(chaine.length());
            }
            this.combo.getSelectionModel().clearSelection();
        }
        if(event.getCode()==KeyCode.ENTER && combo.getSelectionModel().getSelectedIndex()>-1){
            return;
        }
        setItems();
    }

}
