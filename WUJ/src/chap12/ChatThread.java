package chap12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class ChatThread extends Thread { // 쓰레드 사용을 위한 상속 (또는 runnable 인터페이스 구현)
	ChatServer myServer;
	Socket mySocket;
	
	PrintWriter out;
	BufferedReader in; // 필터스트림의 프로그램쪽 말단 i/o 통로 
	
	//스레드 생성자, 메시지 보내기, 연결끊기 구현
	
	public ChatThread(ChatServer server, Socket socket) {
		
		super("ChatThread"); // 쓰레드 생성자 
		myServer = server;
		mySocket = socket;
		out = null;
		in = null;
	}
	
	public void sendMessage(String msg) {
		out.println(msg);
		out.flush();
	}
	
	public void disconnect() {
		try {
			out.flush();
			in.close();
			out.close();
			mySocket.close();
			myServer.removeClient(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}	
	public void run() { // 쓰레드 기동 
		try {
			out = new PrintWriter(new OutputStreamWriter(mySocket.getOutputStream(),"KSC5601"),true);
			in = new BufferedReader(new InputStreamReader(mySocket.getInputStream(),"KSC5601"),1024);	
		
			while (true) { // 입력이 비어있지 않으면 메시지 출력 메서드 실행 
				String inLine = in.readLine();
				if(inLine.equals("")&&inLine.equals(null)) {
					messageProcess(inLine);
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
			disconnect();
		}
			
		
	}

	public void messageProcess(String msg) {
		StringTokenizer st = new StringTokenizer(msg,"|");
		String command = st.nextToken();
		String talk = st.nextToken();
		
		if(command.equals("LOGIN")) {
			System.out.println("[Connect] " + mySocket); //talk아닌가..
			myServer.broadcast("Now Connecting : " + myServer.clientNum);
		} else if(command.equals("LOGOUT")) {
			myServer.clientNum--;
			myServer.broadcast("[Disconnected] " + talk);
			myServer.broadcast("Now Connecting : " + myServer.clientNum);
			disconnect();
		} else {
			myServer.broadcast(talk);
		}
	}
	
}
