package iii.com.chumeet.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import iii.com.chumeet.Task.GetImageTask;
import iii.com.chumeet.Task.MyTask;
import iii.com.chumeet.act.ActDetailActivity;
import iii.com.chumeet.act.ActInsertActivity;
import iii.com.chumeet.act.ActVO;

import static iii.com.chumeet.Common.networkConnected;
import static iii.com.chumeet.Common.showToast;


public class FindFragment extends Fragment {
    private static final String TAG = "FindFragment";
//    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvActs;
    private Toolbar toolbar ;

//onCreateView是创建的时候调用
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_find, container, false);

//        swipeRefreshLayout =
//                (SwipeRefreshLayout) view.findViewById(R.id.findRefresh);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(true);
//                showAllActs();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });

        rvActs = (RecyclerView) view.findViewById(R.id.rvActs);
//        rvActs.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvActs.setLayoutManager(
                new StaggeredGridLayoutManager(
                        1, StaggeredGridLayoutManager.HORIZONTAL));

        return view ;
    }

//onViewCreated是在onCreateView后被触发的事件，前后关系
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = (Toolbar) getView().findViewById(R.id.toolbar_find);
        toolbar.setTitle("Find");
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        //一旦调用((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //就会导致ActivityonCreateOptionsMenu()方法的调用, 而Activity会根据其中Fragment是否设置了setHasOptionsMenu(true)来调用Fragment的
        //onCreateOptionsMenu()方法, 调用顺序是树形的, 按层级调用, 中间如果有false则跳过.

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        //设置导航图标、添加菜单点击事件要在setSupportActionBar方法之后
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_insert_act:
                        Intent intent = new Intent(getActivity(), ActInsertActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    //即先clear()一下, 這樣按鈕就只有Fragment中設置的自己的了, 不會有Activity中的按鈕
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar_menu_find, menu);
    }



//onStart运行时间位于onViewCreated之后
    @Override
    public void onStart() {
        super.onStart();
        showAllActs();
    }

    private void showAllActs(){
        if(networkConnected(getActivity())){
            String url = Common.URL + "ActServletAndroid";
            List<ActVO> actVOs = null;
            try{
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "getAll");
                String jsonOut = jsonObject.toString();
                String jsonIn = new MyTask(url, jsonOut).execute().get();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<ActVO>>(){}.getType();
                actVOs = gson.fromJson(jsonIn, listType);
            }catch (Exception e){
                Log.e(TAG, e.toString());
            }
            if(actVOs == null || actVOs.isEmpty()){
                showToast(getActivity(), R.string.msg_NoActsFound);
            }else{
                rvActs.setAdapter(new ActsRecyclerViewAdapter(getActivity(), actVOs));
            }
        }else{
            showToast(getActivity(), R.string.msg_NoNetwork);
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
            View itemView = layoutInflater.inflate(R.layout.item_view_act, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int postion){
            final ActVO actVO = actVOs.get(postion);
            String url = Common.URL + "ActServletAndroid";
            int id = actVO.getActID();

            new GetImageTask(url, id, imageSize, myViewHolder.ivActImg).execute();

            myViewHolder.tvActName.setText(actVO.getActName());
            myViewHolder.tvActDate.setText(actVO.getActStartDate());
            myViewHolder.ivActImg.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){

                    Intent intent = new Intent(getActivity(), ActDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("actVO", actVO);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

//            myViewHolder.itemView.setOnLongClickListener();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivActImg;
            TextView tvActName, tvActDate;

            MyViewHolder(View itemView) {
                super(itemView);
                ivActImg = (ImageView) itemView.findViewById(R.id.ivActImg);
                tvActName = (TextView) itemView.findViewById(R.id.tvActName);
                tvActDate = (TextView) itemView.findViewById(R.id.tvActDate);
            }
        }
    }
}