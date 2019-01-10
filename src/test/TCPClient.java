package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class TCPClient {
	public static void main(String[] args) {
		
		Scanner scanner=new Scanner(System.in);
		
		System.out.print(">> ");
		String[] line=scanner.nextLine().split(" ");
		
		//1. socket 생성
		Socket socket=null;
		
		try {
			socket=new Socket();
			
			//2.server 연결
			socket.connect(new InetSocketAddress(line[1],Integer.parseInt(line[2])));
			System.out.println("[client] connected");
			
			//3.IOStream 받아오기
			InputStream is=socket.getInputStream();
			OutputStream os=socket.getOutputStream();
			
			//4.쓰기
			while(true) {
			//String data="hello World\n";
			System.out.print(">> ");
			String data=scanner.nextLine().concat("\n");
			//System.out.print(data);
			
			if(data.equals("exit")) break;
			
			os.write(data.getBytes("UTF-8"));
			
			//5.읽기
			byte[] buffer=new byte[255];
			int readByteCount=is.read(buffer);
			
			if(readByteCount == -1) {
				System.out.println("[client] closed by server");
				break;
			}
			
				data=new String(buffer, 0, readByteCount, "UTF-8");
				System.out.print("[client] received : "+data);
			}
		}catch(SocketException e) {
			System.out.println("[client] abnormal closed by client");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
				try {
					if(socket!=null&&!socket.isClosed())
						socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
