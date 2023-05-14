package scraping;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import main.Main;

public class City {
	// +"°"
	private String name;
	private ArrayList<Scrape> scrapes = new ArrayList<Scrape>();
	
	private int avgCurrentTemp;
	private int avgFeelsLike;
	private String avgWindDir;
	private double avgWindSpeed;
	private int avgHumidity;
	
	static private ArrayList<City> all = new ArrayList<City>();
	
	public City(String name) {
		this.name = name;
		City.all.add(this);
	}
	
	public String getName() {
		return this.name;
	}
	
	public static City getByName(String name) {
		return City.all.stream()
						.filter(source -> source.name.equalsIgnoreCase(name))
						.findFirst()
						.orElse(null);
	}
	
	public void addScrape(Scrape scrape) {
		this.scrapes.add(scrape);
	}
	
	public static void calculateAverageValues() {
		for (City c : City.all) {
			System.out.println(c);
			System.out.println(c.scrapes);
			c.avgCurrentTemp = c.getCalculatedAverageTemp();
			c.avgFeelsLike = c.getCalculatedAverageFeelsLike();
			c.avgWindDir = c.getCalculatedAverageWindDir();
			c.avgWindSpeed = c.getCalculatedAverageWindSpeed();
			c.avgHumidity = c.getCalculatedAverageHumidity();
		}
	}
	
	private int getCalculatedAverageTemp() {
		double result = this.scrapes.stream()
							.mapToInt(scrape -> scrape.getCrntTemp())
							.average()
							.orElse(0.0);
		return Main.getInt(result);
	}
	
	private int getCalculatedAverageFeelsLike() {
		double result = this.scrapes.stream()
							.mapToInt(scrape -> scrape.getFeelsLike())
							.average()
							.orElse(0.0);
		return Main.getInt(result);
	}
	
	private String getCalculatedAverageWindDir() {
		return this.scrapes.stream()
							.collect(Collectors.groupingBy(Scrape::getWindDir, Collectors.counting()))
							.entrySet().stream()
							.max(Map.Entry.comparingByValue())
							.map(Map.Entry::getKey)
							.get();
	}
	
	private double getCalculatedAverageWindSpeed() {
		double result = this.scrapes.stream()
							.mapToDouble(scrape -> scrape.getWindSpeed())
							.average()
							.orElse(0.0);
		return Main.roundTo2Decimals(result);
	}
	
	private int getCalculatedAverageHumidity() {
		double result = this.scrapes.stream()
							.mapToInt(scrape -> scrape.getHumidity())
							.average()
							.orElse(0.0);
		return Main.getInt(result);
	}
	
	public int getAvgCurrentTemp() {
		return avgCurrentTemp;
	}

	public int getAvgFeelsLike() {
		return avgFeelsLike;
	}

	public String getAvgWindDir() {
		return avgWindDir;
	}

	public double getAvgWindSpeed() {
		return avgWindSpeed;
	}

	public int getAvgHumidity() {
		return avgHumidity;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	
	
	
	
}
