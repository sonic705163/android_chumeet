package iii.com.chumeet.act;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import iii.com.chumeet.Task.GetImageTask;
import iii.com.chumeet.Task.MyTask;
import iii.com.chumeet.mem.MemVO;

import static iii.com.chumeet.Common.networkConnected;
import static iii.com.chumeet.Common.showToast;


public class MemFragment extends Fragment{
    private final static String TAG = "MemFragment";
    private RecyclerView rvMems;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mem, container, false);

        rvMems = (RecyclerView) view.findViewById(R.id.rvMems);
        rvMems.setLayoutManager(new LinearLayoutManager(getActivity() ,LinearLayoutManager.HORIZONTAL, false));



        return view ;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        showAll();
    }

    private void showAll(){
//        SharedPreferences pref = getActivity().getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
//        String memID = pref.getString("id", "");
//        int id = parseInt(memID);
        Bundle bundle = getArguments();
        int actID =  bundle.getInt("ActID");


        if(networkConnected(getActivity())){
            String url = Common.URL + "ActServletAndroid";
            List<MemVO> memVOs = null;
            try{
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "getActMem");
                jsonObject.addProperty("id", actID);
                String jsonOut = jsonObject.toString();
                String jsonIn = new MyTask(url, jsonOut).execute().get();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<MemVO>>(){}.getType();
                memVOs = gson.fromJson(jsonIn, listType);
            }catch (Exception e){
                Log.e(TAG, e.toString());
            }
            if(memVOs == null || memVOs.isEmpty()){
                showToast(getActivity(), R.string.msg_NoActsFound);
            }else{
                rvMems.setAdapter(new ActsRecyclerViewAdapter(getActivity(), memVOs));
            }
        }else{
            showToast(getActivity(), R.string.msg_NoNetwork);

        }
    }




    private class ActsRecyclerViewAdapter extends RecyclerView.Adapter<ActsRecyclerViewAdapter.MyViewHolder>{
        private LayoutInflater layoutInflater;
        private List<MemVO> memVOs;
        private int imageSize;

        ActsRecyclerViewAdapter(Context context, List<MemVO> memVOs){
            layoutInflater = LayoutInflater.from(context);
            this.memVOs = memVOs;
            /* 螢幕寬度除以4當作將圖的尺寸 */
            imageSize = getResources().getDisplayMetrics().widthPixels / 4;
        }

        @Override
        public int getItemCount(){
            return memVOs.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.item_view_act, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int postion){
            final MemVO memVO = memVOs.get(postion);
            String url = Common.URL + "MemServletAndroid";
            int id = memVO.getMemID();

            new GetImageTask(url, id, imageSize, myViewHolder.ivActImg).execute();

            myViewHolder.tvActName.setText(memVO.getMemName());
//            myViewHolder.tvActDate.setText(memVO.getMemStatus());
//            myViewHolder.ivActImg.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View view){
//
//                    Intent intent = new Intent(getActivity(), ActDetailActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("actVO", memVO);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//            });
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