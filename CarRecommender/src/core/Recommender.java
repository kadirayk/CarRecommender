package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class Recommender {

	
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
	
}
