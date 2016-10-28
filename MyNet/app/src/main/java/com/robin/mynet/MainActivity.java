package com.robin.mynet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doButton(View view){
        switch (view.getId()){
            case R.id.btnSocket:
                doSocket();
                break;
            case R.id.btnsocket2:
                doSocket2();
        }

    }

    private void doSocket2() {
        startActivity(new Intent(this,SocketAct2.class));
    }

    private void doSocket() {
        startActivity(new Intent(this,SocketAct.class));
    }
}
