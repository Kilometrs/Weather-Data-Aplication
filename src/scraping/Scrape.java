package scraping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.jsoup.nodes.Document;

public class Scrape {
	String source;
	String city;
	String currentTemp;
	String feelsLike;
	String windSpeed;
	String humidity;
	public static ArrayList<Scrape> all = new ArrayList<Scrape>();
	
	public Scrape(String source, String city, String currentTemp, String feelsLike, String windSpeed, String humidity) {
		this.source = source;
		this.city = city;
		this.currentTemp = currentTemp;
		this.feelsLike = feelsLike;
		this.windSpeed = windSpeed;
		this.humidity = humidity;
		Scrape.all.add(this);
	}
	
	public static String[] getCityArray() {
	    Set<String> uniqueCities = all.stream()
	            .map(scrape -> scrape.city)
	            .collect(Collectors.toCollection(HashSet::new));
	    System.out.println(uniqueCities);
	    return uniqueCities.toArray(new String[uniqueCities.size()]);
	}
	
	public static Scrape get(String source, String city) {
		return Scrape.all.stream()
						 .filter(str -> str.city.contentEquals(city) && str.source.contentEquals(source))
						 .findFirst()
						 .orElse(null);
	}
 
	// 0 is Temp, 1 is Real Feel, 2 is Wind, 3 is Humidity
	public static Scrape getAccuScrape(String city, String url) {
		Document html = WebReader.getPage(url);
		if (html == null) return null;
		String temp = html.getElementsByClass("display-temp").text();
		String currTmp = temp.substring(0, temp.length()-1);
		String[] lineSplit = html.getElementsByClass("detail-item spaced-content").text().split(" ");
		String feelsLike = lineSplit[2];
		String wind = lineSplit[12]+" "+lineSplit[13]+" "+lineSplit[14];
		String humidity = lineSplit[20];
		return new Scrape("AccuWeather", city,currTmp, feelsLike, wind, humidity);
	}
	



	public static Scrape getGisMeteoScrape(String city,String url) {
		Document html = WebReader.getPage(url);
		String[] split1 = html.getElementsByClass("unit unit_temperature_c").text().split(" ");
		String currTmp = split1[0];
		String feelsLike = split1[1];
		String[] split2 = html.getElementsByClass("item-value").text().split(" ");
		String wind = split2[2]+" "+split2[0]+" "+split2[1];
		String[] split3 = html.getElementsByClass("now-info-item humidity").text().split(" ");
		String humidity = split3[1]+" "+split3[2];
		return new Scrape("GisMeteo", city,currTmp, feelsLike, wind, humidity);
	}

	public static Scrape getLVGMCScrape(String city, String url) {
		JSONArray jsonArray = WebReader.getRawJSON(url);
		int i = 3;
		System.out.println(jsonArray.getJSONObject(i).get("laiks").toString());
		String currTmp  = jsonArray.getJSONObject(i).get("temperatura").toString();
		String feelsLike = jsonArray.getJSONObject(i).get("sajutu_temperatura").toString();
		String wind = jsonArray.getJSONObject(i).get("veja_atrums").toString();
		String humidity = jsonArray.getJSONObject(i).get("relativais_mitrums").toString();
		return new Scrape("LVĢMC", city, currTmp, feelsLike, wind, humidity);
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
	
	public String getSource() {
		return this.source;
	}
	
}
