package chap10;

import java.applet.Applet;
import java.awt.*;

public class JavaCup2 extends Applet implements Runnable{

	int imageTotal; 
	int index;
	int x, y;
	
	Image javacup[];
	Thread clock;
	MediaTracker myTracker;
	
	public void init() {
		imageTotal = 16;
		myTracker = new MediaTracker(this);
		javacup = new Image[imageTotal];
		
		for(int i=0; i<imageTotal; i++) {
//			javacup[i] = getImage(getCodeBase(), ""+i+".gif");
			javacup[i] = Toolkit.getDefaultToolkit().getImage(""+i+".gif");
			myTracker.addImage(javacup[i], 0);
		}
		
		try {
			myTracker.waitForAll();
		}catch (InterruptedException e) {
		}
		
		while((myTracker.statusAll(true)) & MediaTracker.COMPLETE == 0) {}
		
		x= y= 0;
		
	}
	
	public void start() {
		if(clock==null) {
			clock = new Thread(this);
			clock.start();
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(javacup[index], x, y, 59, 90,this);
	}
	
	public void update(Graphics g) {
		Color bgColor = getBackground();
		g.setColor(bgColor);
		g.fillRect(x-1, y-1, 59, 90);
		paint(g);
	}
	
	@Override
	public void run() {
	
		while(true) {
			try {
				clock.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(x<400) {
				x++;
				y++;
			}else {
				x= y= 0;
			}
			
			if(index<imageTotal-1) {
				index++;
			}else {
				index=0;
			}
			
			repaint();
		}	
	}

	public void stop() {
		if((clock==null)&&(clock.isAlive())) {
			clock = null;
		}
	}
	
}
