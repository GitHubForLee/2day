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
	//�߳�ʵ�ֵĹ���
	public void run(){
		try{
			text=dis.readUTF();//��ȡ����������ϢUTF-8�����
			while(!text.equals("quit")){
				ta.append("�Է�˵:"+text);
				ta.append("\n");
				text=dis.readUTF();
			}
		}catch(IOException e){
			System.out.println("���ӽ���!");
		}
		finally{
			System.exit(0);		
		}
		
	}
}
