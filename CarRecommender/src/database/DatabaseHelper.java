package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import model.Car;
import model.IdealCar;

public class DatabaseHelper {

	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_CREATE_URL = "jdbc:derby:carrecommenderdb;create=true";
	
	
	public void createCarsTable() throws ClassNotFoundException{
		
		Class.forName(DRIVER);
		Connection connection;
		
		try {
			connection = DriverManager.getConnection(JDBC_CREATE_URL);
			connection.createStatement().execute("create table cars(title varchar(300),"
					+ " brand varchar(30),"
					+ " model varchar(30),"
					+ " modeldetail varchar(30),"
					+ " product_year INT,"
					+ " car_km INT,"
					+ " color varchar(30),"
					+ " price INT,"
					+ " city varchar(30),"
					+ " town varchar(30))");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getErrorCode() == 30000){
				//table already exists
				return;
			}
		}
		
	}
	
	public void createBrandsTable() throws ClassNotFoundException{
		
		Class.forName(DRIVER);
		Connection connection;
		
		try {
			connection = DriverManager.getConnection(JDBC_CREATE_URL);
			connection.createStatement().execute("create table brands(username varchar(30),"
					+ " brandkey varchar(30),"
					+ " brandvalue INT)");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		
	}
	
	public void createYearsTable() throws ClassNotFoundException{
		
		Class.forName(DRIVER);
		Connection connection;
		
		try {
			connection = DriverManager.getConnection(JDBC_CREATE_URL);
			connection.createStatement().execute("create table years(username varchar(30),"
					+ " yearkey INT, "
					+ " yearvalue INT)");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getErrorCode() == 30000){
				//table already exists
				return;
			}
		}
		
	}
	
	
	public void createColorsTable() throws ClassNotFoundException{
		
		Class.forName(DRIVER);
		Connection connection;
		
		try {
			connection = DriverManager.getConnection(JDBC_CREATE_URL);
			connection.createStatement().execute("create table colors(username varchar(30),"
					+ " colorkey varchar(30), "
					+ " colorvalue INT)");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getErrorCode() == 30000){
				//table already exists
				return;
			}
		}
		
	}
	
	public void createCitiesTable() throws ClassNotFoundException{
		
		Class.forName(DRIVER);
		Connection connection;
		
		try {
			connection = DriverManager.getConnection(JDBC_CREATE_URL);
			connection.createStatement().execute("create table cities(username varchar(30),"
					+ " citykey varchar(30), "
					+ " cityvalue INT)");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getErrorCode() == 30000){
				//table already exists
				return;
			}
		}
		
	}
	
	public void createKmsTable() throws ClassNotFoundException{
		
		Class.forName(DRIVER);
		Connection connection;
		
		try {
			connection = DriverManager.getConnection(JDBC_CREATE_URL);
			connection.createStatement().execute("create table kms(username varchar(30),"
					+ " kmValue INT)");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getErrorCode() == 30000){
				//table already exists
				return;
			}
		}
		
	}

	public void createPricesTable() throws ClassNotFoundException{
		
		Class.forName(DRIVER);
		Connection connection;

		try {
			connection = DriverManager.getConnection(JDBC_CREATE_URL);
			connection.createStatement().execute("create table prices(username varchar(30),"
					+ " priceValue INT)");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getErrorCode() == 30000){
				//table already exists
				return;
			}
		}
		
	}
	
	
	public void createDB() throws ClassNotFoundException{

	
		createCarsTable();
		createBrandsTable();
		createYearsTable();
		createColorsTable();
		createCitiesTable();
		createKmsTable();
		createPricesTable();
		
		
	}
	
	
	public void insertBrandsIntoDB(String userName, String brandKey, int brandValue){
		
		try {
			Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
			connection.createStatement().execute("insert into brands values "
					+ "('"+ userName + "',"
					+ " '" + brandKey + "',"
					+ "" + brandValue + ")");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getErrorCode() == 30000){
				e.printStackTrace();
				return;
			}
			e.printStackTrace();
		}
	
	}
	

	public HashMap<String, Integer> getBrandsMapFromDB(String userName) throws SQLException{
		Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
		Statement statement = connection.createStatement();
		ResultSet resultset = statement.executeQuery("select * from brands where username = '" + userName + "'");
		
		System.out.println("DB QUERY Brands RESULT---------------------------");
		
		
		HashMap<String, Integer> brands = new HashMap<String, Integer>();
		
		while (resultset.next()){
			brands.put(resultset.getString(2), resultset.getInt(3));
		}
		
		if(statement != null)
			statement.close();
		if(connection != null)
			connection.close();
		
		return brands;
	}
	
	
	public void insertYearsIntoDB(String userName, int yearKey, int yearValue){
		
		try {
			Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
			connection.createStatement().execute("insert into years values "
					+ "('"+ userName + "',"
					+ "" + yearKey + ","
					+ "" + yearValue + ")");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getErrorCode() == 30000){
				e.printStackTrace();
				return;
			}
			e.printStackTrace();
		}
	
	}
	
	
	public HashMap<Integer, Integer> getYearsMapFromDB(String userName) throws SQLException{
		Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
		Statement statement = connection.createStatement();
		ResultSet resultset = statement.executeQuery("select * from years where username = '" + userName + "'");
		
		System.out.println("DB QUERY years RESULT---------------------------");
		
		
		HashMap<Integer, Integer> years = new HashMap<Integer, Integer>();
		
		while (resultset.next()){
			years.put(resultset.getInt(2), resultset.getInt(3));
		}
		
		if(statement != null)
			statement.close();
		if(connection != null)
			connection.close();
		
		return years;
	}
	
	
	public void insertColorsIntoDB(String userName, String colorKey, int colorValue){
		
		try {
			Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
			connection.createStatement().execute("insert into colors values "
					+ "('"+ userName + "',"
					+ "'" + colorKey + "',"
					+ "" + colorValue + ")");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getErrorCode() == 30000){
				e.printStackTrace();
				return;
			}
			e.printStackTrace();
		}
	
	}
	
	
	public HashMap<String, Integer> getColorsMapFromDB(String userName) throws SQLException{
		Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
		Statement statement = connection.createStatement();
		ResultSet resultset = statement.executeQuery("select * from colors where username = '" + userName + "'");
		
		System.out.println("DB QUERY years RESULT---------------------------");
		
		
		HashMap<String, Integer> colors = new HashMap<String, Integer>();
		
		while (resultset.next()){
			colors.put(resultset.getString(2), resultset.getInt(3));
		}
		
		if(statement != null)
			statement.close();
		if(connection != null)
			connection.close();
		
		return colors;
	}
	
	
	public void insertCitiesIntoDB(String userName, String cityKey, int cityValue){
		
		try {
			Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
			connection.createStatement().execute("insert into cities values "
					+ "('"+ userName + "',"
					+ "'" + cityKey + "',"
					+ "" + cityValue + ")");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getErrorCode() == 30000){
				e.printStackTrace();
				return;
			}
			e.printStackTrace();
		}
	
	}
	

	public HashMap<String, Integer> getCitiesMapFromDB(String userName) throws SQLException{
		Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
		Statement statement = connection.createStatement();
		ResultSet resultset = statement.executeQuery("select * from cities where username = '" + userName + "'");
		
		System.out.println("DB QUERY years RESULT---------------------------");
		
		
		HashMap<String, Integer> cities = new HashMap<String, Integer>();
		
		while (resultset.next()){
			cities.put(resultset.getString(2), resultset.getInt(3));
		}
		
		if(statement != null)
			statement.close();
		if(connection != null)
			connection.close();
		
		return cities;
	}
	
	
	public void insertKmsIntoDB(String userName, int kmValue){
		
		try {
			Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
			connection.createStatement().execute("insert into kms values "
					+ "('"+ userName + "',"
					+ "" + kmValue + ")");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getErrorCode() == 30000){
				e.printStackTrace();
				return;
			}
			e.printStackTrace();
		}
	
	}
	
	
	public ArrayList<Integer> getKmsListFromDB(String userName) throws SQLException{
		Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
		Statement statement = connection.createStatement();
		ResultSet resultset = statement.executeQuery("select * from kms where username = '" + userName + "'");
		
		System.out.println("DB QUERY years RESULT---------------------------");
		
		
		ArrayList<Integer> kms = new ArrayList<Integer>();
		
		while (resultset.next()){
			kms.add(resultset.getInt(2));
		}
		
		if(statement != null)
			statement.close();
		if(connection != null)
			connection.close();
		
		return kms;
	}
	
	public void insertPricesIntoDB(String userName, int priceValue){
		
		try {
			Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
			connection.createStatement().execute("insert into prices values "
					+ "('"+ userName + "',"
					+ "" + priceValue + ")");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getErrorCode() == 30000){
				e.printStackTrace();
				return;
			}
			e.printStackTrace();
		}
	
	}
	
	public ArrayList<Integer> getPricesListFromDB(String userName) throws SQLException{
		Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
		Statement statement = connection.createStatement();
		ResultSet resultset = statement.executeQuery("select * from prices where username = '" + userName + "'");
		
		System.out.println("DB QUERY years RESULT---------------------------");
		
		
		ArrayList<Integer> prices = new ArrayList<Integer>();
		
		while (resultset.next()){
			prices.add(resultset.getInt(2));
		}
		
		if(statement != null)
			statement.close();
		if(connection != null)
			connection.close();
		
		return prices;
	}
	
	public void insertCarIntoDB(Car mCar){
		try {
			Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
			connection.createStatement().execute("insert into cars values "
					+ "('"+ mCar.getTitle() + "',"
					+ " '" + mCar.getBrand() + "',"
					+ " '" + mCar.getModel() + "',"
					+ " '" + mCar.getModelDetail() + "',"
					+  mCar.getYear() + ","
					+ mCar.getKm() + ","
					+ " '" + mCar.getColor() + "',"
					+ mCar.getPrice() + ","
					+ " '" + mCar.getCity() + "',"
					+ " '" + mCar.getTown() +"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getErrorCode() == 30000){
				return;
			}
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Car> getCarListFromDB() throws SQLException{
		Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
		Statement statement = connection.createStatement();
		ResultSet resultset = statement.executeQuery("select * from cars");
		
		System.out.println("DB QUERY RESULT---------------------------");
		
		ArrayList<Car> carList = new ArrayList<Car>();
		
		while (resultset.next()){			
			Car mCar = new Car();
			mCar.setTitle(resultset.getString(1));
			mCar.setBrand(resultset.getString(2));
			mCar.setModel(resultset.getString(3));
			mCar.setModelDetail(resultset.getString(4));
			mCar.setYear(resultset.getInt(5));
			mCar.setKm(resultset.getInt(6));
			mCar.setColor(resultset.getString(7));
			mCar.setPrice(resultset.getInt(8));
			mCar.setCity(resultset.getString(9));
			mCar.setTown(resultset.getString(10));
			carList.add(mCar);
		}
		
		if(statement != null)
			statement.close();
		if(connection != null)
			connection.close();
		
		return carList;
	}
	
	public ArrayList<Car> getRecommendedCarListFromDB(ArrayList<IdealCar> idealCarList) throws SQLException{
		Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
		Statement statement = connection.createStatement();
		
		ArrayList<Car> carList = new ArrayList<Car>();
		
		for(IdealCar idealCar : idealCarList){
			
			ResultSet resultset = statement.executeQuery("select * from cars where brand='" + idealCar.getBrand() + "'" +
																					" and product_year=" + idealCar.getYear() +
																					" and color='" + idealCar.getColor() + "'" +
																					" and city='" + idealCar.getCity() + "'" +
																					" and car_km<=" + idealCar.getKm_max() +
																					" and car_km>=" + idealCar.getKm_min() +
																					" and price<=" + idealCar.getPrice_max() +
																					" and price>=" + idealCar.getPrice_min());
			
			while (resultset.next()){			
				Car mCar = new Car();
				mCar.setTitle(resultset.getString(1));
				mCar.setBrand(resultset.getString(2));
				mCar.setModel(resultset.getString(3));
				mCar.setModelDetail(resultset.getString(4));
				mCar.setYear(resultset.getInt(5));
				mCar.setKm(resultset.getInt(6));
				mCar.setColor(resultset.getString(7));
				mCar.setPrice(resultset.getInt(8));
				mCar.setCity(resultset.getString(9));
				mCar.setTown(resultset.getString(10));
				carList.add(mCar);
			}
			
		}
		
		if(statement != null)
			statement.close();
		if(connection != null)
			connection.close();
		
		return carList;
	}
	
}
