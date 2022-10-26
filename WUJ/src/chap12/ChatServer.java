package chap12;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer {
	
	Vector<Thread> clientVector = new Vector<>(); // 지네릭 설정 원본에는 없음
	int clientNum = 0 ;
	
	public void broadcast(String msg) {
		//synchronized: 스레드 세이프 (해당 스레드를 제외하고는 데이터에 접근 불가) 
		synchronized (clientVector) {
			for(int i=0; i<clientVector.size(); i++) {
				ChatThread client = (ChatThread) clientVector.elementAt(i);
				synchronized (client) {
					client.sendMessage(msg);	// out.flush(msg);
				}
			}
		}
	}

	public void addClient(ChatThread client) {
		synchronized (clientVector) {
			clientVector.add(client); // 원본에는 addElement 메서드지만, 둘은 동일한 동작이며 add 가 jdk 1.2 ver이다. 리턴값을 준다. 
		}
	}
	
	public void removeClient(ChatThread client) {
		synchronized (clientVector) {
			clientVector.remove(client);
			client = null;
			System.gc();
		}
	}

	public static void main(String[] args) {
		
		ServerSocket myServerSocket = null;
		//Socket mySocket = null;
		
		ChatServer myServer = new ChatServer();
		
		try {
			myServerSocket = new ServerSocket(2587); // 프로그램 포트번호 지정 
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1); // 임의의 에러종료 코드 -1
		}
		
		System.out.println("Server Waiting... ");

		while (true) {
			try {
				//서버소켓에 클라이언트 소켓이 물렸을때 쓰레드생성
				ChatThread client = new ChatThread(myServer, myServerSocket.accept());
				client.start();
				myServer.addClient(client);
				
				myServer.clientNum++;
			
				System.out.println("Now Connecting :" + myServer.clientNum);
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		}
	
	}

	
}
