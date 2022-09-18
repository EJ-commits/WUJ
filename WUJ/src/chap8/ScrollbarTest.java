package chap8;

import java.applet.Applet;
import java.awt.Label;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class ScrollbarTest extends Applet implements AdjustmentListener{

	Label myLabel;
	Scrollbar myScrollbar;

	public void init() {
	//	setLayout(getLayout());
		myScrollbar = new Scrollbar(Scrollbar.HORIZONTAL, 0, 0, 1, 100);
		myScrollbar.addAdjustmentListener(this);
		add("North", myScrollbar);
		
		myLabel = new Label();
		myLabel.setText("스크롤바조정");
		add("Center", myLabel);
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if(e.getSource() == myScrollbar) {
			myLabel.setText("위치: "+myScrollbar.getValue());
		}
	}
	
}
