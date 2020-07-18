package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import service.Contacts;
import service.Service;
import service.ServiceUtility;

public class DialogController {

    @FXML
    private TextField fName;
    @FXML
    private TextField fPhone;

    private Alert alert = new Alert(Alert.AlertType.ERROR);

    public Contacts getNewContact() {
        String name = fName.getText();
        String phone = fPhone.getText();
//
        //Testing if phone number is all digits and name is not only digits
        if (ServiceUtility.getInstance().isPhoneNumber(phone) && !ServiceUtility.getInstance().isPhoneNumber(name)) {
            if (!ServiceUtility.getInstance().checkPhoneNumberLength(phone)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("This isn't a valid phone number");
                alert.setContentText("You have entered : " + fPhone.getText().length() +
                        " characters \nPhone number field only accepts 19 characters");
                alert.showAndWait();
                return null;
            }
            long number = Long.parseLong(ServiceUtility.getInstance().testNegativePhoneNumbers(phone));
            boolean checkIfNumber = ServiceUtility.getInstance().checkIfPhoneNumberExits(number);
            boolean checkIfName = ServiceUtility.getInstance().checkIfNameExits(name);
            if (!checkIfName) {
                alert.setTitle("Contact Exits");
                if (name == null) {
                    alert.setHeaderText("Please enter name !");
                    alert.showAndWait();
                    return null;
                }
                alert.setHeaderText("Username : " + name + " already exits ");
                alert.showAndWait();
            }

            if (!checkIfNumber) {
                alert.setTitle("Contact Exits");
                alert.setHeaderText("Phone number : " + number + " already exits ");
                alert.showAndWait();
            }
            if (checkIfNumber && checkIfName) {
                return new Contacts(name, number);
            }
        } else {
            alert.setTitle("Wrong information");
            alert.setHeaderText("You have entered wrong information");
            alert.showAndWait();
        }
        return null;
    }

    public void editContact(Contacts c) {
        fName.setText(c.getName());
        fPhone.setText("" + c.getPhone());
    }

    public boolean updateContact(Contacts c) {
        boolean checkIfPhoneNumber = ServiceUtility.getInstance().isPhoneNumber(fPhone.getText());
        boolean checkIfName = !ServiceUtility.getInstance().isPhoneNumber(fName.getText());
        if (checkIfPhoneNumber && checkIfName) {
            if (!ServiceUtility.getInstance().checkPhoneNumberLength(fPhone.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("This isn't a valid phone number");
                alert.setContentText("You have entered : " + fPhone.getText().length() +
                        " characters \nPhone number field only accepts 19 characters");
                alert.showAndWait();
                return false;
            }
            long number = Long.parseLong(ServiceUtility.getInstance().testNegativePhoneNumbers(fPhone.getText()));
            //if given contact doesn't equal with what user typed continue
            if (!c.getName().equalsIgnoreCase(fName.getText())) {
                boolean checkIfNameExits = ServiceUtility.getInstance().checkIfNameExits(fName.getText());
                if (!checkIfNameExits) {
                    alert.setTitle("Contact Exits");
                    alert.setHeaderText("Username : " + fName.getText() + " already exits ");
                    alert.showAndWait();
                    return false;
                }
            }
            if (c.getPhone() != number) {
                boolean checkIfNumberExits = ServiceUtility.getInstance().checkIfPhoneNumberExits(number);
                if (!checkIfNumberExits) {
                    alert.setTitle("Contact Exits");
                    alert.setHeaderText("Phone number : " + number + " already exits ");
                    alert.showAndWait();
                    return false;
                }
            }
            c.setName(fName.getText());
            c.setPhone(number);
            return true;
        } else {
            alert.setTitle("Wrong information");
            alert.setHeaderText("You have entered wrong information");
            alert.showAndWait();
            return false;
        }
    }
}
