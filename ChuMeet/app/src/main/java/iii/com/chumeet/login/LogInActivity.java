package iii.com.chumeet.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import iii.com.chumeet.Common;
import iii.com.chumeet.R;

public class LogInActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();

        setResult(RESULT_CANCELED);
    }

    private  void findViews(){
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        Button btLogin = (Button) findViewById(R.id.btLogIn);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if(email.length() <= 0 || password.length() <=0){
                    showMessage(R.string.msg_InvalidUserOrPassword);
                    return;
                }

                if(isUserValid(email, password)){
                    SharedPreferences pref = getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
                    pref.edit()
                            .putBoolean("login", true)
                            .putString("email", email)
                            .putString("password", password)
                            .apply();
                    setResult(RESULT_OK);
                    finish();
                }else{
                    showMessage(R.string.msg_InvalidUserOrPassword);
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        SharedPreferences pref = getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
        boolean login = pref.getBoolean("login", false);
        if(!login){
            String email = pref.getString("email","");
            String password = pref.getString("password", "");
            if(isUserValid(email, password)){
                setResult(RESULT_OK);
                finish();
            }else{
                showMessage(R.string.msg_InvalidUserOrPassword);
            }
        }
    }

    private void showMessage(int msgResId) {
        tvMessage.setText(msgResId);
    }

    private boolean isUserValid(String email, String password) {

        return email.equals("a");
    }




}
