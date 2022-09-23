package chap10;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameJava2_10 extends Applet implements Runnable, ActionListener{

	
	Thread clock; 
	
	Image off; // 가상 팔레트
	Graphics offG; // 도구
	
	Image slot;
	Image machine;
	
	int[] loc;  //슬롯별 값
	int[] speed; // 슬롯 돌아가는 속도
	int[] hit; // 정지될 번호판, loc과 별도 
	int slotNum;
	boolean[] stopSlot; 
	boolean[] moveSlot;
	
	Button startButton, stopButton;
	Panel buttonPanel;
	
	Random ran;
	
	public void init() {
		
		//메모리에 가상화면 탑재 
		off = createImage(314, 310);
		offG = off.getGraphics();
		offG.setColor(Color.white);
		offG.fillRect(0, 0, 314, 310);
		
		//이미지로드 
		 MediaTracker tracker = new MediaTracker(this);
		 slot = Toolkit.getDefaultToolkit().getImage("D:\\LakeSample\\slot.jpg");
		 machine = Toolkit.getDefaultToolkit().getImage("D:\\LakeSample\\machine.jpg");
		
		 //트래커 등록 
		 tracker.addImage(machine, 0);
		 tracker.addImage(slot, 0);
		 try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//논리연산이 1이 될때까지 로드 (두 조건모두 1 = true)
		while ((tracker.statusAll(true) & MediaTracker.COMPLETE) == 0) {}
		
		//gui
		setLayout(new BorderLayout()); // 이 클래스의 레이아웃
		buttonPanel = new Panel();
		startButton = new Button("start");
		stopButton = new Button("stop");
		startButton.addActionListener(this); // 버튼 자기 자신에 리스너 붙임
		stopButton.addActionListener(this);
		buttonPanel.add(startButton);
		buttonPanel.add("South",stopButton);
		
		loc = new int[3];
		speed = new int[3];
		hit = new int[3];
		moveSlot = new boolean[3];
		stopSlot = new boolean[3];
		
		ran = new Random();
		
		for(int i=0; i<3; i++) {
			loc[i] = Math.abs(ran.nextInt() % 7) * 34; // 하단 픽셀값 
			speed[i] = Math.abs(ran.nextInt() % 7) * 8 + 8 ; // 한번에 8픽셀씩 이동 
			stopSlot[i] = true;
			moveSlot[i] = false; //초기화면은 랜덤 숫자의 멈춤화면 
		}
		
		slotNum = 0;
		
	}
	
	public void start() {
		if(clock==null) {
			clock = new Thread(this);
			clock.start();
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(off, 0, 0, this);
	};
	
	public void update(Graphics g) {
		paint(g); // update 오버라이딩으로 화면지우기 기능 없앰 
	};

	/*!!!!!!
	 * <imageobserver 인터페이스> 
	 * 시스템 내부에는 image를 로딩하는 스레드가 있다. 이 스레드는 새 이미지 데이터가 로딩될 때마다 
	 * imageobserver 인터페이스를 통해 로딩 시점을 알려준다. 
	 * 따라서 이 인터페이스를 구현하면 로딩 시점을 알 수 있다. 
	 * -> 그런데, Component가 이것을 구현 - 그 자식들도 모두 구현
	 * 즉, 이미 이미지 객체들은 이걸 구현한 셈이다.
	 * 따라서 this 를 써서 버튼,패널, ... 모두 imageobserver 자리에 넣을수있다. 
	 * */
	
	
	@Override
	public void run() {
		while (true) {
			try {
				clock.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			offG.setColor(Color.white);
			offG.fillRect(0, 0, 314, 310);
			drawSlot();
			offG.drawImage(machine, 0, 0, this); // 슬롯 위에 머신 겹치기 
			repaint(); // 화면에 그리는 메서드인 paint() 소환 
			
		}
	}
	
	public void drawSlot() {
		for(int i=0; i<3; i++) {
			if(moveSlot[i]) {
				if(loc[i]<1649) {
					loc[i] += speed[i];
				}else {
					loc[i] = 0;
				}
			}
			
			if(stopSlot[i]) {
				if(loc[i]/34 == hit[i]) {
					loc[i] = loc[i]/34*34;
					moveSlot[i] = false;
				}
			}
			
			if(loc[i]<320) {
				offG.drawImage(slot, i*34, 0-loc[i], this);
			}else {
				offG.drawImage(slot, i*34, 0-loc[i], this);
				offG.drawImage(slot, i*34, 448-loc[i], this);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == startButton) {
			stopSlot[0] = stopSlot[1] = stopSlot[3] = false;
			moveSlot[0] = moveSlot[1] = moveSlot[3] = true;
			slotNum = 0;
		}
		else if(e.getSource() == stopButton) {
			hit[slotNum] = Math.abs(ran.nextInt()%7);
			stopSlot[slotNum] = true;
			
			if(slotNum<2) 
				slotNum ++;
			else 
				slotNum = 0;
		}
	}
	
	@Override
	public void stop() {
		if(clock!=null && clock.isAlive()) {
			clock = null;
		}
	}
	
	@Override
	public void destroy() {
	}
}
