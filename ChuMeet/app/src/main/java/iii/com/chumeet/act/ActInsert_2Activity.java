package iii.com.chumeet.act;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import iii.com.chumeet.R;

public class ActInsert_2Activity extends AppCompatActivity {
    private TextView tvActStart_show, tvActEnd_show, tvActRegisterStart_show, tvActRegisterEnd_show;
    private CheckBox cbSports, cbLearn, cbFood, cbArts, cbMovie, cbGame, cbOutdoors, cbPets, cbOthers;
    private String poi_1, poi_2, poi_3, poi_4, poi_5, poi_6, poi_7, poi_8, poi_9;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_insert2);

        dateTime();
        checkBox();
    }

    private void checkBox() {
        cbSports = (CheckBox) findViewById(R.id.cbSports);
        cbSports.setOnCheckedChangeListener(chkListener);
        cbLearn = (CheckBox) findViewById(R.id.cbLearn);
        cbLearn.setOnCheckedChangeListener(chkListener);
        cbFood = (CheckBox) findViewById(R.id.cbFood);
        cbFood.setOnCheckedChangeListener(chkListener);
        cbArts = (CheckBox) findViewById(R.id.cbArts);
        cbArts.setOnCheckedChangeListener(chkListener);
        cbMovie = (CheckBox) findViewById(R.id.cbMovie);
        cbMovie.setOnCheckedChangeListener(chkListener);
        cbGame = (CheckBox) findViewById(R.id.cbGame);
        cbGame.setOnCheckedChangeListener(chkListener);
        cbOutdoors = (CheckBox) findViewById(R.id.cbOutdoors);
        cbOutdoors.setOnCheckedChangeListener(chkListener);
        cbPets = (CheckBox) findViewById(R.id.cbPets);
        cbPets.setOnCheckedChangeListener(chkListener);
        cbOthers = (CheckBox) findViewById(R.id.cbOthers);
        cbOthers.setOnCheckedChangeListener(chkListener);

    }

    private CheckBox.OnCheckedChangeListener  chkListener = new CheckBox.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (cbSports.isChecked())
                poi_1 = "Sports";
            else
                poi_1 = "";
            if (cbLearn.isChecked())
                poi_2 = "Learning";
            else
                poi_2 = "";
            if (cbFood.isChecked())
                poi_3 = "Food and Drink";
            else
                poi_3 = "";
            if (cbArts.isChecked())
                poi_4 = "Arts";
            else
                poi_4 = "";
            if (cbMovie.isChecked())
                poi_5 = "Movie";
            else
                poi_5 = "";
            if (cbGame.isChecked())
                poi_6 = "Game";
            else
                poi_6 = "";
            if (cbOutdoors.isChecked())
                poi_7 = "Outdoors";
            else
                poi_7 = "";
            if (cbPets.isChecked())
                poi_8 = "Pets";
            else
                poi_8 = "";
            if (cbOthers.isChecked())
                poi_9 = "Others";
            else
                poi_9 = "";
        }
    };

    private void dateTime(){

        tvActStart_show = (TextView) findViewById(R.id.tvDatePicker_ActStart_show);
        tvActEnd_show = (TextView) findViewById(R.id.tvDatePicker_ActEnd_show);
        tvActRegisterStart_show = (TextView) findViewById(R.id.tvDatePicker_ActRegisterStart_show);
        tvActRegisterEnd_show = (TextView) findViewById(R.id.tvDatePicker_ActRegisterEnd_show);

        Button btNext = (Button) findViewById(R.id.btActInsert_next2);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String actStart = tvActStart_show.getText().toString();
                String actEnd = tvActEnd_show.getText().toString();
                String registerStart = tvActRegisterStart_show.getText().toString();
                String registerEnd = tvActRegisterEnd_show.getText().toString();

                Bundle bundle = ActInsert_2Activity.this.getIntent().getExtras();
                bundle.putString("poi_1", poi_1);
                bundle.putString("poi_2", poi_2);
                bundle.putString("poi_3", poi_3);
                bundle.putString("poi_4", poi_4);
                bundle.putString("poi_5", poi_5);
                bundle.putString("poi_6", poi_6);
                bundle.putString("poi_7", poi_7);
                bundle.putString("poi_8", poi_8);
                bundle.putString("poi_9", poi_9);
                bundle.putString("actStart", actStart);
                bundle.putString("actEnd", actEnd);
                bundle.putString("registerStart", registerStart);
                bundle.putString("registerEnd", registerEnd);

                Intent intent = new Intent(ActInsert_2Activity.this, ActInsert_3Activity.class);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        TextView tvActStart = (TextView) findViewById(R.id.tvDatePicker_ActStart);
        tvActStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTime("tvActStart");
            }
        });

        TextView tvActEnd = (TextView) findViewById(R.id.tvDatePicker_ActEnd);
        tvActEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTime("tvActEnd");
            }
        });

        TextView tvRegisterStart = (TextView) findViewById(R.id.tvDatePicker_ActRegisterStart);
        tvRegisterStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTime("tvRegisterStart");
            }
        });

        TextView tvRegisterEnd = (TextView) findViewById(R.id.tvDatePicker_ActRegisterEnd);
        tvRegisterEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTime("tvRegisterEnd");
            }
        });
    }

    private void setDateTime(String str){
        final String str2 = str;
        DatePickerDialog datePicker = new DatePickerDialog(ActInsert_2Activity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        int thisYear = calendar.get(Calendar.YEAR);
                        int thisMonth = calendar.get(Calendar.MONTH)+1;
                        int today = calendar.get(Calendar.DAY_OF_MONTH);

                        mYear = year;
                        if(mYear < thisYear || mYear > thisYear + 1){
                            Toast.makeText(ActInsert_2Activity.this, "無效年份設定", Toast.LENGTH_SHORT).show();
                            mYear = thisYear;
                        }
                        mMonth = month;
                        if(mMonth < thisMonth){
                            Toast.makeText(ActInsert_2Activity.this, "無效月份設定", Toast.LENGTH_SHORT).show();
                            mMonth = thisMonth;
                        }
                        mDay = day;
                        if(mDay < today){
                            Toast.makeText(ActInsert_2Activity.this, "無效日期設定", Toast.LENGTH_SHORT).show();
                            mDay = today;
                        }
                    }
                }, mYear, mMonth, mDay);

        TimePickerDialog timePicker = new TimePickerDialog (ActInsert_2Activity.this,
                new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute){
                        mHour = hour;
                        mMinute = minute;

                        switch (str2){
                            case "tvActStart":
                                updateDisplay_ActStart();
                                break;
                            case "tvActEnd":
                                updateDisplay_ActEnd();
                                break;
                            case "tvRegisterStart":
                                updateDisplay_RegisterStart();
                                break;
                            case "tvRegisterEnd":
                                updateDisplay_RegisterEnd();
                                break;
                        }
                    }
                }, mHour, mMinute, false);
        timePicker.show();
        datePicker.show();

    }

    private void updateDisplay_ActStart() {
        dateBuilder();
        tvActStart_show.setText(date);
    }

    private void updateDisplay_ActEnd() {
        dateBuilder();
        tvActEnd_show.setText(date);
    }

    private void updateDisplay_RegisterStart() {
        dateBuilder();
        tvActRegisterStart_show.setText(date);
    }

    private void updateDisplay_RegisterEnd() {
        dateBuilder();
        tvActRegisterEnd_show.setText(date);
    }

    private String dateBuilder(){
        date = String.valueOf(mYear) + "-" +
                pad(mMonth + 1) + "-" +
                pad(mDay) + " " +
                pad(mHour) + ":" +
                pad(mMinute);
        return date;
    }

    private String pad(int number) {
        if (number >= 10)
            return String.valueOf(number);
        else
            return "0" + String.valueOf(number);
    }



}

