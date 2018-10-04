package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static fitness_equipment.test.com.fitness_equipment.R.string.gender;

public class UserReigstMessageActivity extends BasActivity {


    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_area)
    EditText etArea;
    @BindView(R.id.last_btn)
    Button lastBtn;
    @BindView(R.id.next_btn)
    Button nextBtn;

    @BindView(R.id.gender_user_img)
    ImageView gender_user_img;

    private UserInfo userInfo;







    private Handler handler =new Handler(){

        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case 1:
                  if (userInfo.getStringInfo("Num").contains("1")){

                      etPhone.setText(telphone);
                      etPhone.setEnabled(false);
                  }else {
                      etEmail.setText(email);
                      etEmail.setEnabled(false);
                  }


                   break;
           }
        }
    };






    @Override
    protected int getContentView() {
        return R.layout.activity_user_reigst_message;
    }

    @Override
    protected void init() {

        userInfo=new UserInfo(this);
        Intent intent =getIntent();
        if (intent.getStringExtra("Gender")!=null){
            if (intent.getStringExtra("Gender").contains("girl"))  {
                gender_user_img.setImageResource(R.mipmap.female_head);
            }else {
                gender_user_img.setImageResource(R.mipmap.male_head);
            }
        }


        OkHttpQuery();



    }


    String email;
    String telphone;

    private void OkHttpQuery() {

        OkHttpClient client;
        client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        JSONObject jsonObject = new JSONObject();
        try {


            jsonObject.put("id", userInfo.getStringInfo("id"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

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
                    Log.i("----->查询用户信息", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject twoJsonOject = jsonObject.getJSONObject("body");
                        JSONObject itemJson = twoJsonOject.getJSONObject("Userinfo");
                        telphone = itemJson.getString("telphone");
                        Log.i("--------->telphone","123"+telphone);
                        email = itemJson.getString("email");
                        Log.i("--------->email","123"+email);
                        Message msg = handler.obtainMessage();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }


    Intent intent;
    @OnClick({R.id.last_btn, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.last_btn:
                intent=new Intent(this,StatureActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.next_btn:

                if (!TextUtils.isEmpty(etName.getText().toString())) {


                    if (!TextUtils.isEmpty(etPhone.getText().toString())) {

                        if (!TextUtils.isEmpty(etEmail.getText().toString())) {

                            if (!TextUtils.isEmpty(etArea.getText().toString())) {

                                try {
                                    ReUser();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }else {
                                showToast(getResources().getString(R.string.inputNoNull));
                            }

                        }else {
                            showToast(getResources().getString(R.string.inputNoNull));
                        }
                    }else {
                        showToast(getResources().getString(R.string.inputNoNull));
                    }
                }else {
                    showToast(getResources().getString(R.string.inputNoNull));
                }



                break;
        }
    }

    private  void  ReUser() throws  Exception {


        final String reigstStr = StringUtils.RESET_USER;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",userInfo.getStringInfo("id"));
            jsonObject.put("niken", etName.getText().toString());
            jsonObject.put("telphone", etPhone.getText().toString());
            jsonObject.put("eMail", etEmail.getText().toString());
            jsonObject.put("address", etArea.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        Log.d("------->", "cookie:challenge " + userInfo.getStringInfo("cookie"));
        final Request request = new Request.Builder()
                .url(reigstStr)
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
                    try {
                        JSONObject object = new JSONObject(result);
                        String code = object.getString("code");
                        if (code.equals("1")) {
                            intent = new Intent(UserReigstMessageActivity.this, LoginActivity.class);
                            intent.putExtra("Gender", gender);
                            startActivity(intent);
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
