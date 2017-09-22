package iii.com.chumeet.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import static iii.com.chumeet.Common.showToast;
import iii.com.chumeet.Common;
import iii.com.chumeet.R;
import iii.com.chumeet.Task.MyTask;

import static iii.com.chumeet.Common.networkConnected;

public class SignUpActivity extends AppCompatActivity {
    private final static String TAG = "SignUpActivity";
    private EditText edCheckEmail;
    private TextView tvCheckEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViews();
    }

    private void findViews() {
        tvCheckEmail = (TextView) findViewById(R.id.tvCheckEmail);
        edCheckEmail = (EditText) findViewById(R.id.etCheckMemEmail);
        Button btnCheckEmail = (Button) findViewById(R.id.btnCheckEmail);
        Button btSet = (Button) findViewById(R.id.btSet);
        btSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edCheckEmail.setText("hahs");
            }
        });
        btnCheckEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edCheckEmail.getText().toString().trim();

                if (email.length() <= 0) {
                    showMessage(R.string.msg_InvalidCheckEmail);
                    return;
                }
                if (isEmailUsable(email)) {
                    showMessage(R.string.msg_CheckEmailOK);

                }else{
                    showMessage(R.string.msg_CheckEmailRepeat);

                }
            }
        });
    }


    private void showMessage(int msgResId) {
        tvCheckEmail.setText(msgResId);
    }

    private boolean isEmailUsable(String email) {
            boolean answer = false;

            if(networkConnected(this)){
                String url = Common.URL + "SignupServletAndroid";

                try {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("action", "checkEmail");
                    jsonObject.addProperty("Email", email);
                    String jsonOut = jsonObject.toString();
                    String jsonIn = new MyTask(url, jsonOut).execute().get();

                    Gson gson = new Gson();
                    String ans = gson.fromJson(jsonIn, String.class);
                    if(ans.equals("repeat")){
                        answer = false;
                    }else{
                        answer = true;
                    }
                } catch (Exception e){
                    Log.e(TAG, e.toString());
                }

            }else{
                showToast(this, R.string.msg_NoNetwork);
            }

            return answer;
            }
}
