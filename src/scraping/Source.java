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

import main.Main;


public class Source {
	private String name;
	ArrayList<Scrape> scrapes = new ArrayList<Scrape>();
	ArrayList<City> cities = new ArrayList<City>();

	public static ArrayList<Source> all = new ArrayList<Source>();
	static boolean isDebugging = true;
	
	public static Source create(String name, String cityName, int currentTemp,
								int feelsLike, String windDir, double windSpeed, int humidity) {
		System.out.println(name+";"+cityName+";"+currentTemp+";"+feelsLike+";"+windDir+";"+windSpeed+";"+humidity+";");
		Source source = getByName(name);
		if (source == null) source = new Source(name);
		City city = City.getByName(cityName);
		if (city == null) city = new City(cityName);
		Scrape newScrape = new Scrape(source, city, currentTemp, feelsLike, windDir, windSpeed, humidity);
		source.scrapes.add(newScrape);
		source.cities.add(city);
		return source;
	}
	
	private Source(String name) {
		this.name = name;
		Source.all.add(this);
		if (isDebugging) System.out.println("<DEBUG> Created new Source called "+this.name);
	}
	
	public String getName() {
		return this.name;
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
		String tempString = html.getElementsByClass("display-temp").text();
		System.out.println(tempString);
		System.out.println(Main.getInt(tempString));
		int currTmp = Main.getInt(html.getElementsByClass("display-temp").text());
		String[] lineSplit = html.getElementsByClass("detail-item spaced-content").text().split(" ");
		int feelsLike = Main.getInt(lineSplit[2]);
		String windDir = lineSplit[12];
		double wind = getAsMPS(lineSplit[13]);
		int humidity = Main.getInt(lineSplit[20]);

		return create("AccuWeather", city, currTmp, feelsLike, windDir, wind, humidity);
	}

	public static Source getGisMeteoScrape(String city,String url) {
		Document html = WebReader.getPage(url);
		if (html == null) return null;
		String[] split1 = html.getElementsByClass("unit unit_temperature_c").text().split(" ");
		int currTmp = Main.getInt(split1[0]);
		int feelsLike = Main.getInt(split1[1]);
		String[] split2 = html.getElementsByClass("item-value").text().split(" ");
		String windDir = getTranslatedWindDir(split2[2]);
		int wind = Main.getInt(split2[0]);
		String[] split3 = html.getElementsByClass("now-info-item humidity").text().split(" ");
		int humidity = Main.getInt(split3[1]);
		return create("GisMeteo", city,currTmp, feelsLike, windDir, wind, humidity);
	}

	public static Source getLVGMCScrape(String city, String url) {
		JSONArray jsonArray = WebReader.getRawJSON(url);
		if (jsonArray == null) return null;
		int i = getCrntPrognosisPos(jsonArray);
//		if (isDebugging) System.out.println("<DEBUG> Time fetched:"+jsonArray.getJSONObject(i).get("laiks").toString());
		int currTmp  = (int) Math.round(jsonArray.getJSONObject(i).getDouble("temperatura"));
		int feelsLike = Main.getInt(jsonArray.getJSONObject(i).get("sajutu_temperatura").toString());
		String windDir = getCardinalDirAbr(jsonArray.getJSONObject(i).getDouble("veja_virziens"));
		int wind = Main.getInt(jsonArray.getJSONObject(i).get("veja_atrums").toString());
		int humidity = Main.getInt(jsonArray.getJSONObject(i).get("relativais_mitrums").toString());
		return create("LVĢMC", city, currTmp, feelsLike, windDir, wind, humidity);
	}
	
	private static int getCrntPrognosisPos(JSONArray jsonArray) {
		int i = 0;
		String crntTime = getCrntLvTime();
		while(i < jsonArray.length()) {
			if (!jsonArray.getJSONObject(i).get("laiks").toString().endsWith(crntTime)) {
				i++;
				continue;
			}
			return i;
		}
		return 3; // because it generally is the correct one
	}

	private static String getCardinalDirAbr(double deg) {
		if (isDebugging) System.out.println("<DEBUG> Degrees: "+deg);
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
	
	private static double getAsMPS(String kph) {
		double parsed = 0;
		try {
			parsed = Double.parseDouble(kph);
		} catch (Exception e) {
			return 0;
		}
		return Math.round((parsed * 0.277778) * 10.0) / 10.0;
	}
	
	public static void printAllDataDebug() {
		Source.all.stream()
				  .forEach(str -> {
					  System.out.println(str+" has "+str.cities);
				  });
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
