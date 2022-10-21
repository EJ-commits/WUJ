package SocketPractice;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SocketTest_Client extends JFrame{
	public final static int SERVER_PORT=9999;
	
	private Socket s;
	private JTextField messageField;
	private JTextArea messages;
	
	public SocketTest_Client() {
		super(" TCP Client");
		messageField=new JTextField(40);
		messages=new JTextArea(10,50);
		
		messages.setBackground(Color.black);		//배경 검은색
		JScrollPane scrolledMessages=new JScrollPane(messages);	//스크롤 가능하도록
		
		this.setLayout(new BorderLayout(10,10));
		this.add("North",messageField);
		this.add("Center",scrolledMessages);
		
		messages.setEnabled(false);		//TextArea disactive
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(620,400);
		this.setLocationRelativeTo(null);	//창 가운데 위치
		this.setVisible(true);
		
		
		connectServer();
	}
	
	public void connectServer() {
		String serverIP="127.0.0.1";
		
		try {
			Socket s=new Socket(serverIP,SERVER_PORT);
			messages.append("server port:"+s.getPort()+", my port:"+s.getLocalPort()+"\n");
			BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			messages.append(br.readLine());
			
			br.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String []ar) {
		new SocketTest_Client();	
	}
}