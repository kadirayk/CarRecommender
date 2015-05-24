package view;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Car;
import model.User;
import network.Crawler;
import database.DatabaseHelper;


public class Main extends Application implements EventHandler<ActionEvent> {

	ListView<String> carListview;
	TableView<Car> carTableView;
	Button enterButton;
	Button showRecommendationsButton;
	TextField userNameTF;
	TextField crawlerPageCountTF;
	Scene userNameScene;
	Scene carListScene;
	Stage window;
	Crawler crawler;
	int crawlerPageCount;
	ArrayList<Car> carList;
	DatabaseHelper dbhelper;
	ArrayList<Car> dbCarList;
	User user;
	ArrayList<Car> likedCars;
	
	public static void main(String[] Args){

		launch(Args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		window = new Stage();
		
		window.setTitle("Car Recommender");		
		
	    window.setScene(setUserNameScene());
		
		user = new User();
	    
		window.show();
		
	}
	
	
	private Scene setUserNameScene(){
		
		enterButton = new Button();
		enterButton.setText("Enter");
		
		enterButton.setOnAction(this);
		
		userNameScene = new Scene(new Group(), 520, 240);

	    userNameTF = new TextField ();
	    
	    Label userNameLabel = new Label();
	    userNameLabel.setText("User Name: ");
	    
	    
	    Label crawlerPageCountLabel = new Label();
	    crawlerPageCountLabel.setText("Enter Page Count: ");
	    
	    Label guideLabel = new Label("Press Enter to get Cars from sahibinden.com to our Database");
	   
	    crawlerPageCountTF = new TextField("1");	    
	    
	    GridPane grid = new GridPane();
	    grid.setPadding(new Insets(90, 0, 0, 36));
	    grid.setHgap(5);
	    grid.setVgap(5);
	    
	    grid.add(guideLabel, 1, 0);
	    grid.add(userNameLabel, 0, 1);
	    grid.add(userNameTF, 1, 1);
	    grid.add(crawlerPageCountLabel, 0, 2);
	    grid.add(crawlerPageCountTF, 1, 2);
	    
	    grid.add(enterButton, 1, 3);
	    
	    Group root = (Group) userNameScene.getRoot();
	    root.getChildren().add(grid);
		
	    return userNameScene;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		
		likedCars = new ArrayList<Car>();
		
		carTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		        //Check whether item is selected and set value of selected item to Label
		        if(carTableView.getSelectionModel().getSelectedItem() != null) 
		        {    
		           TableViewSelectionModel selectionModel = carTableView.getSelectionModel();
		           ObservableList selectedCells = selectionModel.getSelectedCells();
		      		           
		           TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		           Object titleValue = tablePosition.getRow();	           
		           
		           Car likedCar = carTableView.getItems().get( (Integer) titleValue);
		           likedCars.add(likedCar);
		           user.likes(likedCar);
		           
		           System.out.println("Selected Value" + likedCar.getTitle() );
		         }
		         }
		     });
		
		
		showRecommendationsButton = new Button();
		showRecommendationsButton.setText("Show Recommendations");
		
		showRecommendationsButton.setOnAction(this);
		
		Label label = new Label("Recommendation will be based on the Cars you like, Click on the Cars to Like");
		
		layout.getChildren().add(label);
		layout.getChildren().add(carTableView);
		layout.getChildren().add(showRecommendationsButton);
		
		carListScene = new Scene(layout, 600, 480);
		
		return carListScene;
		
	}
	
	@Override
	public void handle(ActionEvent event) {

		if(event.getSource()==enterButton){
			if(userNameTF.getText() != null && ! userNameTF.getText().trim().isEmpty()){
				user.setUserName(userNameTF.getText().trim());				
			}else{
				AlertBox.Display("Error", "Please enter a user name");
				System.out.println("user naaaaaaaaaame");
				return;
			}
			if(crawlerPageCountTF.getText() != null && ! crawlerPageCountTF.getText().trim().isEmpty()){
				crawlerPageCount = Integer.valueOf(crawlerPageCountTF.getText());
			}else{
				AlertBox.Display("Error", "Please enter a number of pages to crawl");
				return;
			}
			try {
				getCarListFromCrawlerToDB(crawlerPageCount);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			window.setScene(setCarListScene());	
		}else if(event.getSource()==showRecommendationsButton){
			try {
				insertUserLikesIntoDB();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public ObservableList<Car> getAllCars(){
		ObservableList<Car> carObservableList = FXCollections.observableArrayList();
		
		for(Car c : carList){
			carObservableList.add(c);
		}
		
		
		return carObservableList;
	}
	
	public ObservableList<Car> getLikedCars(){
		ObservableList<Car> carObservableList = FXCollections.observableArrayList();

		for(Car c : likedCars){
			carObservableList.add(c);
		}
		
		return carObservableList;
		
	}
	
	private void insertUserLikesIntoDB() throws Exception{
		
		/**
		 * insert brands that user likes into DB
		 * 
		 */
		
		SortedSet<String> brandSet = new TreeSet<String>(user.getBrands().keySet());
		
		for(String brand : brandSet){
			
			dbhelper.insertBrandsIntoDB(user.getUserName(), brand, user.getBrands().get(brand));
			
		}
		
		/**
		 * get brands of user from DB
		 * and print brands
		 * 
		 */
		
		HashMap<String, Integer> brandsfromDB = dbhelper.getBrandsMapFromDB(user.getUserName());
		
		SortedSet<String> brandsfromDBSet = new TreeSet<String>(brandsfromDB.keySet());
		
		System.out.println("---------------------brands from db ");
		for(String b : brandsfromDBSet){
			System.out.println(b + " : " + brandsfromDB.get(b));
		}
		
		System.out.println("-------------------");
		
		
		/**
		 * get years that the user likes
		 * 
		 */
		
		SortedSet<Integer> yearSet = new TreeSet<Integer>(user.getYears().keySet());
		
		for(Integer year : yearSet){
			System.out.print(year + " : ");
			for(int i = 0; i< user.getYears().get(year); i++){
				System.out.print("*");
			}
			System.out.println();
		}
		
		
		/**
		 * insert the years that user likes into DB
		 * 
		 */
		
		for(Integer y : yearSet){
			dbhelper.insertYearsIntoDB(user.getUserName(), y, user.getYears().get(y));
		}
		
		/**
		 * get the user's years from DB
		 * and print years
		 */
		
		HashMap<Integer, Integer> yearsfromDB = dbhelper.getYearsMapFromDB(user.getUserName());
		
		SortedSet<Integer> yearsfromDBSet = new TreeSet<Integer>(yearsfromDB.keySet());
		
		System.out.println("---------------------brands from db ");
		for(Integer y : yearsfromDBSet){
			System.out.println(y + " : " + yearsfromDB.get(y));
		}
		
		
		/**
		 * get colors that user likes
		 * 
		 */
		
		SortedSet<String> colorSet = new TreeSet<String>(user.getColors().keySet());
		
		for(String color : colorSet){
			System.out.print(color + " : ");
			for(int i = 0; i< user.getColors().get(color); i++){
				System.out.print("*");
			}
			System.out.println();
		}
		
		/**
		 * insert the colors that user likes into DB
		 * 
		 */
		
		for(String c : colorSet){
			dbhelper.insertColorsIntoDB(user.getUserName(), c, user.getColors().get(c));
		}
		
		
		/**
		 * get the user's colors from DB
		 * and print colors
		 */
		
		HashMap<String, Integer> colorsfromDB = dbhelper.getColorsMapFromDB(user.getUserName());
		
		SortedSet<String> colorsfromDBSet = new TreeSet<String>(colorsfromDB.keySet());
		
		System.out.println("---------------------brands from db ");
		for(String c : colorsfromDBSet){
			System.out.println(c + " : " + colorsfromDB.get(c));
		}
		
		
		/**
		 * get cities that user likes
		 * 
		 */
		
		SortedSet<String> citySet = new TreeSet<String>(user.getCities().keySet());
		
		for(String city : citySet){
			System.out.print(city + " : ");
			for(int i = 0; i< user.getCities().get(city); i++){
				System.out.print("*");
			}
			System.out.println();
		}
		
		/**
		 * insert the cities that user likes into DB
		 * 
		 */
		
		for(String c : citySet){
			dbhelper.insertCitiesIntoDB(user.getUserName(), c, user.getCities().get(c));
		}
		
		
		/**
		 * get the user's cities from DB
		 * and print cities
		 */
		
		HashMap<String, Integer> citiesfromDB = dbhelper.getCitiesMapFromDB(user.getUserName());
		
		SortedSet<String> citiesfromDBSet = new TreeSet<String>(citiesfromDB.keySet());
		
		System.out.println("---------------------brands from db ");
		for(String c : citiesfromDBSet){
			System.out.println(c + " : " + citiesfromDB.get(c));
		}
		
		
		/**
		 * get Kms that user likes
		 * 
		 */

		ArrayList<Integer> kmList = user.getKms();
		
		for(Integer i : kmList){
			System.out.println(i);
		}
		
		/**
		 * insert the kms that user likes into DB
		 * 
		 */
		
		for(Integer i : kmList){
			dbhelper.insertKmsIntoDB(user.getUserName(), i);
		}
		
		
		/**
		 * get the user's kms from DB
		 * and print kms
		 */
		
		ArrayList<Integer> kmsFromDB = dbhelper.getKmsListFromDB(user.getUserName());
		
		System.out.println("---------------------KMS from db ");
		for(Integer i : kmsFromDB){
			System.out.println(i);
		}
		
		
		/**
		 * get prices that user likes
		 * 
		 */

		ArrayList<Integer> priceList = user.getPrices();
		
		for(Integer i : priceList){
			System.out.println(i);
		}
		
		/**
		 * insert the prices that user likes into DB
		 * 
		 */
		
		for(Integer i : priceList){
			dbhelper.insertPricesIntoDB(user.getUserName(), i);
		}
		
		
		/**
		 * get the user's prices from DB
		 * and print prices
		 */
		
		ArrayList<Integer> pricesFromDB = dbhelper.getPricesListFromDB(user.getUserName());
		
		System.out.println("---------------------prices from db ");
		for(Integer i : pricesFromDB){
			System.out.println(i);
		}
		
		
	}
	
	private void getCarListFromCrawlerToDB(int crawlerPageCount) throws Exception{
		
	    
	    /**
	     * get cars from crawler
	     */
	    
	    crawler = new Crawler();
		
		carList = new ArrayList<Car>();
		
		carList = crawler.getCarListFromUrl(crawlerPageCount);
		
		/**
		 * insert cars from crawler to DB
		 */
		
		dbhelper = new DatabaseHelper();
		dbhelper.createDB();

		for(int i = 0; i < carList.size(); i++){
			dbhelper.insertCarIntoDB(carList.get(i));
		}		
	
		dbCarList = dbhelper.getCarListFromDB();
		
	}
	
}
