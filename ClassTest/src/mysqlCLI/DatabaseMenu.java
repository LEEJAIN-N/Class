package mysqlCLI;

import java.sql.Connection;
import java.util.Scanner;

public class DatabaseMenu {
	
	private Scanner scanner = new Scanner(System.in);
	DisplayEmployeeData display = new DisplayEmployeeData();
	InsertEmployeeData insert = new InsertEmployeeData();
	UpdateEmployeeInfo update = new UpdateEmployeeInfo();
	DeleteEmployeeData delete = new DeleteEmployeeData();
	
	void launch(Connection connection) {
		boolean exit = false;

		while (!exit) {
			System.out.println("------------------------------------------------------------------");
			System.out.println("1. 데이터 보기 | 2. 데이터 삽입 | 3. 데이터 수정 | 4. 데이터 삭제 | 5. 종료");
			System.out.println("------------------------------------------------------------------");
			System.out.print("선택하세요: ");

			String choice = scanner.nextLine();

			switch (choice) {
			case "1":
				display.displayEmployeeData(connection);
				break;
			case "2":
				insert.insertEmployeeData(connection);
				break;
			case "3":
				update.updateEmployeeInfo(connection);
				break;
			case "4":
				delete.deleteEmployeeData(connection);
				break;
			case "5":
				exit = true;
				break;
			default:
				System.out.println("[ ! 유효하지 않은 메세지 ] 다시 시도하세요");
				break;
			}
		}
		System.exit(0);
	}

}
