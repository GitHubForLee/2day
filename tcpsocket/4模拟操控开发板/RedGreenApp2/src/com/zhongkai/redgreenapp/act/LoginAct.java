package com.zhongkai.redgreenapp.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhongkai.redgreenapp.R;


public class LoginAct extends Activity implements View.OnClickListener{
	Button btnLogin,btnCancel;
	EditText editUser,editPass;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		btnLogin=(Button)findViewById(R.id.btnLogin);
		btnCancel=(Button)findViewById(R.id.btnCancel);
		btnLogin.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		editUser=(EditText)findViewById(R.id.editUser);
		editPass=(EditText)findViewById(R.id.editPass);
		
		
		
	}




	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btnLogin:
			String name=editUser.getText().toString();
			String pwd=editPass.getText().toString();
			
			if("rose".equals(name)&&"123".equals(pwd)){
				Toast.makeText(this,"你登陆信息\n 用户名:"+name+"密码:"+pwd+"\n恭喜你登陆成功!", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(this,MainAct.class);
				intent.putExtra("username", name);
				startActivity(intent);//启动一个Activity
				finish();
				
				
			}else{
				Toast.makeText(this,"对比起,用户密码错误!",Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btnCancel:
			finish();
			break;

		default:
			break;
		}
	}
	

}
