package garbaj;

import java.util.ArrayList;

public class Person {
	Integer id;
	String name;
	int month;
	String monthText;
	int date;
	int count; //how many ppl have this name
	static String[] monthNames = "Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec".split(",");
	static ArrayList<Person> all = new ArrayList<Person>();
	public Person(String name, int month, int date) {
		this.name = name;
		this.month = month;
		this.monthText = monthNames[this.month - 1];
		this.date = date;
		all.add(this);
	}
	
	void setCount(int count) {
		this.count = count;
	}
	
	static void save() {
		DBd db = new DBd();
		int i = 0;
		for (Person p : Person.all) {
			i++;
			if (i % 10 == 0) {
				System.out.println("Saving " +i+"/"+Person.all.size());
			}
			String query = "INSERT INTO person (name, count, date, month) values"
					+ " ('"+p.name+"', "+p.count+", "+p.date+", "+p.month+");";
			db.insert(query);
		}
	}
	
	public String toString() {
		return monthText+" "+date+" = " +name+" = " +count;
	}
}
