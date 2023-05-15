package garbaj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBd {
	private Connection con;
	private Statement stmt;
	private PreparedStatement ps;
	private ResultSet rs;
	private String db = "test";
	
	public DBd() {
		try {
			String params = "?useSSL=false&autoReconnect=true&allowMultiQueries=true";
			this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db+params, "root", "root");
			this.stmt = con.createStatement();
		} catch (Exception e) {
			System.err.println("Problems creating DB connection");
			e.printStackTrace();
		}
	}
	
	public ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<User>();
		String query = "SELECT id, username, name, password from user";
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String name = rs.getString("name");
				String password = rs.getString("password");
				users.add(new User(id, username, name, password));
			}
		} catch (Exception e) {
			System.out.println("Problem with selecting users");
			e.printStackTrace();
		}
		return users;
	}
	
	public String insertProperly() {
		
		//ps.executeUpdate();
		return null;
	}
	
	public String getUserCorrectly(String username) {
		try {
			ps = con.prepareStatement("SELECT name from user where username=?;");
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) { //iterates every row
				return rs.getString("name"); //returns the first value from first row
				//return rs.getString(1);
			}			
		} catch (Exception e) {
			System.out.println("Things happened " +e);
		}
		return null;
	}
	
	public String getUser(String username) {
		String query = "SELECT name from user where username='"+username+"';";
		System.out.println(query);
		rs = select(query);
		
		try {
			while (rs.next()) { //iterates every row
				return rs.getString("name"); //returns the first value from first row
			}			
		} catch (Exception e) {
			System.out.println("Things happened " +e);
		}
		return null;
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
		} catch (Exception e) {
			System.err.println("Problems with query: " +query);
			e.printStackTrace();
		}
	}
}
