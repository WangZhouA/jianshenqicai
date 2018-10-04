package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.enitiy.Login;
import fitness_equipment.test.com.fitness_equipment.enitiy.Reigst;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static fitness_equipment.test.com.fitness_equipment.R.id.sms_btn;
import static fitness_equipment.test.com.fitness_equipment.R.id.sms_et_send;

/**
 * Created by 陈姣姣 on 2017/11/13.
 */

public class RegisterActivity extends BasActivity  implements TextWatcher {
    @BindView(R.id.header_left)
    ImageButton headerLeft;
    @BindView(R.id.header_text)
    TextView headerText;
    @BindView(R.id.header_haoyou)
    ImageButton headerHaoyou;
    @BindView(R.id.header_right)
    ImageButton headerRight;
    @BindView(R.id.header_right_msg)
    TextView headerRightMsg;
    @BindView(R.id.header_all)
    RelativeLayout headerAll;
    @BindView(R.id.sms_et_phone)
    EditText smsEtPhone;
    @BindView(R.id.sms_et_code)
    EditText smsEtCode;
    @BindView(R.id.sms_et_send)
    TextView smsEtSend;
    @BindView(R.id.sme_et_newPass)
    EditText smeEtNewPass;
    @BindView(R.id.sme_et_lock)
    ImageView smeEtLock;
    @BindView(R.id.sme_et_determine_number)
    EditText smeEtDetermineNumber;
    @BindView(R.id.sms_lin_repass)
    LinearLayout smsLinRepass;
    @BindView(R.id.sms_btn)
    Button smsBtn;

    TextView sms_textTyp;
    private  int flag = 0;
//    private OkHttpClient.Builder builder;
//    private OkHttpClient okHttpClient;

//    private PersistentCookieStore persistentCookieStore;

    private UserInfo userInfo;

    //定时器
    private CountDownTimer timer;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //成功获取验证码后
                case 0:


                    smsEtSend.setEnabled(false);
                    timer.start();

                    break;
                case 2:
                    showToast(getResources().getString(R.string.NogetCode));
                    break;
//                case 1:
//
//                    showToast(getResources().getString(R.string.reigst_sure));
//                    finish();
//
//                    break;
                case 3:
                    showToast(getResources().getString(R.string.Noreigst));
                    finish();

                    break;
            }
        }
    };


    @Override
    protected int getContentView() {
        return R.layout.activity_sms;
    }

    @Override
    protected void init() {

        smsEtPhone.addTextChangedListener(this);
        smeEtNewPass.addTextChangedListener(this);
        smsEtCode.addTextChangedListener(this);

        userInfo=new UserInfo(this);

        smsEtPhone.setHint(getResources().getString(R.string.phone_or_sms));
        smeEtNewPass.setHint(getResources().getString(R.string.input_pass));
        sms_textTyp= (TextView) findViewById(R.id.sms_textType);
        sms_textTyp.setVisibility(View.GONE);
        headerText.setText(getResources().getString(R.string.reigst));
        smsLinRepass.setVisibility(View.GONE);
        headerText.setTextColor(Color.parseColor("#18C49A"));

//        builder = new OkHttpClient.Builder();
//        persistentCookieStore = new PersistentCookieStore(getApplicationContext());
//        CookieJarImpl cookieJarImpl = new CookieJarImpl(persistentCookieStore);
//        builder.cookieJar(cookieJarImpl);
//        okHttpClient = builder.build();
//
        //密文显示
        if (flag==0) {
            smeEtNewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            flag=1;
        }



        //定时器
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //时间期间
                smsEtSend.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                //时间结束后
                smsEtSend.setText(R.string.get_code);
                smsEtSend.setEnabled(true);
            }
        };
    }

    @OnClick({R.id.header_left, sms_et_send, sms_btn,R.id.sme_et_lock})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.sme_et_lock:
                if (flag==1) {
                    smeEtNewPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    flag=0;
                }else {
                    smeEtNewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flag=1;
                }
                break;
            case sms_et_send:
                //有没网
                if (TureOrFalseNetwork()==true) {
                    //是否合法
                    if (isMobile(smsEtPhone.getText().toString()) == true || isEmail(smsEtPhone.getText().toString())) {

                        okHttpGetCode();
                    } else {
                        showToast(getResources().getString(R.string.true_number));
                    }
                }else {

                    showToast(getResources().getString(R.string.No_Internet_connection));
                }
                break;
            case sms_btn:
                //有没网
                if (TureOrFalseNetwork()==true) {
                    //是否合法
                    if (isMobile(smsEtPhone.getText().toString()) == true || isEmail(smsEtPhone.getText().toString())) {
                        if (!TextUtils.isEmpty(smsEtCode.getText().toString()) && smsEtCode.getText().toString().length() == 6) {
                            if (!TextUtils.isEmpty(smeEtNewPass.getText().toString()) && smeEtNewPass.getText().toString().length() >= 6) {
                                okokHttpReigst();
                            } else {
                                showCustomToast(getResources().getString(R.string.Nopassword));
                            }
                        } else {
                            showCustomToast(getResources().getString(R.string.NoCode));
                        }
                    } else {
                        showToast(getResources().getString(R.string.true_number));
                    }
                }else {

                    showToast(getResources().getString(R.string.No_Internet_connection));
                }

