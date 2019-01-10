package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	public static void main(String[] args) {
		Socket socket = null;
		Scanner sc = new Scanner(System.in);
		String encodingType = "UTF-8";
		try {
			socket = new Socket();

			System.out.print("=> ");
			String[] command = sc.nextLine().split(" ");

			socket.connect(new InetSocketAddress(command[0], Integer.parseInt(command[1])));
			System.out.println("[client] conneted");
			try {
				BufferedReader bis = new BufferedReader(new InputStreamReader(socket.getInputStream(), encodingType));
				PrintWriter bos = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), encodingType), true);
			
				
				while(true) {
				System.out.print(">> ");
				String line=sc.nextLine();
				
				if(line.contains("exit")) break;
				
				//bos.write(line.concat("\n");
				//bos.println(line);
				bos.print(line.concat("\n"));
				bos.flush();
				
				line=bis.readLine();
				
				if(line!=null)
					System.out.println("<< "+line);
				}
				
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
				
			}

		} catch (IOException e) {
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
