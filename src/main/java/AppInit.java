import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class AppInit extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        System.out.println("AppInit.start");
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("Update.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setResizable(false);
            primaryStage.setAlwaysOnTop(true);
            primaryStage.requestFocus();
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("AppInit.start : error : " + e.getMessage());
        }
    }
}