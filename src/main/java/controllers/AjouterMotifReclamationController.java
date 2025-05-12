package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.MotifReclamation;
import tn.esprit.services.ServiceMotifReclamation;

public class AjouterMotifReclamationController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField typeField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button submitButton;

    private ServiceMotifReclamation serviceMotif;

    @FXML
    public void initialize() {
        serviceMotif = new ServiceMotifReclamation();
    }

    @FXML
    private void ajouterMotif() {
        // Validate input
        String nom = nomField.getText().trim();
        String description = descriptionField.getText().trim();
        String type = typeField.getText().trim();

        if (nom.isEmpty() || description.isEmpty() || type.isEmpty()) {
            messageLabel.setText("Veuillez remplir tous les champs !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            MotifReclamation motif = new MotifReclamation(0, nom, description, type);

            serviceMotif.add(motif);
            messageLabel.setText("Motif ajouté avec succès !");
            messageLabel.setStyle("-fx-text-fill: green;");

            // Clear the form
            nomField.clear();
            descriptionField.clear();
            typeField.clear();
        } catch (Exception e) {
            messageLabel.setText("Erreur lors de l'ajout : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void annuler() {
        // Close the window
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }
}