package network;

import java.io.IOException;
import java.util.ArrayList;

import model.Car;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Crawler {
	
	public ArrayList<Car> getCarListFromUrl(int maxPageNum){
		
		ArrayList<Car> carList = new ArrayList<Car>();
		
		Document doc = null;
		
		for(int j = 0; j < maxPageNum; j++){
			
			try {
				
				doc = Jsoup.connect("http://www.sahibinden.com/otomobil?pagingOffset=" + (j * 20) ).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").timeout(5000).get();	
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Elements titles = doc.select("a[class=\"classifiedTitle\"]");
			Elements brands_models = doc.select("div[class=\"classifiedSubtitle\"]");
			Elements years_kms_colors = doc.select("td[class=\"searchResultsAttributeValue\"]");
			Elements prices = doc.select("td[class=\"searchResultsPriceValue\"]");
			Elements cities_towns = doc.select("td[class=\"searchResultsLocationValue\"]");
			
			
			for(int i = 0; i < titles.size(); i++){
				Car car = new Car();
				car.setTitle(titles.get(i).text());
				car.setBrand(brands_models.get(i).text().split(">")[0].trim());
				car.setModel(brands_models.get(i).text().split(">")[1].trim());
				car.setModelDetail(brands_models.get(i).text().split(">")[2].trim());
				car.setYear(Integer.parseInt(years_kms_colors.get( ( i * 3 ) ).text().replace(".", "")));
				car.setKm(Integer.parseInt(years_kms_colors.get( ( i * 3 ) + 1 ).text().replace(".", "")));
				car.setColor(years_kms_colors.get( ( i * 3 ) + 2 ).text());
				
				car.setPrice(removeCurrencyFromPrice(prices.get(i).text()));
				car.setCity(cities_towns.get(i).text().split(" ")[0].trim());
				car.setTown(cities_towns.get(i).text().split(" ")[1].trim());
				carList.add(car);
			}
			
		}
		
		return carList;
	}
	
	
	private int removeCurrencyFromPrice(String price){
		
		price = price.replace(".", "").replaceAll("[^\\d.]", "");
		return Integer.valueOf(price);
	}
	
}