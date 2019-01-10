package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class EchoServer {
	private static int PORT=6000;
	public static void main(String[] args) {
		ServerSocket serverSocket=null;
		String encodingType="UTF-8";

		try {
			serverSocket=new ServerSocket();
			
			InetAddress inetAddress=InetAddress.getLocalHost();
			String hostAddress=inetAddress.getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			System.out.println("[server] "+ hostAddress+" : "+PORT);
			
			//Socket socket=serverSocket.accept();
			//System.out.println("[server] connected by client");
			
			//try {
				while(true) {
				Socket socket=serverSocket.accept();
				Thread th1=new EchoServerReceiveThread(socket);
				th1.start();
				}
				
			/*BufferedReader bis=new BufferedReader(new InputStreamReader(socket.getInputStream(), encodingType));
			PrintWriter bos=new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), encodingType), true);
			Scanner scanner=new Scanner(System.in);
			while(true) {
				
				String line=bis.readLine();
				if(line!=null) {
					System.out.println("[server] received : "+line);
				}
				
				bos.print(line+"\n");
				bos.flush();
			}
			
			}catch(SocketException e) {
				System.out.println("[server] abnormally closed");
			}finally {
				
				try {
					if(socket!=null&&!socket.isClosed()) {
						socket.close();
					}
				}catch(IOException e) {
					e.printStackTrace();
				}
					
			}*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
				try {
					if(serverSocket!=null&&!serverSocket.isClosed())
						serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public static void log(String string) {
		// TODO Auto-generated method stub
		System.out.println(string);
	}
}
