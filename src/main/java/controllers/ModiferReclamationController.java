package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import tn.esprit.models.Reclamation;

public class ModiferReclamationController {

    @FXML
    private TextArea contenuField;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private ComboBox<String> motifComboBox;

    @FXML
    private Label messageLabel;

    private Reclamation reclamationToModify;

    public void setReclamation(Reclamation reclamation) {
        this.reclamationToModify = reclamation;
        loadMotifs();
        loadStatuses();
        loadReclamationData();
    }

    private void loadMotifs() {
        motifComboBox.getItems().clear();
        motifComboBox.getItems().addAll("Mauvais Service", "Produit Défectueux", "Facturation", "Retard Livraison", "Autre");
    }

    private void loadStatuses() {
        statusComboBox.getItems().clear();
        statusComboBox.getItems().addAll("En attente", "Annulé", "Traité");
    }

    private void loadReclamationData() {
        if (reclamationToModify != null) {
            contenuField.setText(reclamationToModify.getContenu());
            statusComboBox.setValue(reclamationToModify.getStatus());
            motifComboBox.setValue(reclamationToModify.getType());
        }
    }

    @FXML
    private void saveChanges() {
        if (reclamationToModify == null) {
            messageLabel.setText("Aucune réclamation sélectionnée !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String contenu = contenuField.getText();
        String status = statusComboBox.getValue();
        String type = motifComboBox.getValue();

        if (contenu.isEmpty() || status == null || type == null) {
            messageLabel.setText("Tous les champs sont obligatoires !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        reclamationToModify.setContenu(contenu);
        reclamationToModify.setStatus(status);
        reclamationToModify.setType(type);

        try {
            new tn.esprit.services.ServiceReclamation().update(reclamationToModify);
            messageLabel.setText("Réclamation modifiée avec succès !");
            messageLabel.setStyle("-fx-text-fill: green;");
            clearFieldsAndClose();
        } catch (Exception e) {
            messageLabel.setText("Erreur lors de la modification : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void cancel() {
        clearFieldsAndClose();
    }

    private void clearFieldsAndClose() {
        contenuField.clear();
        statusComboBox.getSelectionModel().clearSelection();
        motifComboBox.getSelectionModel().clearSelection();
        Stage stage = (Stage) contenuField.getScene().getWindow();
        stage.close();
    }
}
