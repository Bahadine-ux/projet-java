package tn.esprit.models;

public class Client {
    private int id_client;
    private String nom;
    private String prenom;
    private String email;
    private String mot_de_passe;

    public Client(int id_client, String nom, String prenom, String email, String mot_de_passe) {
        this.id_client = id_client;
        this.nom = nom;
        this.prenom = prenom;
        setEmail(email);
        this.mot_de_passe = mot_de_passe;
    }

    public Client(String nom, String prenom, String email, String mot_de_passe) {
        this(0, nom, prenom, email, mot_de_passe);
    }

    public Client() {}

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email must contain '@'");
        }
        this.email = email;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        if (mot_de_passe == null || mot_de_passe.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.mot_de_passe = mot_de_passe;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id_client=" + id_client +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                '}';  // Password intentionally excluded from toString()
    }}