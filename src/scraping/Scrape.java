package scraping;

import java.util.ArrayList;

import main.DB;
import main.Main;

public class Scrape {
	private Source source;
	private City city;
	private int currentTemp;
	private int feelsLike;
	private String windDir;
	private double windSpeed;
	private int humidity;
	private String[][] history;
	
	static boolean isDebugging = true;
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
		if (isDebugging) System.out.println("<DEBUG> Source "+this.source+" now has weather data about "+this.city.getName());
	}
	
	public static void save() {
		DB db = Main.db;
		for (Scrape s : all) {
			String query = "INSERT INTO `data` (source_fk, city_fk, type, time, temperature, "
						 + "feels_like, wind_direction, wind_speed, humidity)"
						 + "VALUES"
						 + " ((SELECT id FROM `sources` WHERE name = '"+s.source.getName()+"'),"
						 + " (SELECT id FROM `cities` WHERE name = '"+s.city.getName()+"'),"
						 + "2, NOW(), "+s.getCrntTemp()+", "+s.getFeelsLike()+", '"+s.getWindDir()+"',"
						 + ""+s.getWindSpeed()+", "+s.getHumidity()+");";
			db.insert(query);
		}
	}
	
	public static void fillHistories() {
		for (Scrape scrape : all) {
			scrape.history = Main.db.getSavedHistory(scrape.source.getName(), scrape.city.getName());
		}
	}
	
	public City getCity() {
		return this.city;
	}
	
	public static Scrape getBy(Source source, City city) {
		return Scrape.all.stream()
						 .filter(s -> s.source == source && 
								 	  s.city == city)
						 .findFirst()
						 .orElse(null);
	}
	
	public Source getSource() {
		return source;
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
