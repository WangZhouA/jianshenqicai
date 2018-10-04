package fitness_equipment.test.com.fitness_equipment.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.activity.MonmentListActivity;
import fitness_equipment.test.com.fitness_equipment.adapter.BangDanAdapter;
import fitness_equipment.test.com.fitness_equipment.enitiy.BangDan;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 陈姣姣 on 2017/12/2.
 */

public class BicycleDayPaiHangbangFragment extends Fragment {


    private ListView listViews;
    private BangDanAdapter adapter;
    private UserInfo userInfo;

    private List<BangDan.BodyBean.ListBean>lists;

    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:

                    adapter=new BangDanAdapter(lists,getActivity());
                    listViews.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    break;
                case 1:


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (bangDan.getBody().getList().size()>0) {
                                dissMissMyLoadingDialogTrue();
                                lists.addAll(bangDan.getBody().getList());
                                Message msg2 = handler.obtainMessage();
                                msg2.what = 0;
                                handler.sendMessage(msg2);
                            }else {
//                                dissMissMyLoadingDialogFalse();
                            }
                        }
                    },1000);

                    break;
            }
        }
    };




    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_daypaihangbang_bicyvle, null);
        listViews= (ListView) view.findViewById(R.id.listviews);
        userInfo=new UserInfo(getActivity());
        lists=new ArrayList<>();

        IntentFilter IntentFilter =new IntentFilter();
        IntentFilter.addAction("DELETE");
        IntentFilter.addAction("ADD");
        getActivity().registerReceiver(broadcastReceiver,IntentFilter);

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

      getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();

            showMyLoadingDialog();
            if (MonmentListActivity.getNowTime() != null) {
                Query(MonmentListActivity.getNowTime());
            }

    }

    BangDan bangDan;
    private void Query(String time ){


        OkHttpClient client;
        client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        final String reigstStr = StringUtils.BANGDAN_QUERY;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("time",time);
            jsonObject.put("typeId", "1");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        final Request request = new Request.Builder()
                .url(reigstStr)
                .post(body).addHeader("cookie", userInfo.getStringInfo("cookie"))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("--->", "" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();
                    Log.i("---->result",result);
                    lists.clear();
                    Gson gson =new Gson();
                    bangDan = gson.fromJson(result,BangDan.class);
                    if (bangDan.getCode().equals("1")) {
                        Message msg1 = handler.obtainMessage();
                        msg1.what = 1;
                        handler.sendMessage(msg1);
                    }
                } else {
                    Log.i("--->", "错误的修改信息" + response.body().string());

                }
            }
        });

    }
//
    public  void  showMyLoadingDialog(){
        LemonBubble.getRoundProgressBubbleInfo()
                .setTitle("Loading...").show(getContext(),5000);
    }

    public  void    dissMissMyLoadingDialogTrue( ){


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LemonBubble.showRight(getContext(),getActivity().getResources().getString(R.string.trueRequset), 2000);
            }
        }, 2000);
    }

    public  void dissMissMyLoadingDialogFalse(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LemonBubble.showError(getContext(), getActivity().getResources().getString(R.string.No_data), 2000);
            }
        }, 2000);
    }



    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();
                if (action.contains("DELETE")) {
                    String deleteTime = intent.getStringExtra("DELETE");
                    showMyLoadingDialog();
                    Query(deleteTime);
                }
                if (action.contains("ADD")) {

                    showMyLoadingDialog();
                    Toast.makeText(getActivity(), ""+MonmentListActivity.getNowTime(), Toast.LENGTH_SHORT).show();
                    Query(MonmentListActivity.getNowTime());
                }
        }
    };


}
