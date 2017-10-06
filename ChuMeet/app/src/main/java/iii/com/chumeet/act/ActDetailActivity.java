package iii.com.chumeet.act;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import iii.com.chumeet.Common;
import iii.com.chumeet.R;
import iii.com.chumeet.Task.GetImageTask;
import iii.com.chumeet.Task.MyTask;
import iii.com.chumeet.home.HomeActivity;
import iii.com.chumeet.mem.MemVO;

import static iii.com.chumeet.Common.networkConnected;
import static iii.com.chumeet.Common.showToast;
import static java.lang.Integer.parseInt;


public class ActDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "ActDetailActivity";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView actImg;
    private TextView actName;
    private TextView actCont;
    private TextView actDate;
    private TextView actLoc;
    private TextView acthost;
    private ActVO actVO;
    private MemVO memVO;
    private GoogleMap map;
    private Integer actID;
    private Button btnJoin;
    private Bundle bundle;
    private Integer status = null;

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


        btnJoin = (Button) findViewById(R.id.btnJoinAct);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Common.URL + "ActServletAndroid";
                String jsonIn = null;
                ActMemVO actMemVO = null;

                SharedPreferences pref = getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
                String memID = pref.getString("id", "");

                try {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("action", "getActMem");          //一直點會不會有問題?
                    jsonObject.addProperty("id", actVO.getActID());
                    jsonObject.addProperty("memId", parseInt(memID));
                    String jsonOut = jsonObject.toString();

                    jsonIn = new MyTask(url, jsonOut).execute().get();

                    Gson gson = new Gson();
                    actMemVO = gson.fromJson(jsonIn, ActMemVO.class);

                }catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
                if(actMemVO != null) {
                    status = actMemVO.getActMemStatus();
                }

                if(status == null) {                                        //第一次加入
                    try {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "joinAct");
                        jsonObject.addProperty("actMemStatus", 2);
                        jsonObject.addProperty("id", actID);
                        jsonObject.addProperty("memId", parseInt(memID));
                        String jsonOut = jsonObject.toString();

                        jsonIn = new MyTask(url, jsonOut).execute().get();

                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                    if (jsonIn != null) {
                        btnJoin.setText("退出活動");
                        memFmStart();
                    } else {
                        Log.d(TAG, "加入失敗");
                    }
                }else{
                    switch (status) {
                        case 1:

                            break;
                        case 2:
                            try{
                                JsonObject jsonObject = new JsonObject();
                                jsonObject.addProperty("action", "ActMemStatusChange");
                                jsonObject.addProperty("actMemStatus", 6);
                                jsonObject.addProperty("id", actID);
                                jsonObject.addProperty("memId", parseInt(memID));
                                String jsonOut = jsonObject.toString();

                                jsonIn = new MyTask(url, jsonOut).execute().get();

                            }catch (Exception e){
                                Log.e(TAG, e.toString());
                            }
                            if (jsonIn != null) {
                                btnJoin.setText("加入活動");
                                memFmStart();
                            } else {
                                Log.d(TAG, "退出失敗");
                            }
                            break;
                        case 3:
                            btnJoin.setText("你不可能在這");
                            break;
                        case 4:
                            btnJoin.setText("你不可能進得來");
                            break;
                        case 5:
                            btnJoin.setText("加入活動");
                            break;
                        case 6:
                            try {
                                JsonObject jsonObject = new JsonObject();
                                jsonObject.addProperty("action", "ActMemStatusChange");
                                jsonObject.addProperty("actMemStatus", 2);
                                jsonObject.addProperty("id", actID);
                                jsonObject.addProperty("memId", parseInt(memID));
                                String jsonOut = jsonObject.toString();

                                jsonIn = new MyTask(url, jsonOut).execute().get();

                            } catch (Exception e) {
                                Log.e(TAG, e.toString());
                            }

                            if (jsonIn != null) {
                                btnJoin.setText("退出活動");
                                memFmStart();
                            } else {
                                Log.d(TAG, "加入失敗");
                            }
                            break;
                    }
                }
            }
        });



        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fmMap);

        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        bundle = this.getIntent().getExtras();
        actVO =  (ActVO) bundle.getSerializable("actVO");

        //先確定一下你有沒有加入過
        checkJoined();

        showResults();

    }

    private void checkJoined() {
        SharedPreferences pref = getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
        String memID = pref.getString("id", "");

        ActMemVO actMemVO = null;

        if (networkConnected(this)) {
            String url = Common.URL + "ActServletAndroid";

            try{
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "getActMem");          //先找ActMem有沒有資料
                jsonObject.addProperty("id", actVO.getActID());
                jsonObject.addProperty("memId", parseInt(memID));
                String jsonOut = jsonObject.toString();

                String jsonIn = new MyTask(url, jsonOut).execute().get();

                Gson gson = new Gson();
                actMemVO = gson.fromJson(jsonIn, ActMemVO.class);

                if(actMemVO != null){
                    status = actMemVO.getActMemStatus();        //有資料時取actMemStatus//
                    switch (status){                            //1=hoder, 2=joined member 3=invited 4=banned 5=tracking 6=leave
                        case 1:
                            btnJoin.setText("解散活動");
                            break;
                        case 2:
                            btnJoin.setText("退出活動");
                            break;
                        case 3:
                            btnJoin.setText("你不可能在這");
                            break;
                        case 4:
                            btnJoin.setText("你不可能進得來");
                            break;
                        case 5:
                            btnJoin.setText("加入活動");
                            break;
                        case 6:
                            btnJoin.setText("加入活動");
                            break;

                    }
                }
            }catch (Exception e){
                Log.e(TAG, e.toString());
            }
            memFmStart();
        }else{
            showToast(this, R.string.msg_NoNetwork);
        }

    }

    private void memFmStart(){
        bundle.putInt("ActID", actVO.getActID());
        MemFragment fragment = new MemFragment();

        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fmActDetail, fragment).commit();
    }


