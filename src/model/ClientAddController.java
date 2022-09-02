/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Client;
import controller.Connections;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class ClientAddController implements Initializable {

    @FXML
    private VBox parent;
    @FXML
    private TextField textnom;
    @FXML
    private TextField textprenom;
    @FXML
    private TextField textphone;
    @FXML
    private TextField texteAdr;

        public boolean reponse()
    {
      String nom = textnom.getText();
      String prenom = textprenom.getText();
      String tel = textphone.getText();
      String adre = texteAdr.getText();
        String requette = "INSERT INTO clients (nom,prenom,tel,adresse) VALUES(?,?,?,?)";
         try{
         PreparedStatement pst = Connections.getInstance().getConnection().prepareStatement(requette);
         pst.setString(1, nom);
         pst.setString(2, prenom);
         pst.setString(3, tel);
         pst.setString(4,adre);
         pst.executeUpdate();
             JOptionPane.showMessageDialog(null,"vous avez inseret Mr : " + nom + ", Prenom : " + prenom + " Address :"+adre);
          
          }catch(Exception ex)
        {
            ex.printStackTrace();
        } 
         return true;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void getADD(ActionEvent event) {
         Stage stage = (Stage) parent.getScene().getWindow();
         stage.close();
      String nom = textnom.getText();
      String prenom = textprenom.getText();
      String tel = textphone.getText();
      String adre = texteAdr.getText();
  
     
          if (nom.isEmpty() || nom.isEmpty() || prenom.isEmpty()|| adre.isEmpty()) {
              JOptionPane.showMessageDialog(null, "y a des case qui sont vide");
            return;
        }
       boolean result = reponse();
        if (result) {
             JOptionPane.showMessageDialog(null, "insertion bien effectuer");
       
        }
        else
           JOptionPane.showMessageDialog(null, "pas de modication ou insertion");  
    }

    @FXML
    private void getCancel(ActionEvent event) {
            Stage stage = (Stage) parent.getScene().getWindow();
            stage.close();
    }
    
    
     
    }  


