package simple;

import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleMenu {
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/firm";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "mysql";

	static Statement statement = null;
	static ResultSet resultSet = null;

	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		SimpleMenu mysqlMenu = new SimpleMenu();

		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			boolean exit = false;

			while (!exit) {
				System.out.println("------------------------------------------------------------------");
				System.out.println("1. 데이터 보기 | 2. 데이터 삽입 | 3. 데이터 수정 | 4. 데이터 삭제 | 5. 종료");
				System.out.println("------------------------------------------------------------------");
				System.out.print("번호를 입력하세요 | ");

				String choice = scanner.nextLine();

				switch (choice) {
				case "1":
					mysqlMenu.viewData(connection);
					break;
				case "2":
					mysqlMenu.insertData(connection);
					break;
				case "3":
					mysqlMenu.update(connection);
					break;
				case "4":
					mysqlMenu.deleteData(connection);
					break;
				case "5":
					exit = true;
					break;
				default:
					System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
					break;
				}
			}
		} catch (SQLException e) {
			System.out.println("[ ! 연결 오류 ]");
			e.printStackTrace();
		}
	}

	private void viewData(Connection connection) {
		System.out.println("------------------------");
		System.out.println("1. 전체 검색 | 2. 이름 검색");
		System.out.println("------------------------");
		System.out.print("번호를 입력하세요 | ");

		String choice = scanner.nextLine();

		switch (choice) {
		case "1":
			try {
				String sql = "select * from emp";

				Statement statement = connection.createStatement();
				 resultSet = statement.executeQuery(sql);

				while (resultSet.next()) {
					System.out.print(resultSet.getInt("empno") + ",");
					System.out.print(resultSet.getString("ename") + ",");
					System.out.print(resultSet.getString("job") + ",");
					System.out.print(resultSet.getInt("mgr") + ",");
					System.out.print(resultSet.getString("hiredate") + ",");
					System.out.print(resultSet.getDouble("sal") + ",");
					System.out.print(resultSet.getDouble("comm") + ",");
					System.out.println(resultSet.getInt("deptno"));
				}

			} catch (SQLException e) {
				System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
				e.printStackTrace();
			}

			break;

		case "2":
			try {
				System.out.print("[View] 사원 이름을 검색하세요 | ");
				String ename = scanner.nextLine();
				String sql = "select * from emp where ename = '" + ename + "'";

				statement = connection.createStatement();
				resultSet = statement.executeQuery(sql);

				boolean found = false;

				while (resultSet.next()) {
					System.out.print(resultSet.getInt("empno") + ",");
					System.out.print(resultSet.getString("ename") + ",");
					System.out.print(resultSet.getString("job") + ",");
					System.out.print(resultSet.getInt("mgr") + ",");
					System.out.print(resultSet.getString("hiredate") + ",");
					System.out.print(resultSet.getDouble("sal") + ",");
					System.out.print(resultSet.getDouble("comm") + ",");
					System.out.println(resultSet.getInt("deptno"));
					found = true;
				}
				if (!found) {
					System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
				}

			} catch (SQLException e) {
				System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
				viewData(connection);
				e.printStackTrace();
			}

			break;
		}

	}

	private void insertData(Connection connection) {
		try {
			System.out.println("[Insert] 사원을 입력하세요");
			System.out.println("---------------------");
			System.out.print("1. 사원번호 | ");
			int empno = Integer.parseInt(scanner.nextLine());
			System.out.print("2. 사원이름 | ");
			String ename = scanner.nextLine();
			System.out.print("3. 업   무 | ");
			String job = scanner.nextLine();
			System.out.print("4. 관리담당 | ");
			int mgr = Integer.parseInt(scanner.nextLine());
			System.out.print("5. 고용일자 | ");
			String hiredate = scanner.nextLine();
			System.out.print("6. 월   급 | ");
			double sal = Double.parseDouble(scanner.nextLine());
			System.out.print("7. 성과급여 | ");
			double comm = Double.parseDouble(scanner.nextLine());
			System.out.print("8. 부서번호 | ");
			int deptno = Integer.parseInt(scanner.nextLine());

			statement = connection.createStatement();
			String sql = "insert into emp(empno, ename, job, mgr, hiredate, sal, comm, deptno) values (" + empno + ", '"+ ename + "', '" + job + "', " + mgr + ", '" + hiredate + "', " + sal + ", " + comm + ", " + deptno+ ")";
			
			System.out.println("-----------");
			System.out.println("입력 내용 | "+empno+", "+ename+", "+job+", "+mgr+","+hiredate+", "+sal+", "+comm+", "+deptno);

			int result = statement.executeUpdate(sql);

			if (result == 1) {
				System.out.println("[ o 입력 완료 ]");
			} else {
				System.out.println("[ ! 입력 실패 ]");
			}
		} catch (SQLException e) {
			System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
			e.printStackTrace();
		}

	}

	private void update(Connection connection) throws SQLException {
		System.out.print("[Update] 사원 번호를 입력하세요 | ");
		int empno = Integer.parseInt(scanner.nextLine());
		System.out.println("------------------------");
		System.out.println("사원 번호 | " + empno); 
		System.out.println("------------------------------------------------------------");
		System.out.println("1. 사원이름 | 2. 업무 | 3. 매니저 | 4. 월급 | 5. 보너스 | 6. 부서 번호");
		System.out.println("------------------------------------------------------------");
		System.out.println("수정 내용을 선택하세요 | ");
		String choice = scanner.nextLine();

		switch (choice) {
		case "1":
			System.out.print("사원이름 | ");
			String ename = scanner.nextLine();
			statement = connection.createStatement();

			String sql = "update emp set ename = '" + ename + "'where empno = " + empno;
			int result = statement.executeUpdate(sql);
			if (result == 1) {
				System.out.println("[ o 갱신 완료 ]");
			} else {
				System.out.println("[ ! 갱신 실패 ]");
			}
			break;
		case "2":
			System.out.print("업   무 | ");
			String job = scanner.nextLine();
			statement = connection.createStatement();

			sql = "update emp set job = '" + job + "'where empno = " + empno;
			result = statement.executeUpdate(sql);
			if (result == 1) {
				System.out.println("[ o 갱신 완료 ]");
			} else {
				System.out.println("[ ! 갱신 실패 ]");
			}
			break;
		case "3":
			System.out.print("관리담당 | ");
			int mgr = Integer.parseInt(scanner.nextLine());
			statement = connection.createStatement();

			sql = "update emp set mgr = " + mgr + "where empno = " + empno;
			result = statement.executeUpdate(sql);
			if (result == 1) {
				System.out.println("[ o 갱신 완료 ]");
			} else {
				System.out.println("[ ! 갱신 실패 ]");
			}
			break;
		case "4":
			System.out.print("월   급 | ");
			double sal = Double.parseDouble(scanner.nextLine());
			statement = connection.createStatement();

			sql = "update emp set sal = " + sal + "where empno = " + empno;
			result = statement.executeUpdate(sql);
			if (result == 1) {
				System.out.println("[ o 갱신 완료 ]");
			} else {
				System.out.println("[ ! 갱신 실패 ]");
			}
			break;
		case "5":
			System.out.print("성과급여 | ");
			double comm = Double.parseDouble(scanner.nextLine());
			statement = connection.createStatement();

			sql = "update emp set comm = " + comm + "where empno = " + empno;
			result = statement.executeUpdate(sql);
			if (result == 1) {
				System.out.println("[ o 갱신 완료 ]");
			} else {
				System.out.println("[ ! 갱신 실패 ]");
			}
			break;
		case "6":
			System.out.print("부서번호 | ");
			int deptno = Integer.parseInt(scanner.nextLine());
			statement = connection.createStatement();

			sql = "update emp set deptno = " + deptno + "where empno = " + empno;
			result = statement.executeUpdate(sql);
			if (result == 1) {
				System.out.println("[ o 갱신 완료 ]");
			} else {
				System.out.println("[ ! 갱신 실패 ]");
			}
			break;
			
		}
		

	}

	private void deleteData(Connection connection) {
		int result;
		
		System.out.print("[Delete] 사원 번호를 입력하세요 | ");
		int empno = Integer.parseInt(scanner.nextLine());
		System.out.println("-----------------------------");
		System.out.println("사원 번호 | "+empno+" 검색");
		System.out.println("-----------------------------");
		System.out.print("[사원]"+empno);
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

	}
}