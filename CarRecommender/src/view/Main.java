package view;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseHelper;
import model.Car;
import model.User;
import network.Crawler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application implements EventHandler<ActionEvent>{

	ListView<String> carListview;
	Button button;
	TextField userNameTF;
	Scene userNameScene;
	Scene carListScene;
	Stage window;
	Crawler crawler;
	ArrayList<Car> carList;
	DatabaseHelper dbhelper;
	ArrayList<Car> dbCarList;
	User user;
	
	
	public static void main(String[] Args){

		launch(Args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		window = new Stage();
		
		window.setTitle("Car Recommender");		
		
	    window.setScene(setUserNameScene());
	    
	    
	    
	    /**
	     * get cars from crawler
	     */
	    
	    crawler = new Crawler();
		
		carList = new ArrayList<Car>();
		
		carList = crawler.getCarListFromUrl(5);
		
		/**
		 * insert cars from crawler to DB
		 */
		
		dbhelper = new DatabaseHelper();
		dbhelper.createDB();

		for(int i = 0; i < carList.size(); i++){
			dbhelper.insertCarIntoDB(carList.get(i));
		}		
	
		dbCarList = dbhelper.getCarListFromDB();
		
		user = new User();
	    
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
		
		
		
		carListview = new ListView<String>();
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		
		
		for(Car c : carList){
			carListview.getItems().add(c.getTitle());
		}
		
		carListview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		layout.getChildren().add(carListview);
		
		carListScene = new Scene(layout, 600, 480);
		
		return carListScene;
		
	}
	
	@Override
	public void handle(ActionEvent event) {

		if(event.getSource()==button){
			user.setUserName(userNameTF.getText());
			window.setScene(setCarListScene());
			
		}
		
	}
	
	
	
	
}
