package chap10;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SpriteTest extends Applet 
	implements Runnable, MouseMotionListener{

	Thread clock;
	
	Image off; 
	Graphics offG;
	
	Image bgImage; 
	Image ufo; 
	int x, y; 
	
	int mouseX, mouseY;
	
	public void init() {
		off = createImage(500, 500);
		offG = off.getGraphics();
		
		MediaTracker myTracker = new MediaTracker(this);
		
//		bgImage = getImage(getCodeBase(), "");
		bgImage = Toolkit.getDefaultToolkit().getImage("");
		myTracker.addImage(bgImage, 0);
		
		try {
			myTracker.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	while((myTracker.statusAll(true)) & MediaTracker.COMPLETE == 0) {}
		
		x= 100;
		y= 100; // 시작위치
		mouseX =100; 
		mouseY = 100; // 커서초기값
		
		addMouseMotionListener(this);
	}
	
	public void start() {
		if(clock==null) {
			clock = new Thread(this);
			clock.start();
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(off, 0, 0, this);
	}
	

	public void update(Graphics g) {
		paint(g);
	}
	
	public void run() {
		
		while(true) {
			try {
				clock.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//마우스 위치로 이미지 이동
			if(x<mouseX) {
				x++;
			}else if(x>mouseX){
				x--;
			}
			if(y<mouseY) {
				y++;
			}else if(y>mouseY){
				y--;
			}
			
			offG.setColor(Color.white);
			offG.fillRect(0, 0, 500, 500);
			offG.drawImage(bgImage, 0, 0, this);
			offG.drawImage(ufo, x, y, this);
			
			repaint();
		}
		
	}//run end
	
	public void stop() {
		if((clock==null)&&(clock.isAlive())) {
			clock = null;
		}
	}

	@Override
	public void destroy() {
	}
	
	//마우스처리
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}
	
	public void mouseDragged(MouseEvent e) {
		
	}
}
