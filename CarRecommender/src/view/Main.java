package view;
import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Car;
import model.IdealCar;
import model.User;
import network.Crawler;

import org.apache.commons.io.FileUtils;

import core.Recommender;
import database.DatabaseHelper;


public class Main extends Application implements EventHandler<ActionEvent> {

	ListView<String> carListview;
	TableView<Car> carTableView;
	TableView<Car> likedCarsTableView;
	TableView<Car> recommendedCarsTableView;
	Button refresButton;
	Button enterButton;
	Button showRecommendationsButton;
	TextField userNameTF;
	TextField crawlerPageCountTF;
	Scene userNameScene;
	Scene carListScene;
	Scene recommendationsScene;
	Stage window;
	Crawler crawler;
	int crawlerPageCount;
	ArrayList<Car> carList;
	DatabaseHelper dbhelper;
	ArrayList<Car> dbCarList;
	User user;
	ArrayList<Car> likedCars;
	ArrayList<Car> recommendedCars;
	
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
		
		window.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent arg0) {
				System.out.println("closing");
				try {
					System.out.println(System.getProperty("user.dir"));
					FileUtils.deleteDirectory(new File(System.getProperty("user.dir").trim() + "\\carrecommenderdb"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}); 
		
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
	   
	    crawlerPageCountTF = new TextField("10");	    
	    
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
	
	@SuppressWarnings("unchecked")
	private Scene setRecommendationsScene() throws Exception{
		
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
		
		likedCarsTableView = new TableView<Car>();
		likedCarsTableView.setItems(getLikedCars());
		likedCarsTableView.getColumns().addAll(titleColumn, brandColumn, modelColumn, 
				modelDetailColumn, yearColumn, kmColumn, colorColumn, priceColumn, cityColumn, townColumn);
		
		
		
		TableColumn<Car, String> rtitleColumn = new TableColumn<Car, String>("Title");
		rtitleColumn.setMinWidth(200);
		rtitleColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("Title"));
		
		TableColumn<Car, String> rbrandColumn = new TableColumn<Car, String>("Brand");
		rbrandColumn.setMinWidth(100);
		rbrandColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("Brand"));
		
		TableColumn<Car, String> rmodelColumn = new TableColumn<Car, String>("Model");
		rmodelColumn.setMinWidth(100);
		rmodelColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("Model"));
		
		TableColumn<Car, String> rmodelDetailColumn = new TableColumn<Car, String>("Model Detail");
		rmodelDetailColumn.setMinWidth(200);
		rmodelDetailColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("ModelDetail"));
		
		TableColumn<Car, Integer> ryearColumn = new TableColumn<Car, Integer>("Year");
		ryearColumn.setMinWidth(100);
		ryearColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("Year"));
		
		TableColumn<Car, Integer> rkmColumn = new TableColumn<Car, Integer>("Km");
		rkmColumn.setMinWidth(100);
		rkmColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("Km"));
		
		TableColumn<Car, String> rcolorColumn = new TableColumn<Car, String>("Color");
		rcolorColumn.setMinWidth(100);
		rcolorColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("Color"));
		
		TableColumn<Car, Integer> rpriceColumn = new TableColumn<Car, Integer>("Price");
		rpriceColumn.setMinWidth(100);
		rpriceColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("Price"));
		
		TableColumn<Car, String> rcityColumn = new TableColumn<Car, String>("City");
		rcityColumn.setMinWidth(100);
		rcityColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("City"));
		
		TableColumn<Car, String> rtownColumn = new TableColumn<Car, String>("Town");
		rtownColumn.setMinWidth(100);
		rtownColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("Town"));
		
		recommendedCarsTableView = new TableView<Car>();
		recommendedCarsTableView.setItems(getRecommendedCars());
		recommendedCarsTableView.getColumns().addAll(rtitleColumn, rbrandColumn, rmodelColumn, 
				rmodelDetailColumn, ryearColumn, rkmColumn, rcolorColumn, rpriceColumn, rcityColumn, rtownColumn);
		
		Label likedLabel = new Label("Cars You Like");
		Label recommendedLabel = new Label("Our Recommendations (Ordered by Best Recommendation)");
		
		refresButton = new Button("Refresh");
		refresButton.setOnAction(this);
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(5,5,5,5));
		
		layout.getChildren().addAll(refresButton, likedLabel, likedCarsTableView, recommendedLabel, recommendedCarsTableView);
		
		recommendationsScene = new Scene(layout, 600, 480);
		
		return recommendationsScene;
		
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
				getRecommendedCarList();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				window.setScene(setRecommendationsScene());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(event.getSource()==refresButton){
			try {
				window.setScene(setRecommendationsScene());
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
	
	public ObservableList<Car> getRecommendedCars() throws Exception{
		ObservableList<Car> carObservableList = FXCollections.observableArrayList();
		System.out.println("observe");
		for(Car c : recommendedCars){
			carObservableList.add(c);
			System.out.println(c.getTitle());
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
		 * insert the years that user likes into DB
		 * 
		 */
		
		SortedSet<Integer> yearSet = new TreeSet<Integer>(user.getYears().keySet());
		
		for(Integer y : yearSet){
			dbhelper.insertYearsIntoDB(user.getUserName(), y, user.getYears().get(y));
		}
		
		/**
		 * insert the colors that user likes into DB
		 * 
		 */

		SortedSet<String> colorSet = new TreeSet<String>(user.getColors().keySet());
		
		for(String c : colorSet){
			dbhelper.insertColorsIntoDB(user.getUserName(), c, user.getColors().get(c));
		}
		
		/**
		 * insert the cities that user likes into DB
		 * 
		 */
		
		SortedSet<String> citySet = new TreeSet<String>(user.getCities().keySet());
		
		for(String c : citySet){
			dbhelper.insertCitiesIntoDB(user.getUserName(), c, user.getCities().get(c));
		}

		/**
		 * insert the kms that user likes into DB
		 * 
		 */
		
		ArrayList<Integer> kmList = user.getKms();
		
		for(Integer i : kmList){
			dbhelper.insertKmsIntoDB(user.getUserName(), i);
		}
				
		/**
		 * insert the prices that user likes into DB
		 * 
		 */
		
		ArrayList<Integer> priceList = user.getPrices();
		
		for(Integer i : priceList){
			dbhelper.insertPricesIntoDB(user.getUserName(), i);
		}
		
	}
	
	private void getRecommendedCarList() throws Exception{
		
		recommendedCars = new ArrayList<Car>();
		
		/**
		 * get brands of user from DB
		 */
		
		HashMap<String, Integer> brandsfromDB = dbhelper.getBrandsMapFromDB(user.getUserName());
		
		
		/**
		 * get the user's years from DB
		 */
		
		HashMap<Integer, Integer> yearsfromDB = dbhelper.getYearsMapFromDB(user.getUserName());	
		
		/**
		 * get the user's colors from DB
		 */
		
		HashMap<String, Integer> colorsfromDB = dbhelper.getColorsMapFromDB(user.getUserName());
		
		
		/**
		 * get the user's cities from DB
		 */
		
		HashMap<String, Integer> citiesfromDB = dbhelper.getCitiesMapFromDB(user.getUserName());
		
		
		/**
		 * get the user's kms from DB
		 */
		
		ArrayList<Integer> kmsFromDB = dbhelper.getKmsListFromDB(user.getUserName());		
		
		/**
		 * get the user's prices from DB
		 */
		
		ArrayList<Integer> pricesFromDB = dbhelper.getPricesListFromDB(user.getUserName());
		
		/**
		 * 
		 * generate ideal car profile for the user
		 * 
		 */
		
		System.out.println("engine results");
		
		Recommender recommender = new Recommender(10, brandsfromDB, yearsfromDB, colorsfromDB, citiesfromDB, pricesFromDB, kmsFromDB);
		
		ArrayList<IdealCar> idealCarList = new ArrayList<IdealCar>();
		
		idealCarList = recommender.getIdealCarList();
		
		for(IdealCar idealCar : idealCarList){
			System.out.println("brand: " + idealCar.getBrand()
					+ " year: " + idealCar.getYear()
					+ " color: " + idealCar.getColor()
					+ " city: " + idealCar.getCity());
		}
		
		/**
		 * get recommended cars according to ideal cars of the user
		 * 
		 */
		
		ArrayList<Car> recommendedCarList = dbhelper.getRecommendedCarListFromDB(idealCarList);
		
		for(Car c : recommendedCarList){
			System.out.println(c.getTitle() + " brand: " + c.getBrand() + " year: " + c.getYear() + " color: " + c.getColor() + " city: " + c.getCity());
		}
		
		/**
		 * eliminate liked cars in order not to show to the user if she already liked it
		 * 
		 */
		
		System.out.println("eliminated cars: ");
		
		recommendedCars = recommender.eliminateLikedCarsFromRecommendedCars(likedCars, recommendedCarList);
		
		for(Car c : recommendedCars){
			System.out.println(c.getTitle());
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
