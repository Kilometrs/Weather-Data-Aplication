package main;

import java.util.regex.Pattern;

import GUI.GUI;
import scraping.Source;

public class Main {
	static boolean isDebugging = true;
	public static void main(String[] args) {
		Main.createScrapeObjects();
		GUI.createMainFrame();
		GUI.createMainComboBoxes();
		GUI.createTabbedPane();
	}
	
	public static void createScrapeObjects() {
		// FOR THE LOVE OF JESUS, CREATE THREADS!!!
		Source.getAccuScrape("Valmiera","https://www.accuweather.com/en/lv/valmiera/226476/current-weather/226476");
		Source.getGisMeteoScrape("Valmiera", "https://www.gismeteo.lv/weather-valmiera-4101/now/");
		Source.getLVGMCScrape("Valmiera", "https://videscentrs.lvgmc.lv/data/weather_forecast_for_location_hourly?punkts=P11");

		Source.getAccuScrape("Rīga", "https://www.accuweather.com/en/lv/riga/1-225780_1_al/current-weather/1-225780_1_al");
		Source.getGisMeteoScrape("Rīga", "https://www.gismeteo.lv/weather-riga-4136/now/");
		Source.getLVGMCScrape("Rīga", "https://videscentrs.lvgmc.lv/data/weather_forecast_for_location_hourly?punkts=P28");

		Source.getAccuScrape("Liepāja", "https://www.accuweather.com/en/lv/liepaja/1-228612_1_al/current-weather/1-228612_1_al");
		Source.getGisMeteoScrape("Liepāja", "https://www.gismeteo.lv/weather-liepaja-4134/now/");
		Source.getLVGMCScrape("Liepāja", "https://videscentrs.lvgmc.lv/data/weather_forecast_for_location_hourly?punkts=P77");

		Source.getAccuScrape("Daugavpils", "https://www.accuweather.com/en/lv/daugavpils/227465/current-weather/227465");
		Source.getGisMeteoScrape("Daugavpils", "https://www.gismeteo.lv/weather-daugavpils-4177/now/");
		Source.getLVGMCScrape("Daugavpils", "https://videscentrs.lvgmc.lv/data/weather_forecast_for_location_hourly?punkts=P75");
	}
	
	public static int getInt(String string) {
		Pattern regex = Pattern.compile("[-+]?\\d+[.]?\\d*");
		try {
			System.out.println(regex.matcher(string).group());
			return Integer.parseInt(regex.matcher(string).toString());
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("String error at "+string);
			return 0;
		}
	}
}
