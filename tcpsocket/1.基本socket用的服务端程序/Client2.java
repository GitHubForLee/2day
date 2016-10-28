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
			client=new Socket("192.168.7.243",2345);//������
			System.out.println("����:");
			br1=new BufferedReader(new InputStreamReader(System.in));
			instr1=br1.readLine();//������
			is=client.getInputStream();//�������server�� ����������
			os=client.getOutputStream();//�������server ���������
			dis=new DataInputStream(is);
			dos=new DataOutputStream(os);
			dos.writeUTF(instr1);//�����ַ�����server
			instr2=dis.readUTF();//��server���������� ������������������
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
