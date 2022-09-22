package chap10;

import java.applet.Applet;
import java.awt.*;

public class JavaCup3_DoubleBuffering extends Applet implements Runnable{

	int imageTotal; 
	int index;
	int x, y;
	
	Image javacup[];
	Thread clock;
	MediaTracker myTracker;
	
	Image off; // 가상이미지 컨텍스트
	Graphics offG; // 가상이미지용 도구
	/* 자바 그래픽 : 컴포넌트(그래픽의 대상) + 컨텍스트(도구)
	 * 컴포넌트: 프레임, 캔버스, 패널, 애플릿 등... 갖다붙일 수 있는 공간
	 * 컴포넌트 1개는 paint() 1개를 갖는다 
	 * paint()는 그래픽 컨텍스트(도구)를 사용한다 
	 * paint() 메서드 안에서 Graphics(도구)를 통해 그림*/
	public void init() {
		
		off = createImage(400,400);
		offG = off.getGraphics();
		imageTotal = 16;
		myTracker = new MediaTracker(this);
		javacup = new Image[imageTotal];
		
		for(int i=0; i<imageTotal; i++) {
//			javacup[i] = getImage(getCodeBase(), ""+i+".gif");
			javacup[i] = Toolkit.getDefaultToolkit().getImage("D:\\LakeSample\\img_900"+i+".jpg");
			myTracker.addImage(javacup[i], 0);
		}
		
		try {
			myTracker.waitForAll();
		}catch (InterruptedException e) {
		}
		
		//statusAll은 특별한 사용법을 갖는다 (특정객체와 비트연산)
		while(((myTracker.statusAll(true) & MediaTracker.COMPLETE) == 0)) {}
		
		x= y= 0;
		
	}
	
	public void start() {
		if(clock==null) {
			clock = new Thread(this);
			clock.start();
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(off,0, 0, 500, 300,this);
	}
	
	public void update(Graphics g) {
		/*update가 화면을 지우지 않고 바로 paint()하도록 만듬.
		 *이 paint내용은 이미 off 에 그려진 대로 가져옴 
		 *현재 이 클래스에서, off 는 run시마다 그려지고있음 
		 *쓰레드는 50밀리초마다 껐다켜짐 -> 50밀리초마다 off에 다음 그림[i] 불러옴
		 *그림[i]는 이미 init()시에 전체 로드되어 있음 */
		paint(g);
	}
	
	@Override
	public void run() {
	
		while(true) {
			try {
//				clock.sleep(50);
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
			
			Color bgColor = getBackground();
			offG.setColor(bgColor);
		//	offG.fillRect(x-1, y-1, 200, 150);
			offG.drawImage(javacup[index], x, y, 500, 300,this);
			
		//repaint 하면 기존에는 update - paint - g.drawimage(img)
		//지금은
			repaint();
		}	
	}

	public void stop() {
		if((clock==null)&&(clock.isAlive())) {
			clock = null;
		}
	}
	
}
