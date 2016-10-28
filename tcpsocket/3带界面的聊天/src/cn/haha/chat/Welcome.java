package cn.haha.chat;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class Welcome extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }
    public void welcome_login(View v) {  
      	Intent intent = new Intent();
		intent.setClass(Welcome.this,Login.class);
		startActivity(intent);
		this.finish();
    	
      }  
    public void welcome_register(View v) {  
    	Toast.makeText(this, "正在建设中....", Toast.LENGTH_LONG).show();
      	/*Intent intent = new Intent();
		intent.setClass(Welcome.this,MainWeixin.class);
		startActivity(intent);
		this.finish();*/
      }  
   
}
