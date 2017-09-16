package iii.com.chumeet.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import iii.com.chumeet.HomeActivity;
import iii.com.chumeet.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
//切換到主頁面
    public void gotoHomeActivity(View v){
        startActivity(new Intent(this, HomeActivity.class));
    }

//切換到會員登入頁面
    public void gotoLogInActivity(View v){
        startActivity(new Intent(this, LogInActivity.class));
    }
}
