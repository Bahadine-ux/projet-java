package tn.esprit.models;

import java.time.LocalDate; // Importer LocalDate pour gérer les dates

public class Reclamation {
    private int id;
    private String contenu;
    private String status;
    private String type;
    private int id_client;
    private LocalDate date; // Nouvelle propriété pour la date

    // Constructeur par défaut
    public Reclamation() {}

    // Constructeur avec tous les champs sauf date
    public Reclamation(int id, String contenu, String status, String type, int id_client) {
        this.id = id;
        this.contenu = contenu;
        this.status = status;
        this.type = type;
        this.id_client = id_client;
        this.date = LocalDate.now(); // Par défaut, date actuelle si non spécifiée
    }

    // Constructeur avec tous les champs, y compris date
    public Reclamation(int id, String contenu, String status, String type, int id_client, LocalDate date) {
        this.id = id;
        this.contenu = contenu;
        this.status = status;
        this.type = type;
        this.id_client = id_client;
        this.date = date;
    }

    // Getters et Setters pour les champs existants
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    // Getter et Setter pour la nouvelle propriété date
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date + // Ajout de la date dans toString
                '}';
    }
}