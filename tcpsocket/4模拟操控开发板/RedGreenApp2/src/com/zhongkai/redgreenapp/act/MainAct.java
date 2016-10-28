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
	
	//����ʵ�������Ķ���
	Socket socket=null;
	InputStream is=null;
	OutputStream os=null;
	BufferedWriter bw=null;//��������ַ������
	BufferedReader br=null;//��������ַ�������
	boolean isConnOk=false;
	
	
	
	
	//����Handler������ʵ�����̺߳����̵߳�ͨ��
	Handler hanlder=new Handler(){
		
		//��Ϣ������
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CONNECT_OK://���ӳɹ��Ĵ���
				txtConnect.setText("���ӿ�����ɹ�!");//����UI
				break;
			case CONNECT_FAIL://����ʧ�ܵĴ���
				txtConnect.setText("���ӿ�����ʧ��!");
				break;
			default:
				break;
			}
		}
	};
	
	
	@Override  //������ʼ��Activityʵ������
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		username=intent.getStringExtra("username");
		initView();//���ó�ʼ��������ͼ�ķ���
		
		
		
	}
	
	//��ʼ��������ͼ�ķ�������
	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.main);
		txtTitle=(TextView)findViewById(R.id.txtTitle);//��ȡ����ؼ�����
		txtTitle.setText("��ӭ��:"+username+"\nʹ������ѧԺ�Զ���ϵͳ");
		btnLedOn=(Button)findViewById(R.id.btnLedOn);
		btnLedOff=(Button)findViewById(R.id.btnLedOff);
		btnLedOn.setOnClickListener(this);//ע�ᰴť�¼�������
		btnLedOff.setOnClickListener(this);//ע�ᰴť�¼�������
		imgLed=(ImageView)findViewById(R.id.imgLed);
		btnConnect=(Button)findViewById(R.id.btnConnect);
		btnDisconnect=(Button)findViewById(R.id.btnDisconnect);
		btnConnect.setOnClickListener(this);//ע�ᰴť�¼�������
		btnDisconnect.setOnClickListener(this);//ע�ᰴť�¼�������
		
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
	
	
	
	@Override  //��ť���¼�����
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnLedOn://led on�İ�ť�¼�����
			sendCmd("led_on");
			imgLed.setImageResource(R.drawable.led_on);
			
			
			
			
			break;
		case R.id.btnLedOff://led off�İ�ť�¼�����
			sendCmd("led_off");
			imgLed.setImageResource(R.drawable.led_off);
			break;
		case R.id.btnConnect://���ӿ����尴ť�¼�����
			
			connectServer();//�������ӿ�����������ķ���
			
			txtConnect.setVisibility(View.VISIBLE);//���ӱ�ǩ�ɼ�
//			txtConnect.setText("���ӳɹ�!");
			
			btnDisconnect.setEnabled(true);//�����Ͽ����ӵİ�ť
			btnLedOn.setEnabled(true);//����ledon��ť
			btnLedOff.setEnabled(true);//����ledoff��ť
			
			btnBeepOn.setEnabled(true);
			btnBeepOff.setEnabled(true);
			txtBeep.setVisibility(View.VISIBLE);
			imgLed.setVisibility(View.VISIBLE);
			
			
			txtDisconnect.setVisibility(View.GONE);//�Ͽ����ӱ�ǩ����
			break;
		case R.id.btnDisconnect://�Ͽ��������¼�����
			disConectServer();//���öϿ�������������ķ���
			txtDisconnect.setVisibility(View.VISIBLE);//�Ͽ����ӱ�ǩ�ɼ�
			txtDisconnect.setText("�Ͽ�����");
			btnLedOn.setEnabled(false);//����ledon��ť
			btnLedOff.setEnabled(false);//����ledoff��ť
			
			btnBeepOff.setEnabled(false);
			btnBeepOn.setEnabled(false);
			txtBeep.setVisibility(View.GONE);
			
			
			txtConnect.setVisibility(View.GONE);//���ӱ�ǩ����
			break;
			
		case R.id.btnBeepOn:
			sendCmd("beep_on");
			txtBeep.setText("��������");
			
			
			break;
		case R.id.btnBeepOff:
			sendCmd("beep_off");
			txtBeep.setText("��������");
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
			bw.write(cmd);//�����������������cmd����
			bw.newLine();//���һ�����з�   
			bw.flush();//ˢ��
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void disConectServer() {
		// �ر�io�� socket
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
		//����һ�����ȶԻ���
		final ProgressDialog dialog=new ProgressDialog(this);
		dialog.setTitle("����������ʾ");
		dialog.setMessage("��������Զ�̵Ŀ�����,���Ժ�...");
		dialog.show();//�������ȶԻ���
		
		
		//����������һ���߳�,��Ϊsocketͨ�Ż�������
		new Thread(){
			
			public void run() {
				
				//������Ϣ����,������handler��������Ϣ��
				Message msg=Message.obtain();

				try {
//					socket=new Socket("192.168.16.72",22222);
					socket=new Socket();//
					ipAddres=editIPAddress.getText().toString().trim();//ȥ�հ�
					SocketAddress remoteAddress=new InetSocketAddress(ipAddres,22222);
					
					
					socket.connect(remoteAddress,3000);//������������ʱ3��,��ʱ�����쳣
					//���������Ĵ���   connect()���ر�ʾ���ӷ������ɹ�
					
					System.out.println("�ͻ������ӷ������ɹ�!!!");
					
					//��socket��IO��
					is=socket.getInputStream();
					os=socket.getOutputStream();
					
					//��������������OutputStreamWriter��ʵ���ֽ�ת�ַ���,��BufferedWriterת����������ַ���
					bw=new BufferedWriter(new OutputStreamWriter(os));
					//���������������InputStreamReader��ʵ���ֽ�ת�ַ���,��BufferedReaderת����������ַ���
					br=new BufferedReader(new InputStreamReader(is));
					
					
					//��Ϊ�����߳��в��ܸ���UI  ����textview����������� handler������Ϣ
					msg.what=CONNECT_OK;//����Ϣ��������whatֵ
					hanlder.sendMessage(msg);//handler������Ϣ����msg  
					//handler�ͻ����handleMessage()����
					
					
					
					
				} catch (UnknownHostException e) {
					// �������������쳣�Ĵ���
					e.printStackTrace();
					//������Ϣ�����whatֵ
					msg.what=CONNECT_FAIL;
					hanlder.sendMessage(msg);//hadnler������Ϣ
					
				} catch (IOException e) {
					// �������������쳣�Ĵ���
					
					e.printStackTrace();
					//������Ϣ�����whatֵ
					msg.what=CONNECT_FAIL;
					hanlder.sendMessage(msg);//hadnler������Ϣ
				}//ip,port
				
				dialog.dismiss();//�رնԻ���
			}
		}.start();//�����߳�
		
	}
			
			

}
