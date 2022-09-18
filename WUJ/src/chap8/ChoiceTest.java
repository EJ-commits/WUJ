package chap8;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ChoiceTest extends Applet implements ItemListener{

	Label myLabel;
	Choice myChoice;
	
	public void init() {
		myChoice = new Choice();
		myChoice.addItem("C");
		myChoice.addItem("C++");
		myChoice.addItem("java");
		myChoice.addItemListener(this);
		add(myChoice);
		
		myLabel = new Label();
		myLabel.setText("���̽� �׸� ���� ");
		myLabel.setAlignment(Label.CENTER);
		myLabel.setBackground(Color.yellow);
		add(myLabel);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {	
		if(e.getSource() == myChoice) {
			myLabel.setText("����" + myChoice.getSelectedItem());
		}
		
	}
	
	
}
