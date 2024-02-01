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
	
	//데이터 보기
	void displayEmployeeData(Connection connection) {
		
		//보기 Menu
		System.out.println("------------------------");
		System.out.println("1. 전체 검색 | 2. 이름 검색 ");
		System.out.println("------------------------");
		System.out.print("번호를 입력하세요 | ");
		String choice = scanner.nextLine();
		System.out.println("------------------------");
		
		switch (choice) {
		
		//전체 검색
		case "1":
			try {
				String sql = "select * from emp";
				Statement statement = connection.createStatement();
				resultSet = statement.executeQuery(sql);
				
				//사원 정보 출력
				while (resultSet.next()) {
					printInfo(connection);
				}
				
			} catch (SQLException e) {
				System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
				e.printStackTrace();

			}
			// 진행 여부 확인
			viewContinuQ(connection);
			break;
			
		//사원 이름 검색
		case "2":
			try {
				System.out.print("[View] 사원 이름을 검색하세요 | ");
				String ename = scanner.nextLine();
				System.out.println("--------------------------------------------------------");
				
				String sql = "select * from emp where ename = '" + ename + "'";
				statement = connection.createStatement();
				resultSet = statement.executeQuery(sql);
				
				//사원 정보 존재 여부
				boolean found = false;
				
				//사원 정보 출력
				while (resultSet.next()) {
					printInfo(connection);
					found = true;
				}
				System.out.println("--------------------------------------------------------");
				
				//존재하지 않는 사원 이름
				if (!found) {
					System.out.println("[ ! 존재하지 않는 사원 이름 ] 다시 시도하세요");
				}
			
			} catch (SQLException e) {
				System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
				
				//보기 Menu
				displayEmployeeData(connection);
				e.printStackTrace();
			}
			
			// 진행 여부 확인
			viewContinuQ(connection);
			break;
		default:
			System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
			break;
			
		}
			
	}
	
	//사원 정보 출력
	void printInfo(Connection connection) {
		try {
			System.out.print(resultSet.getInt("empno") + ", ");
			System.out.print(resultSet.getString("ename") + ", ");
			System.out.print(resultSet.getString("job") + ", ");
			System.out.print(resultSet.getInt("mgr") + ", ");
			System.out.print(resultSet.getString("hiredate") + ", ");
			System.out.print(resultSet.getDouble("sal") + ", ");
			System.out.print(resultSet.getDouble("comm") + ", ");
			System.out.println(resultSet.getInt("deptno"));
		} catch (SQLException e) {
			System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
			e.printStackTrace();
		}
	}
	
	// 진행 여부 확인
	public void viewContinuQ(Connection connection) {
		System.out.print("계속 검색하시겠습니까? (YES=1, N0=2) | ");
		String continuChoice = scanner.nextLine();
		
		//메뉴 돌아가기
		if (continuChoice.equals("2")) {
			System.out.println("[ View 종료 ]");
			
		//보기 Menu
		}else if(continuChoice.equals("1")) {
			displayEmployeeData(connection);
			
		//입력 오류 메세지
		}else {
	        System.out.println("[ 오류 ] 1 또는 2를 입력하세요.");
	        viewContinuQ(connection);
			
		}
	}
	

}