import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//ģ�⿪�����C�������еĹ���
//Ҫ��������˳���һֱ���� ���̵߳�Ӧ��
public class Server4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Tcp Server have started....");
		
		try {
			ServerSocket serverSocket=new ServerSocket(22222);
			while(true){
				Socket socket=serverSocket.accept();//ÿ���������ӳɹ�
				//����һ�����߳�
				new Thread(new MyserverRunnable(socket)).start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
