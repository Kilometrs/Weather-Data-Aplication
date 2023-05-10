package main;

import GUI.GUI;
import scraping.Scrape;

public class Main {
	static String[] cityArray = {"Valmiera", "Rīga", "Daugavpils", "Liepāja"};
	public static void main(String[] args) {
		Main.createScrapeObjects();
		GUI.createMainFrame();
		GUI.createMainComboBox(Scrape.getCityArray());
		GUI.createTabbedPane();
		System.out.println(Scrape.all.size());
	}
	
	public static void createScrapeObjects() {
		// FOR THE LOVE OF JESUS, CREATE THREADS!!!
		Scrape.getAccuScrape("Valmiera","https://www.accuweather.com/en/lv/valmiera/226476/current-weather/226476");
		Scrape.getAccuScrape("Rīga", "https://www.accuweather.com/en/lv/riga/1-225780_1_al/current-weather/1-225780_1_al");
		Scrape.getAccuScrape("Liepāja", "https://www.accuweather.com/en/lv/liepaja/1-228612_1_al/current-weather/1-228612_1_al");
		Scrape.getAccuScrape("Daugavpils", "https://www.accuweather.com/en/lv/daugavpils/227465/current-weather/227465");
		
		Scrape.getGisMeteoScrape("Valmiera", "https://www.gismeteo.lv/weather-valmiera-4101/now/");
		Scrape.getGisMeteoScrape("Rīga", "https://www.gismeteo.lv/weather-riga-4136/now/");
		Scrape.getGisMeteoScrape("Liepāja", "https://www.gismeteo.lv/weather-liepaja-4134/now/");
		Scrape.getGisMeteoScrape("Daugavpils", "https://www.gismeteo.lv/weather-daugavpils-4177/now/");
		
		Scrape.getLVGMCScrape("Valmiera", "https://videscentrs.lvgmc.lv/data/weather_forecast_for_location_hourly?punkts=P11");
		Scrape.getLVGMCScrape("Rīga", "https://videscentrs.lvgmc.lv/data/weather_forecast_for_location_hourly?punkts=P28");
		Scrape.getLVGMCScrape("Liepāja", "https://videscentrs.lvgmc.lv/data/weather_forecast_for_location_hourly?punkts=P77");
		Scrape.getLVGMCScrape("Daugavpils", "https://videscentrs.lvgmc.lv/data/weather_forecast_for_location_hourly?punkts=P75");
	}
}
