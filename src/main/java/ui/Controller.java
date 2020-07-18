package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import service.Contacts;
import service.Service;
import service.ServiceUtility;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    private Service service = new Service();
    @FXML
    private VBox vBox;
    @FXML
    private TableView<Contacts> tableView;


    //bind Contacts data with tableView
    public void listContacts() {
        Task<ObservableList<Contacts>> task = new Task<ObservableList<Contacts>>() {
            @Override
            protected ObservableList<Contacts> call() throws Exception {
                return FXCollections.observableArrayList(service.getContacts());
            }
        };

        tableView.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    @FXML
    public void addContacts() throws IOException {
        //Create a new dialog window to add users
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(vBox.getScene().getWindow());
        dialog.setTitle("Add New Contact");
        dialog.setHeaderText("Enter information for new contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/addContact.fxml"));
        dialog.getDialogPane().setContent(fxmlLoader.load());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> optionalButtonType = dialog.showAndWait();
        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.OK) {
            DialogController dialogController = fxmlLoader.getController();
            Contacts contacts = dialogController.getNewContact();
            if (contacts == null) {
                return;
            }
            String name = contacts.getName();
            long phone = contacts.getPhone();
            String phoneRegex = "" + phone;
            String location = ServiceUtility.getInstance().phoneNumbersRegex(phoneRegex);
            if (location == null) {
                location = "Unknown";
            }
            service.insertContact(name, phone, location);
            listContacts();
        }
    }

    @FXML
    public void editContact() {
        Contacts contact = tableView.getSelectionModel().getSelectedItem();
        if (contact == null) {
            Alert alert = new Alert((Alert.AlertType.INFORMATION));
            alert.setTitle("Select Contact");
            alert.setHeaderText("No Contact Selected");
            alert.setContentText("Please Select the contact you want to edit");
            alert.showAndWait();
            return;
        }
        String oldName = contact.getName();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(vBox.getScene().getWindow());
        dialog.setTitle("Edit Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/addContact.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        DialogController dialogController = fxmlLoader.getController();
        dialogController.editContact(contact);
        Optional<ButtonType> optionalButtonType = dialog.showAndWait();
        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.OK) {
            if (dialogController.updateContact(contact)) {
                String name = contact.getName();
                long phone = contact.getPhone();
                String phoneRegex = ServiceUtility.getInstance().phoneNumbersRegex("" + phone);
                String location = (phoneRegex == null) ? "Unknown" : phoneRegex;
                service.editContact(oldName, name, phone, location);
                listContacts();
                System.out.println("Edited successfully");
            }
        }
    }

    @FXML
    public void deleteContact() {
        Contacts contact = tableView.getSelectionModel().getSelectedItem();
        if (contact == null) {
            Alert alert = new Alert((Alert.AlertType.INFORMATION));
            alert.setTitle("Select Contact");
            alert.setHeaderText("No Contact Selected");
            alert.setContentText("Please select the contact you want to delete!");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete : " + contact.getName());
        Optional<ButtonType> optionalButtonType = alert.showAndWait();
        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.OK) {
            service.deleteContact(contact.getName());
            listContacts();
        }
    }
}
