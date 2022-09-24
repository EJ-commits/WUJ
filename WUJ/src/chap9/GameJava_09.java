package chap9;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameJava_09 extends Applet 
implements Runnable, ActionListener, MouseListener{

	static final int maxX = 40;
	static final int maxY = 20;
	boolean map[][]; // 해당 좌표의 생명 여부 
	int mapState[][]; // 해당좌표 이웃의 수
	int gen; // 세대수 표시
	boolean nextGen; // 다음세대 시작 
	
	Panel controlPanel;
	Label genLabel;
	Button startButton, stopButton;
	
	Thread clock;
	
	public void init() { // 애플릿 실행
		map = new boolean[maxX][maxY]; // boolean형 40*20배열 
		mapState = new int[maxX][maxY]; // int형 40*20배열
		gen = 1;
		nextGen = false; // 다음세대 시작 안함 
		
		// 1세대 : (15,10) ~ (24, 10) 의 10칸
		for(int x=15; x<25; x++) {
			for(int y=10; y<11; y++) {
				map[x][y] = true;
			}
		}
		
		//실행시 gui 세트
		setLayout(new BorderLayout());
		controlPanel = new Panel();
		controlPanel.setBackground(Color.blue);
		genLabel = new Label("North", Label.CENTER);
		genLabel.setBackground(Color.green);
		controlPanel.add(genLabel);
		startButton = new Button("start");
		stopButton = new Button("stop");
		startButton.addActionListener(this); // 이벤트 리스너 객체는 버튼 자기 자신이므로, this 
		stopButton.addActionListener(this);
		controlPanel.add(startButton);
		controlPanel.add(stopButton);
		add("South",controlPanel);
		
		addMouseListener(this); // 애플릿 전체에 리스너 
		
	} 
	
	// 스레드 메서드
	public void start() {
		if(clock==null) {
			clock = new Thread(this); // 클래스 전체를 타겟으로, 스레드 생성. 클래스가 러너블이므로 타겟이 될 수 있음. 
			clock.start();
		}
	}
	
	@Override
	public void stop() {
		if((clock!=null)&&(clock.isAlive())) {
			clock = null;
		}
	}
	
	@Override
	public void paint(Graphics g) {
		//칸 그리기: 생명이 있으면(map 배열 true) 채운다
		for(int x=0; x<maxX; x++) {
			for(int y=0; y<maxY; y++) {
				if(map[x][y]) {
					g.fillRect(x*10, y*10, 10, 10); // ** x*10 왜? 
				}else {
					g.drawRect(x*10, y*10, 10, 10);
				}
				
			}
		}
	}
	
	public void distroy() {
		// TODO Auto-generated method stub

	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		map[mouseX/10][mouseY/10] = !map[mouseX/10][mouseY/10]; //반대값 
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == startButton) {
			nextGen = true;
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
		}else if(e.getSource() == stopButton) {
			nextGen = false;
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				clock.sleep(1000); // 1초에 한번 스레드 중지
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			
			if(nextGen) {
				makeNextGen();
				gen++;
				genLabel.setText(gen+"세대");
				repaint(); //네모색깔채우기 메서드 
			}
			
		} 
	}

	public void makeNextGen() {
		for(int x=0; x<maxX; x++) {
			for(int y=0; y<maxY; y++) {
				if(map[x][y]) {
					mapState[x][y] = 100; // 해당좌표에 생명이 있으면 100
				}else {
					mapState[x][y] = 0; // 생명이 없으면 0
				}
			} //1. 생명 세기 
		}	
		for(int x=0; x<maxX; x++) {
			for(int y=0; y<maxY; y++) {
				countLife(x,y); // mapstate를 이웃 수에 맞추어 준다. 
			}
		}// 2. 이웃 수 세기 
		for(int x=0; x<maxX; x++) {
			for(int y=0; y<maxY; y++) {
				switch(mapState[x][y]) {
				case 3:
				case 102:
				case 103:
					map[x][y] = true; // 해당좌표 생명 있음
					break;
				default:
					map[x][y] = false; // 그외경우 생명 소멸 
					break;
				}
			}	
		}//3. 이웃 수에 따라 생성, 소멸 변경됨
	}

	public void countLife(int x, int y) {
		//양옆아래 칸 확인 
		for(int i=-1; i<=1; i++){
			for(int j=-1; j<=1; j++) {
				if(i!=0 || j!=0) { // i,j 모두 0아님 : !(i==0 && j==0) 분배법칙으로 or연산화
					if((x+i)>=0 && (x+i<maxX) && (y+j>=0) && (y+j<maxY)) { // 좌표전체 안에 있는지
						if(map[x+i][y+i]) { // 해당 좌표의 상하좌우에 생명이 존재한다면
							mapState[x][y]++; // 이웃수를 1 늘린다. 
						}
					}
				}
			}
		}		
	}

}
