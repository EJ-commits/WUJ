package chap8;

import java.applet.Applet;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Label;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

//�����ڽ��� üũ�ڽ��� �����̴� (������ üũ�ڽ�) - ���� item�̺�Ʈ�� �߻��Ѵ�!!
public class CheckboxGroupTest extends Applet implements ItemListener {
	
	Label myLabel;
	Checkbox myCheckbox1, myCheckbox2, myCheckbox3;
	CheckboxGroup group;
	
	public void init() {
		group = new CheckboxGroup();
		
		myCheckbox1 = new Checkbox("c", false, group);
		myCheckbox1.addItemListener(this);
		add(myCheckbox1);
		
		myCheckbox2 = new Checkbox("c++", false, group);
		myCheckbox2.addItemListener(this);
		add(myCheckbox2);
		
		myCheckbox3 = new Checkbox("java", false, group);
		myCheckbox3.addItemListener(this);
		add(myCheckbox3);
		
		myLabel = new Label("�����ϼ���", Label.CENTER);
		myLabel.setBackground(Color.yellow);
		add(myLabel);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == myCheckbox1)	myLabel.setText("C");
		else if(e.getSource() == myCheckbox2)	myLabel.setText("C++");
		else if(e.getSource() == myCheckbox3)	myLabel.setText("java");
	} 
	

}