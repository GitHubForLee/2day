package com.zhongkai.redgreenapp.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.zhongkai.redgreenapp.R;
public class LogoAct extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logo);
		//�����������߳�
		Thread thread=new Thread(runnable);
		thread.start();
		
		
	}
	Handler handler=new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==1)
			{
				Intent intent=new Intent();
				LogoAct.this.startActivity(new Intent(LogoAct.this,LoginAct.class));
				LogoAct.this.finish();
			}
		}
	};
	
	@Override
	public void finish() {
		super.finish();
	}

	
	Runnable runnable=new Runnable() {
		@Override  //�߳�ִ�еĴ�������
		public void run() {	
			
			try {
				Thread.sleep(5000);//��ʱ5s
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//��handler������Ϣ
			 Message message=  handler.obtainMessage();
			 message.what=1;
			 message.sendToTarget();
		}
	};
	
	
	

}
