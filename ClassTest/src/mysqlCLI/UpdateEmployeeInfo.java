package mysqlCLI;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UpdateEmployeeInfo {
	private Scanner scanner = new Scanner(System.in);
	private Statement statement;
	int result;
	
	//데이터 수정
	void updateEmployeeInfo(Connection connection) {
		
		//사원 번호 입력
		System.out.print("[Update] 사원 번호를 입력하세요 | ");
		int empno = Integer.parseInt(scanner.nextLine());
		
		//수정 Menu
		System.out.println("----------------------------------------------------------------");
		System.out.println("1. 사원이름 | 2. 업무 | 3. 관리담당 | 4. 월급 | 5. 성과급여 | 6. 부서번호");
		System.out.println("----------------------------------------------------------------");
		System.out.print("수정 내용을 선택하세요 | ");
		String choice = scanner.nextLine();

		switch (choice) {

		// 사원이름 변경
		case "1":
			System.out.print("사원이름 | ");
			String ename = scanner.nextLine();
			try {
				statement = connection.createStatement();
				String sql = "update emp set ename = '" + ename + "'where empno = " + empno;
				result = statement.executeUpdate(sql);
				
				// 갱신 확인 메세지
				updatPrint();
				
			} catch (SQLException e) {
				
				// 오류 메세지
				exceptionPrint(connection);
				e.printStackTrace();
			}
			
			// 추가 확인 질문
			continueUpdateQuery(connection);
			break;

		// 업무 변경
		case "2":
			System.out.print("업   무 | ");
			String job = scanner.nextLine();
			try {
				statement = connection.createStatement();
				String sql = "update emp set job = '" + job + "'where empno = " + empno;
				result = statement.executeUpdate(sql);
				updatPrint();
			} catch (SQLException e) {
				exceptionPrint(connection);
				e.printStackTrace();
			}
			continueUpdateQuery(connection);
			break;

		// 관리담당 변경
		case "3":
			System.out.print("관리담당 | ");
			int mgr = Integer.parseInt(scanner.nextLine());
			try {
				statement = connection.createStatement();
				String sql = "update emp set mgr = " + mgr + " where empno = " + empno;
				result = statement.executeUpdate(sql);
				updatPrint();
			} catch (SQLException e) {
				exceptionPrint(connection);
				e.printStackTrace();
			}
			continueUpdateQuery(connection);
			break;

		// 월급 변경
		case "4":
			System.out.print("월   급 | ");
			double sal = Double.parseDouble(scanner.nextLine());
			try {
				statement = connection.createStatement();
				String sql = "update emp set sal = " + sal + "where empno = " + empno;
				result = statement.executeUpdate(sql);
				updatPrint();
			} catch (SQLException e) {
				exceptionPrint(connection);
				e.printStackTrace();
			}
			continueUpdateQuery(connection);
			break;

		// 성과급여 변경
		case "5":
			System.out.print("성과급여 | ");
			double comm = Double.parseDouble(scanner.nextLine());
			try {
				statement = connection.createStatement();
				String sql = "update emp set comm = " + comm + "where empno = " + empno;
				result = statement.executeUpdate(sql);
				updatPrint();
			} catch (SQLException e) {
				exceptionPrint(connection);
				e.printStackTrace();
			}
			continueUpdateQuery(connection);
			break;

		// 부서번호 변경
		case "6":
			System.out.print("부서번호 | ");
			int deptno = Integer.parseInt(scanner.nextLine());
			try {
				statement = connection.createStatement();
				String sql = "update emp set deptno = " + deptno + " where empno = " + empno;
				result = statement.executeUpdate(sql);
				updatPrint();
			} catch (SQLException e) {
				exceptionPrint(connection);
				e.printStackTrace();
			}
			continueUpdateQuery(connection);
			break;

		}

	}

	// 갱신 확인 메세지
	void updatPrint() {
		if (result == 1) {
			System.out.println("[ o 갱신 완료 ]");
		} else {
			System.out.println("[ ! 갱신 실패 ]");
		}
	}

	// 오류 메세지
	void exceptionPrint(Connection connection) {
		System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
		continueUpdateQuery(connection);
	}

	// 추가 확인 질문
	void continueUpdateQuery(Connection connection) {
		System.out.print("계속 갱신하시겠습니까? (YES=1, N0=2) |");
		String continuChoice = scanner.nextLine();
		if (continuChoice.equals("1")) {
			updateEmployeeInfo(connection);
		} else {
			System.out.println("[ Update 종료 ]");
		}
	}

}
