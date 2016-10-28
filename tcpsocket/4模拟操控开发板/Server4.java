import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//模拟开发板的C程序运行的功能
//要求服务器端程序一直运行 多线程的应用
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
				Socket socket=serverSocket.accept();//每次请求连接成功
				//开启一个子线程
				new Thread(new MyserverRunnable(socket)).start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
