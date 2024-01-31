package mysqlCLI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DisplayEmployeeData {
	
	private Scanner scanner = new Scanner(System.in);
	private Statement statement;
	private ResultSet resultSet;
	
	void displayEmployeeData(Connection connection) {
		System.out.println("------------------------");
		System.out.println("1. 전체 검색 | 2. 이름 검색 ");
		System.out.println("------------------------");
		System.out.print("번호를 입력하세요 | ");
		String choice = scanner.nextLine();
		System.out.println("------------------------");
		
		switch (choice) {
		case "1":
			try {
				String sql = "select * from emp";

				Statement statement = connection.createStatement();
				resultSet = statement.executeQuery(sql);
				
				while (resultSet.next()) {
					System.out.print(resultSet.getInt("empno") + ", ");
					System.out.print(resultSet.getString("ename") + ", ");
					System.out.print(resultSet.getString("job") + ", ");
					System.out.print(resultSet.getInt("mgr") + ", ");
					System.out.print(resultSet.getString("hiredate") + ", ");
					System.out.print(resultSet.getDouble("sal") + ", ");
					System.out.print(resultSet.getDouble("comm") + ", ");
					System.out.println(resultSet.getInt("deptno"));
				}

			} catch (SQLException e) {
				System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
				e.printStackTrace();
			}
			viewContinuQ(connection);
			break;
			
		case "2":
			try {
				System.out.print("[View] 사원 이름을 검색하세요 | ");
				String ename = scanner.nextLine();
				System.out.println("--------------------------------------------------------");
				
				String sql = "select * from emp where ename = '" + ename + "'";

				statement = connection.createStatement();
				resultSet = statement.executeQuery(sql);

				boolean found = false;

				while (resultSet.next()) {
					System.out.print(resultSet.getInt("empno") + ", ");
					System.out.print(resultSet.getString("ename") + ", ");
					System.out.print(resultSet.getString("job") + ", ");
					System.out.print(resultSet.getInt("mgr") + ", ");
					System.out.print(resultSet.getString("hiredate") + ", ");
					System.out.print(resultSet.getDouble("sal") + ", ");
					System.out.print(resultSet.getDouble("comm") + ", ");
					System.out.println(resultSet.getInt("deptno"));
					found = true;
				}
				System.out.println("--------------------------------------------------------");
				if (!found) {
					System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
				}

			} catch (SQLException e) {
				System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
				displayEmployeeData(connection);
				e.printStackTrace();
			}
			viewContinuQ(connection);
			break;
		default:
			System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
			break;
			
		}
			
	}
	
	public void viewContinuQ(Connection connection) {
		System.out.print("계속 검색하시겠습니까? (YES=1, N0=2) | ");
		String continuChoice = scanner.nextLine();
		if (continuChoice.equals("2")) {
			System.out.println("[ View 종료 ]");
		}else if(continuChoice.equals("1")) {
			displayEmployeeData(connection);	
		}else {
	        System.out.println("[ 오류 ] 1 또는 2를 입력하세요.");
	        viewContinuQ(connection);
			
		}
	}
	

}
