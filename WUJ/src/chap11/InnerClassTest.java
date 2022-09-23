package chap11;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InnerClassTest extends Applet {
	
	Label myLabel;
	int[] x, y;
	int num; 
	
	public void init() {
		setLayout(new BorderLayout());
		myLabel = new Label("원그리기");
		myLabel.setAlignment(Label.CENTER);
		add("North",myLabel);
		
		x=new int[100];
		y=new int[100];
		num = 100; 
		
		addMouseListener(new MymouseHandler());
	
	}

	public void paint(Graphics g) {
		g.setColor(Color.red);
		
		for(int i=0; i<num; i++) {
			g.fillOval(x[i]-20, y[i]-20, 40, 40);
		}
	}
	
	class MymouseHandler implements MouseListener{

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
}	