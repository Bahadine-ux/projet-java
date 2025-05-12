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
import tn.esprit.models.Client;
import tn.esprit.models.Reclamation;
import tn.esprit.services.ServiceReclamation;

import java.io.IOException;
import java.util.List;

public class ListReclamationController {

    @FXML
    private TableView<Reclamation> reclamationTable;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label messageLabel;

    @FXML
    private Label titleLabel;


    private ServiceReclamation serviceReclamation;
    private Client currentClient;

    public void setClient(Client client) {
        this.currentClient = client;
        if (titleLabel != null) {
            titleLabel.setText("Liste des Réclamations de " + currentClient.getNom());
        }
    }

    public void initialize() {
        serviceReclamation = new ServiceReclamation();
        loadReclamations();

        // Enable/Disable modify and delete buttons based on selection
        reclamationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean disable = newSelection == null;
            modifyButton.setDisable(disable);
            deleteButton.setDisable(disable);
        });

        // Hide the ID column
        reclamationTable.getColumns().get(0).setVisible(false);
    }

    private void loadReclamations() {
        List<Reclamation> reclamations = serviceReclamation.getAll();
        reclamationTable.getItems().setAll(reclamations);
    }

    @FXML
    private void addReclamation() {
        try {
            String path = "/AjouterReclamation.fxml";
            System.out.println("Loading FXML: " + path);
            System.out.println("Resource URL: " + getClass().getResource(path));

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            loader.setControllerFactory(controllerClass -> new AjouterReclamationController()); // Manually create the controller
            Parent root = loader.load();

            AjouterReclamationController controller = loader.getController();
            controller.setClient(currentClient);
            Stage stage = new Stage();
            stage.setTitle("Ajouter une Réclamation");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table after adding
            loadReclamations();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture du formulaire : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void modifyReclamation() {
        Reclamation selectedReclamation = reclamationTable.getSelectionModel().getSelectedItem();
        if (selectedReclamation == null) {
            messageLabel.setText("Veuillez sélectionner une réclamation !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            String path = "/ModifierReclamation.fxml";
            System.out.println("Loading FXML: " + path);
            System.out.println("Resource URL: " + getClass().getResource(path));

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            ModiferReclamationController controller = loader.getController();
            controller.setReclamation(selectedReclamation);
            Stage stage = new Stage();
            stage.setTitle("Modifier une Réclamation");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table after modifying
            loadReclamations();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture du formulaire : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteReclamation() {
        Reclamation selectedReclamation = reclamationTable.getSelectionModel().getSelectedItem();
        if (selectedReclamation == null) {
            messageLabel.setText("Veuillez sélectionner une réclamation !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer cette réclamation ?");
        alert.setContentText("Cette action est irréversible.");
        if (alert.showAndWait().get().getButtonData().isCancelButton()) {
            return;
        }

        try {
            serviceReclamation.delete(selectedReclamation);
            messageLabel.setText("Réclamation supprimée avec succès !");
            messageLabel.setStyle("-fx-text-fill: green;");
            loadReclamations();
        } catch (Exception e) {
            messageLabel.setText("Erreur lors de la suppression : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void goToListeReponse() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListReponse.fxml"));
            Parent root = loader.load();

            ListReponseController controller = loader.getController();
            Stage stage = (Stage) reclamationTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Liste des Réponses");
        } catch (IOException e) {
            messageLabel.setText("Erreur lors du chargement de la page des réponses : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

}