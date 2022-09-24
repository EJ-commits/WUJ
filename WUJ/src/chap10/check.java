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

public class check extends Applet implements Runnable, ActionListener{

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
		buttonPanel.add(stopButton);
		
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
		offG.setColor(Color.white);
		offG.fillRect(0, 0, 314, 310);
		offG.drawImage(machine, 0, 0, this); // 슬롯 위에 머신 겹치기 
	
		slotNum = 0;
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
