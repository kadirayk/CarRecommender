package view;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Car;
import model.User;
import network.Crawler;
import database.DatabaseHelper;


public class Main extends Application implements EventHandler<ActionEvent>{

	ListView<String> carListview;
	TableView<Car> carTableView;
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
		
		TableColumn<Car, String> titleColumn = new TableColumn<Car, String>("Title");
		titleColumn.setMinWidth(200);
		titleColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("Title"));
		
		TableColumn<Car, String> brandColumn = new TableColumn<Car, String>("Brand");
		brandColumn.setMinWidth(100);
		brandColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("Brand"));
		
		TableColumn<Car, String> modelColumn = new TableColumn<Car, String>("Model");
		modelColumn.setMinWidth(100);
		modelColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("Model"));
		
		TableColumn<Car, String> modelDetailColumn = new TableColumn<Car, String>("Model Detail");
		modelDetailColumn.setMinWidth(200);
		modelDetailColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("ModelDetail"));
		
		TableColumn<Car, Integer> yearColumn = new TableColumn<Car, Integer>("Year");
		yearColumn.setMinWidth(100);
		yearColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("Year"));
		
		TableColumn<Car, Integer> kmColumn = new TableColumn<Car, Integer>("Km");
		kmColumn.setMinWidth(100);
		kmColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("Km"));
		
		TableColumn<Car, String> colorColumn = new TableColumn<Car, String>("Color");
		colorColumn.setMinWidth(100);
		colorColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("Color"));
		
		TableColumn<Car, Integer> priceColumn = new TableColumn<Car, Integer>("Price");
		priceColumn.setMinWidth(100);
		priceColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("Price"));
		
		TableColumn<Car, String> cityColumn = new TableColumn<Car, String>("City");
		cityColumn.setMinWidth(100);
		cityColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("City"));
		
		TableColumn<Car, String> townColumn = new TableColumn<Car, String>("Town");
		townColumn.setMinWidth(100);
		townColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("Town"));
		
		
		carTableView = new TableView<Car>();
		carTableView.setItems(getAllCars());
		carTableView.getColumns().addAll(titleColumn, brandColumn, modelColumn, 
				modelDetailColumn, yearColumn, kmColumn, colorColumn, priceColumn, cityColumn, townColumn);
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(5,5,5,5));
		
		
		layout.getChildren().add(carTableView);
		
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
	
	public ObservableList<Car> getAllCars(){
		ObservableList<Car> carObservableList = FXCollections.observableArrayList();
		
		for(Car c : carList){
			carObservableList.add(c);
		}
		
		
		return carObservableList;
	}
	
	
	
}
