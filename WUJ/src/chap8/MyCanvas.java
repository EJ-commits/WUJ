package chap8;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class MyCanvas extends Canvas{
	public MyCanvas() {
		setBackground(Color.green);
		setSize(100,100);
	}
	
	public void paint(Graphics g) {
		g.drawString("MyCanvas", 10, 20);
		g.drawOval(40, 40, 40, 40);
		g.drawLine(30, 30, 60, 60);
		g.drawRect(ALLBITS, ABORT, 99, 99);
	}
	
}
