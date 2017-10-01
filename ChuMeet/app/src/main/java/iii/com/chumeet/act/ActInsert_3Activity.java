package iii.com.chumeet.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import iii.com.chumeet.R;

public class ActInsert_3Activity extends AppCompatActivity {
    private EditText etContent;
    private ActVO actVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_insert3);

        findViews();
    }

    private void findViews() {
        etContent = (EditText) findViewById(R.id.etActInsert_content);

        Button btNext = (Button) findViewById(R.id.btActInsert_final);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = ActInsert_3Activity.this.getIntent().getExtras();
                String actName = bundle.getString("actName");
                String locationName = bundle.getString("locationName");
                String actStart = bundle.getString("actStart");
                String actEnd = bundle.getString("actEnd");
                String registerStart = bundle.getString("registerStart");
                String registerEnd = bundle.getString("registerEnd");



                Intent intent = new Intent(ActInsert_3Activity.this, ActDetailActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("actVO", actVO);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });
    }
}
