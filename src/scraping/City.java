package scraping;

import java.util.ArrayList;

public class City {
	Source source;
	String name;
	String currentTemp;
	String feelsLike;
	String windSpeed;
	String humidity;
	static private ArrayList<City> all = new ArrayList<City>();
	
	public City(Source source, String name, String currentTemp, String feelsLike, String windSpeed, String humidity) {
		this.source = source;
		this.name = name;
		this.currentTemp = currentTemp;
		this.feelsLike = feelsLike;
		this.windSpeed = windSpeed;
		this.humidity = humidity;
		City.all.add(this);
		System.out.println("<DEBUG> Source "+this.source+" now has weather data about "+this.name);
	}
	

	public final String getCurrentTemp() {
		return currentTemp;
	}

	public final String getFeelsLike() {
		return feelsLike;
	}

	public final String getWindSpeed() {
		return windSpeed;
	}

	public final String getHumidity() {
		return humidity;
	}

	public final void setCurrentTemp(String currentTemp) {
		this.currentTemp = currentTemp;
	}

	public final void setFeelsLike(String feelsLike) {
		this.feelsLike = feelsLike;
	}

	public final void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public final void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	
	
	
	
}
