package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import view.ViewManager;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			
			ViewManager manager = new ViewManager();
			primaryStage = manager.getMainStage();
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
