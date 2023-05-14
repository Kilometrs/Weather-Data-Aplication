package scraping;

import java.util.ArrayList;

public class Scrape {
	private Source source;
	private City city;
	private int currentTemp;
	private int feelsLike;
	private String windDir;
	private double windSpeed;
	private int humidity;
	
	static ArrayList<Scrape> all = new ArrayList<Scrape>();
	
	public Scrape(Source source, City city, int currentTemp,
			int feelsLike, String windDir, double windSpeed, int humidity) {
		this.source = source;
		this.city = city;
		this.currentTemp = currentTemp;
		this.feelsLike = feelsLike;
		this.windDir = windDir;
		this.windSpeed = windSpeed;
		this.humidity = humidity;
		Scrape.all.add(this);
		this.city.addScrape(this);
		System.out.println("<DEBUG> Source "+this.source+" now has weather data about "+this.city.getName());
	}
	
	public static Scrape getBy(Source source, City city) {
		return Scrape.all.stream()
						 .filter(s -> s.source == source && 
								 	  s.city == city)
						 .findFirst()
						 .orElse(null);
	}
	
	public int getCrntTemp() {
		return currentTemp;
	}

	public final int getFeelsLike() {
		return feelsLike;
	}

	public final double getWindSpeed() {
		return windSpeed;
	}
	
	public final String getWindDir() {
		return windDir;
	}

	public final int getHumidity() {
		return humidity;
	}
	
	@Override
	public String toString() {
		return this.city.getName();
	}
}
