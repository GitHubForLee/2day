import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class TestServer extends JFrame implements ActionListener{

	DataInputStream dis;
	DataOutputStream dos;
	JTextField tf;
	JTextArea ta;
	public TestServer(){
		this.setTitle("�����������");
		
		//1.����GUI���沼��
		
		JScrollPane jp=new JScrollPane();//��������������
		ta=new JTextArea(10,10);//�����ı���
		Panel p=new Panel();//�м�����
		tf=new JTextField(20);
		JButton b=new JButton("����");
		b.addActionListener(this);//ע�������
		tf.addActionListener(this);
		
		p.add(tf);//���ı�����ӽ�����
		p.add(b);//����ť��ӽ�����
		jp.setViewportView(ta);//���ı�����ӽ�����������
		
		this.getContentPane().add(jp);
		this.getContentPane().add("South",p);//�������ֵ�����
		this.setSize(320,250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		tf.requestFocus();//��ý���
		
		//2.��������
		this.connect();
		
		//3.����������Ϣ�߳�
		this.createReadThread();
		
	}
	public void connect(){
		try{
			//����ServerSocket����,�˿�Ϊ911
			ServerSocket ss=new ServerSocket(911);
			//�÷������ȴ�ֱ���ͻ����ӵ��˿�,�������׽ӿ�s2,���׽ӿ�s2ʵ������ͻ�ԭ�������ӿڵ�����			
			Socket s2=ss.accept();
			//������׽ӿ�s2�󶨵�������is
			InputStream is=s2.getInputStream();
			//����DataInputStream����dis
			dis=new DataInputStream(is);			
			OutputStream os=s2.getOutputStream();
			dos=new DataOutputStream(os);
		}catch(IOException e){
			System.out.println("���ӷ���������!");
		}
	}
	
	public void createReadThread(){
		//�ö����ı������ͻ�������������������̶߳���
		ReadThread rt=new ReadThread(this.ta,this.dis);
		rt.start();
	}	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try{
			String s=tf.getText();
			dos.writeUTF(s);//����������Ϣ
			if(s.equals("quit")){
				System.exit(0);				
			}
			
			ta.append("�Լ�˵:"+s);//�������ı�׷�ӵ��ĵ���β
			ta.append("\n");
			tf.setText("");
			tf.requestFocus();
		}catch(IOException e1){
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new TestServer();
		
	}
	
}
