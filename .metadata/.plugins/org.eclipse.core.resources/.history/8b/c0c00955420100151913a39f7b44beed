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
					System.out.println("added: " + brand);
				}
			}
		}
		
		recommendedBrands = new ArrayList<String>(recommendedBrands.subList(0, count));
		
		return recommendedBrands;
	}
	
	
}
