package app;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Car;
import network.Crawler;

public class Run {
	
	public static void main(String[] Args){
		
		System.out.println("Car Recommender");
		
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
		
	}
	

}
