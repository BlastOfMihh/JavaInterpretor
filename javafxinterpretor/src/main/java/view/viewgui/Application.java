package view.viewgui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Stage executionStage = new Stage();
        FXMLLoader fxmlLoader2 = new FXMLLoader(Application.class.getResource("mihh-execute-program.fxml"));
        Scene scene2 = new Scene(fxmlLoader2.load(), 1200 , 700);
        executionStage.setTitle("Program selector!");
        executionStage.setScene(scene2);
        executionStage.show();

        ExecuteProgramController executeController=fxmlLoader2.getController();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mihh-select-program.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600 , 600);
        stage.setTitle("Program selector!");
        stage.setScene(scene);
        stage.show();
        SelectProgramController selectController=fxmlLoader.getController();

        selectController.setExecuteWindow(executeController);
    }

    public static void main(String[] args) {
        launch();
    }
}