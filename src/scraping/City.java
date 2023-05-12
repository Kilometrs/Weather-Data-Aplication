package scraping;

import java.util.ArrayList;

public class City {
	// +"°"
	Source source;
	String name;
	int currentTemp;
	int feelsLike;
	String windDir;
	double windSpeed;
	int humidity;
	static private ArrayList<City> all = new ArrayList<City>();
	
	public City(Source source, String name, int currentTemp,
			int feelsLike, String windDir, double windSpeed, int humidity) {
		this.source = source;
		this.name = name;
		this.currentTemp = currentTemp;
		this.feelsLike = feelsLike;
		this.windDir = windDir;
		this.windSpeed = windSpeed;
		this.humidity = humidity;
		City.all.add(this);
		System.out.println("<DEBUG> Source "+this.source+" now has weather data about "+this.name);
	}
	
	public String getAverageCrntTemp() {
		try {
			return City.all.stream()
			.filter(city -> city.name.equalsIgnoreCase(this.name))
			.mapToInt(city -> Integer.parseInt(city.getCrntTemp()))
			.average()
			.toString();
		} catch (Exception e) {
			System.out.println(e);
			return "<e5>";
		}
		
	}
	
	public final String getCrntTemp() {
		return currentTemp+" °";
	}
	
	public String getAverageFeelsLike() {
		try {
			return City.all.stream()
			.filter(city -> city.name.equalsIgnoreCase(this.name))
			.mapToInt(city -> Integer.parseInt(city.getFeelsLike()))
			.average()
			.toString();
		} catch (Exception e) {
			System.out.println(e);
			return "<e5>";
		}
		
	}

	public final String getFeelsLike() {
		return feelsLike+" °";
	}
	
	public String getAverageWindSpeed() {
		try {
			return City.all.stream()
			.filter(city -> city.name.equalsIgnoreCase(this.name))
			.mapToInt(city -> Integer.parseInt(city.getWindSpeed()))
			.average()
			.toString();
		} catch (Exception e) {
			System.out.println(e);
			return "<e5>";
		}
		
	}

	public final String getWindSpeed() {
		return windDir+" "+windSpeed+" m/s";
	}
	
	public String getAverageHumidity() {
		try {
			return City.all.stream()
			.filter(city -> city.name.equalsIgnoreCase(this.name))
			.mapToInt(city -> Integer.parseInt(city.getHumidity()))
			.average()
			.toString();
		} catch (Exception e) {
			System.out.println(e);
			return "<e5>";
		}
	}

	public final String getHumidity() {
		return humidity+"%";
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	
	
	
	
}
