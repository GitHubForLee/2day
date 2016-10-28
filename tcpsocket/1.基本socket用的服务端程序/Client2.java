import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client2 {

	
	public static void main(String[] args) {
		Socket client=null;
		InputStream is=null;
		OutputStream os=null;
		BufferedReader br1 = null;
		DataInputStream dis=null;
		DataOutputStream dos=null;
		String instr1,instr2;
		try {
			client=new Socket("192.168.7.243",2345);//阻塞点
			System.out.println("口令:");
			br1=new BufferedReader(new InputStreamReader(System.in));
			instr1=br1.readLine();//阻塞点
			is=client.getInputStream();//获得连接server的 输入流对象
			os=client.getOutputStream();//获得连接server 输出流对象
			dis=new DataInputStream(is);
			dos=new DataOutputStream(os);
			dos.writeUTF(instr1);//发送字符串到server
			instr2=dis.readUTF();//读server发来的数据 如果读不到代码会阻塞
			System.out.println(instr2);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dis.close();
				dos.close();
				os.close();
				is.close();
				br1.close();				
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
	}

}
