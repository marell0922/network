
package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		
		while(true) {
		System.out.print(">> ");
		String line=scanner.nextLine();
		
		if(line.equals("exit"))break;
		
		try {
			InetAddress[] inetAddresses=InetAddress.getAllByName(line);
			System.out.println("---Address---");
			for(InetAddress inetAddress : inetAddresses) {
				String hostAddress=inetAddress.getHostAddress();
				String hostname=inetAddress.getHostName();
				System.out.println(hostname +" : "+hostAddress);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	}
}
