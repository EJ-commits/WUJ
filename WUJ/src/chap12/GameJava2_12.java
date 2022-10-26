package chap12;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class GameJava2_12 extends Applet implements Runnable, ActionListener {

	Socket mySocket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	
	Thread clock; 
	TextArea memo;
	TextField name;
	TextField input; 
	Panel myPanel;
	
	@Override
	public void init() {
		try {
			mySocket = new Socket(getCodeBase().getHost(), 2587); // 애플릿 자체의 호스트명과, 2587 포트로 소켓생성
			out = new PrintWriter(new OutputStreamWriter(mySocket.getOutputStream(), "KSC5601"));
			in = new BufferedReader(new InputStreamReader(mySocket.getInputStream(), "KSC5601"), 1024);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//GUI
		setLayout(new BorderLayout());
		memo = new TextArea(10,55);
		add("Center", memo);
		myPanel = new Panel();
		name = new TextField(8);
		name.setText("name");
		myPanel.add(name);
		input = new TextField(40);
		input.addActionListener(this);
		myPanel.add(input);
		add("South",myPanel);
	}
	
	@Override
	public void start() {
		
		if(clock==null) {
			clock = new Thread(this);
			clock.start();
		}
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==input) {
			String data = input.getText();
			input.setText("");
			out.println("TALK|"+name.getText() + " : " + data);
			out.flush();
		}
	}

	@Override
	public void run() {
		out.println("LOGIN|" + mySocket);
		memo.append("Connected : " + getCodeBase().toString() + "\n");
		
		try {
			while(true) {
				String msg = in.readLine();
				if(!msg.equals("") && !msg.equals(null)) {
					memo.append(msg+"\n");
				}
			}
		} catch (IOException e) {
				memo.append(e.toString());
			}
	}
	
	@Override
	public void stop() {
		if(clock!=null && clock.isAlive()) {
			clock = null;
		}
		
		out.println("LOGOUT|"+ name.getText());
		out.flush();
		
		try {
			out.close();
			in.close();
			mySocket.close();
		} catch (IOException e) {
			memo.append(e.toString()+"\n");
		}
	}

}
