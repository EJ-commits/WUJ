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
	boolean map[][]; // �ش� ��ǥ�� ���� ���� 
	int mapState[][]; // �ش���ǥ �̿��� ��
	int gen; // ����� ǥ��
	boolean nextGen; // �������� ���� 
	
	Panel controlPanel;
	Label genLabel;
	Button startButton, stopButton;
	
	Thread clock;
	
	public void init() { // ���ø� ����
		map = new boolean[maxX][maxY]; // boolean�� 40*20�迭 
		mapState = new int[maxX][maxY]; // int�� 40*20�迭
		gen = 1;
		nextGen = false; // �������� ���� ���� 
		
		// 1���� : (15,10) ~ (24, 10) �� 10ĭ
		for(int x=15; x<25; x++) {
			for(int y=10; y<11; y++) {
				map[x][y] = true;
			}
		}
		
		//����� gui ��Ʈ
		setLayout(new BorderLayout());
		controlPanel = new Panel();
		controlPanel.setBackground(Color.blue);
		genLabel = new Label("North", Label.CENTER);
		genLabel.setBackground(Color.green);
		controlPanel.add(genLabel);
		startButton = new Button("start");
		stopButton = new Button("stop");
		startButton.addActionListener(this); // �̺�Ʈ ������ ��ü�� ��ư �ڱ� �ڽ��̹Ƿ�, this 
		stopButton.addActionListener(this);
		controlPanel.add(startButton);
		controlPanel.add(stopButton);
		add("South",controlPanel);
		
		addMouseListener(this); // ���ø� ��ü�� ������ 
		
	} 
	
	// ������ �޼���
	public void start() {
		if(clock==null) {
			clock = new Thread(this); // Ŭ���� ��ü�� Ÿ������, ������ ����. Ŭ������ ���ʺ��̹Ƿ� Ÿ���� �� �� ����. 
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
		//ĭ �׸���: ������ ������(map �迭 true) ä���
		for(int x=0; x<maxX; x++) {
			for(int y=0; y<maxY; y++) {
				if(map[x][y]) {
					g.fillRect(x*10, y*10, 10, 10); // ** x*10 ��? 
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
		map[mouseX/10][mouseY/10] = !map[mouseX/10][mouseY/10]; //�ݴ밪 
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
				clock.sleep(1000); // 1�ʿ� �ѹ� ������ ����
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			
			if(nextGen) {
				makeNextGen();
				gen++;
				genLabel.setText(gen+"����");
				repaint(); //�׸����ä��� �޼��� 
			}
			
		} 
	}

	public void makeNextGen() {
		for(int x=0; x<maxX; x++) {
			for(int y=0; y<maxY; y++) {
				if(map[x][y]) {
					mapState[x][y] = 100; // �ش���ǥ�� ������ ������ 100
				}else {
					mapState[x][y] = 0; // ������ ������ 0
				}
			} //1. ���� ���� 
		}	
		for(int x=0; x<maxX; x++) {
			for(int y=0; y<maxY; y++) {
				countLife(x,y); // mapstate�� �̿� ���� ���߾� �ش�. 
			}
		}// 2. �̿� �� ���� 
		for(int x=0; x<maxX; x++) {
			for(int y=0; y<maxY; y++) {
				switch(mapState[x][y]) {
				case 3:
				case 102:
				case 103:
					map[x][y] = true; // �ش���ǥ ���� ����
					break;
				default:
					map[x][y] = false; // �׿ܰ�� ���� �Ҹ� 
					break;
				}
			}	
		}//3. �̿� ���� ���� ����, �Ҹ� �����
	}

	public void countLife(int x, int y) {
		//�翷�Ʒ� ĭ Ȯ�� 
		for(int i=-1; i<=1; i++){
			for(int j=-1; j<=1; j++) {
				if(i!=0 || j!=0) { // i,j ��� 0�ƴ� : !(i==0 && j==0) �й��Ģ���� or����ȭ
					if((x+i)>=0 && (x+i<maxX) && (y+j>=0) && (y+j<maxY)) { // ��ǥ��ü �ȿ� �ִ���
						if(map[x+i][y+j]) { // �ش� ��ǥ�� �����¿쿡 ������ �����Ѵٸ�
							mapState[x][y]++; // �̿����� 1 �ø���. 
						}
					}
				}
			}
		}		
	}

}
