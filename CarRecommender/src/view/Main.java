package view;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application implements EventHandler<ActionEvent>{

	Button button;
	TextField userNameTF;
	Scene userNameScene;
	Scene carListScene;
	Stage window;
	
	
	public static void main(String[] Args){

		launch(Args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		window = new Stage();
		
		window.setTitle("Car Recommender");		
		
	    window.setScene(setUserNameScene());
	    window.show();
	    
		
	}

	
	private Scene setUserNameScene(){
		
		button = new Button();
		button.setText("Enter");
		
		button.setOnAction(this);
		
		userNameScene = new Scene(new Group(), 360, 240);

	    userNameTF = new TextField ();
	    
	    Label userNameLabel = new Label();
	    userNameLabel.setText("User Name: ");
	    
	    GridPane grid = new GridPane();
	    grid.setPadding(new Insets(90, 0, 0, 36));
	    grid.setHgap(5);
	    
	    grid.add(userNameLabel, 0, 0);
	    grid.add(userNameTF, 2, 0);
	    
	    grid.add(button, 4, 0);
	    
	    Group root = (Group) userNameScene.getRoot();
	    root.getChildren().add(grid);
		
	    return userNameScene;
	}
	
	
	private Scene setCarListScene(){
		
		carListScene = new Scene(new Group(), 600, 480);
		
		return carListScene;
		
	}
	
	@Override
	public void handle(ActionEvent event) {

		if(event.getSource()==button){
			System.out.println(userNameTF.getText());
			window.setScene(setCarListScene());
			
		}
		
	}
	
	
	
	
}
