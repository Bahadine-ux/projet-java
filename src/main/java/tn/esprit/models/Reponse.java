package tn.esprit.models;

import java.time.LocalDate; // Importer LocalDate pour gérer les dates

public class Reponse {
    private int id;
    private Reclamation reclamation;
    private String reponse;
    private LocalDate date; // Nouvelle propriété pour la date
    private String statut;  // Nouvelle propriété pour le statut

    public Reponse() {}

    // Constructeur avec les champs originaux
    public Reponse(int id, Reclamation reclamation, String reponse) {
        this.id = id;
        this.reclamation = reclamation;
        this.reponse = reponse;
        this.date = LocalDate.now(); // Par défaut, date actuelle
        this.statut = "En attente";  // Par défaut, statut initial
    }

    // Constructeur avec tous les champs, y compris date et statut
    public Reponse(int id, Reclamation reclamation, String reponse, LocalDate date, String statut) {
        this.id = id;
        this.reclamation = reclamation;
        this.reponse = reponse;
        this.date = date;
        this.statut = statut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    // Getter et Setter pour la date
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Getter et Setter pour le statut
    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", reclamation=" + (reclamation != null ? reclamation.getId() : "null") +
                ", reponse='" + reponse + '\'' +
                ", date=" + date +
                ", statut='" + statut + '\'' +
                '}';
    }
}