package chap11;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import chap11.InnerClassTest.MymouseHandler;

public class AdapterTest extends Applet {


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
	
	class MymouseHandler extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(num<100) {
				x[num] = e.getX();
				y[num] = e.getY();
				num++;
			}
			repaint();
		}
	}
}
