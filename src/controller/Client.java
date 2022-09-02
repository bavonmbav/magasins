
package controller;


public class Client {
    private String nom;
    private  String prenom;
    private  Integer telephone;
    private String adresse;
    private  Integer id;

    public Client(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Client(String nom, String prenom, Integer telephone, String adresse, Integer id) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client(String nom, String prenom, Integer telephone, String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Client() {
    }
    
    
  
}
