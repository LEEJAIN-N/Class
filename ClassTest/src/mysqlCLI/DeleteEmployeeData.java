package mysqlCLI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DeleteEmployeeData {
	
	private Scanner scanner = new Scanner(System.in);
	private Statement statement;
	private ResultSet resultSet;
	int result;
	
	DatabaseMenu menu = new DatabaseMenu();
	
	void deleteEmployeeData(Connection connection) {
		int result;

		System.out.print("[Delete] 사원 번호를 입력하세요 | ");
		int empno = Integer.parseInt(scanner.nextLine());
		empInfodate(connection, empno);
		try {
			statement = connection.createStatement();
			String sql = "delete from emp where empno = " + empno;
			result = statement.executeUpdate(sql);
			if (result == 1) {
				System.out.println("[ o 삭제 완료 ]");
			} else {
				System.out.println("[ ! 삭제 실패 ]");
			}
		} catch (SQLException e) {
			System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
			e.printStackTrace();
		}
		deleteContinuQ(connection);

	}
	
	// 사원 정보
	void empInfodate(Connection connection, int empno) {
			String sql = "select * from emp where empno = '" + empno + "'";
			boolean found = false;
			try {
				System.out.print("사원 정보 | ");
				statement = connection.createStatement();
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
					found = true;
				}
				if (!found) {
					System.out.println("[ ! 존재하지 않는 이름 ] 다시 시도하세요");
					menu.launch(connection);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				deleteContinuQ(connection);
			}

		}
	
	// 추가 확인 메세지
	void deleteContinuQ(Connection connection) {
		System.out.print("계속 삭제하시겠습니까? (YES=1, N0=2) |");
		String continuChoice = scanner.nextLine();
		if (continuChoice.equals("1")) {
			deleteEmployeeData(connection);
		}else {
			System.out.println("[ Delete 종료 ]");
		}
	}

}
