package com.zhongkai.redgreenapp.act;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongkai.redgreenapp.R;


public class MainAct extends Activity implements View.OnClickListener{
	protected static final int CONNECT_OK = 0;
	protected static final int CONNECT_FAIL = 1;
	TextView txtTitle,txtConnect,txtDisconnect,txtBeep;
	Button btnLedOn,btnLedOff,btnConnect,btnDisconnect,btnBeepOn,btnBeepOff;
	Button btnSetting;
	ImageView imgLed;
	EditText editIPAddress;
	
	String username;
	String ipAddres;
	
	//用来实现联网的对象
	Socket socket=null;
	InputStream is=null;
	OutputStream os=null;
	BufferedWriter bw=null;//带缓冲的字符输出流
	BufferedReader br=null;//带缓冲的字符输入流
	boolean isConnOk=false;
	
	
	
	
	//用来Handler对象来实现子线程和主线程的通信
	Handler hanlder=new Handler(){
		
		//消息处理方法
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CONNECT_OK://连接成功的处理
				txtConnect.setText("连接开发板成功!");//更新UI
				break;
			case CONNECT_FAIL://连接失败的处理
				txtConnect.setText("连接开发板失败!");
				break;
			default:
				break;
			}
		}
	};
	
	
	@Override  //用来初始化Activity实例方法
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		username=intent.getStringExtra("username");
		initView();//调用初始化界面视图的方法
		
		
		
	}
	
	//初始化界面视图的方法定义
	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.main);
		txtTitle=(TextView)findViewById(R.id.txtTitle);//获取界面控件对象
		txtTitle.setText("欢迎您:"+username+"\n使用仲恺学院自动化系统");
		btnLedOn=(Button)findViewById(R.id.btnLedOn);
		btnLedOff=(Button)findViewById(R.id.btnLedOff);
		btnLedOn.setOnClickListener(this);//注册按钮事件监听请
		btnLedOff.setOnClickListener(this);//注册按钮事件监听请
		imgLed=(ImageView)findViewById(R.id.imgLed);
		btnConnect=(Button)findViewById(R.id.btnConnect);
		btnDisconnect=(Button)findViewById(R.id.btnDisconnect);
		btnConnect.setOnClickListener(this);//注册按钮事件监听请
		btnDisconnect.setOnClickListener(this);//注册按钮事件监听请
		
		txtConnect=(TextView)findViewById(R.id.txtConnect);
		txtDisconnect=(TextView)findViewById(R.id.txtDisconnect);
		
		btnBeepOn=(Button)findViewById(R.id.btnBeepOn);
		btnBeepOff=(Button)findViewById(R.id.btnBeepOff);
		txtBeep=(TextView)findViewById(R.id.txtBeep);
		btnBeepOn.setOnClickListener(this);
		btnBeepOff.setOnClickListener(this);
		
		editIPAddress=(EditText)findViewById(R.id.editIp);
		btnSetting=(Button)findViewById(R.id.btnSetting);
		btnSetting.setOnClickListener(this);
	}
	
	
	
	@Override  //按钮的事件处理
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnLedOn://led on的按钮事件处理
			sendCmd("led_on");
			imgLed.setImageResource(R.drawable.led_on);
			
			
			
			
			break;
		case R.id.btnLedOff://led off的按钮事件处理
			sendCmd("led_off");
			imgLed.setImageResource(R.drawable.led_off);
			break;
		case R.id.btnConnect://连接开发板按钮事件处理
			
			connectServer();//调用连接开发板服务器的方法
			
			txtConnect.setVisibility(View.VISIBLE);//连接标签可见
