package cn.haha.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChatActivity extends Activity implements OnClickListener,Runnable {
	/** Called when the activity is first created. */

	private Button mBtnSend;
	private Button mBtnBack;
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_xiaohei);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			//ǿ�����߳�ʹ���������
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
		
		
		// ����activityʱ���Զ����������
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		initView();

		initData();
		connection();//��������
		//�������߳�
        if(ok==true)
        new Thread(this).start();
	}

	

	public void initView() {
		mListView = (ListView) findViewById(R.id.listview);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mBtnBack = (Button) findViewById(R.id.btn_back);
		mBtnBack.setOnClickListener(this);

		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
	}

	private String[] msgArray = new String[] { "����", "�У����أ�", "��Ҳ��", "��˵��",
			"���������ð�����˵��", "զ��ȥ����Ӱ�أ�ʲô��Ƭ��", "̩̹��˺�", "��ϲ��....", };

	private String[] dataArray = new String[] { "2012-09-01 18:00",
			"2012-09-01 18:10", "2012-09-01 18:11", "2012-09-01 18:20",
			"2012-09-01 18:30", "2012-09-01 18:35", "2012-09-01 18:40",
			"2012-09-01 18:50" };
	private final static int COUNT = 8;

	public void initData() {
		for (int i = 0; i < COUNT; i++) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(dataArray[i]);
			if (i % 2 == 0) {
				entity.setName("�ܿ�");
				entity.setMsgType(true);
			} else {
				entity.setName("��˹");
				entity.setMsgType(false);
			}

			entity.setText(msgArray[i]);
			mDataArrays.add(entity);
		}

		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_send:
			send();
			break;
		case R.id.btn_back:
			finish();
			break;
		}
	}

	private void send() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(getDate());
			entity.setName("��˹");
			entity.setMsgType(false);
			entity.setText(contString);
			
			try {
				dos.writeUTF(contString);
				mDataArrays.add(entity);
				mAdapter.notifyDataSetChanged();

				mEditTextContent.setText("");

				mListView.setSelection(mListView.getCount() - 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	private String getDate() {
		Calendar c = Calendar.getInstance();

		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH));
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins);

		return sbBuffer.toString();
	}

	public void head_xiaohei(View v) { // ������ ���ذ�ť
		Intent intent = new Intent(ChatActivity.this, InfoXiaohei.class);
		startActivity(intent);
	}
	
	Socket sc;
	DataInputStream dis;
	DataOutputStream dos;
	String content;
	boolean ok=false;

	//������Ϣ,����UI
	Handler myhander=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			if (content.length() > 0) {
				ChatMsgEntity entity = new ChatMsgEntity();
				entity.setDate(getDate());
				entity.setName("�ܿ�");
				entity.setMsgType(true);
				entity.setText(content);

				mDataArrays.add(entity);
				mAdapter.notifyDataSetChanged();
				mListView.setSelection(mListView.getCount() - 1);
			}
			
		}
		
	};
	
	private void connection() {
		// TODO Auto-generated method stub
		sc=new Socket();
    	SocketAddress remoteAddr=new InetSocketAddress("192.168.7.102",911);//����localhost,ip=10.0.2.2
    	
    	try {
			sc.connect(remoteAddr,3000);//��ʱ����
			//���������Ӵ�IO��
			dis=new DataInputStream(sc.getInputStream());
			dos=new DataOutputStream(sc.getOutputStream());
			ok=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.showDialog("���ӷ���������");
			e.printStackTrace();
		}
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				content=dis.readUTF();
				System.out.println("server:"+content);
				
				//������Ϣ��,����Ϣ������UI
				Message msg=Message.obtain();
				this.myhander.sendMessage(msg);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void showDialog(String msg){
    	new AlertDialog.Builder(this).setTitle("֪ͨ").setMessage(msg).setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		} ).show();
    	
    	
    	
    }
	
	
	
	
	
}