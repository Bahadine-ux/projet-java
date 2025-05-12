package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import tn.esprit.models.Client;
import tn.esprit.models.Reclamation;
import tn.esprit.services.ServiceReclamation;

import java.io.IOException;
import java.time.LocalDate;

public class AjouterReclamationController {

    @FXML
    private TextArea contenuField;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private ComboBox<String> motifComboBox;

    @FXML
    private DatePicker datePicker; // Ajout du DatePicker pour la date

    @FXML
    private Label messageLabel;

    private ServiceReclamation serviceReclamation;
    private Client currentClient;

    @FXML
    public void initialize() {
        serviceReclamation = new ServiceReclamation();
        loadMotifs();
        loadStatusOptions();
    }

    public void setClient(Client client) {
        this.currentClient = client;
    }

    private void loadMotifs() {
        motifComboBox.getItems().addAll(
                "Mauvais Service",
                "Produit Défendu",
                "Retard Livraison",
                "Erreur Facturation",
                "Autre"
        );
    }

    private void loadStatusOptions() {
        statusComboBox.getItems().addAll(
                "En attente",
                "Annule",
                "Traité"
        );
    }

    @FXML
    private void ajouterReclamation() {
        String contenu = contenuField.getText();
        String status = statusComboBox.getValue();
        String type = motifComboBox.getValue();
        LocalDate date = datePicker.getValue(); // Récupérer la date du DatePicker

        // Vérification des champs, y compris la date
        if (contenu.isEmpty() || status == null || type == null || date == null) {
            messageLabel.setText("Tous les champs sont obligatoires !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Créer une réclamation avec la date
        Reclamation reclamation = new Reclamation(0, contenu, status, type, currentClient.getId_client(), date);
        try {
            serviceReclamation.add(reclamation);
            messageLabel.setText("Réclamation ajoutée avec succès !");
            messageLabel.setStyle("-fx-text-fill: green;");
            clearFields();
        } catch (Exception e) {
            messageLabel.setText("Erreur lors de l'ajout : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void annuler() {
        clearFields();
        messageLabel.setText("");
    }

    @FXML
    private void goToList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListReclamation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Liste des Réclamations");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) contenuField.getScene().getWindow();
            currentStage.close();
        } catch (IOException | NullPointerException e) {
            messageLabel.setText("Erreur lors de l'ouverture de la liste : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        contenuField.clear();
        statusComboBox.getSelectionModel().clearSelection();
        motifComboBox.getSelectionModel().clearSelection();
        datePicker.setValue(null); // Réinitialiser le DatePicker
    }
}