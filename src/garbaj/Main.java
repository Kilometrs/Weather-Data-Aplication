package garbaj;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	public static void main(String[] args) {
		scrapeNames();
		scrapeCounts();
		Person.save();
	}
	
	static void scrapeCounts() {
		int i = 0;
		for (Person p : Person.all) {
			i++;
			int count = scrapeNameCount(p.name);
			p.setCount(count);
			if (i % 20 == 0) {
				System.out.println("Checked " +i +" people");
			}
		}
	}
	
	static int scrapeNameCount(String name) {
		String url = "https://personvardi.pmlp.gov.lv/index.php?name=" + name;
		Document doc = WebWorm.getPage(url);
		try {
			Element tableRow = doc.selectFirst("table tbody tr");
			String countText = tableRow.select("td").get(1).text();
			return Integer.parseInt(countText);
		} catch (Exception e) {
			System.out.println("Problem parsing for " +name);
		}
		return 0;
	}
	
	static void scrapeNames() {
		for (int month = 1; month <= 12; month++) {
			scrapeMonthNames(month);
		}
	}
	
	static void scrapeMonthNames(int month) {
		System.out.println("We are at month " +month);
		String url = "http://vardadiena.com/kalendars/LV/2023/"+month+"/";
		Document doc = WebWorm.getPage(url);
		Elements dates = doc.select(".wday, .wdayred");
		for (Element d : dates) {
			scrapeDateNames(d, month);
		}
	}
	
	static void scrapeDateNames(Element d, int month) {
		try {
			String dateString = d.text().split(" ")[0].trim();
			if (dateString.isEmpty()) return;
			int date = Integer.parseInt(dateString);
			String[] names = d.selectFirst(".names").text().split(" ");
			for (String name : names) {
				new Person(name, month, date);
			}
		} catch (Exception e) {
			System.out.println("Problem with " +d);
			e.printStackTrace();
		}
	}
	
}
