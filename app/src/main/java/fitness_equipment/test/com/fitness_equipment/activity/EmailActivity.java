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
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.enitiy.Reigst;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static fitness_equipment.test.com.fitness_equipment.R.id.sme_et_newPass;

/**
 * Created by 陈姣姣 on 2017/11/13.
 */

public class EmailActivity extends BasActivity  implements TextWatcher {
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
    @BindView(sme_et_newPass)
    EditText smeEtNewPass;
    @BindView(R.id.sme_et_lock)
    ImageView smeEtLock;
    @BindView(R.id.sme_et_determine_number)
    EditText smeEtDetermineNumber;
    @BindView(R.id.sms_lin_repass)
    LinearLayout smsLinRepass;
    @BindView(R.id.sms_btn)
    Button smsBtn;
    @BindView(R.id.sms_textType)
    TextView sms_textType;

    Intent intent;

    private int flag=0;
    private int flags=0;

    //定时器
    private CountDownTimer timer;


    private UserInfo userInfo;

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
                case 1:

                    showToast(getResources().getString(R.string.reigst_sure));
                    finish();

                    break;
                case 3:
                    showToast(getResources().getString(R.string.zhaohui_No));
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

        userInfo=new UserInfo(this);

        smsEtPhone.addTextChangedListener(this);
        smeEtNewPass.addTextChangedListener(this);
        smsEtCode.addTextChangedListener(this);
        smeEtDetermineNumber.addTextChangedListener(this);

        headerText.setTextColor(Color.parseColor("#19C49B"));
        headerText.setText(getResources().getString(R.string.pass_find_email));
        smsEtPhone.setHint(getResources().getString(R.string.intput_email_number));
        smeEtNewPass.setHint(getResources().getString(R.string.input_pass));
        smeEtNewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        smeEtDetermineNumber.setTransformationMethod(PasswordTransformationMethod.getInstance());


        //密文显示
        if (flag==0) {
            smeEtNewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            flag=1;
        }
        //密文显示
        if (flags==0) {
            smeEtDetermineNumber.setTransformationMethod(PasswordTransformationMethod.getInstance());
            flags=1;
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


    @OnClick({R.id.header_left, R.id.sms_et_send, R.id.sme_et_lock, R.id.sms_btn,R.id.sms_textType,R.id.ToEqualNumber})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.sms_et_send:

                //有没网
                if (TureOrFalseNetwork()==true) {
                    //是否合法
                    if (isEmail(smsEtPhone.getText().toString()) == true ) {

                        okHttpGetCode();
                    } else {
                        showToast(getResources().getString(R.string.true_number_p));
                    }
                }else {

                    showToast(getResources().getString(R.string.No_Internet_connection));
                }


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
            case R.id.sms_btn:


                //有没网
                if (TureOrFalseNetwork()==true) {
                    //是否合法
                    if (isEmail(smsEtPhone.getText().toString()) == true ) {
                        if (!TextUtils.isEmpty(smsEtCode.getText().toString()) && smsEtCode.getText().toString().length() == 6) {
                            if (!TextUtils.isEmpty(smeEtNewPass.getText().toString()) && smeEtNewPass.getText().toString().length() >= 6) {
                                if (smeEtDetermineNumber.getText().toString().equals(smeEtNewPass.getText().toString())) {
                                    okokHttpRepass();
                                }else{
                                    showCustomToast(getResources().getString(R.string.NopasswordTwo));
                                }
                            } else {
                                showCustomToast(getResources().getString(R.string.Nopassword));
                            }

                        } else {
                            showCustomToast(getResources().getString(R.string.NoCode));
                        }
                    } else {
                        showToast(getResources().getString(R.string.true_number_p));
                    }

                }else {

                    showToast(getResources().getString(R.string.No_Internet_connection));

                }



                break;
            case R.id.ToEqualNumber:

                if (flags==1) {
                    smeEtDetermineNumber.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    flags=0;
                }else {
                    smeEtDetermineNumber.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flags=1;
                }


                break;
            case R.id.sms_textType:

                intent=new Intent(this,SMSActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    Reigst reigst;
    private void okokHttpRepass() {

        final String reigstStr = StringUtils.PASS_FOR_PHONE;

        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        JSONObject jsonObject = new JSONObject();
        try {


            jsonObject.put("msg", smsEtCode.getText().toString());
            jsonObject.put("eMail", smsEtPhone.getText().toString());
            jsonObject.put("password", smeEtNewPass.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        final Request request = new Request.Builder()
                .url(reigstStr)
                .post(body).addHeader("cookie", cookic)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("--->", "" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.i("--->", "正确的修改信息" + response.body().string());
                    String result = response.body().string();
                    Gson gson =new Gson();
                    reigst = gson.fromJson(result, Reigst.class);
                    if (reigst.getCode().contains("1")){
                        userInfo.setUserInfo("name",smsEtPhone.getText().toString());
                        userInfo.setUserInfo("password",smeEtNewPass.getText().toString());
                        showToast(reigst.getBody().getStatus());
                        Intent intent =new Intent(EmailActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        showToast(reigst.getBody().getStatus());
                    }


                    Message www = handler.obtainMessage();
                    www.what = 1;
                    handler.sendMessage(www);

                } else {
                    Log.i("--->", "错误的修改信息" + response.body().string());

                    Message www = handler.obtainMessage();
                    www.what = 3;
                    handler.sendMessage(www);
                }
            }
        });




    }
    String cookic;
    private void okHttpGetCode() {
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("eMail",smsEtPhone.getText().toString());
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

    //判断email格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
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
        if ( smeEtNewPass.length()>=6 && smsEtCode.length()==6 && smeEtDetermineNumber.length()>=6) {
            user_num =smsEtPhone.getText().toString().trim();
            if (isEmail(user_num) == true   ) {
                smsBtn.setEnabled(true);
                smsBtn.setBackgroundColor(Color.parseColor("#0E755C"));
            }

        }else {

            smsBtn.setEnabled(false);
            smsBtn.setBackgroundColor(Color.parseColor("#BFE7DD"));
        }

    }
}
