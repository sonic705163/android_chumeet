package iii.com.chumeet.act;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import iii.com.chumeet.Common;
import iii.com.chumeet.R;
import iii.com.chumeet.fragment.GetImageTask;

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
        actCont = (TextView) findViewById(R.id.tvClubDetContent);
        actDate = (TextView) findViewById(R.id.tvActDetDate);
        actLoc = (TextView) findViewById(R.id.tvActDetLoc);
        acthost = (TextView) findViewById(R.id.tvActDetHost);

        swipeRefreshLayout =
                (SwipeRefreshLayout) findViewById(R.id.actDetailRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                showResults();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
//        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onStart(){
        super.onStart();
        showResults();
    }
    private void showResults() {
        String url = Common.URL + "ActServletAndroid";
        String startDate, endDate;
        actVO = (ActVO) getIntent().getSerializableExtra("actVO");

        int imageSize = getResources().getDisplayMetrics().widthPixels / 4;

        int id = actVO.getActID();
        try {
            new GetImageTask(url, id, imageSize, actImg).execute().get();
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }
        actName.setText(actVO.getActName());
        actCont.setText(actVO.getActContent());
        actLoc.setText(actVO.getActLocName());
        startDate = actVO.getActStartDate();
        endDate = actVO.getActEndDate();

        actDate.setText("Start:  " + startDate + "\nEnd:    " + endDate);

        Log.d(TAG, "showResults");
    }


}
