import java.io.DataInputStream;
import java.io.IOException;

import javax.swing.JTextArea;


public class ReadThread extends Thread{
	JTextArea ta;
	DataInputStream dis;
	String text;
	public ReadThread(JTextArea t,DataInputStream d){
		this.ta=t;
		this.dis=d;		
	}
	//线程实现的功能
	public void run(){
		try{
			text=dis.readUTF();//读取输入流的信息UTF-8多国码
			while(!text.equals("quit")){
				ta.append("对方说:"+text);
				ta.append("\n");
				text=dis.readUTF();
			}
		}catch(IOException e){
			System.out.println("连接结束!");
		}
		finally{
			System.exit(0);		
		}
		
	}
}
