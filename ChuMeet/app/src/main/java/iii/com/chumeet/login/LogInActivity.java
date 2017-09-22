package iii.com.chumeet.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import iii.com.chumeet.Common;
import iii.com.chumeet.HomeActivity;
import iii.com.chumeet.R;
import iii.com.chumeet.Task.MyTask;

import static iii.com.chumeet.Common.networkConnected;
import static iii.com.chumeet.Common.showToast;







public class LogInActivity extends AppCompatActivity {
    private final static String TAG = "LogInActivity";
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvMessage;
    private MemVO memVO = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();

//防止使用者亂寫
        setResult(RESULT_CANCELED);
    }

    private void findViews(){
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
//如果登入成功
//就儲存起來
                if(isUserValid(email, password)){
                    SharedPreferences pref = getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
                    pref.edit()
                            .putBoolean("login", true)
                            .putString("email", email)
                            .putString("password", password)
                            .apply();
                    setResult(RESULT_OK);



                    Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("memVO", memVO);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }else{
                    showMessage(R.string.msg_InvalidUserOrPassword);
                }
            }
        });
    }

//如果在其他設備修改密碼
//先檢查一下密碼
    @Override
    protected void onStart(){
        super.onStart();
//        SharedPreferences pref = getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
//        boolean login = pref.getBoolean("login", false);
//        if(login){
//            String email = pref.getString("email","");
//            String password = pref.getString("password", "");
//            if(isUserValid(email, password)){
//                setResult(RESULT_OK);
//                finish();
//            }else{
//                showMessage(R.string.msg_InvalidUserOrPassword);
//            }
//        }
    }

    private void showMessage(int msgResId) {
        tvMessage.setText(msgResId);
    }

    private boolean isUserValid(String email, String password) {
        boolean answer = false;

        if(networkConnected(this)){
            String url = Common.URL + "LoginServletAndroid";

            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("Email", email);
                jsonObject.addProperty("Password", password);
                String jsonOut = jsonObject.toString();
                String jsonIn = new MyTask(url, jsonOut).execute().get();
//                memVO = new LoginTask(url, email, password).execute().get();
                Gson gson = new Gson();
                memVO = gson.fromJson(jsonIn, MemVO.class);

            } catch (Exception e){
                Log.e(TAG, e.toString());
            }
            if(memVO != null){
                answer = true;
            }else {
                answer = false;
            }
        }else{
            showToast(this, R.string.msg_NoNetwork);
        }

        return answer;
    }

//Task
//    class LoginTask extends AsyncTask<String, Integer, MemVO> {
//        private final static String TAG = "LoginTask";
//        String url, email, password;
//
//        LoginTask(String url, String email, String password) {
//            this.url = url;
//            this.email = email;
//            this.password = password;
//        }
//
//        @Override
//        protected MemVO doInBackground(String... params) {
//            url = params[0];
//            email = params[1];
//            password = params[2];
//
//            String jsonIn;
//
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("Email", email);
//            jsonObject.addProperty("Password", password);
//            try {
//                jsonIn = getRemoteData(url, jsonObject.toString());
//            } catch (IOException e) {
//                Log.e(TAG, e.toString());
//                return null;
//            }
//            Gson gson = new Gson();
//            memVO = gson.fromJson(jsonIn, MemVO.class);
//
//            return memVO;
//        }
//
//
//        //連線取資料
//        private String getRemoteData(String url, String jsonOut) throws IOException {
//            StringBuilder jsonIn = new StringBuilder();
//            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
//            con.setDoInput(true); // allow inputs
//            con.setDoOutput(true); // allow outputs
//            con.setUseCaches(false); // do not use a cached copy
//            con.setRequestMethod("POST");
//            con.setRequestProperty("charset", "UTF-8");
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
//            bw.write(jsonOut);
//            Log.d(TAG, "outStr: " + jsonOut);
//            bw.close();
//
//            int responseCode = con.getResponseCode();
//
//            if (responseCode == 200) {
//                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                String line;
//                while ((line = br.readLine()) != null) {
//                    jsonIn.append(line);
//                }
//            } else {
//                Log.d(TAG, "response code: " + responseCode);
//            }
//            con.disconnect();
//            Log.d(TAG, "inStr: " + jsonIn);
//            return jsonIn.toString();
//        }
//
//    }

}


