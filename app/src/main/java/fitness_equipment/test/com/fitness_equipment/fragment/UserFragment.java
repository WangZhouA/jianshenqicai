package fitness_equipment.test.com.fitness_equipment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import fitness_equipment.test.com.fitness_equipment.MyApplication;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.activity.AboutActivity;
import fitness_equipment.test.com.fitness_equipment.activity.HealthRecordsActivity;
import fitness_equipment.test.com.fitness_equipment.activity.LoginActivity;
import fitness_equipment.test.com.fitness_equipment.activity.SettingActivity;
import fitness_equipment.test.com.fitness_equipment.activity.TimingControlActivity;
import fitness_equipment.test.com.fitness_equipment.activity.UserSetActivity;
import fitness_equipment.test.com.fitness_equipment.enitiy.Login;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.interfaces.OnUpdateUserTabTextListener;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import fitness_equipment.test.com.fitness_equipment.view.CircleImageView;
import fitness_equipment.test.com.fitness_equipment.view.MyDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 陈姣姣 on 2017/11/14.
 */

public class UserFragment extends Fragment implements View.OnClickListener{


    View view;
    //头像
    CircleImageView circleImageView;
    //退出
    TextView btn_exit;
    Intent intent;
    //更新Ui 接口
    OnUpdateUserTabTextListener onUpdateUserTabTextListener;


    LinearLayout linear_setting;
    LinearLayout linear_Health;
    LinearLayout linear_about;
    LinearLayout linear_tixing;
    LinearLayout linMsg;


    UserInfo userInfo;

    TextView textViewName;
    TextView ldid_tv;


    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0:

                  if (!TextUtils.isEmpty(name)){
                      textViewName.setText(name);
                  }else {

                      ldid = ldid.replaceAll("3","*");
                      ldid = ldid.replaceAll("4","*");
                      textViewName.setText(ldid);
                  }
                    ldid = ldid.replaceAll("3","*");
                    ldid = ldid.replaceAll("4","*");
                    ldid_tv.setText(ldid);


                    if (heading!=null){
                        StringUtils.showImage(getActivity(),StringUtils.GET_PHOTO+heading,R.mipmap.male_head2,R.mipmap.male_head2,circleImageView);
                    }

                    break;
            }
        }
    };


    public void setOnUpdateUserTabTextListener(OnUpdateUserTabTextListener onUpdateUserTabTextListener) {
        this.onUpdateUserTabTextListener = onUpdateUserTabTextListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.user_fragment,null);
        MyApplication.getInstance().addActyToList(getActivity());
        circleImageView= (CircleImageView) view.findViewById(R.id.user_photo);
        btn_exit= (TextView) view.findViewById(R.id.btn_exit);
        linear_setting= (LinearLayout) view.findViewById(R.id.linear_setting);
        linear_Health= (LinearLayout) view.findViewById(R.id.linear_Health);
        linear_about= (LinearLayout) view.findViewById(R.id.linear_about);
        textViewName= (TextView) view.findViewById(R.id.textView10);
        ldid_tv= (TextView) view.findViewById(R.id.ldid_tv);
        linear_tixing= (LinearLayout) view.findViewById(R.id.linear_tixing);
        linMsg= (LinearLayout) view.findViewById(R.id.lin_msg);
        /**
         * 第一次进去去跟新tab
         * */
        onUpdateUserTabTextListener.setOnUpdateUserTabTextListener();

        setListeners();

        userInfo=new UserInfo(getActivity());

         okHttpQueryUserMsg();

        return view;
    }



    private void setListeners() {
        btn_exit.setOnClickListener(this);
        circleImageView.setOnClickListener(this);
        linear_setting.setOnClickListener(this);
        linear_Health.setOnClickListener(this);
        linear_about.setOnClickListener(this);
        linear_tixing.setOnClickListener(this);
        linMsg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit:
                showDialog();
                break;
            case R.id.lin_msg:

                Toast.makeText(getActivity(), ""+  getActivity().getResources().getString(R.string.nuli), Toast.LENGTH_SHORT).show();

                break;

            case R.id.user_photo:
                intent=new Intent(getActivity(), UserSetActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_setting:
                intent=new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_Health:
                intent=new Intent(getActivity(), HealthRecordsActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_about:
                intent=new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_tixing:
                intent=new Intent(getActivity(), TimingControlActivity.class);
                startActivity(intent);
                break;


        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
         if (!hidden){
             //跟新
             onUpdateUserTabTextListener.setOnUpdateUserTabTextListener();
             /***
              *
              *   查询用户数据
              */
             okHttpQueryUserMsg();
         }

    }
    /***
     *
     *   查询用户数据
     */
    Login Login;
    String name;
    String ldid;
    String heading;
    private void okHttpQueryUserMsg() {
        OkHttpClient client; client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        JSONObject jsonObject = new JSONObject();
        try {

            Log.i("----->id","123"+userInfo.getIntInfo("id"));
            jsonObject.put("id",userInfo.getIntInfo("id"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Log.d("------->", " jsonobject " + ""+jsonObject.toString());

        Log.d("------->", "cookie:challenge " + userInfo.getStringInfo("cookie"));
        final Request request = new Request.Builder()
                .url(StringUtils.QUERY_USER_DATE)
                .post(body).addHeader("cookie", userInfo.getStringInfo("cookie")).
                        build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("--->", "" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();
                    Log.i("----->查询用户信息",result);
                    try {
                        JSONObject jsonObject =new JSONObject(result);
                        JSONObject twoJsonOject =jsonObject.getJSONObject("body");
                        JSONObject itemJson =twoJsonOject.getJSONObject("Userinfo");
                        ldid = itemJson.getString("ldid");
                        heading= itemJson.getString("heading");
                        name=itemJson.getString("niken");
                        Message msg =handler.obtainMessage();
                        msg.what=0;
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });




    }

    private void showDialog() {

        View view =LayoutInflater.from(getActivity()).inflate(R.layout.qiangzhi_xiaxian_layout,null);
        final MyDialog builder = new MyDialog(getActivity(), 0, 0, view, R.style.MyDialog);
        builder.setCancelable(false);
        Button btn_no_xian_ss= (Button) view.findViewById(R.id.btn_no_xian_ss);
        Button  btn_yes_xiaxian_ss= (Button) view.findViewById(R.id.btn_yes_xiaxian_ss);

        //取消按钮
        btn_no_xian_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });


        //确认按钮
        btn_yes_xiaxian_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent  =new Intent(getActivity(), LoginActivity.class);
                getActivity().finish();
                OkHttpExit();
            }
        });

        builder.show();
    }

    private void OkHttpExit() {

        OkHttpClient client;
        client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        JSONObject jsonObject = new JSONObject();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        final Request request = new Request.Builder()
                .url(StringUtils.EXIT_LOGIN)
                .post(body).addHeader("cookie", userInfo.getStringInfo("cookie")).
                        build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("--->", "" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();
                    Log.i("----->查询用户信息", result);


                }
            }
        });
    }
}
