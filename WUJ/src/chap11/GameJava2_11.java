package chap11;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameJava2_11 extends Applet implements ActionListener, Runnable {
	
	Thread clock;
	Image off; 
	Graphics offG;
	
	Random r;
	
	boolean map[][] ; // 1칸 객체, 15x15픽셀
	Color[] colorType; // 각 블럭마다 컬러 지정
	Color[][] colorMap;
	
	int blockType;
	int[] blockX;
	int[] blockY;	//블록의 생김새를 결정하는 변수 
	int blockPos;
	
	int score;
	int delayTime;	// 쓰레드 지연시간 
	int runGame;
	
	URL url;
	
	Clip clip; // 음악 로딩용 클립 
	File turnAudio = new File("D:\\LakeSamples\button-3.wav");
	File deleteAudio = new File("D:\\LakeSamples\\button-1.wav");
	File gameOverAudio = new File("D:\\LakeSamples\\midnight_ride.wav");
		
	Button startButton;
	Panel buttonPenal;
	
	@Override
	public void init() {
		
		//가상화면 그리기
		off = createImage(181,316); //가로 15픽셀 12칸, 세로 15픽셀 21칸
		offG = off.getGraphics();
		offG.setColor(Color.white);
		offG.fillRect(0, 0, 192, 192); // ?
		
		// 소리 설정
			try {
				clip = AudioSystem.getClip();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
			
		
		//GUI
		setLayout(new BorderLayout());
		buttonPenal = new Panel();
		
		startButton = new Button("start");
		startButton.addActionListener(this);
		buttonPenal.add(startButton);
		add("South", buttonPenal);
		
		map = new boolean[12][21]; // 칸별 상태값을 저장하는 배열
		colorMap = new Color[12][21]; // 칸별 컬러값 지정
		
		colorType = new Color[7]; // 7개의 블록별로 색깔을 지정
		setColorType(); 
		
		blockX = new int[4]; 
		blockY = new int[4]; // 1개 블록
		blockPos = 0;  // 7개 블록 중 어떤 블록인지 설정 
		r = new Random();
		blockPos = Math.abs(r.nextInt() % 7 );  // 랜덤 블록 설정
		setBlockXY(blockType);
		
		drawBlock(); 
		drawMap();
		drawGrid();
		
		score = 0;
		delayTime = 1000; // 1초
		runGame = 0;
		
		addKeyListener(new MyKeyHandler());
	}
	
	public void setColorType() {
		colorType[0] = new Color(65,228,82);
		colorType[1] = new Color(58,98,235);
		colorType[2] = new Color(128,0,64);
		colorType[3] = new Color(255,35,31);
		colorType[4] = new Color(68,17,11);
		colorType[5] = new Color(246,118,57);
		colorType[6] = new Color(224,134,4);
	}
	
	public void setBlockXY(int type) {
		// 블록 7가지의 생김새 정의
		switch(type) {
			case 0:
				blockX[0]=5; blockY[0] =0;
				blockX[1]=6; blockY[1] =0;
				blockX[2]=5; blockY[2] =1;
				blockX[3]=6; blockY[3] =1;
				break;
			case 1:
				blockX[0]=6; blockY[0] =0;
				blockX[1]=5; blockY[1] =1;
				blockX[2]=6; blockY[2] =1;
				blockX[3]=7; blockY[3] =1;
				break;
			case 2:
				blockX[0]=7; blockY[0] =0;
				blockX[1]=5; blockY[1] =1;
				blockX[2]=6; blockY[2] =1;
				blockX[3]=7; blockY[3] =1;
				break;
			case 3:
				blockX[0]=5; blockY[0] =0;
				blockX[1]=5; blockY[1] =1;
				blockX[2]=6; blockY[2] =1;
				blockX[3]=7; blockY[3] =1;
				break;
			case 4:
				blockX[0]=5; blockY[0] =0;
				blockX[1]=5; blockY[1] =1;
				blockX[2]=6; blockY[2] =1;
				blockX[3]=6; blockY[3] =2;
				break;
			case 5:
				blockX[0]=6; blockY[0] =0;
				blockX[1]=5; blockY[1] =1;
				blockX[2]=6; blockY[2] =1;
				blockX[3]=5; blockY[3] =2;
				break;
			case 6:
				blockX[0]=4; blockY[0] =0;
				blockX[1]=5; blockY[1] =0;
				blockX[2]=6; blockY[2] =0;
				blockX[3]=7; blockY[3] =0;
				break;
		}
	}
	
	@Override
	public void start() {
		if(clock == null) {
			clock = new Thread(this);
			clock.start();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(off, 0, 0, this);
	}
		
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				clock.sleep(delayTime);
			} catch (InterruptedException e) {
			}
		
			dropBlock();
			
			switch (runGame) {
			case 1:
				drawBlock();
				drawMap();
				drawGrid();
				break;
			case 2:
				drawScore();
			default:
				drawTitle();
				break;
			}
			repaint();
		
		}
	}
	
	public void dropBlock() {
		removeBlock();
		
		if(checkDrop()) {
			for(int i=0; i<4; i++) {
				blockY[i] = blockY[i]+1;
			}
		}else {
			drawBlock();
			nextBlock();
		}
	}

	public void drawGrid() {
		
		offG.setColor(new Color(190,190,190));
		
		for(int i=0; i<12; i++) {
			for(int j=0; j<21; j++) {
				offG.drawRect(i*15, j*15, 15, 15);
			}
		}
	}

	public void drawMap() {
		for(int i=0; i<12; i++) {
			for(int j=0; j<21; j++) {
				if(map[i][j]) {
					offG.setColor(colorMap[i][j]); //블럭이 있으면 블럭 색깔대로 (이미 drawBlock()시점에 지정됨)
					offG.fillRect(i*15, j*15, 15, 15); // 그리드 안 색깔 채우기 
				} else {
					offG.setColor(Color.white);
					offG.fillRect(i*15, j*15, 15, 15);
				}
			}
		}
	}
	
	public void drawBlock() {
		for(int i=0; i<4; i++) {
			map[blockX[i]][blockY[i]] = true; // 테트리스 레이어 true
			colorMap[blockX[i]][blockY[i]] = colorType[blockType];
		}
	}
	
	public boolean checkDrop() {
		boolean dropOk = true;
		
		for(int i=0; i<4; i++) {
			if((blockY[i]+1<21) ) {
				if(map[blockX[i]][blockY[i]+1]) {
					dropOk =false;
				}
			}else {
				dropOk = false;
			}
		}
		return dropOk;
	}

	public void delLine() {
		boolean delOk; // 한줄 지워질 수 있는지
		for(int row=20; row >=0; row --) {
			delOk =true;
			for(int col=0; col<12; col++) {
				if(!map[col][row]) 
					delOk=false;
			}
			
			if(delOk) {
				try {
					clip.open(AudioSystem.getAudioInputStream(deleteAudio));
 					clip.start();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
				score += 10;
				
				if(score <1000) //속도조절
					delayTime = 1000 - score;

				for(int delRow=row; delRow >0; delRow--) {
					for(int delCol=0; delCol <12 ; delCol++) {
						map[delCol][delRow] = map[delCol][delRow-1];
						colorMap[delCol][delRow] = colorMap[delCol][delRow-1]; // 화면표시용
					}
				}
			} else {
//				delayTime = 0;
			}
			
			
			for(int i=0; i<12; i++) {
				map[0][i] =  false;
				colorMap[0][i] = Color.white;
			}
			
//			row++;
		}
	}
	
	

	public void nextBlock() {
		blockType = Math.abs(r.nextInt() % 7);
		blockPos = 0;
		delLine();
		setBlockXY(blockType);
		checkGameOver();
	}

	public void checkGameOver() {
		for(int i=0; i<4; i++) {
			if(map[blockX[i]][blockY[i]]) {
				if(runGame==1) { 	// 게임오버
					try {
						clip.open(AudioSystem.getAudioInputStream(gameOverAudio));
						clip.start();
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
						e.printStackTrace();
					}
					runGame = 2;
				}
			}
				
		}
	}

	public void removeBlock() {
		for(int i=0; i<4; i++) {
			map[blockX[i]][blockY[i]] = false;
			colorMap[blockX[i]][blockY[i]] = Color.white;
		}
	}

	public void drawTitle() {
		offG.setColor(Color.white);
		offG.fillRect(29, 120, 123, 70);
		offG.setColor(Color.black);
		offG.drawRect(31, 125, 121, 60);
		offG.setColor(Color.red);
		offG.drawString("TETRIS", 70, 150);
		offG.setColor(Color.blue);
		offG.drawString("PRESS START BUTTON", 35, 170);
	}		
	

	public void drawScore() {
		offG.setColor(Color.white);
		offG.fillRect(35, 120, 110, 70);
		offG.setColor(Color.black);
		offG.drawRect(40, 125, 100, 60);
		offG.setColor(Color.red);
		offG.drawString("GameOver", 56, 150);
		offG.setColor(Color.blue);
		offG.drawString("Score"+score, 56, 170);
	}
	
	@Override
	public void stop() {
		if((clock!=null) && clock.isAlive()) {
			clock=null;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		blockPos = 0;
		for(int i=0; i<12; i++) {
			for(int j=0; j<21 ; j++) {
				map[i][j] = false; 
			}
		}
		
		r = new Random();
		blockType = Math.abs(r.nextInt()%7);
		setBlockXY(blockType);
		
		drawBlock();
		drawMap();
		drawGrid();
		
		score = 0;
		delayTime =1000;
		runGame = 1;
		
		this.requestFocus();
	}
	

	class MyKeyHandler extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			
			int keyCode = e.getKeyCode();
			
			if(keyCode==KeyEvent.VK_LEFT) {
				if(checkMove(-1)) {
					for(int i=0; i<4; i++) {
						blockX[i]= blockX[i]-1;
					}
				}
			}
			
			if(keyCode == KeyEvent.VK_RIGHT) {
				if(checkMove(1)) {
					for(int i=0; i<4; i++) {
						blockX[i] = blockX[i]+1;
					}
				}
			}
			
			if(keyCode == KeyEvent.VK_DOWN) {
				if( checkDrop() ) {
					for(int i=0; i<4; i++) {
						blockY[i] = blockY[i]+1;
					}
				}else {
					drawBlock(); 
				}
			}
			
			if(keyCode == KeyEvent.VK_UP) {
				int[] tempX = new int[4];
				int[] tempY = new int[4];
				
				for(int i=0; i<4; i++) {
					tempX[i] = blockX[i];
					tempY[i] = blockY[i];
				}
				
				removeBlock();
				turnBlock();
				
				if(checkTurn()) {
					try {
						clip.open(AudioSystem.getAudioInputStream(turnAudio));
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					clip.start();
					
					if(blockPos<4) {	// 블록의 종류
						blockPos++;		// 회전한 블록으로 변경 
					}else {
						blockPos = 0;
					}
					
				}else {
					for(int i=0; i<4; i++) {
						blockX[i] = tempX[i];
						blockY[i] = tempY[i];
						map[blockX[i]][blockY[i]] = true;
						colorMap[blockX[i]][blockY[i]] = colorType[blockType]; // 원상복구
						
					}
				}
			}// if(keyCode == KeyEvent.VK_UP) end
			
			drawBlock();
			drawMap();
			drawGrid();
			repaint();
			
		} // keyPressed end 

		public boolean checkMove(int dir) {
			
			boolean moveOK = true;
			removeBlock();
			
			for(int i=0; i<4; i++) {
				if( blockX[i]+dir>=0 && blockX[i]+dir<12 ) {
					if(map[blockX[i]+dir][blockY[i]]) 
						moveOK = false;
				}else {
					moveOK = false;
				}
			}
			if(!moveOK) 
				drawBlock(); // moveok가 false값이면 블록 그리기
			return moveOK;
		}

		public boolean checkTurn() {
			
			boolean turnOK = true;
			
			for(int i=0; i<4; i++) {
				if(blockX[i]>=0 && blockY[i]>=0 && blockX[i]<12 && blockY[i] <12) {
					if(map[blockX[i]][blockY[i]]) {
						turnOK = false;
					} 
				}else {
					turnOK = false;
				}
			}
			return turnOK;
		}//checkMove end
		
		public void turnBlock() {
			switch(blockType) {
			case 1:
				switch (blockPos) {
				case 0:
					blockX[0] = blockX[0];	blockY[0] = blockY[0];
					blockX[1] = blockX[1];	blockY[1] = blockY[1];
					blockX[2] = blockX[2];	blockY[2] = blockY[2];
					blockX[3] = blockX[3]-1;	blockY[3] = blockY[3]+1;
					break;
				case 1:
					blockX[0] = blockX[0]-1;	blockY[0] = blockY[0];
					blockX[1] = blockX[1]+1;	blockY[1] = blockY[1]-1;
					blockX[2] = blockX[2]+1;	blockY[2] = blockY[2]-1;
					blockX[3] = blockX[3];	blockY[3] = blockY[3]-1;
					break;
				case 2:
					blockX[0] = blockX[0]+1;	blockY[0] = blockY[0];
					blockX[1] = blockX[1];	blockY[1] = blockY[1]+1;
					blockX[2] = blockX[2];	blockY[2] = blockY[2]+1;
					blockX[3] = blockX[3];	blockY[3] = blockY[3]+1;
					break;
				case 3:
					blockX[0] = blockX[0];	blockY[0] = blockY[0];
					blockX[1] = blockX[1]-1;	blockY[1] = blockY[1];
					blockX[2] = blockX[2]-1;	blockY[2] = blockY[2];
					blockX[3] = blockX[3]+1;	blockY[3] = blockY[3]-1;
					break;
				}
				break;
			case 2:
				switch (blockPos) {
				case 0:
					blockX[0] = blockX[0]-2;	blockY[0] = blockY[0];
					blockX[1] = blockX[1]+1;	blockY[1] = blockY[1]-1;
					blockX[2] = blockX[2];	blockY[2] = blockY[2];
					blockX[3] = blockX[3]-1;	blockY[3] = blockY[3]+1;
					break;
				case 1:
					blockX[0] = blockX[0];	blockY[0] = blockY[0];
					blockX[1] = blockX[1];	blockY[1] = blockY[1];
					blockX[2] = blockX[2]+1;	blockY[2] = blockY[2]-1;
					blockX[3] = blockX[3]-1;	blockY[3] = blockY[3]-1;
					break;
				case 2:
					blockX[0] = blockX[0]+1;	blockY[0] = blockY[0];
					blockX[1] = blockX[1];	blockY[1] = blockY[1]+1;
					blockX[2] = blockX[2]-1;	blockY[2] = blockY[2]+2;
					blockX[3] = blockX[3]+2;	blockY[3] = blockY[3]+1;
					break;
				case 3:
					blockX[0] = blockX[0]+1;	blockY[0] = blockY[0];
					blockX[1] = blockX[1]-1;	blockY[1] = blockY[1];
					blockX[2] = blockX[2];	blockY[2] = blockY[2]-1;
					blockX[3] = blockX[3];	blockY[3] = blockY[3]-1;
					break;
				}
				break;
			case 3:
				switch (blockPos) {
				case 0:
					blockX[0] = blockX[0]+1;	blockY[0] = blockY[0];
					blockX[1] = blockX[1]+1;	blockY[1] = blockY[1];
					blockX[2] = blockX[2]-1;	blockY[2] = blockY[2]+1;
					blockX[3] = blockX[3]-1;	blockY[3] = blockY[3]+1;
					break;
				case 1:
					blockX[0] = blockX[0]-2;	blockY[0] = blockY[0];
					blockX[1] = blockX[1]-1;	blockY[1] = blockY[1]-1;
					blockX[2] = blockX[2]+1;	blockY[2] = blockY[2]-2;
					blockX[3] = blockX[3];	blockY[3] = blockY[3]-1;
					break;
				case 2:
					blockX[0] = blockX[0]+1;	blockY[0] = blockY[0];
					blockX[1] = blockX[1]+1;	blockY[1] = blockY[1];
					blockX[2] = blockX[2]-1;	blockY[2] = blockY[2]+1;
					blockX[3] = blockX[3]-1;	blockY[3] = blockY[3]+1;
					break;
				case 3:
					blockX[0] = blockX[0];	blockY[0] = blockY[0];
					blockX[1] = blockX[1]-1;	blockY[1] = blockY[1]+1;
					blockX[2] = blockX[2]+1;	blockY[2] = blockY[2];
					blockX[3] = blockX[3]+2;	blockY[3] = blockY[3]-1;
					break;
				}
				break;
			case 4:
				switch (blockPos) {
				case 0:
				case 2:
					blockX[0] = blockX[0]+1;	blockY[0] = blockY[0];
					blockX[1] = blockX[1]+2;	blockY[1] = blockY[1]-1;
					blockX[2] = blockX[2]-1;	blockY[2] = blockY[2];
					blockX[3] = blockX[3];	blockY[3] = blockY[3]-1;
					break;
				case 1:
				case 3:
					blockX[0] = blockX[0]-1;	blockY[0] = blockY[0];
					blockX[1] = blockX[1]-2;	blockY[1] = blockY[1]+1;
					blockX[2] = blockX[2]+1;	blockY[2] = blockY[2];
					blockX[3] = blockX[3];	blockY[3] = blockY[3]+1;
					break;
				}
				break;
			case 5:
				switch (blockPos) {
				case 0:
				case 2:
					blockX[0] = blockX[0]-1;	blockY[0] = blockY[0];
					blockX[1] = blockX[1]+1;	blockY[1] = blockY[1]-1;
					blockX[2] = blockX[2];	blockY[2] = blockY[2];
					blockX[3] = blockX[3]+2;	blockY[3] = blockY[3]-1;
					break;
				case 1:
				case 3:
					blockX[0] = blockX[0]+1;	blockY[0] = blockY[0];
					blockX[1] = blockX[1]-1;	blockY[1] = blockY[1]+1;
					blockX[2] = blockX[2];	blockY[2] = blockY[2];
					blockX[3] = blockX[3]-2;	blockY[3] = blockY[3]+1;
					break;
				}
				break;
			case 6:
				switch (blockPos) {
				case 0:
				case 2:
					blockX[0] = blockX[0]+2;	blockY[0] = blockY[0];
					blockX[1] = blockX[1]+1;	blockY[1] = blockY[1]+1;
					blockX[2] = blockX[2];	blockY[2] = blockY[2]+2;
					blockX[3] = blockX[3]-1;	blockY[3] = blockY[3]+3;
					break;
				case 1:
				case 3:
					blockX[0] = blockX[0]-2;	blockY[0] = blockY[0];
					blockX[1] = blockX[1]-1;	blockY[1] = blockY[1]-1;
					blockX[2] = blockX[2];	blockY[2] = blockY[2]-2;
					blockX[3] = blockX[3]+1;	blockY[3] = blockY[3]-3;
					break;
				}
				break;
			}
		}
		
		
	}//class MyKeyHandler end 
}
