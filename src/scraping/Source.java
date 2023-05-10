package scraping;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.jsoup.nodes.Document;

public class Source {
	String name;
	ArrayList<City> cities = new ArrayList<City>();
	public static ArrayList<Source> all = new ArrayList<Source>();
	
	public static Source create(String name, String city, String currentTemp,
								String feelsLike, String windSpeed, String humidity) {
		Source source = getByName(name);
		if (source == null) source = new Source(name);
		source.cities.add(new City(source, city, currentTemp, feelsLike, windSpeed, humidity));
		return source;
	}
	
	private Source(String name) {
		this.name = name;
		Source.all.add(this);
		System.out.println("<DEBUG> Created new Source called "+this.name);
	}
	
	private static Boolean alreadyExists(String name) {
		return Source.all.stream()
						 .anyMatch(source -> source.name.equals(name));
	}
	
	private static Source getByName(String name) {
//		System.out.println(Source.all.stream()
//							.filter(source -> source.name.equalsIgnoreCase(name))
//							.toList());
		return Source.all.stream()
							.filter(source -> source.name.equalsIgnoreCase(name))
							.findFirst()
							.orElse(null);
	}
	
	public static Source[] getAvailableSources() {
		return Source.all.toArray(Source[]::new);
	}
	
	public City[] getSrcAvailableCities() {
		return this.cities.toArray(City[]::new);
	}
 
	// 0 is Temp, 1 is Real Feel, 2 is Wind, 3 is Humidity
	public static Source getAccuScrape(String city, String url) {
		Document html = WebReader.getPage(url);
		if (html == null) return null;
		String temp = html.getElementsByClass("display-temp").text();
		String currTmp = temp.substring(0, temp.length()-1);
		String[] lineSplit = html.getElementsByClass("detail-item spaced-content").text().split(" ");
		String feelsLike = lineSplit[2];
		//round this fart!!!!
		String wind = lineSplit[12]+" "+getAsMPS(lineSplit[13])+" "+lineSplit[14];
		String humidity = lineSplit[20];
		return create("AccuWeather", city, currTmp, feelsLike, wind, humidity);
	}

	public static Source getGisMeteoScrape(String city,String url) {
		Document html = WebReader.getPage(url);
		if (html == null) return null;
		String[] split1 = html.getElementsByClass("unit unit_temperature_c").text().split(" ");
		String currTmp = split1[0]+"°";
		if (currTmp.startsWith("+")) currTmp = currTmp.substring(1);
		String feelsLike = split1[1]+"°";
		String[] split2 = html.getElementsByClass("item-value").text().split(" ");
		String wind = getTranslatedWindDir(split2[2])+" "+split2[0]+" "+split2[1];
		String[] split3 = html.getElementsByClass("now-info-item humidity").text().split(" ");
		String humidity = split3[1]+" "+split3[2];
		return create("GisMeteo", city,currTmp, feelsLike, wind, humidity);
	}

	public static Source getLVGMCScrape(String city, String url) {
		JSONArray jsonArray = WebReader.getRawJSON(url);
		if (jsonArray == null) return null;
		int i = 0;
		String crntTime = getCrntLvTime();
		while(i < jsonArray.length()) {
			if (!jsonArray.getJSONObject(i).get("laiks").toString().endsWith(crntTime)) {
				i++;
				continue;
			}
			break;
		}
		System.out.println(jsonArray.getJSONObject(i).get("laiks").toString());
		String currTmp  = (int) Math.round(jsonArray.getJSONObject(i).getDouble("temperatura"))+"°";
		String feelsLike = jsonArray.getJSONObject(i).get("sajutu_temperatura").toString()+"°";
		double windDir = jsonArray.getJSONObject(i).getDouble("veja_virziens");
		String wind = getCardinalDirAbr(windDir)+" "+jsonArray.getJSONObject(i).get("veja_atrums").toString()+" m/s";
		String humidity = jsonArray.getJSONObject(i).get("relativais_mitrums").toString();
		return create("LVĢMC", city, currTmp, feelsLike, wind, humidity);
	}

	private static String getCardinalDirAbr(double deg) {
		// this originally consisted of ~50 lines...
		System.out.println("<DEBUG> Degrees: "+deg);
		String[] abrs = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
		int res = (int) Math.floor((deg / 22.5) + 0.5);
		return abrs[res%16];
	}
	
	private static String getCrntLvTime() {
		LocalTime currentTime = LocalTime.now(ZoneId.of("Europe/Riga"));
		int minute = currentTime.getMinute();
		if (minute >= 30) {
		    currentTime = currentTime.plusHours(1);
		}
		currentTime = currentTime.truncatedTo(ChronoUnit.HOURS);
		return currentTime.toString().replace(":", "");
	}
	
	private static String getTranslatedWindDir(String direction) {
		if (direction.equalsIgnoreCase("ziemeļu")) return "N";
		if (direction.equalsIgnoreCase("ZA")) return "NE";
		if (direction.equalsIgnoreCase("austrumu")) return "E";
		if (direction.equalsIgnoreCase("DA")) return "SE";
		if (direction.equalsIgnoreCase("dienvidu")) return "S";
		if (direction.equalsIgnoreCase("DR")) return "SW";
		if (direction.equalsIgnoreCase("rietumu")) return "W";
		if (direction.equalsIgnoreCase("ZR")) return "NW";
		return "";
	}
	
	private static String getAsMPS(String kph) {
		double parsed = 0;
		try {
			parsed = Double.parseDouble(kph);
		} catch (Exception e) {
			return "<e4>";
		}
		return Double.toString(Math.round((parsed * 0.277778) * 10.0) / 10.0);
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
