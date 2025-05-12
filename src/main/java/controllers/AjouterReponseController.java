package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import tn.esprit.models.Client;
import tn.esprit.models.Reclamation;
import tn.esprit.models.Reponse;
import tn.esprit.services.EmailSender;
import tn.esprit.services.ServiceClient;
import tn.esprit.services.ServiceReclamation;
import tn.esprit.services.ServiceReponse;

import java.time.LocalDate;
import java.util.List;

public class AjouterReponseController {

    @FXML
    private ComboBox<Reclamation> reclamationComboBox;  // Sélection de la réclamation

    @FXML
    private TextArea reponseField;

    @FXML
    private DatePicker datePicker;  // Ajout du DatePicker pour la date

    @FXML
    private ComboBox<String> statutComboBox;  // Ajout du ComboBox pour le statut

    @FXML
    private Label messageLabel;

    private ServiceReponse serviceReponse;
    private ServiceReclamation serviceReclamation;
    private List<Reclamation> reclamations;

    public void initialize() {
        serviceReponse = new ServiceReponse();
        serviceReclamation = new ServiceReclamation();
        loadReclamations();
        loadStatutOptions();  // Charger les options de statut
    }

    private void loadReclamations() {
        reclamations = serviceReclamation.getAll();
        for (Reclamation reclamation : reclamations) {
            reclamationComboBox.getItems().add(reclamation);  // Ajouter les objets Reclamation
        }
    }

    private void loadStatutOptions() {
        statutComboBox.getItems().addAll(
                "En attente",
                "Traité",
                "Rejeté"
        );  // Options prédéfinies pour le statut
    }

    @FXML
    private void ajouterReponse() {
        Reclamation selectedReclamation = reclamationComboBox.getValue();
        String reponseText = reponseField.getText();
        LocalDate date = datePicker.getValue();  // Récupérer la date
        String statut = statutComboBox.getValue();  // Récupérer le statut

        // Vérification des champs obligatoires
        if (selectedReclamation == null || reponseText.isEmpty() || date == null || statut == null) {
            messageLabel.setText("Tous les champs sont obligatoires !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Créer une réponse avec date et statut
        Reponse reponse = new Reponse(0, selectedReclamation, reponseText, date, statut);
        try {
            serviceReponse.add(reponse);
            messageLabel.setText("Réponse ajoutée avec succès !");
            messageLabel.setStyle("-fx-text-fill: green;");

            ServiceClient serviceClient = new ServiceClient();
            Client client = serviceClient.getById(selectedReclamation.getId_client());
            System.out.println(client.getEmail());
            String recipientEmail = client.getEmail();
            String subject = "Réponse à votre réclamation #" + selectedReclamation.getId();
            String body = "Bonjour,\n\nNous avons répondu à votre réclamation :\n\n" + reponseText + "\n\nCordialement,\nL'équipe de support.";

            EmailSender.sendEmail(recipientEmail, subject, body);

            clearFieldsAndClose();
        } catch (Exception e) {
            messageLabel.setText("Erreur lors de l'ajout : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void cancel() {
        clearFieldsAndClose();
    }

    private void clearFieldsAndClose() {
        reclamationComboBox.getSelectionModel().clearSelection();
        reponseField.clear();
        datePicker.setValue(null);  // Réinitialiser le DatePicker
        statutComboBox.getSelectionModel().clearSelection();  // Réinitialiser le ComboBox du statut
        Stage stage = (Stage) reponseField.getScene().getWindow();
        stage.close();
    }
}