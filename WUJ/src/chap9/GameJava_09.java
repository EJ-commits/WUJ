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

@SuppressWarnings("serial")
public class GameJava_09 extends Applet 
implements Runnable, ActionListener, MouseListener{
	
	//클래스 변수 지정
	static final int maxX = 40;
	static final int maxY = 20;  // 좌표
	boolean map[][];
	int mapState[][]; //2 3 만 map true
	int gen; // 세대 
	boolean nextGen; // 월드 종료여부 
	
	Panel controlPanel;
	Label genLabel;
	Button startButton, stopButton;
	
	Thread clock;
	
	public void init() {
		map = new boolean[maxX][maxY];
		mapState = new int[maxX][maxY]; 
		gen = 1; 
		nextGen = false;
		
		// 처음 시작시의 상태 (15,10)~(24,10) true값 
		for(int x=15; x<25; x++) {
			for(int y=10; y<11; y++) {
				map[x][y] = true;
			}
		}
		
		//GUI
		setLayout(new BorderLayout());
		controlPanel = new Panel();
		controlPanel.setBackground(Color.blue);
		genLabel = new Label(gen + "세대");
		genLabel.setAlignment(Label.CENTER);
		genLabel.setBackground(Color.yellow);
		controlPanel.add(genLabel);
		startButton = new Button("start");
		startButton.addActionListener(this);
		controlPanel.add(startButton);
		stopButton = new Button("stop");
		stopButton.setEnabled(false);
		stopButton.addActionListener(this);
		controlPanel.add(stopButton);
		add("South", controlPanel);
		
		addMouseListener(this);
	}
	
	@Override
	public void start() {
		if(clock==null) {
			clock = new Thread(this);
			clock.start();
		}
	}
	
	@Override
	public void stop() {
		if((clock!=null)&&(clock.isAlive())) {
			clock = null; // clock 삭제 
		}
	}
	
	public void distroy() {
	}
	
	@Override
	public void paint(Graphics g) {
		for(int x=0; x<maxX; x++) {
			for(int y=0; y<maxY; y++) {
				if(map[x][y]) { // 생명 true면 색 채우기 
					g.fillRect(x*10, y*10, 10, 10);
				}else{
					g.drawRect(x*10, y*10, 10, 10);
				}
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		map[mouseX/10][mouseY/10] = !map[mouseX/10][mouseY/10]; //반대로 저장
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

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		while(true) {
			try {
				clock.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(nextGen) {
				makeNextGen();
				gen++;
				genLabel.setText(gen+"세대");
				repaint();
			}
		}
	}

	public void makeNextGen() {
		//mapstate
		for(int x=0; x<maxX; x++) {
			for(int y=0; y<maxY; y++) {
				if(map[x][y]) {
					mapState[x][y] =100;
				}else {
					mapState[x][y] = 0; 
				}
			}
		}
		
		//생명 숫자 세기
		for(int x=0; x<maxX; x++) {
			for(int y=0; y<maxY; y++) {
				countLife(x,y);
			}
		}
		
		//새 세대 발생 
		for(int x=0; x<maxX; x++) {
			for(int y=0; y<maxY; y++) {
				switch (mapState[x][y]) {
				case 3:
				case 102:
				case 103:
					map[x][y] = true;
					break;

				default:
					map[x][y] = false;
					break;
				}
			}
		}
	}

	public void countLife(int x, int y) {
		for(int i=-1; i<=1; i++) {
			for(int j=-1; j<=1; j++) {
				if(i!=0 || j!=0) {
					if( (x+i>=0) && (x+i<maxX) && (y+j>=0) && (y+j<maxY) ) {
						if(map[x+i][y+i]) {
							mapState[x][y]++;
						}
					}
				}
			}
		}
	}
	
	

}
