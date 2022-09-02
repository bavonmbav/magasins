/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Article;
import controller.Client;
import controller.Connections;
import controller.Model;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;


public class ClientsController implements Initializable {

    @FXML
    private TableView<Client> tableauClient;
    @FXML
    private TableColumn<Client, String> noms;
    @FXML
    private TableColumn<Client, String> prenom;
    @FXML
    private TableColumn<Client, String> adresses;
    @FXML
    private TableColumn<Client, Integer> telephones;
    @FXML
    private AnchorPane parents;
     Model model = new Model();
        private Connections connections;
    
    ObservableList<Client> liste = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            selecte();
    }    

    @FXML
    private void getADD(ActionEvent event) {
            String page = "/vue/clientAdd";
          String titre = "ajouter Client";
          model.stage(page, titre);
    }

    @FXML
    private void getDelete(ActionEvent event) {
    }

    @FXML
    private void getUpdate(ActionEvent event) {
          Client client = tableauClient.getSelectionModel().getSelectedItem();
         String page = "/vue/clientUpdate";
        if (client == null) {
               JOptionPane.showMessageDialog(null,"veuillez choisir une conlonne");
                 return;
        }
        else {
          
            try {
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource(page+".fxml"));
                Parent parent = loader.load();
                ClientUpdateController controller = (ClientUpdateController) loader.getController();
                controller.inflateUI(client);
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("Edit Client");
                stage.setScene(new Scene(parent));
                stage.show();   
                stage.setOnHidden((e)->{
                    getRefrsh(new ActionEvent());
                });
            
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }

    @FXML
    private void getProduit(ActionEvent event) {
          Stage stage = (Stage) parents.getScene().getWindow();
         stage.close();
         String page = "/vue/forme";
          String titre = "Produit";
          model.stage(page, titre);
    }
    
 public void select()
    {
        liste.clear();
           String select = "SELECT * FROM clients";
           Connections conne  = Connections.getInstance();
           ResultSet rs = conne.execQuery(select);
          try {
              while(rs.next()) {    
                                 String noms = rs.getString("nom");
                                 String prenoms = rs.getString("prenom");
                                 Integer tele = rs.getInt("tel");
                                 String adres = rs.getString("adresse");
                                 
                       liste.addAll(new Client(noms, prenoms, tele, adres));
              }
                     
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        tableauClient.setItems(liste);
    }
    
    
     public void selecte()
     {
          noms.setCellValueFactory(new PropertyValueFactory<>("nom"));   
          prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
          adresses.setCellValueFactory(new PropertyValueFactory<>("adresse"));
          telephones.setCellValueFactory(new PropertyValueFactory<>("telephone"));   
          select();
     }

    @FXML
    private void getRefrsh(ActionEvent event) {
        selecte();
    }

    
}
