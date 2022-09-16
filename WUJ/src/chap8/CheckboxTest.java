package chap8;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class CheckboxTest extends Applet implements ItemListener{ //üũ�ڽ��� �Է½� itemevent�߻�
	Label myLabel;
	Checkbox myCheckbox1, myCheckbox2, myCheckbox3;
	
	public void init() {
		myCheckbox1 = new Checkbox();
		myCheckbox1.setLabel("C���");
		myCheckbox1.addItemListener(this);
		add(myCheckbox1);
		
		myCheckbox2 = new Checkbox("C++");
		myCheckbox2.addItemListener(this);
		add(myCheckbox2);
		
		myCheckbox3 = new Checkbox("java");
		myCheckbox3.addItemListener(this);
		add(myCheckbox3);
		
		myLabel = new Label();
		myLabel.setText("üũ�ڽ� ����");
		myLabel.setAlignment(Label.CENTER);
		myLabel.setBackground(Color.yellow);
		add(myLabel);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == myCheckbox1)		myLabel.setText("C���");
		else if(e.getSource() == myCheckbox2)		myLabel.setText("C++");
		else if(e.getSource() == myCheckbox3)		myLabel.setText("java");
	}
}