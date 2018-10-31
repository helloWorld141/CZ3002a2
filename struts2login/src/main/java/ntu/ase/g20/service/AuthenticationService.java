package ntu.ase.g20.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import ntu.ase.g20.model.User;

// this is a singleton
public class AuthenticationService {
	private static AuthenticationService authenticationService;
	private Connection connection;
	private MessageDigest md;
	
	private AuthenticationService() {
		// create message digest for password
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("-------- MySQL JDBC Connection Testing ------------");
		// check for mysql driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}

		System.out.println("MySQL JDBC Driver Registered!");
		// try connecting to database
		try {
			connection = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com/sql12263605", 
					"sql12263605", "Nq83zZBfmw");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		// connected
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		
	}
	
	public static AuthenticationService getIntance() {
		if (authenticationService == null) {
			authenticationService = new AuthenticationService();
		}
		return authenticationService;
	}
	// check for db connection
	public boolean isConnected() {
		if (connection != null) {
			return true;
		}
		return false;
	}
	
	// business logic: check user's credential
	public User authenticate(String username, String password) {
		// digest the password inputed by user
		byte[] messageDigest = md.digest(password.getBytes());
		BigInteger no = new BigInteger(1, messageDigest);
		String scrambled = no.toString(16);
		while (scrambled.length() < 32) { 
			scrambled = "0" + scrambled; 
        }
		// prepare sql query to get user
		String query = String.format("select password from users where username='%s'", username);
		PreparedStatement getPswd = null;
		try {
			connection.setAutoCommit(false);
			getPswd = connection.prepareStatement(query);
			ResultSet rs = getPswd.executeQuery();
			while (rs.next()) {
				String pswd = rs.getString("password");
				if (scrambled.equals(pswd)) return new User(username);
			}
		} catch (SQLException e) {
			System.out.println(e);
			// roll back operation if connected to db
			if (connection != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                connection.rollback();
	            } catch(SQLException excep) {
	                System.out.println(excep);
	            }
	        }
		}
		return null;
	}
}
