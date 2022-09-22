package chap10;

import java.applet.Applet;
import java.awt.*;

public class JavaCup extends Applet implements Runnable{

	int imageTotal; 
	int index;
	int x, y;
	
	Image javacup[];
	Thread clock;
	MediaTracker myTracker;
	
	public void init() {
		imageTotal = 45;
		myTracker = new MediaTracker(this);
		javacup = new Image[imageTotal];
		
		for(int i=1; i<imageTotal; i++) {
//			javacup[i] = getImage(getCodeBase(), ""+i+".gif");
			javacup[i] = Toolkit.getDefaultToolkit().getImage("D:\\LakeSample\\img_900"+i+".jpg");
			myTracker.addImage(javacup[i], 0);
		}
		
		try {
			myTracker.waitForAll();
		}catch (InterruptedException e) {
		}
		
		//while((myTracker.statusAll(true) == 0) && (MediaTracker.COMPLETE == 0)) {}
		//두 비트가 모두 1일 경우에만 1, 두 비트 중 하나만 0이라도 0.
		/*MediaTracker.LOADING  : 미디어가 로드되는 중이다               
		   MediaTracker.ABORTED  : 미디어의 로드를 취소했다               
		   MediaTracker.ERRORED  : 미디어를 로드하는 도중 오류가 발생했다 
		   MediaTracker.COMPLETE: 미디어 로드를 완료했다                 
		            [표1] MediaTracker가 제공하는 미디어의 상태 값

		      각각의 상태를 체크하는 방법은 statusAll메소드가 되돌린값과 [표1]
		    값을 논리 AND 한 값을 이용하면 됩니다. 
		   */
		while((myTracker.statusAll(true) & MediaTracker.COMPLETE) == 0) {}
		
		x= y= 0;
		
	}
	
	public void start() {
		if(clock==null) {
			clock = new Thread(this);
			clock.start();
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(javacup[index], x, y,200, 150,this);
	}
	
	@Override
	public void run() {
	
		while(true) {
			try {
				//clock.sleep(50);
				clock.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			if(x<400) {
//				x++;
//				y++;
//			}else {
				x= y= 0;
//			}
			
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
