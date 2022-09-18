package chap8;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameJava2_08 extends Applet implements ActionListener {
	
	int count; 
	int key;
	int strike, ball;
	
	int[] user = new int[3]; 
	int[] com = new int[3]; // 3개씩 입력 
	
	boolean gameover = false; 
	
	Label display;
	TextArea history; 
	
	Panel numPanel;
	Button[] buttons = new Button[12];
	
	public void init() {
		Random r = new Random();
		com[0] = Math.abs(r.nextInt()%9) + 1; // 1~9
		
		do{
			com[1] = Math.abs(r.nextInt() % 9 ) + 1;
		}while(com[1] == com[0]);

		do{
			com[2] = Math.abs(r.nextInt() % 9 ) + 1;
		}while(com[2] == com[1] || com[2] == com[1] );	
		
		count = 0 ;
		key = 0;
		user[0] = user[1] = user[2] = 0;
		
		
		//GUI
		setLayout(new BorderLayout());
		
		display = new Label();
		display.setAlignment(Label.RIGHT);
		add("North",display);
		
		numPanel = new Panel();
		numPanel.setLayout(new GridLayout(4, 3));
		
		for(int i=7; i>0; i-=3) {
			for(int j=0; j<3; j++) {
				buttons[i+j] = new Button(String.valueOf(i+j));
				numPanel.add(buttons[i+j]);
			}
		}
		
		buttons[0] = new Button("<-");		
		numPanel.add(buttons[0]);
		
		buttons[10] = new Button("다시 입력");		
		numPanel.add(buttons[10]);
		
		buttons[11] = new Button("입력완료");		
		numPanel.add(buttons[11]);
		
		add("Center", numPanel);
		
		
		history = new TextArea(10,20);
		add("South", history);
		for(int i=0; i<12; i++) {
			buttons[i].addActionListener(this);
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(gameover) return;
		if(e.getSource() == buttons[0]) { //다시입력
			if(key>0) {
				key--;
				for(int i=0; i<key; i++) {
				display.setText(display.getText() + user[i]);
				}
			}
		}else if(e.getSource() == buttons[11]) {
			if(key==3) {
				strike = ball = 0;
				
				if(user[0] == com[0]) strike++;
				if(user[1] == com[1]) strike++;
				if(user[2] == com[2]) strike++;

				if(user[0] == com[1]) ball++;
				if(user[0] == com[2]) ball++;
				if(user[1] == com[0]) ball++;
				if(user[1] == com[2]) ball++;
				if(user[2] == com[0]) ball++;
				if(user[2] == com[1]) ball++;
				
				history.append(user[0]+"."+user[1]+","+user[2]+
						" Strike "+ strike + " Ball " + ball + "\n");
				
				//다시 초기화
				count++;
				key = 0; 
				user[0] = user[1] =user[2] = 0;
				display.setText("");
				
				if((strike==3)||(count==3)) {
					if(count<=2) history.append("Perfect");
					else if (count<=5) history.append("Good");
					else if(count<=9) history.append("Not Bad");
					else history.append("Boo");
					
					gameover = true; 
					display.setText("Game over");
					history.append("\n\n END");
				}
			}
		}
		
	}
	
	
}
