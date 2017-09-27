package iii.com.chumeet.act;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
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

import iii.com.chumeet.Common;
import iii.com.chumeet.HomeActivity;
import iii.com.chumeet.R;
import iii.com.chumeet.Task.GetImageTask;
import iii.com.chumeet.mem.MemVO;

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

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fmMap);

        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onStart() {
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
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        actName.setText(actVO.getActName());
        actCont.setText(actVO.getActContent());
        actLoc.setText(actVO.getActLocName());
        acthost.setText(actVO.getMemName());
        startDate = actVO.getActStartDate();
        endDate = actVO.getActEndDate();

        actDate.setText("Start:  " + startDate + "\nEnd:    " + endDate);

        Log.d(TAG, "showResults");
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        map.setTrafficEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        if(actVO == null){
            Common.showToast(this, R.string.msg_NoActsFound);
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

            Intent intent = new Intent(ActDetailActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        }
        return true;
    }
}
