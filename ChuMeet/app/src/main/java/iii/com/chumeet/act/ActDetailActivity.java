package iii.com.chumeet.act;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import iii.com.chumeet.R;

public class ActDetailActivity extends AppCompatActivity {
    private static final String TAG = "ActDetailActivity";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView actImg;
    private TextView actName;
    private TextView actCont;
    private TextView actDate;
    private TextView actLoc;
    private TextView acthost;
    private ActVO actVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_detail);

        actImg = (ImageView) findViewById(R.id.ivActDetImg);
        actName = (TextView) findViewById(R.id.tvActDetName);
        actCont = (TextView) findViewById(R.id.tvActDetContent);
        actDate = (TextView) findViewById(R.id.tvActDetDate);
        actLoc = (TextView) findViewById(R.id.tvActDetLoc);
        acthost = (TextView) findViewById(R.id.tvActDetHost);
        showResults();
        Log.d(TAG, "onCreate");
    }

    private void showResults(){
        String startDate, endDate;

        actVO = (ActVO) getIntent().getSerializableExtra("actVO");
        actName.setText(actVO.getActName());
        actCont.setText(actVO.getActContent());
        actLoc.setText(actVO.getActLocName());

        startDate = actVO.getActStartDate();
        endDate = actVO.getActEndDate();

        actDate.setText("Start:  " + startDate + "\nEnd:    " + endDate);

        Log.d(TAG, "showResults");
    }

//    class ActDetTask extends AsyncTask<String, Integer, List<ActVO>>{
//        @Override
//        protected  void onPreExecute(){
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(ActDetailActivity.this);
//            progressDialog.setMessage("Loading...");
//            progressDialog.show();
//        }
//
//        @Override
//        protected List<ActVO> doInBackground(String... params){
//            String url = params[0];
//            String jsonIn;
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("action", getActId);
//        }
//    }
}
