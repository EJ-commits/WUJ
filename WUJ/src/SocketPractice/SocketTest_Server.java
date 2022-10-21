package SocketPractice;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTest_Server {
	
	public final static int SERVER_PORT=9999;
	public static void main(String[] ar) {
		ServerSocket ss=null;
		try {
			ss=new ServerSocket(SERVER_PORT);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			try {
				System.out.println("Waiting connection...");
				Socket s=ss.accept();		//새끼 Socket 넘겨줌
				System.out.println("[ Connection Info ]");
				System.out.println("client address:"+s.getInetAddress());	//클라이언트 IP주소
				System.out.println("client port:"+s.getPort());			//클라이언트 포트 번호
				System.out.println("my port:"+s.getLocalPort());		//나(Server, Local)의 포트
				
				PrintWriter pw=new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
				pw.println("Hello!! From server");
			
				pw.close();
				s.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}