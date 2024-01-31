package mysqlCLI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseManage {
	
	private String DB_URL = "jdbc:mysql://localhost:3306/firm";
	private String DB_USER = "root";
	private String DB_PASSWORD = "mysql";
	
	Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		DatabaseManage databaseManage = new DatabaseManage();
		DatabaseMenu menu = new DatabaseMenu();
		
		try(Connection connection = DriverManager.getConnection(databaseManage.DB_URL, databaseManage.DB_USER, databaseManage.DB_PASSWORD)) {
			menu.launch(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}

}