//			txtConnect.setText("连接成功!");
			
			btnDisconnect.setEnabled(true);//启动断开连接的按钮
			btnLedOn.setEnabled(true);//启动ledon按钮
			btnLedOff.setEnabled(true);//启动ledoff按钮
			
			btnBeepOn.setEnabled(true);
			btnBeepOff.setEnabled(true);
			txtBeep.setVisibility(View.VISIBLE);
			imgLed.setVisibility(View.VISIBLE);
			
			
			txtDisconnect.setVisibility(View.GONE);//断开连接标签隐藏
			break;
		case R.id.btnDisconnect://断开开发板事件处理
			disConectServer();//调用断开开发板服务器的方法
			txtDisconnect.setVisibility(View.VISIBLE);//断开连接标签可见
			txtDisconnect.setText("断开连接");
			btnLedOn.setEnabled(false);//禁用ledon按钮
			btnLedOff.setEnabled(false);//禁用ledoff按钮
			
			btnBeepOff.setEnabled(false);
			btnBeepOn.setEnabled(false);
			txtBeep.setVisibility(View.GONE);
			
			
			txtConnect.setVisibility(View.GONE);//连接标签隐藏
			break;
			
		case R.id.btnBeepOn:
			sendCmd("beep_on");
			txtBeep.setText("蜂鸣器响");
			
			
			break;
		case R.id.btnBeepOff:
			sendCmd("beep_off");
			txtBeep.setText("蜂鸣器关");
			break;
		
		case R.id.btnSetting:
			ipAddres=editIPAddress.getText().toString().trim();
			
			break;
			
		default:
			break;
		}
		
	}
	
	private void sendCmd(String cmd) {
		// TODO Auto-generated method stub
		try {
			bw.write(cmd);//给开发板服务器发送cmd命令
			bw.newLine();//添加一个换行符   
			bw.flush();//刷新
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void disConectServer() {
		// 关闭io流 socket
		try {
			if(br!=null)br.close();
			if(bw!=null)bw.close();
			if(socket!=null)socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void connectServer() {
		//创建一个进度对话框
		final ProgressDialog dialog=new ProgressDialog(this);
		dialog.setTitle("连接网络提示");
		dialog.setMessage("正在连接远程的开发板,请稍候...");
		dialog.show();//弹出进度对话框
		
		
		//创建并启动一个线程,因为socket通信会有阻塞
		new Thread(){
			
			public void run() {
				
				//创建消息对象,用来给handler对象发送消息的
				Message msg=Message.obtain();

				try {
//					socket=new Socket("192.168.16.72",22222);
					socket=new Socket();//
					ipAddres=editIPAddress.getText().toString().trim();//去空白
					SocketAddress remoteAddress=new InetSocketAddress(ipAddres,22222);
					
					
					socket.connect(remoteAddress,3000);//网络连接允许超时3秒,超时产生异常
					//具有阻塞的代码   connect()返回表示连接服务器成功
					
					System.out.println("客户端连接服务器成功!!!");
					
					//用socket打开IO流
					is=socket.getInputStream();
					os=socket.getOutputStream();
					
					//把网络的输出流用OutputStreamWriter类实现字节转字符流,用BufferedWriter转换带缓冲的字符流
					bw=new BufferedWriter(new OutputStreamWriter(os));
					//把网络的输入流用InputStreamReader类实现字节转字符流,用BufferedReader转换带缓冲的字符流
					br=new BufferedReader(new InputStreamReader(is));
					
					
					//因为在子线程中不能更新UI  设置textview对象的内容用 handler对象发消息
					msg.what=CONNECT_OK;//给消息对象设置what值
					hanlder.sendMessage(msg);//handler发送消息对象msg  
					//handler就会调用handleMessage()方法
					
					
					
					
				} catch (UnknownHostException e) {
					// 产生网络连接异常的处理
					e.printStackTrace();
					//设置消息对象的what值
					msg.what=CONNECT_FAIL;
					hanlder.sendMessage(msg);//hadnler发送消息
					
				} catch (IOException e) {
					// 产生网络连接异常的处理
					
					e.printStackTrace();
					//设置消息对象的what值
					msg.what=CONNECT_FAIL;
					hanlder.sendMessage(msg);//hadnler发送消息
				}//ip,port
				
				dialog.dismiss();//关闭对话框
			}
		}.start();//启动线程
		
	}
			
			

}
