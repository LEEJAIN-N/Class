package mysqlCLI;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertEmployeeData {
	
	private Scanner scanner = new Scanner(System.in);
	private Statement statement;
	
	void insertEmployeeData(Connection connection) {
		try {
			System.out.println("-----------------------");
			System.out.println("[Insert] 사원을 입력하세요");
			System.out.println("-----------------------");
			System.out.print("1. 사원번호 | ");
			int empno = Integer.parseInt(scanner.nextLine());
			System.out.print("2. 사원이름 | ");
			String ename = scanner.nextLine();
			System.out.print("3. 업   무 | ");
			String job = scanner.nextLine();
			System.out.print("4. 관리담당 | ");
			int mgr = Integer.parseInt(scanner.nextLine());
			System.out.print("5. 고용일자 |(EX.2024-01-01) ");
			String hiredate = scanner.nextLine();
			System.out.print("6. 월   급 | ");
			double sal = Double.parseDouble(scanner.nextLine());
			System.out.print("7. 성과급여 | ");
			double comm = Double.parseDouble(scanner.nextLine());
			System.out.print("8. 부서번호 | ");
			int deptno = Integer.parseInt(scanner.nextLine());

			statement = connection.createStatement();
			String sql = "insert into emp(empno, ename, job, mgr, hiredate, sal, comm, deptno) values (" + empno + ", '"+ ename + "', '" + job + "', " + mgr + ", '" + hiredate + "', " + sal + ", " + comm + ", " + deptno+ ")";

			System.out.println("-----------------------");
			System.out.println("입력 내용 | " + empno + ", " + ename + ", " + job + ", " + mgr + "," + hiredate + ", " + sal+ ", " + comm + ", " + deptno);
			System.out.println("-----------------------");
			
			int result = statement.executeUpdate(sql);

			if (result == 1) {
				System.out.println("[ o 입력 완료 ]");
			} else {
				System.out.println("[ ! 입력 실패 ]");
			}
		} catch (SQLException e) {
			System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
			continueInsertQuery(connection);
			e.printStackTrace();
		}
		continueInsertQuery(connection);

	}
	
	public void continueInsertQuery(Connection connection) {
		System.out.print("사원을 추가하시겠습니까? (YES=1, N0=2) | ");
		String continuChoice = scanner.nextLine();
		
		if (continuChoice.equals("1")) {
			insertEmployeeData(connection);
		}else {
			System.out.println("[ Insert 종료 ]");
		}
	}


}
