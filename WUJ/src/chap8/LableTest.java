package chap8;

import java.applet.*;
import java.awt.Color;
import java.awt.Label;

public class LableTest extends Applet{
	Label myLabel1, myLable2, myLable3;
	
	public LableTest() {}
	
	public void init() {
		myLabel1 = new Label();
		myLabel1.setText("C���");
		myLabel1.setAlignment(Label.LEFT);
		myLabel1.setBackground(Color.cyan);
		add(myLabel1);
		
		myLable2 = new Label("C++");
		myLable2.setAlignment(Label.CENTER);
		myLable2.setBackground(Color.green);
		add(myLable2);
		
		myLable3 = new Label("�ڹ�", Label.RIGHT);
		myLable3.setBackground(Color.yellow);
		add(myLable3);
	}
}