//    private void isJoined() {
//        SharedPreferences pref = getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
//        String memID = pref.getString("id", "");
//
//        if(networkConnected(this)){
//            String url = Common.URL + "ActServletAndroid";
//            List<Mem_ActMemVO> mem_ActMemVOs = null;
//            try{
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("action", "getActMem");
//                jsonObject.addProperty("id", actID);
//                String jsonOut = jsonObject.toString();
//
//                String jsonIn = new MyTask(url, jsonOut).execute().get();
//
//                Gson gson = new Gson();
//                Type listType = new TypeToken<List<Mem_ActMemVO>>(){}.getType();
//                mem_ActMemVOs = gson.fromJson(jsonIn, listType);
//            }catch (Exception e){
//                Log.e(TAG, e.toString());
//            }
//            if(mem_ActMemVOs == null || mem_ActMemVOs.isEmpty()){
//                showToast(this, R.string.msg_NoActsFound);
//            }else{
//                for(Mem_ActMemVO mem : mem_ActMemVOs){
//                    String id =  mem.getMemID().toString();
//                    Integer stauts = mem.getActMemStatus();
//
//                    if(id.equals(memID)){
//                        if(stauts==2){
//                            btnJoin.setText("退出活動");
//                        }else if(stauts==1){
//                            btnJoin.setText("取消活動");
//                        }
//                    }
//                }
//            }
//        }else{
//            showToast(this, R.string.msg_NoNetwork);
//        }
//    }


    private void showResults() {
        String url = Common.URL + "ActServletAndroid";
        String startDate, endDate;


        int imageSize = getResources().getDisplayMetrics().widthPixels / 4;

        actID = actVO.getActID();
        try {
            new GetImageTask(url, actID, imageSize, actImg).execute().get();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        actName.setText(actVO.getActName());
        actCont.setText(actVO.getActContent());
        actLoc.setText(actVO.getActLocName());
//        acthost.setText(actVO.getMemName());
        startDate = actVO.getActStartDate();
        endDate = actVO.getActEndDate();

        actDate.setText("Start:  " + startDate + "\nEnd:    " + endDate);

        Log.d(TAG, "showResults");
    }


    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        map.setTrafficEnabled(true);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        if(actVO == null){
            showToast(this, R.string.msg_NoActsFound);
        }else{
            showMap(actVO);
        }
    }

    private void showMap(ActVO actVO){
        LatLng position = new LatLng(actVO.getActLat(), actVO.getActLong());
        String snippet = getString(R.string.col_Name) + ":" + actVO.getActName() + "\n" +
                getString(R.string.col_Address) + ":" + actVO.getActAdr();

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .zoom(9)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory
                .newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);

        map.addMarker(new MarkerOptions()
                .position(position)
                .title(actVO.getActName())
                .snippet(snippet));

//        map.setInfoWindowAdapter(new MyInfoWindowAdapter(this, actVO));

    }


    //監聽返回鍵點擊事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();

        }
        return true;
    }
}
