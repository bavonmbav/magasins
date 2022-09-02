
package model;

import controller.Client;
import controller.Connections;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ClientUpdateController implements Initializable {

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
    @FXML
    private Text textID;

    Tooltip infos = new Tooltip();
    
    public void update() {
                 Client c = new Client();
                String nom = textnom.getText();
                String prenom = textprenom.getText();
                String tel = textphone.getText();
                String adre = texteAdr.getText();
               Integer id = Integer.parseInt(textID.getText());
              
             
        try {
            String update = "UPDATE clients SET nom=?, prenom=?, tel=?,adresse=? WHERE ID=?";
             PreparedStatement pst = Connections.getInstance().getConnection().prepareStatement(update);
          
         pst.setString(1, nom);
         pst.setString(2, prenom);
         pst.setString(3, tel);
         pst.setString(4, adre);
         
        pst.setInt(5, id);
               
         pst.executeUpdate();
         
                     JOptionPane.showMessageDialog(null,"avec succes la mise a jour");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
        
    }    

    @FXML
    private void getADD(ActionEvent event) {
            Stage stage = (Stage) parent.getScene().getWindow();
             stage.close();
   
                String nom = textnom.getText();
                String prenom = textprenom.getText();
                String tel = textphone.getText();
                String adre = texteAdr.getText();
           
   
          if (nom.isEmpty() || prenom.isEmpty() || tel.isEmpty()|| adre.isEmpty()) {
              JOptionPane.showMessageDialog(null, "y a des case qui sont vide");
            return;
        }
      else{
            update();
          
            return;
        }
    }

    @FXML
    private void getCancel(ActionEvent event) {
            Stage stage = (Stage) parent.getScene().getWindow();
            stage.close();
    }
    public void inflateUI(Client client) {
        textnom.setText(client.getNom());
        textprenom.setText(client.getPrenom());
        textphone.setText(""+client.getTelephone());
        texteAdr.setText(client.getAdresse()); 
        textID.setText(""+client.getId());
    }
}
