package iii.com.chumeet.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import iii.com.chumeet.Common;
import iii.com.chumeet.R;
import iii.com.chumeet.act.ActVO;

public class FindFragment extends Fragment {
    private static final String TAG = "FindFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvActs;
    private Toolbar toolbar ;

//onCreateView是创建的时候调用
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_find, container, false);

        swipeRefreshLayout =
                (SwipeRefreshLayout) view.findViewById(R.id.findRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                showAllActs();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        rvActs = (RecyclerView) view.findViewById(R.id.rvActs);
        rvActs.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rvActs.setLayoutManager(
//                new StaggeredGridLayoutManager(
//                        1, StaggeredGridLayoutManager.HORIZONTAL));

        return view ;
    }

//onViewCreated是在onCreateView后被触发的事件，前后关系
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = (Toolbar) getView().findViewById(R.id.toolbar_find);
        toolbar.setTitle("Find");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


    }

//onStart运行时间位于onViewCreated之后
    @Override
    public void onStart() {
        super.onStart();
        showAllActs();
    }

    private void showAllActs(){
        if(Common.networkConnected(getActivity())){
            String url = Common.URL + "ActServletAndroid";
            List<ActVO> actVOs = null;
            try{
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "getAll");
                String jsonOut = jsonObject.toString();
                String jsonIn = new MyTask(url, jsonOut).execute().get();
                Log.d(TAG, jsonIn);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<ActVO>>(){}.getType();
                actVOs = gson.fromJson(jsonIn, listType);
            }catch (Exception e){
                Log.e(TAG, e.toString());
            }
            if(actVOs == null || actVOs.isEmpty()){
                Common.showToast(getActivity(), R.string.msg_NoActsFound);
            }else{
                rvActs.setAdapter(new ActsRecyclerViewAdapter(getActivity(), actVOs));
            }
        }else{
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }



    private class ActsRecyclerViewAdapter extends RecyclerView.Adapter<ActsRecyclerViewAdapter.MyViewHolder>{
        private LayoutInflater layoutInflater;
        private List<ActVO> actVOs;
        private int imageSize;

        ActsRecyclerViewAdapter(Context context, List<ActVO> actVOs){
            layoutInflater = LayoutInflater.from(context);
            this.actVOs = actVOs;
            /* 螢幕寬度除以4當作將圖的尺寸 */
            imageSize = getResources().getDisplayMetrics().widthPixels / 4;
        }

        @Override
        public int getItemCount(){
            return actVOs.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.itemview_act, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int postion){
        final ActVO actVO = actVOs.get(postion);
            String url = Common.URL + "ActServletAndroid";
            int id = actVO.getActID();
            new GetImageTask(url, id, imageSize, myViewHolder.ivActImage).execute();
            myViewHolder.tvActName.setText(actVO.getActName());
            myViewHolder.tvActDate.setText(actVO.getActStartDate());
            myViewHolder.ivActImage.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){

                }
            });

//            myViewHolder.itemView.setOnLongClickListener();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivActImage;
            TextView tvActName, tvActDate;

            MyViewHolder(View itemView) {
                super(itemView);
                ivActImage = (ImageView) itemView.findViewById(R.id.ivActImage);
                tvActName = (TextView) itemView.findViewById(R.id.tvActName);
                tvActDate = (TextView) itemView.findViewById(R.id.tvActDate);
            }
        }
    }

//    private void switchFragment(Fragment fragment) {
//        FragmentTransaction fragmentTransaction =
//                getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.body, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }
}