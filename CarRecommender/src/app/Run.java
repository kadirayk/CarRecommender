package app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import model.Car;
import model.IdealCar;
import model.User;
import network.Crawler;
import core.Recommender;
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
		
		carList = crawler.getCarListFromUrl(10);
		
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
		
		System.out.println("cars that user likes");
		
		ArrayList<Car> likedCars = new ArrayList<Car>();
		
		for(int i = 0; i <30; i++){
			user.likes(dbCarList.get(i));
			likedCars.add(dbCarList.get(i));
			System.out.println(dbCarList.get(i).getTitle() + "--------------------------------------------");
				
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
		
		ArrayList<Car> eliminatedCars = recommender.eliminateLikedCarsFromRecommendedCars(likedCars, recommendedCarList);
		
		for(Car c : eliminatedCars){
			System.out.println(c.getTitle());
		}
		
	}
	
	
	

}
