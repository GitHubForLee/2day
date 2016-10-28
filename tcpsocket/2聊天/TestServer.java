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
		this.setTitle("聊天程序服务端");
		
		//1.创建GUI界面布局
		
		JScrollPane jp=new JScrollPane();//带滚动条的容器
		ta=new JTextArea(10,10);//多行文本框
		Panel p=new Panel();//中间容器
		tf=new JTextField(20);
		JButton b=new JButton("发送");
		b.addActionListener(this);//注册监听器
		tf.addActionListener(this);
		
		p.add(tf);//将文本框添加进容器
		p.add(b);//将按钮添加进容器
		jp.setViewportView(ta);//将文本框添加进滚动条容器
		
		this.getContentPane().add(jp);
		this.getContentPane().add("South",p);//容器布局到南向
		this.setSize(320,250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		tf.requestFocus();//获得焦点
		
		//2.建立连接
		this.connect();
		
		//3.启动接收信息线程
		this.createReadThread();
		
	}
	public void connect(){
		try{
			//构造ServerSocket对象,端口为911
			ServerSocket ss=new ServerSocket(911);
			//让服务器等待直到客户连接到端口,返回新套接口s2,新套接口s2实现了与客户原来建立接口的连接			
			Socket s2=ss.accept();
			//获得新套接口s2绑定的输入流is
			InputStream is=s2.getInputStream();
			//构造DataInputStream对象dis
			dis=new DataInputStream(is);			
			OutputStream os=s2.getOutputStream();
			dos=new DataOutputStream(os);
		}catch(IOException e){
			System.out.println("连接服务器故障!");
		}
	}
	
	public void createReadThread(){
		//用多行文本框对象和获得输入流对象来构造线程对象
		ReadThread rt=new ReadThread(this.ta,this.dis);
		rt.start();
	}	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try{
			String s=tf.getText();
			dos.writeUTF(s);//发送聊天信息
			if(s.equals("quit")){
				System.exit(0);				
			}
			
			ta.append("自己说:"+s);//将给定文本追加到文档结尾
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
