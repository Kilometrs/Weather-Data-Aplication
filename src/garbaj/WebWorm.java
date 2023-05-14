package garbaj;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebWorm {
	static String baseUrl;
	
	
	static Document getPage(String urlString) {
		Document doc = null;
		try {
			pause(2000);
			String randomUA = "Internet Explorer 1.0 Magical laptop from Narnia";
			String contentType = "application/json";
			return Jsoup
					.connect(urlString)
					.timeout(20000)
					.header("content-type", contentType)
					.header("cookie", "somethinghere")
					.userAgent(randomUA)
					.ignoreContentType(true)
					.execute()
					.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	static void pause(int millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {}
	}
	
}