//                okHttpLogin();

                break;
        }
    }
    /**
     * 注册
     * */

    Reigst reigst ;
    private void okokHttpReigst() {

        final String reigstStr = StringUtils.REIGST;


        JSONObject jsonObject = new JSONObject();
        try {

            if (!user_num.contains("@")) {
                Log.i("----->msg","msg:"+ smsEtCode.getText().toString()+"telphone:"+smsEtPhone.getText().toString()+"password:"+smeEtNewPass.getText().toString());
                jsonObject.put("msg", smsEtCode.getText().toString());
                jsonObject.put("telphone", smsEtPhone.getText().toString());
                jsonObject.put("password", smeEtNewPass.getText().toString());
                userInfo.setUserInfo("Num","1");

            }else {
                Log.i("----->msg","msg:"+ smsEtCode.getText().toString()+"eMail:"+smsEtPhone.getText().toString()+"password:"+smeEtNewPass.getText().toString());
                jsonObject.put("msg", smsEtCode.getText().toString());
                jsonObject.put("eMail", smsEtPhone.getText().toString());
                jsonObject.put("password", smeEtNewPass.getText().toString());
                userInfo.setUserInfo("Num","2");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());


        final Request request = new Request.Builder()
                .url(reigstStr)
                .post(body).addHeader("cookie", cookic).

                        build();
        Log.i("---------->注册的cookic",cookic);


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("--->", "" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();

                        Log.i("--->注册", "" + result);

                    Gson gson =new Gson();
                    reigst = gson.fromJson(result, Reigst.class);
                    if (reigst.getCode().equals("1")){

                        //查询接口数据
                        okHttpLogin();
//                      showToast(reigst.getBody().getStatus());
                        Intent intent =new Intent(RegisterActivity.this,ReigstGreader.class);
                        startActivity(intent);
                        finish();
                    }else {
//                        showToast(reigst.getBody().getStatus());

                        Message www = handler.obtainMessage();
                        www.what = 3;
                        handler.sendMessage(www);
                    }

                } else {
                    Log.i("--->", "错误的修改信息" + response.body().string());
                    Message www = handler.obtainMessage();
                    www.what = 3;
                    handler.sendMessage(www);
                }
            }
        });
    }


    /**
     *  无奈的选择，
     * */

    Login  login;
    private void okHttpLogin() {

        final String reigstStr = StringUtils.LOGIN;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", smsEtPhone.getText().toString());
            jsonObject.put("password", smeEtNewPass.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        final Request request = new Request.Builder()
                .url(reigstStr)
                .post(body)
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
                    login =gson.fromJson(result,Login.class);
                    if (login.getCode().equals("1")){
                        int  id =login.getBody().getUser().getId();
                        Log.i("---->id",""+login.getBody().getUser().getId());
                        userInfo.setUserInfo("id",String.valueOf(id));
                        userInfo.setUserInfo("name",smsEtPhone.getText().toString());
                        userInfo.setUserInfo("password",smeEtNewPass.getText().toString());


                        Headers headers = response.headers();
                        List<String> cookies = headers.values("Set-Cookie");
                        Log.d("------>", "onResponse: "+cookies.size());
                        for (String str:cookies) {
                            if (str.startsWith("JSESSIONID")) {
                                //将sessionId保存到本地
                                Log.d("------->", "onResponse: " + str);
                            }
                            cookic=str;
                        }
                        String d = cookic;
                        String [] str =d.split(";");
                        System.out.println(str[0]);
                        cookic = str[0];
                        Log.d("------->123", "cookic: " + cookic);
                        userInfo.setUserInfo("cookie",cookic);


                        Intent intent =new Intent(RegisterActivity.this,ReigstGreader.class);
                        startActivity(intent);
                        finish();

                    }

                } else {
                    Log.i("--->", "错误的修改信息" + response.body().string());

                }
            }
        });
    }

    /**
     * 获取验证码
     * */

    String cookic;
    private void okHttpGetCode() {

        JSONObject jsonObject = new JSONObject();
        try {

            if (!smsEtPhone.getText().toString().contains("@")) {
                jsonObject.put("telphone",smsEtPhone.getText().toString() );
            }else {
                jsonObject.put("eMail", smsEtPhone.getText().toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        final Request request = new Request.Builder()
                .url(StringUtils.GET_CODE)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("--->", "" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.i("--->result", response.body().string());
                    Headers headers = response.headers();
                    List<String> cookies = headers.values("Set-Cookie");
                    Log.d("------>", "onResponse: "+cookies.size());
                    for (String str:cookies) {
                        if (str.startsWith("JSESSIONID")) {
                            //将sessionId保存到本地
                            Log.d("------->", "onResponse: " + str);
                        }
                        cookic=str;
                    }
                    String d = cookic;
                    String [] str =d.split(";");
                    System.out.println(str[0]);
                    cookic = str[0];
                    Log.d("------->123", "cookic: " + cookic);
                    userInfo.setUserInfo("cookie",cookic);

                    Message msg =new Message();
                    msg.what=0;
                    handler.sendMessage(msg);
                } else {
                    Log.i("--->", "错误的验证码信息" + response.body().string());
                    Message msg =new Message();
                    msg.what=2;
                    handler.sendMessage(msg);
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    String user_num;
    @Override
    public void afterTextChanged(Editable s) {
        //1.同时监听俩个输入框，监听长度
        if ( smeEtNewPass.length()>=6 && smsEtCode.length()==6) {
            user_num =smsEtPhone.getText().toString().trim();
            if (isMobile(user_num) == true  || isEmail(user_num)  ) {

                smsBtn.setEnabled(true);
                smsBtn.setBackgroundColor(Color.parseColor("#0E755C"));
            }else {
                smsBtn.setBackgroundColor(Color.parseColor("#BFE7DD"));
            }
        }else {
            smsBtn.setEnabled(false);
            smsBtn.setBackgroundColor(Color.parseColor("#BFE7DD"));
        }
    }


    //判断email格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

}
