package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;

public class RequestHandler extends Thread {
	private Socket socket;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			consoleLog("connected from " + inetSocketAddress.getAddress().getHostAddress() + ":"
					+ inetSocketAddress.getPort());

			// get IOStream
			OutputStream outputStream = socket.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			consoleLog("================Request=================");
			String request = null;
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;

				// header만 읽음
				if ("".equals(line))
					break;

				// consoleLog(line);
				// 헤더의 첫번째 라인만 처리
				if (request == null)
					request = line;
			}

			consoleLog(request);

			String[] tokens = request.split(" ");

			if (tokens[0].equals("GET")) {
				responseStaticResource(outputStream, tokens[1], tokens[2]);
			} else {
				//POST, PUT, DELETE, ETC 명령
				//consoleLog("bad request : "+ request);
				response400Error(outputStream, tokens[2]);
				/*
				 * HTTP/1.0 400 Bad Request
				 * 
				 * */
			}

			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
			//outputStream.write("HTTP/1.1 200 OK\r\n".getBytes("UTF-8"));
			//outputStream.write("Content-Type:text/html; charset=utf-8\r\n".getBytes("UTF-8"));
			// outputStream.write( "\r\n".getBytes() );
			// outputStream.write( "<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할 준비가 된
			// 것입니다.</h1>".getBytes( "UTF-8" ) );

		} catch (Exception ex) {
			consoleLog("error:" + ex);
		} finally {
			// clean-up
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}

			} catch (IOException ex) {
				consoleLog("error:" + ex);
			}
		}
	}

	private void response400Error(OutputStream outputStream, String protocol) throws IOException {
		// TODO Auto-generated method stub
		
		File file=new File("./webapp/error/400.html");
		byte[] body=Files.readAllBytes(file.toPath());
		String contentType=Files.probeContentType(file.toPath());//type 결저해줌.
		
		outputStream.write((protocol+" 400 Bad Request\r\n").getBytes("UTF-8"));
		outputStream.write(("Content-Type:"+contentType+"; charset=utf-8\r\n").getBytes("UTF-8"));
	    outputStream.write( "\r\n".getBytes() );
	    outputStream.write(body);
	}

	private void responseStaticResource(OutputStream outputStream, String url, String protocol) throws IOException {
		
		if("/".equals(url))
			url="/index.html";
		
		File file=new File("./webapp"+url);
		consoleLog("responseStaticResource : "+file.toString());
		
		if(!file.exists()) {
			response404Error(outputStream, protocol);
			/*
			 * HTTP/1.0 404 File is not find!
			 * Content-Type:text/html 
			 * */
			return;
		}
		
		//nio -1.7 version 
		byte[] body=Files.readAllBytes(file.toPath());
		String contentType=Files.probeContentType(file.toPath());//type 결저해줌.
		//응답
		outputStream.write("HTTP/1.1 200 OK\r\n".getBytes("UTF-8"));
		outputStream.write(("Content-Type:"+contentType+"; charset=utf-8\r\n").getBytes("UTF-8"));
		outputStream.write( "\r\n".getBytes() );
		outputStream.write(body);
	}

	private void response404Error(OutputStream outputStream, String protocol) throws IOException {
		// TODO Auto-generated method stub
		File file=new File("./webapp/error/404.html");
		byte[] body=Files.readAllBytes(file.toPath());
		String contentType=Files.probeContentType(file.toPath());//type 결저해줌.
		//응답
		outputStream.write((protocol+" 404 File is not find!\r\n").getBytes("UTF-8"));
		outputStream.write(("Content-Type:"+contentType+"; charset=utf-8\r\n").getBytes("UTF-8"));
		outputStream.write( "\r\n".getBytes() );
		outputStream.write(body);
	}

	public void consoleLog(String message) {
		System.out.println("[RequestHandler#" + getId() + "] " + message);
	}
}
