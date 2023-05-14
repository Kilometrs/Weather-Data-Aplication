package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DB {
	private Connection con;
	private Statement stmt;
	private PreparedStatement ps;
	private ResultSet rs;
	private String db = "weather_app_database";
	
	public DB() {
		try {
			String params = "?useSSL=false&autoReconnect=true&allowMultiQueries=true";
			this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db+params, "root", "root");
			this.stmt = con.createStatement();
		} catch (Exception e) {
			System.err.println("Problems creating DB connection");
			e.printStackTrace();
		}
	}
	
	// .. code to fetch data 
}