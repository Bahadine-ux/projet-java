package controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.models.Reponse;
import tn.esprit.services.ServiceReponse;

import java.io.IOException;
import java.util.List;

public class ListReponseController {

    @FXML
    private TableView<Reponse> reponseTable;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label messageLabel;

    private ServiceReponse serviceReponse;

    public void initialize() {
        serviceReponse = new ServiceReponse();
        loadReponses();

        // Enable/Disable modify and delete buttons based on selection
        reponseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean disable = newSelection == null;
            modifyButton.setDisable(disable);
            deleteButton.setDisable(disable);
        });

        // Customize the reclamation column to show the Reclamation ID
        TableColumn<Reponse, Integer> reclamationColumn = (TableColumn<Reponse, Integer>) reponseTable.getColumns().get(1);
        reclamationColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getReclamation() != null
                        ? cellData.getValue().getReclamation().getId()
                        : 0).asObject());

        // Hide the ID column
        reponseTable.getColumns().get(0).setVisible(false);
    }

    private void loadReponses() {
        List<Reponse> reponses = serviceReponse.getAll();
        reponseTable.getItems().setAll(reponses);
    }

    @FXML
    private void addReponse() {
        try {
            String path = "/AjouterReponse.fxml";
            System.out.println("Loading FXML: " + path);
            System.out.println("Resource URL: " + getClass().getResource(path));

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter une Réponse");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table after adding
            loadReponses();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture du formulaire : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void modifyReponse() {
        Reponse selectedReponse = reponseTable.getSelectionModel().getSelectedItem();
        if (selectedReponse == null) {
            messageLabel.setText("Veuillez sélectionner une réponse !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            String path = "/ModifierReponse.fxml";
            System.out.println("Loading FXML: " + path);
            System.out.println("Resource URL: " + getClass().getResource(path));

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            ModifierReponseController controller = loader.getController();
            controller.setReponse(selectedReponse);
            Stage stage = new Stage();
            stage.setTitle("Modifier une Réponse");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table after modifying
            loadReponses();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture du formulaire : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteReponse() {
        Reponse selectedReponse = reponseTable.getSelectionModel().getSelectedItem();
        if (selectedReponse == null) {
            messageLabel.setText("Veuillez sélectionner une réponse !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer cette réponse ?");
        alert.setContentText("Cette action est irréversible.");
        if (alert.showAndWait().get().getButtonData().isCancelButton()) {
            return;
        }

        try {
            serviceReponse.delete(selectedReponse);
            messageLabel.setText("Réponse supprimée avec succès !");
            messageLabel.setStyle("-fx-text-fill: green;");
            loadReponses();
        } catch (Exception e) {
            messageLabel.setText("Erreur lors de la suppression : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void goToListeReclamation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListReclamation.fxml"));
            Parent root = loader.load();

            ListReclamationController controller = loader.getController();

            Stage stage = (Stage) reponseTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Liste des Réclamations");
        } catch (IOException e) {
            messageLabel.setText("Erreur lors du chargement de la page des réclamations : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

}