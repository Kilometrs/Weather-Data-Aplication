package garbaj;

public class User {
	Integer id; //might not have value. Default is null (instead of 0 when choosing int)
	String username;
	String name;
	String password;
	boolean isAdmin;
	
	public User(int id, String username, String name, String password) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
	}

	public User(String username, String name, String password) {
		this.username = username;
		this.name = name;
		this.password = password;
	}
	
	public static void create(String username, String name, String password) {
		new User(username, name, password);
		//save in the DB
	}
	
	public String toString() {
		return "#"+id+" " +name + " ("+username+")";
	}
	
}
