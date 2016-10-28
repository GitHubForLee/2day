import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Login extends JFrame implements ActionListener{
	JFrame jf=new JFrame("login");
	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	JLabel l1=new JLabel("请输入您的昵称:");
	JLabel l2=new JLabel("请设置服务器地址:");
	JTextField t1=new JTextField();
	JTextField t2=new JTextField();
	JButton loginButton=new JButton("Login");
	
	public Login(){
		//登录GUI布局
		p1.setLayout(new GridLayout(2,2));//网络布局2行2列
		p1.add(l1);
		p1.add(t1);
		p1.add(l2);
		p1.add(t2);
		p2.setLayout(new FlowLayout());
		p2.add(loginButton);
		jf.getContentPane().add(p1,"North");
		jf.getContentPane().add(p2);
		jf.pack();//调整到合适大小.
		jf.setLocation(300,200);
		jf.setVisible(true);
		t1.addActionListener(this);
		t2.addActionListener(this);
		loginButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(!t1.getText().equals("")&& !t2.getText().equals("")){
	
			TestClient c=new TestClient(t1.getText(),t2.getText());//启动客户端聊天主窗口
			jf.setVisible(false);
			
		}
	}
	public static void main(String[] args){

		Login aa=new Login();
		
	}
		
}
