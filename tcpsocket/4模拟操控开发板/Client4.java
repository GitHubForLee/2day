import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client4 {

	/**
	 *    Usage: java Client4 serverip, serverport
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length<2){
			System.out.println("Usage: java Client4 serverip, serverport");
			return ;
		}
			
		try {
			Socket s=new Socket(InetAddress.getByName(args[0]),Integer.parseInt(args[1]));
			//获得键盘的输入字符流
			BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
			//获得网络的输出流
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			//获得网络的输入流
			BufferedReader br2=new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			while(true){
				String instr=br1.readLine();//从键盘读数据
				bw.write(instr);
				bw.newLine();
				bw.flush();
				if("quit".equals(instr))
					break;
				
				//读server的数据
				System.out.println(br2.readLine());
				
			}
			
			
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
