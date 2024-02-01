package mysqlGUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SwingGui extends JFrame {
	JTextField[] field = new JTextField[8];
	JTextArea textArea = new JTextArea(20, 80);

	// 버튼
	JButton[] button = { new JButton("전체검색"), new JButton("입력"), new JButton("이름검색"), new JButton("수정"),
			new JButton("삭제") };

	Connection connection;
	Statement statement;
	int resultSet;

	// 설정
	public SwingGui() {
		String url = "jdbc:mysql://localhost:3306/firm";
		String id = "root";
		String pass = "mysql";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, id, pass);
			statement = connection.createStatement();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 라벨
		JLabel[] label = { new JLabel("사원번호 "), new JLabel("사원이름 "), new JLabel("업   무 "), new JLabel("관리담당 "),
				new JLabel("고용일자 "), new JLabel("월   급 "), new JLabel("성과급여 "), new JLabel("부서번호 ") };

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		JPanel panelButton = new JPanel(new FlowLayout());
		for (JButton buttons : button) {
			panelButton.add(buttons);
		}

		contentPane.add(panelButton, BorderLayout.SOUTH);
		JScrollPane scroll = new JScrollPane(textArea);

		JPanel panelArea = new JPanel(new FlowLayout());
		panelArea.add(scroll);
		contentPane.add(panelArea, BorderLayout.CENTER);

		JPanel panelInput = new JPanel(new FlowLayout());
		contentPane.add(panelInput, BorderLayout.NORTH);

		for (int i = 0; i < label.length; i++) {
			panelInput.add(label[i]);
			field[i] = new JTextField(5);
			panelInput.add(field[i]);
		}

		this.setTitle("Employee Database");
		this.setSize(1020, 420);
		this.setLocation(500, 300);
		this.setResizable(false); // false
		this.setVisible(true);

		// 버튼 이벤트
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select();
			}
		});
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert();
				clearTextField();
			}
		});
		button[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectName();
			}
		});
		button[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
				clearTextField();
			}
		});
		button[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
				clearTextField();
			}
		});

		printText();
	}

	// 설명서
	public void printText() {
		textArea.append("[ MESSAGE ] 사용 시 주의사항\n▶ 사원번호, 고용일자는 변경 불가합니다\n▶ 고용일자 입력 시 : -를 입력해주세요 (예시,2024-01-01)"
				+ "\n▶ 사원 수정 시, 사원 번호를 입력 후 버튼을 누르세요\n");
	}

	// 전체검색
	public void select() {
		String sql = "select empno, ename, job, mgr, hiredate, sal, comm, deptno from emp";
		getInfo(sql);
	}

	// 이름검색
	public void selectName() {
		String sql = String.format("select * from emp where ename = '%s'", field[1].getText());
		getInfo(sql);
		try {
			ResultSet resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				field[0].setText(resultSet.getString("empno"));
				field[1].setText(resultSet.getString("ename"));
				field[2].setText(resultSet.getString("job"));
				field[3].setText(resultSet.getString("mgr"));
				field[4].setText(resultSet.getString("hiredate"));
				field[5].setText(resultSet.getString("sal"));
				field[6].setText(resultSet.getString("comm"));
				field[7].setText(resultSet.getString("deptno"));
			} else {
				textArea.setText("[ MESSAGE ] 해당 사원이 존재하지 않습니다. 새로운 정보를 입력하세요.\n");
				clearTextField();
			}
		} catch (SQLException e) {
			textArea.setText("[ ! ] 유효하지 않은 메세지, 다시 시도하세요\n");
			e.printStackTrace();
		}
	}

	// 입력
	public void insert() {
		String sql = String.format("insert into emp values(%s, '%s', '%s', %s, '%s', %s, %s, %s)", field[0].getText(),
				field[1].getText(), field[2].getText(), field[3].getText(), field[4].getText(), field[5].getText(),
				field[6].getText(), field[7].getText());
		getInfo(sql);
		textArea.setText("");
		try {
			int result = statement.executeUpdate(sql);
			textArea.append("[ MESSAGE ] 사원 정보가 입력되었습니다\n");
		} catch (SQLException e) {
			textArea.append("[ ! ] 유효하지 않은 메세지, 다시 시도하세요\n");
			e.printStackTrace();
		}
	}

	// 수정
	public void update() {
		selectName();

		String updateSql = String.format(
				"update emp set ename = '%s', job = '%s', mgr = %s, hiredate = '%s', sal = %s, comm = %s, deptno = %s where empno = %s",
				field[1].getText(), field[2].getText(), field[3].getText(), field[4].getText(), field[5].getText(),
				field[6].getText(), field[7].getText(), field[0].getText());

		try {
			int result = statement.executeUpdate(updateSql);
			textArea.append("[ MESSAGE ] 사원 정보가 수정되었습니다\n");
		} catch (SQLException e) {
			textArea.append("[ ! ] 유효하지 않은 메세지, 다시 시도하세요\n");
			e.printStackTrace();
		}

		clearTextField();
	}

	// 삭제
	public void delete() {
		String sql = "delete from emp where empno = " + field[0].getText();
		try {
			resultSet = statement.executeUpdate(sql);
			textArea.setText("");
			textArea.append("[ MESSAGE ] 사원 정보가 삭제되었습니다\n");
		} catch (SQLException e) {
			textArea.append("[ ! ] 유효하지 않은 메세지, 다시 시도하세요\n");
			e.printStackTrace();
		}

	}

	// 사원정보
	public void getInfo(String sql) {
		try {
			ResultSet resultSet = statement.executeQuery(sql);
			textArea.setText("");
			textArea.append("▶\n");
			while (resultSet.next()) {
				int empno = resultSet.getInt("empno");
				String ename = resultSet.getString("ename");
				String job = resultSet.getString("job");
				int mgr = resultSet.getInt("mgr");
				String hiredate = resultSet.getString("hiredate");
				double sal = resultSet.getDouble("sal");
				double comm = resultSet.getDouble("comm");
				int deptno = resultSet.getInt("deptno");
				String str = String.format("%d, %s, %s, %d, %s, %f, %f, %d\n", empno, ename, job, mgr, hiredate, sal,
						comm, deptno);
				textArea.append(str);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 텍스트 삭제
	private void clearTextField() {
		field[0].setText("");
		field[1].setText("");
		field[2].setText("");
		field[3].setText("");
		field[4].setText("");
		field[5].setText("");
		field[6].setText("");
		field[7].setText("");
	}

	public static void main(String[] args) {
		new SwingGui();
	}

}
