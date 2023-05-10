package scraping;


import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.json.JSONArray;


public class WebReader {

	static Document getPage(String url) {
		// this one returns the whole HTML of the page
//		System.out.println("GET: " +url);
		pause(300);
		try {
			return Jsoup.connect(url)
					.data("query", "Java")
					.userAgent("Mozilla/5.0")
					.cookie("auth", "token")
					.timeout(10000)
					.get();
			
		} catch (Exception e) {
			System.out.println("Error on " +url);
			e.printStackTrace();
			return null;
		}
	}
	
	static JSONArray getRawJSON(String url) {
//		System.out.println("GET JSON: " +url);
        try {
            Document doc = Jsoup.connect(url)
            		.data("query", "Java")
					.userAgent("Mozilla/5.0")
					.cookie("auth", "token")
					.timeout(10000)
            		.ignoreContentType(true)
            		.get();
            String jsonString = doc.body().text();
//          System.out.println(jsonString);
            JSONArray jsonArray = new JSONArray(jsonString);
//            System.out.println(jsonArray);
//          System.out.println(jsonObject);
            return jsonArray;
        } catch (Exception e) {
        	System.out.println("Error on " +url);
            e.printStackTrace();
        }
        return null;
	}

	static void pause(int millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {}
	}

}
