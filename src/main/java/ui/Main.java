package ui;

import database.Datasource;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        Parent root = loader.load();
        root.getStylesheets().add("Style.css");
        Controller controller = loader.getController();
        controller.listContacts();
        primaryStage.setTitle("Contacts App");
        primaryStage.setScene(new Scene(root, 600, 591));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        if (!Datasource.getInstance().open()) {
            System.out.println("Error connecting to database");
            Platform.exit();
        } else {
            Datasource.getInstance().open();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Datasource.getInstance().close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
