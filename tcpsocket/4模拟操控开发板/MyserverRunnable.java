

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MyserverRunnable implements Runnable {
	private Socket socket;
	

	public MyserverRunnable(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket=socket;
		System.out.println("create client connection...");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		BufferedReader br=null;
		BufferedWriter bw=null;
		
		try {
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while(true){
				String str=br.readLine();
				if("quit".equals(str)){
					System.out.println("client disconnect.");
					break;
				}
				System.out.println("from client :"+str);				
				bw.write("to server:"+str);
				bw.newLine();
				bw.flush();
				System.out.println("开发板调用"+str+"对应的驱动控制外围设备!");
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
