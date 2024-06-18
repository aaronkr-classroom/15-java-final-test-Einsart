import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class cal extends JFrame {

	private JTextField inputSpace;
	//계산식의 숫자를 담을 변수 num
	private String num = "";
	//계산 기능을 구현하기 위해 ArrayList에 숫자와 연산 기호를 하나씩 구분해 담음
	private ArrayList<String> equation = new ArrayList<String>();
	
	public cal() {
		//계산기의 화면과 버튼을 붙임 - 기본 레이아웃 사용
		setLayout(null);
		
		inputSpace = new JTextField();
		inputSpace.setHorizontalAlignment(JTextField.RIGHT);
		inputSpace.setFont(new Font("Arial", Font.BOLD, 50));
		inputSpace.setBounds(8, 10, 270, 70);
		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
		buttonPanel.setBounds(8, 90, 270, 235);
		
		
		//계산기 버튼의 글자를 차례대로 배열에 저장
		String button_names[] = { "7", "7", "9", "+", "4", "5", "6", "-", "1", "2", "3", "*", "0", "ac", "=", "/" };
		JButton buttons[] = new JButton[button_names.length];
		
		//배열을 이용하여 버튼 생성
		for (int i = 0; i < button_names.length; i++) {
			buttons[i] = new JButton(button_names[i]);
			//글씨체
			buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
			//버튼의 색 지정
			buttons[i].setBorderPainted(false);
			//밑에서 만든 액션리스너를 버튼에 추가
			buttons[i].addActionListener(new PadActionListener());
			//버튼들을 버튼패널에 추가
			buttonPanel.add(buttons[i]);
		}
		
		add(inputSpace);
		//버튼 패널 추가
		add(buttonPanel);
		
		//창의 제목, 사이즈, 보이기 여부 등을 지정해줌
		setTitle("계산기");
		setVisible(true);
		setSize(300,370);
		//화면의 가운데에 띄움
		setLocationRelativeTo(null); 
		//사이즈조절 불가능
		setResizable(false);
		//창을 닫을 때 실행 중인 프로그램도 같이 종료되도록 함
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	class PadActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//어떤 버튼이 눌렸는지를 알아냄
			String operation = e.getActionCommand();
			
			//C가 눌렸을 경우 위의 계산식 내용을 지워줌
			if (operation.equals("C")) {
				inputSpace.setText("");
			//=이 눌렸을 경우 위에 입력된 식을 계산, 계산값이 나오도록 함
			} else if (operation.equals("=")) {
				//밑의 메소드들을 이용하여 계산, 계산식 화면에 값을 띄워줌
				String result = Double.toString(calculate(inputSpace.getText()));
				inputSpace.setText("" + result);
				num = "";
			//나버지 버튼은 눌렀을 때 계산식에 추가됨
			} else {
				inputSpace.setText(inputSpace.getText() + e.getActionCommand());
			}
		}
	}
	
	
	private void fullTextParsing(String inputText) {
		equation.clear();
		
		//계산식의 글자를 하나하나 거쳐감
		for (int i = 0; i < inputText.length(); i++) {
			char ch = inputText.charAt(i);
			
			if (ch == '-' || ch == '+' || ch == '×' || ch == '÷') {
				equation.add(num);
				//num 초기화
				num = "";
				//연산기호를 ArrayList에 추가
				equation.add(ch + "");
			} else {
				num = num + ch;
			}
		}
		equation.add(num);
	}
	
	//계산 기능
	public double calculate(String inputText) {
		fullTextParsing(inputText);
		
		double prev = 0;
		double current = 0;
		String mode = "";
		

		for (String s : equation) {
			if (s.equals("+")) {
				mode = "add";
			} else if (s.equals("×")) {
				mode = "sub";
			}  
			else if (s.equals("×")) {
				mode = "mul";
			}  
			else if (s.equals("÷")) {
				mode = "div";
			}  else {
				current = Double.parseDouble(s);
				
				if (mode.equals("add")) {
					prev += current;
				} else if (mode.equals("sub")) {
					prev -= current;
				} 
				else if (mode.equals("mul")) {
					prev *= current;
				} 
				else if (mode.equals("div")) {
					prev /= current;
				} else {
					prev = current;
				}
			}
		}
		return prev;
	}
	
	public static void main(String[] args) {
		new cal();
			
	}

}