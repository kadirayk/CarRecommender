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
	
	
}
