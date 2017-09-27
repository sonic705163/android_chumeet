package iii.com.chumeet.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iii.com.chumeet.R;


public class GoingFragment extends Fragment {

    private Toolbar toolbar ;

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = (Toolbar) getView().findViewById(R.id.toolbar_going);
        toolbar.setTitle("You're going");

//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_going, container, false);
    }




}
