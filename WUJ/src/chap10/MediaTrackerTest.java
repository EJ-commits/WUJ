package chap10;

import java.awt.Color;
import java.applet.Applet;
import java.awt.*;

public class MediaTrackerTest extends Applet implements Runnable{

	int imageTotal;
	int imageLoaded;
	
	Image adventurer[];
	Thread clock;
	MediaTracker myTracker;
	
	public void init() {
		imageTotal = 3;
		imageLoaded = 0;
		
		myTracker = new MediaTracker(this);
		
		adventurer = new Image[imageTotal];
		
		for(int i=0; i<imageTotal; i++) {
//			adventurer[i] = getImage(getCodeBase(), "adventurer"+i+".png");
			adventurer[i] = Toolkit.getDefaultToolkit().getImage("D:\\adventurer"+i+".jpg");
			myTracker.addImage(adventurer[i], i); // 각 이미지가 각 그룹에 속함 
		}	
	}//init end
	
	public void start() {
		if(clock==null) {
			clock = new Thread(this);
			clock.start();
		}
	}
	
	public void stop() {
		if((clock!=null) && (clock.isAlive())) {
			clock = null;
		}
	}
	
	public void paint(Graphics g) {
		if(imageLoaded<imageTotal) {
			g.setColor(Color.blue);
			g.drawRect(72, 115, 150, 40);
			g.fillRect(72, 115, imageLoaded*50, 40); // 로딩되는 중 
		}else{
			for(int i=0; i<3; i++) {
				for(int j=0; j<3; j++) {
					g.drawImage(adventurer[i*3+j], j*59, i*50, 59, 90, this);
				}
			}
		}
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	@Override
	public void run() {
		for(int i=0; i<imageTotal; i++) {
			try {
				myTracker.waitForID(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			while(myTracker.statusID(i, true) && MediaTracker.COMPLETE == 0) {} 
			imageLoaded++;
			repaint();
			try {
				clock.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	
}
