import java.applet.Applet;

import chap8.MyCanvas;

public class CanvasTest extends Applet{
	MyCanvas myCanvas;
	
	public void init() {
		myCanvas = new MyCanvas();
		add(myCanvas);
	}
}
