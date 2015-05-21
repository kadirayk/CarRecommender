package model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
	
	private String userName;
	private HashMap<String,Integer> brands;
	private HashMap<Integer,Integer> years;
	private ArrayList<Integer> kms;
	private HashMap<String, Integer> colors;
	private ArrayList<Integer> prices;
	private HashMap<String, Integer> cities;
	
	
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
