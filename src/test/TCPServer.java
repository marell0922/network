package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class TCPServer {
	private static final int PORT=5000;
	public static void main(String[] args) {
		ServerSocket serverSocket=null;
		Scanner scanner=new Scanner(System.in);
		try{
			//1. server socket 생성
			serverSocket= new ServerSocket();
			
			//2. Binding(ScoketAddrees(IPAddress + port) in Socket)
			InetAddress inetAddress=InetAddress.getLocalHost();
			String localHostAddress=inetAddress.getHostAddress();
			
			serverSocket.bind(new InetSocketAddress(localHostAddress, PORT));
			System.out.println("[server] binding "+localHostAddress + " : "+PORT);
			
			//accept of connetion suggestong from client
			Socket socket=serverSocket.accept(); //blocking
			
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			String remoteHostAddress=inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort=inetRemoteSocketAddress.getPort();
			System.out.println("[server] connected by client[ "+remoteHostAddress+" : "+remotePort+" ]");
			
			try {
			//4. IOStream
				InputStream is=socket.getInputStream();
				OutputStream os=socket.getOutputStream();
			
				while(true) {
					//5.read data
					byte[] buffer=new byte[256];
					int readByteCount = is.read(buffer);
					if(readByteCount == -1) {
						// 정상종료 : remote socket close()
						// 메소드를 통해서 정상적으로 소켓을 닫은 경우
						System.out.println("[server] closed by client");
						break;
					}
			
					String data=new String(buffer,0,readByteCount, "UTF-8");
					System.out.print("[server] received : "+ data);
					
					System.out.print(">> ");
					data=scanner.nextLine().concat("\n");
					os.write(data.getBytes("UTF-8"));
			
				}
			}catch(SocketException e) {
				System.out.println("[server] abnormal closed by client");
			}catch(IOException e){
				e.printStackTrace();
			}finally {
				try {
					//7. 자원 정리
					if(socket!=null)
						socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
				try {

					if(serverSocket!=null&&serverSocket.isClosed() ==false)
						serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
