package app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import model.Car;
import model.User;
import network.Crawler;
import database.DatabaseHelper;

public class Run {
	
	public static void main(String[] Args) throws SQLException, ClassNotFoundException{
		
		System.out.println("Car Recommender");
		
		/**
		 * 
		 * get cars from crawler
		 * 
		 * */
		Crawler crawler = new Crawler();
		
		ArrayList<Car> carList = new ArrayList<Car>();
		
		carList = crawler.getCarListFromUrl(3);
		
		for(int i = 0; i < carList.size(); i++){
			System.out.println(carList.get(i).getTitle());
			System.out.println(carList.get(i).getBrand());
			System.out.println(carList.get(i).getModel());
			System.out.println(carList.get(i).getModelDetail());
			System.out.println(carList.get(i).getYear());
			System.out.println(carList.get(i).getKm());
			System.out.println(carList.get(i).getColor());
			System.out.println(carList.get(i).getPrice());
			System.out.println(carList.get(i).getCity());
			System.out.println(carList.get(i).getTown() + "\n");
		}
		
		
		/**
		 * insert cars from crawler to DB
		 */
		
		DatabaseHelper dbhelper = new DatabaseHelper();
		
		dbhelper.createDB();
		
		for(int i = 0; i < carList.size(); i++){
			dbhelper.insertCarIntoDB(carList.get(i));
		}		
	
		ArrayList<Car> dbCarList = dbhelper.getCarListFromDB();
			
		for(Car c : dbCarList){
			System.out.println(c.getTitle());
		}
		
		
		/**
		 * create user, add the cars that user like
		 * 
		 */
		
		User user = new User();
		
		user.setUserName("dummy");
		
		for(Car c : dbCarList){
			user.likes(c);
		}
		
		/**
		 * print brands histogram of the user
		 * 
		 */
		
		SortedSet<String> brandSet = new TreeSet<String>(user.getBrands().keySet());
		
		for(String brand : brandSet){
            System.out.print(brand + " : ");
            for(int i = 0; i< user.getBrands().get(brand); i++){
                System.out.print("*");
            }
            System.out.println();
        }
		
		
		/**
		 * insert brands that user likes into DB
		 * 
		 */
		
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
		
	}
	
	
	

}
