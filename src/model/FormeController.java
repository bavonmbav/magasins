
package model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controller.Article;
import controller.Connections;
import controller.Model;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;


public class FormeController implements Initializable {

    Connections con = new Connections();
    @FXML
    private TextField recherche;
    @FXML
    private TableView<Article> tableaux;
    @FXML
    private TableColumn<Article, String> code;
    @FXML
    private TableColumn<Article, String> nom;
    @FXML
    private TableColumn<Article, Integer> prix;
    @FXML
    private TableColumn<Article, String> amballage;

     Model model = new Model();
    private Connections connections;
    
    ObservableList<Article> liste = FXCollections.observableArrayList();
    @FXML
    private BorderPane parent;
      //=========recherche filter==========//
      public void rechercheFilter()
      {
          FilteredList<Article> eleve = new FilteredList<>(liste, b->true);
          recherche.textProperty().addListener((observable,ancienValuer,valeur)->{
              eleve.setPredicate((Article e) -> {
                  if(valeur == null || valeur.isEmpty()){
                      return true;
                  }
                  String amener = valeur.toLowerCase();
                  int val = valeur.charAt(0);
                  if (e.getCode().toLowerCase().contains(amener)) {
                      return true;
                  }
                  else if(e.getNom().toLowerCase().contains(amener)){
                      return true;
                  }
                  else if(e.getAmballage().toLowerCase().contains(amener)){
                      return true;
                  }
                  return false;
              }); 
          });    
        SortedList<Article> sorte = new SortedList<>(eleve);
        sorte.comparatorProperty().bind(tableaux.comparatorProperty());
        tableaux.setItems(sorte);
      }
      
      
    
    
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connections = Connections.getInstance();
       selecte();
       rechercheFilter();
    }    

    @FXML
    private void getADD(ActionEvent event) {
          String page = "/vue/formulaire1";
          String titre = "ajouter Article";
          model.stage(page, titre);
    }

    @FXML
    private void getDelete(ActionEvent event) {
          Article article = tableaux.getSelectionModel().getSelectedItem();
          if (article == null) {
               JOptionPane.showMessageDialog(null,"veuillez choisir une conlonne");
                 return;
        }
          else
          {                
                   liste.removeAll(article);
                  connections.deleteArticle(article);
                   selecte();
          }
        
    }

    @FXML
    private void getUpdate(ActionEvent event) {
         Article article = tableaux.getSelectionModel().getSelectedItem();
         String page = "/vue/Formulaire";
        if (article == null) {
               JOptionPane.showMessageDialog(null,"veuillez choisir une conlonne");
                 return;
        }
        else {
          
            try {
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource(page+".fxml"));
                Parent parent = loader.load();
                FormulaireController controller = (FormulaireController) loader.getController();
                controller.inflateUI(article);
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("Edit Article");
                stage.setScene(new Scene(parent));
                stage.show();   
                stage.setOnHidden((e)->{
                    getRefresh(new ActionEvent());
                });
            
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
    public void select()
    {
        liste.clear();
           String select = "SELECT * FROM articles";
           Connections conne  = Connections.getInstance();
           ResultSet rs = conne.execQuery(select);
          try {
              while(rs.next()) {    
                                 String codes = rs.getString("code");
                                 String noms = rs.getString("nomProduit");
                                 Integer prixs = rs.getInt("prix");
                                 String amballer = rs.getString("amballage");
                                 
                        liste.addAll(new Article(codes,noms,prixs,amballer));
              }
                     
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        tableaux.setItems(liste);
    }
    
    
     public void selecte()
     {
          code.setCellValueFactory(new PropertyValueFactory<>("code"));   
          nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
          prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
          amballage.setCellValueFactory(new PropertyValueFactory<>("amballage"));   
          select();
     }

    @FXML
    private void getRefresh(ActionEvent event) {
        selecte();
    }

    @FXML
    private void getImprimer(ActionEvent event) {
          Article article = tableaux.getSelectionModel().getSelectedItem();
        if (article == null) {
               JOptionPane.showMessageDialog(null,"veuillez choisir une conlonne");
                 return;
        }
        else {
        
            try {
            String chemin = "Facture.pdf";
            String images = "C:\\Users\\GB\\Documents\\NetBeansProjects\\magasins\\src\\image\\moto.png";
            Image igm = Image.getInstance(images);
            LocalDate date=LocalDate.now();
            Document doc = new  Document();
            PdfWriter.getInstance(doc, new FileOutputStream(chemin));
            doc.open();

            doc.add(igm);
                    doc.add(new Paragraph("                                                                                                        Date : " +date));
            doc.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            
           Paragraph par = new Paragraph("ETABLISEMENT NATHAN");
            doc.add(par);
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Av : munama \n Q: lubumbashi \n N* : 123 "));
            doc.add(new Paragraph("Bienvenue chez Nathan.net"));
    
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("                                          NOM du produit :" + article.getNom()));
            doc.add(new Paragraph(" "));
                 doc.add(new Paragraph("                                    -------------------------------------"));
            doc.add(new Paragraph("\t\t\t                                       prix du produit :  " + article.getPrix()));
            doc.add(new Paragraph(" "));
                 doc.add(new Paragraph("                                    -------------------------------------"));
            doc.add(new Paragraph("\t\t\t                                        code : "+article.getCode()));
            doc.add(new Paragraph(" "));
                 doc.add(new Paragraph("                                    -------------------------------------"));
            doc.add(new Paragraph("\t\t\t                                       Amballage : "+article.getAmballage()));
            doc.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));          
             doc.close();
             Desktop.getDesktop().open(new File(chemin));
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(FormeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (DocumentException ex) {
            Logger.getLogger(FormeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(FormeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    }

    @FXML
    private void getExporte(ActionEvent event) {
            String select = "SELECT * FROM articles";
           Connections conne  = Connections.getInstance();
           ResultSet rs = conne.execQuery(select);
        
            try {
                
            String images = "C:\\Users\\GB\\Documents\\NetBeansProjects\\magasins\\src\\image\\moto.png";
            Image igm = Image.getInstance(images);
                
            String chemin = "Liste_Article.pdf";
            LocalDate date=LocalDate.now();
            Document doc = new  Document();
            PdfWriter.getInstance(doc, new FileOutputStream(chemin));
            doc.open();
             doc.add(igm);
                    doc.add(new Paragraph("                                                                                                        Date : " +date));
            doc.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            
           Paragraph par = new Paragraph("ETABLISEMENT NATHAN");
            doc.add(par);
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Av : munama \n Q: lubumbashi \n N* : 123 "));
            doc.add(new Paragraph("Bienvenue chez Nathan.net"));
         
            PdfPTable tables = new PdfPTable(3);
            PdfPCell cel = new PdfPCell(new Phrase("CODE",FontFactory.getFont("Comic Sans MS",12)));
              cel.setHorizontalAlignment(Element.ALIGN_CENTER);
              cel.setBackgroundColor(BaseColor.BLUE);
              cel.setPaddingLeft(30);
              tables.addCell(cel);
              
              cel = new PdfPCell(new Phrase("NOM_PRODUIT",FontFactory.getFont("Comic Sans MS",12)));
              cel.setHorizontalAlignment(Element.ALIGN_CENTER);
              cel.setBackgroundColor(BaseColor.BLUE);
              cel.setBorderColor(BaseColor.GREEN);
              cel.setPaddingLeft(30);
              
              tables.addCell(cel);
              
              cel = new PdfPCell(new Phrase("PRIX",FontFactory.getFont("Comic Sans MS",12)));
              cel.setHorizontalAlignment(Element.ALIGN_CENTER);
              cel.setBackgroundColor(BaseColor.BLUE);
              cel.setBorderColor(BaseColor.GREEN);
              cel.setPaddingLeft(30);
              tables.addCell(cel);
              
              cel = new PdfPCell(new Phrase("AMBALLAGE",FontFactory.getFont("Comic Sans MS",12)));
              cel.setHorizontalAlignment(Element.ALIGN_CENTER);
              cel.setBackgroundColor(BaseColor.BLUE);
              cel.setPaddingLeft(30);
              cel.setBorderColor(BaseColor.GREEN);
              tables.addCell(cel);
              
              tables.setHeaderRows(1);
              tables.setWidthPercentage(100);
            
                            while(rs.next()) {    
                                 String codes = rs.getString("code");
                                 String noms = rs.getString("nomProduit");
                                 Integer prixs = rs.getInt("prix");
                                 String amballer = rs.getString("amballage");
                                    tables.addCell(codes);
                                    tables.addCell(noms);
                                    tables.addCell(prixs.toString());
                                    tables.addCell(amballer);
                        
              } 
             doc.add(tables); 
             doc.close();
             Desktop.getDesktop().open(new File(chemin));
        }
        catch (Exception ex) {
            Logger.getLogger(FormeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void getClient(ActionEvent event) {
         Stage stage = (Stage) parent.getScene().getWindow();
         stage.close();
         String page = "/vue/clients";
          String titre = "Table client";
          model.stage(page, titre);
    }
}
