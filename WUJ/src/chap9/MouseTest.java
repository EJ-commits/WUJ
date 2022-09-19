package chap9;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class MouseTest extends Applet implements MouseListener{

	Label myLabel;
	int[] x, y;
	int num;
	
	public void init() {
		setLayout(new BorderLayout());
		Label myLabel = new Label("마우스클릭하면원그림");
		myLabel.setAlignment(Label.CENTER);
		add("North",myLabel); 
		
		x = new int[100];
		y = new int[100]; // 100크기의 배열임을 명시
		num = 0; // 초기값 부여 
		addMouseListener(this);
	}
	
	@Override
	public void paint(Graphics g) { // 페인트 메서드는 그래픽스 객체를 파라미터로 받음
		g.setColor(Color.red);
		for(int i=0; i<num; i++) {
			g.fillOval(x[i]-20, y[i]-20, 40, 40);
		}
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(num<100) {
			x[num] = e.getX();
			y[num] = e.getY();
			num++;
		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
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

}
