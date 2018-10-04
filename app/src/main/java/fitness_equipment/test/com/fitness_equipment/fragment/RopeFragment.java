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
import android.widget.TextView;

import com.google.gson.Gson;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fitness_equipment.test.com.fitness_equipment.MyApplication;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.activity.QueryHistoryActivity;
import fitness_equipment.test.com.fitness_equipment.enitiy.HistoryDay;
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
 * Created by 陈姣姣 on 2017/11/17.
 */

public class RopeFragment extends Fragment {


    View view;
    @BindView(R.id.tv_name_km)
    TextView tvNameKm;
    @BindView(R.id.tv_number_km)
    TextView tvNumberKm;
    @BindView(R.id.tv_number_time)
    TextView tvNumberTime;
    @BindView(R.id.tv_number_kical)
    TextView tvNumberKical;
    Unbinder unbinder;
    UserInfo userInfo;
    @BindView(R.id.tv_km)
   TextView tvKm;

    private Handler handler =new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0:

                    /**
                     *  请求成功
                     * */


                    if (calorie != null) {
                        tvNumberKical.setText(calorie);
                    } else {
                        tvNumberKical.setText("0");
                    }

                    if (kilometre != null) {
                        tvNumberKm.setText(kilometre);
                    } else {

                        tvNumberKm.setText("0");
                    }

                    if (speed != null) {
                        tvNumberTime.setText(speed);
                    } else {
                        tvNumberTime.setText("0");
                    }

                    break;
                case 1:

                    /**
                     *  请求成功
                     * */
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LemonBubble.showRight(getActivity(), getResources().getString(R.string.trueRequset), 1000);
                        }
                    }, 2000);

                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.week_fragment, null);
        MyApplication.getInstance().addActyToList(getActivity());

        unbinder = ButterKnife.bind(this, view);
        userInfo=new UserInfo(getActivity());

        IntentFilter IntentFilter =new IntentFilter("TOUPDATE");
        getActivity().registerReceiver(broadcastReceiver,IntentFilter);




        return view;
    }
    HistoryDay history;
    String calorie;
    String kilometre;
    String times;
    String heartrate;
    String speed;
    private void okHttpQueryDayDate() {


        OkHttpClient client;
        client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        final String reigstStr = StringUtils.QUERY_ROPE_DAY;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("time", QueryHistoryActivity.getNowTime());
            jsonObject.put("typeId", "2");
            jsonObject.put("uid", String.valueOf(userInfo.getIntInfo("id")));

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
                    Gson gson =new Gson();
                    history=gson.fromJson(result,HistoryDay.class);
                    if (history.getCode().equals("1")){
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                        if (history.getBody().getCountlist().size()>0) {
                            //卡路里
                            calorie = history.getBody().getCountlist().get(0).getCalorie();
                            //公里
                            kilometre = history.getBody().getCountlist().get(0).getKilometre();
                            //时间
                            speed = history.getBody().getCountlist().get(0).getSpeed();
                            Message msg1 = new Message();
                            msg1.what = 0;
                            handler.sendMessage(msg1);
                        }
                    }

                } else {
                    Log.i("--->", "错误的修改信息" + response.body().string());

                }
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        showMyLoadingDialog();
        okHttpQueryDayDate();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    public  void  showMyLoadingDialog(){
        LemonBubble.getRoundProgressBubbleInfo()
                .setTitle("Loading...").show(getActivity(),5000);
    }

    private  BroadcastReceiver  broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action =intent.getAction();
            if (action.contains("TOUPDATE")){
                showMyLoadingDialog();
                okHttpQueryDayDate();
            }
        }
    };
}
