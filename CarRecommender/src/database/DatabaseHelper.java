package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import model.Car;

public class DatabaseHelper {

	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_CREATE_URL = "jdbc:derby:carrecommenderdb;create=true";
	
	
	public void createDB() throws ClassNotFoundException{
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
	
	public void insertCarIntoDB(Car mCar) throws SQLException{
		
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
		
	}
	
	public void getCarFromDB() throws SQLException{
		Connection connection = DriverManager.getConnection(JDBC_CREATE_URL);
		Statement statement = connection.createStatement();
		ResultSet resultset = statement.executeQuery("select * from cars");
		ResultSetMetaData resultSetMetaData = resultset.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();
		
		for(int i = 1; i <= columnCount; i++){
			System.out.format("%20s", resultSetMetaData.getColumnName(i) + " | ");
		}
		while (resultset.next()){
			System.out.println("");
			System.out.println(resultset.getString(1));
			System.out.println(resultset.getString(2));
			System.out.println(resultset.getString(3));
			System.out.println(resultset.getString(4));
			System.out.println(resultset.getInt(5));
			System.out.println(resultset.getInt(6));
			System.out.println(resultset.getString(7));
			System.out.println(resultset.getInt(8));
			System.out.println(resultset.getString(9));
			System.out.println(resultset.getString(10));
		}
		
		if(statement != null)
			statement.close();
		if(connection != null)
			connection.close();
		
	}
	
}
