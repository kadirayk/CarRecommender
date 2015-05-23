package view;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application{

	Button button;
	
	public static void main(String[] Args){

		launch(Args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Car Recommender");
		button = new Button();
		button.setText("Enter");
		
		StackPane layout = new StackPane();
		layout.getChildren().add(button);
		
		Scene scene = new Scene(layout, 600, 480);
		primaryStage.setScene(scene);
		primaryStage.show();		
		
	}
	
}
