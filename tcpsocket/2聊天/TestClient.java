import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class TestClient extends JFrame implements ActionListener{
	DataInputStream dis;
	DataOutputStream dos;
	JTextField tf;
	JTextArea ta;
	String s11,s22;
	
	public TestClient(String s1,String s2){
		this.setTitle("�������ͻ���");
		
		//1.����GUI���沼��
		JScrollPane jp=new JScrollPane();
		ta=new JTextArea(10,10);
		Panel p=new Panel();
		tf=new JTextField(20);
		JButton b=new JButton("����");
		b.addActionListener(this);
		tf.addActionListener(this);
		p.add(tf);
		p.add(b);
		jp.setViewportView(ta);
		this.getContentPane().add(jp);
		this.getContentPane().add("South",p);
		this.setSize(350,250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.s11=s1;
		this.s22=s2;
		this.setVisible(true);
		tf.requestFocus();
		//2.��������
		this.connect();
		//3.����������Ϣ�߳�
		this.createReadThread();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
			String s=tf.getText();
			if(s.equals("quit")){
				System.exit(0);				
			}
			dos.writeUTF(s11+"˵:"+s);
			ta.append("�Լ�˵:"+s);
			ta.append("\n");
			tf.setText("");
			tf.requestFocus();
		}catch(IOException e1){
			e1.printStackTrace();
		}
	}
	
	public void connect(){
		try{
			Socket s2=new Socket(s22,911);
			InputStream is=s2.getInputStream();
			dis=new DataInputStream(is);
			OutputStream os=s2.getOutputStream();
			dos=new DataOutputStream(os);
		}catch(IOException e){
			System.out.println("���ӷ���������!");
		}
		
	}
	public void createReadThread(){
		
		ReadThread rt=new ReadThread(this.ta,this.dis);
		rt.start();
	}
}
