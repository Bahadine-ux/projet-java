package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tn.esprit.models.MotifReclamation;
import tn.esprit.services.ServiceMotifReclamation;

import java.io.IOException;
import java.util.List;

public class ListMotifReclamationController {

    @FXML
    private TableView<MotifReclamation> motifTable;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label messageLabel;

    private ServiceMotifReclamation serviceMotif;

    public void initialize() {
        serviceMotif = new ServiceMotifReclamation();
        loadMotifs();

        // Enable/Disable modify and delete buttons based on selection
        motifTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean disable = newSelection == null;
            modifyButton.setDisable(disable);
            deleteButton.setDisable(disable);
        });

        // Hide the ID column
        motifTable.getColumns().get(0).setVisible(false);
    }

    private void loadMotifs() {
        List<MotifReclamation> motifs = serviceMotif.getAll();
        motifTable.getItems().setAll(motifs);
    }

    @FXML
    private void addMotif() {
        try {
            String path = "/AjouterMotifReclamation.fxml";
            System.out.println("Loading FXML: " + path);
            System.out.println("Resource URL: " + getClass().getResource(path));

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter un Motif de Réclamation");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table after adding
            loadMotifs();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture du formulaire : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void modifyMotif() {
        MotifReclamation selectedMotif = motifTable.getSelectionModel().getSelectedItem();
        if (selectedMotif == null) {
            messageLabel.setText("Veuillez sélectionner un motif !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            String path = "/ModifierMotifReclamation.fxml";
            System.out.println("Loading FXML: " + path);
            System.out.println("Resource URL: " + getClass().getResource(path));

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            ModifierMotifReclamationController controller = loader.getController();
            controller.setMotifReclamation(selectedMotif);
            Stage stage = new Stage();
            stage.setTitle("Modifier un Motif de Réclamation");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table after modifying
            loadMotifs();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture du formulaire : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteMotif() {
        MotifReclamation selectedMotif = motifTable.getSelectionModel().getSelectedItem();
        if (selectedMotif == null) {
            messageLabel.setText("Veuillez sélectionner un motif !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce motif ?");
        alert.setContentText("Cette action est irréversible.");
        if (alert.showAndWait().get().getButtonData().isCancelButton()) {
            return;
        }

        try {
            serviceMotif.delete(selectedMotif);
            messageLabel.setText("Motif supprimé avec succès !");
            messageLabel.setStyle("-fx-text-fill: green;");
            loadMotifs();
        } catch (Exception e) {
            messageLabel.setText("Erreur lors de la suppression : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
}