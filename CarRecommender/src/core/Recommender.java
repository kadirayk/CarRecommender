package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import model.IdealCar;

public class Recommender {

	private int count;
	private HashMap<String, Integer> brands;
	private HashMap<Integer, Integer> years;
	private HashMap<String, Integer> colors;
	private HashMap<String, Integer> cities;
	private ArrayList<Integer> prices;
	private ArrayList<Integer> kms;
	
	
	public Recommender(int count, 
			HashMap<String, Integer> brands, 
			HashMap<Integer, Integer> years, 
			HashMap<String, Integer> colors, 
			HashMap<String, Integer> cities, 
			ArrayList<Integer> prices, 
			ArrayList<Integer> kms){
		
		this.count = count;
		this.brands = brands;
		this.years = years;
		this.colors = colors;
		this.cities = cities;
		this.prices = prices;
		this.kms = kms;
			
	}
	
	
	public static ArrayList<String> getRecommendedBrands(int count, HashMap<String, Integer> brands){	
		
		SortedSet<Integer> brandValues = new TreeSet<Integer>(brands.values()).descendingSet();
		SortedSet<String> brandKeys = new TreeSet<String>(brands.keySet());		
		ArrayList<Integer> brandValueList = new ArrayList<Integer>(brandValues);
		ArrayList<String> recommendedBrands = new ArrayList<String>();
		
		for(int i = 0; i < count; i++){
			for(String brand : brandKeys){
				if(brands.get(brand) == brandValueList.get(i)){
					recommendedBrands.add(brand);
				}
			}
		}	
		recommendedBrands = new ArrayList<String>(recommendedBrands.subList(0, count));
		return recommendedBrands;
	}
	
	public static ArrayList<Integer> getRecommendedYears(int count, HashMap<Integer, Integer> years){	
		
		SortedSet<Integer> yearValues = new TreeSet<Integer>(years.values()).descendingSet();
		SortedSet<Integer> yearKeys = new TreeSet<Integer>(years.keySet());		
		ArrayList<Integer> yearValueList = new ArrayList<Integer>(yearValues);
		ArrayList<Integer> recommendedYears = new ArrayList<Integer>();
		
		for(int i = 0; i < count; i++){
			for(Integer year : yearKeys){
				if(years.get(year) == yearValueList.get(i)){
					recommendedYears.add(year);
				}
			}
		}	
		recommendedYears = new ArrayList<Integer>(recommendedYears.subList(0, count));
		return recommendedYears;
	}
	
	
	public static ArrayList<String> getRecommendedColors(int count, HashMap<String, Integer> colors){	
		
		SortedSet<Integer> colorValues = new TreeSet<Integer>(colors.values()).descendingSet();
		SortedSet<String> colorKeys = new TreeSet<String>(colors.keySet());		
		ArrayList<Integer> colorValueList = new ArrayList<Integer>(colorValues);
		ArrayList<String> recommendedColors = new ArrayList<String>();
		
		for(int i = 0; i < count; i++){
			for(String color : colorKeys){
				if(colors.get(color) == colorValueList.get(i)){
					recommendedColors.add(color);
				}
			}
		}	
		recommendedColors = new ArrayList<String>(recommendedColors.subList(0, count));
		return recommendedColors;
	}
	
	public static ArrayList<String> getRecommendedCities(int count, HashMap<String, Integer> cities){	
		
		SortedSet<Integer> cityValues = new TreeSet<Integer>(cities.values()).descendingSet();
		SortedSet<String> cityKeys = new TreeSet<String>(cities.keySet());		
		ArrayList<Integer> cityValueList = new ArrayList<Integer>(cityValues);
		ArrayList<String> recommendedCities = new ArrayList<String>();
		
		for(int i = 0; i < count; i++){
			for(String city : cityKeys){
				if(cities.get(city) == cityValueList.get(i)){
					recommendedCities.add(city);
				}
			}
		}	
		recommendedCities = new ArrayList<String>(recommendedCities.subList(0, count));
		return recommendedCities;
	}
	
	public static ArrayList<Integer> getRecommendedKmRange(ArrayList<Integer> kms){
		
		ArrayList<Integer> kmRange = new ArrayList<Integer>();
		kmRange.add(Collections.max(kms));
		kmRange.add(Collections.min(kms));
		
		return kmRange;
	}
	
	public static ArrayList<Integer> getRecommendedPriceRange(ArrayList<Integer> prices){
		
		ArrayList<Integer> priceRange = new ArrayList<Integer>();
		priceRange.add(Collections.max(prices));
		priceRange.add(Collections.min(prices));
		
		return priceRange;
	}
	
	
	public ArrayList<IdealCar> getIdealCarList(){
		
		ArrayList<IdealCar> idealCarList = new ArrayList<IdealCar>();

		getRecommendedKmRange(this.kms);
		getRecommendedKmRange(this.prices);
		
		for(int i = 0; i <this.count; i++){
			
			for(int j = 0; j < this.count; j++){
				
				for(int k = 0; k < this.count; k++){
					
					for(int l = 0; l < this.count; l++){
						
						IdealCar idealCar = new IdealCar();
						
						idealCar.setBrand(getRecommendedBrands(this.count, this.brands).get(i));
						idealCar.setYear(getRecommendedYears(this.count, this.years).get(j));
						idealCar.setColor(getRecommendedColors(this.count, this.colors).get(k));
						idealCar.setCity(getRecommendedCities(this.count, this.cities).get(l));
						
						idealCarList.add(idealCar);
						
					}
					
				}
				
			}
			
		}
		
		
		return idealCarList;
	}
	
}
