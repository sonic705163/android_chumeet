package iii.com.chumeet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import iii.com.chumeet.R;


public class ProfileFragment extends Fragment {

    Toolbar toolbar ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = (Toolbar) getView().findViewById(R.id.toolbar_profile);
        toolbar.setTitle("Profile");
//        setHasOptionsMenu(true);
//        toolbar.setTitle("Parent Fragment");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

//即先clear()一下, 這樣按鈕就只有Fragment中設置的自己的了, 不會有Activity中的按鈕
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar_profile_settings, menu);
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//
//        setHasOptionsMenu(true);
////设置导航图标、添加菜单点击事件要在setSupportActionBar方法之后
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.action_settings:
//                        break;
//                }
//                return true;
//            }
//        });
//    }

}