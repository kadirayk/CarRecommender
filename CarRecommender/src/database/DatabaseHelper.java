package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {

	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:carrecommenderdb;create=true";
	
	public void createDB() throws ClassNotFoundException, SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
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
		connection.createStatement().execute("insert into cars values "
				+ "('ilan basligi', 'porche', 'cayenne', '911', 2009, 100000, 'blue', 300000, 'istanbul', 'beykoz')");	
	}
	
	
}
