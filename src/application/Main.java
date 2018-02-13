package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(Main.class.getResource("/view/MainView.fxml"));
			Scene mainScene = new Scene(root);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Snippopotamus Rex");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getMessage());
			alert.show();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
