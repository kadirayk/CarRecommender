package model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
	
	private String userName;
	private HashMap<String, Integer> brands;
	private HashMap<Integer, Integer> years;
	private ArrayList<Integer> kms;
	private HashMap<String, Integer> colors;
	private ArrayList<Integer> prices;
	private HashMap<String, Integer> cities;
	
	public User(){
		
		brands = new HashMap<String, Integer>();
		years = new HashMap<Integer, Integer>();
		kms = new ArrayList<Integer>();
		colors = new HashMap<String, Integer>();
		prices = new ArrayList<Integer>();
		cities = new HashMap<String, Integer>();
		
	}
	
	
	public void likes(Car car){
		
		int brandValue = 0;
		int yearValue = 0;
		int colorValue = 0;
		int cityValue = 0;
		
		if(brands.get(car.getBrand()) != null){
			brandValue = brands.get(car.getBrand());
		}
		if(years.get(car.getYear()) != null){
			yearValue = years.get(car.getYear());
		}
		if(colors.get(car.getColor()) != null){
			colorValue = colors.get(car.getColor());
		}
		if(cities.get(car.getCity()) != null){
			cityValue = cities.get(car.getCity());
		}
		
		brands.put(car.getBrand(), brandValue + 1);
		years.put(car.getYear(), yearValue + 1);
		colors.put(car.getColor(), colorValue + 1);
		cities.put(car.getCity(), cityValue + 1);
		kms.add(car.getKm());
		prices.add(car.getPrice());
		
	}

	
	public User getUserInfo(){
		return this;
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public HashMap<String, Integer> getBrands() {
		return brands;
	}
	public void setBrands(HashMap<String, Integer> brands) {
		this.brands = brands;
	}
	public HashMap<Integer, Integer> getYears() {
		return years;
	}
	public void setYears(HashMap<Integer, Integer> years) {
		this.years = years;
	}
	public ArrayList<Integer> getKms() {
		return kms;
	}
	public void setKms(ArrayList<Integer> kms) {
		this.kms = kms;
	}
	public HashMap<String, Integer> getColors() {
		return colors;
	}
	public void setColors(HashMap<String, Integer> colors) {
		this.colors = colors;
	}
	public ArrayList<Integer> getPrices() {
		return prices;
	}
	public void setPrices(ArrayList<Integer> prices) {
		this.prices = prices;
	}
	public HashMap<String, Integer> getCities() {
		return cities;
	}
	public void setCities(HashMap<String, Integer> cities) {
		this.cities = cities;
	}
		
	
}
