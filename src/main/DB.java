package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DB {
	private Connection con;
	private Statement stmt;
	private PreparedStatement ps;
	private ResultSet rs;
	private String db = "java";
	
	public DB() {
		try {
			String params = "?useSSL=false&autoReconnect=true&allowMultiQueries=true";
			this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db+params, "root", "");
			this.stmt = con.createStatement();
		} catch (Exception e) {
			System.err.println("Problems creating DB connection");
			e.printStackTrace();
		}
	}
	
	
	public ResultSet select(String query) {
		try {
			return stmt.executeQuery(query);
		} catch (Exception e) {
			System.err.println("Problems with query: " +query);
			e.printStackTrace();
			return null;
		}
	}
	
	public void insert(String query) {
		try {
			stmt.executeUpdate(query);
//			System.out.println("STATEMENT EXECUTION -> "+query);
//			ps.executeUpdate(query);
		} catch (Exception e) {
			System.err.println("Problems with query: " +query);
			e.printStackTrace();
		}
	}
	
	public String[][] getSavedHistory(String sourceName, String cityName) {
		String[][] result;
		String query;
		String source = sourceName == null ? 
						"IS NULL" : "= (SELECT id FROM sources WHERE `name` = '"+sourceName+"')";
		query = "SELECT * "
				+  "FROM data "
				+  "WHERE city_fk = (SELECT id FROM cities WHERE `name` = '"+cityName+"') "
				+  "AND source_fk "+source+"; ";
		ArrayList<String[]> arrayList = new ArrayList<String[]>();
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String[] timeStamp = rs.getString("time").split(" ");
				String date = timeStamp[0].substring(6);
				String time = timeStamp[1].substring(0, 5);
				String temp = Main.getFormatTemp(rs.getInt("temperature"));
				String feelsLike = Main.getFormatTemp(rs.getInt("feels_like"));
				String windDir =  rs.getString("wind_direction");
				String windSpeed = rs.getString("wind_speed");
				String humidity = Main.getFormatPercent(rs.getInt("humidity"));
				String[] row = {date, time, temp, feelsLike, windDir, windSpeed, humidity};
				arrayList.add(row);
			}
		} catch (Exception e) {
			System.out.println("Problem with selecting users");
			e.printStackTrace();
		}
		return result = arrayList.toArray(new String[getSavedDataCount(sourceName, cityName)][]);
	}
	
	private int getSavedDataCount(String sourceName, String cityName) {
		int result = 0;
		String query = "SELECT count(*)\r\n"
					 + "FROM data\r\n"
					 + "WHERE city_fk = (SELECT id FROM cities WHERE `name` = '"+cityName+"')\r\n"
					 + "AND source_fk = (SELECT id FROM sources WHERE `name` = '"+sourceName+"');\r\n";
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				result = rs.getInt("count(*)");
			}
		} catch (Exception e) {
			System.out.println("Problem with getting count");
			e.printStackTrace();
		}
		return result;
	}
	
	// .. code to fetch data 
}