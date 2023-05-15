package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import GUI.GUI;
import scraping.City;
import scraping.Scrape;
import scraping.Source;

public class Main {
	public static DB db = new DB();
	static boolean isDebugging = true;
	public static void main(String[] args) {
		System.out.println("BINHDS");
		Main.createScrapeObjects();
		City.calculateAverageValues();
		Main.saveDataToDB();
		Main.fillAllHistories();
		GUI.createMainFrame();
		GUI.createMainComboBoxes();
		GUI.createTabbedPane();
	}
	
	public static void createScrapeObjects() {
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
	
	private static void saveDataToDB() {
		Source.save();
		City.save();
		Scrape.save();
	}
	
	private static void fillAllHistories() {
		Scrape.fillHistories();
	}
	public static int getInt(String input) {
	    Pattern doublePattern = Pattern.compile("([-+]?\\d+[.]\\d+)");
	    Matcher matcher = doublePattern.matcher(input);
	    if (matcher.find()) {
	        String doubleMatch = matcher.group(1);
	        try {
	            double result = Double.parseDouble(doubleMatch);
	            return (int) Math.round(result);
	        } catch (NumberFormatException e) {
	            System.out.println("Could not parse double value: " + doubleMatch);
	        }
	    }
	    Pattern intPattern = Pattern.compile("([-+]?\\d+)");
	    matcher = intPattern.matcher(input);
	    if (matcher.find()) {
	        String intMatch = matcher.group(1);
	        try {
	            return Integer.parseInt(intMatch);
	        } catch (NumberFormatException e) {
	            System.out.println("Could not parse int value: " + intMatch);
	        }
	    }
	    System.out.println("No match found in input: " + input);
	    return 0;
	}
	
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    
    public static void printArray(String[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.println(i+"> "+array[i]);
		}
		System.out.println();
		
    }

	public static double roundTo2Decimals(double value) {
		return Math.round(value * 10.0) / 10.0;
	}
	
	public static int getInt(double value) {
		return (int) Math.round(value);
	}
	
	public static String getFormatTemp(int temp) {
		return temp+"°";
	}
	
	public static String getFormatWind(String direction, double speed) {
		return direction+" "+speed+" m/s";
	}
	
	public static String getFormatPercent(int perc) {
		return perc+"%";
	}
}
