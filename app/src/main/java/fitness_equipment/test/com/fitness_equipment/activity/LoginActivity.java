package fitness_equipment.test.com.fitness_equipment.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.MainActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.enitiy.Login;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import fitness_equipment.test.com.fitness_equipment.utils.HTTPRequest;
import fitness_equipment.test.com.fitness_equipment.utils.HTTPUtil;
import fitness_equipment.test.com.fitness_equipment.view.MyDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static fitness_equipment.test.com.fitness_equipment.http.StringUtils.LOGIN;

public class LoginActivity extends BasActivity implements TextWatcher {

    String openId;
    String md5 ="";
    @BindView(R.id.login_et_phone)
    EditText loginEtPhone;
    @BindView(R.id.login_et_pass)
    EditText loginEtPass;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.login_tv_reigst)
    TextView loginTvReigst;
    @BindView(R.id.login_im_xinlang)
    ImageView loginImXinlang;
    @BindView(R.id.login_im_qq)
    ImageView loginImQq;
    @BindView(R.id.login_im_weixin)
    ImageView loginImWeixin;
    EditText login_et_number;
    @BindView(R.id.longin_tv_repass)
    TextView longin_tv_repass;

    MyDialog builder;
    UserInfo userInfo;

    @BindView(R.id.IMG_dervice)
    ImageView IMGDervice;


    private ProgressDialog dialog;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                //登录失败了
                case 0:
                    showEerroDialog();
                    break;

                /***
                 *
                 * 第三方登录的未绑定
                 *
                 */
                case 2:
                    showDialog();
                    break;
                case 1:

                    /**
                     *  请求成功
                     * */
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LemonBubble.showRight(LoginActivity.this, getResources().getString(R.string.trueRequset), 1000);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    Log.i("------>openidssssssss","openid:"+userInfo.getStringInfo("openid"));
                                    if (!userInfo.getStringInfo("openid").equals("123456789")){
                                        okHttpGoToBinder();
                                    }
                                }

                            },1000);
                        }
                    }, 2000);
                    break;
            }

        }
    };


    /**
     *  来自于第三方的绑定信息
     * */
    private void okHttpGoToBinder() {

        final String reigstStr = StringUtils.THREE_LONGIN_BINDER;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("threeId",openId);
            jsonObject.put("type","qqId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        final Request request = new Request.Builder()
                .url(reigstStr)
                .post(body).addHeader("cookie",userInfo.getStringInfo("cookie"))
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
                    Log.i("--->第三方绑定", result);
                    /**
                     * 到这里为止说明 已经绑定成功了，这个时候就可以去情调openID L
                     * */
                    logOut("openid");

                } else {
                    Log.i("--->", "错误的修改信息" + response.body().string());

                }
            }
        });

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        userInfo = new UserInfo(this);
        if (userInfo.getStringInfo("name") != null && userInfo.getStringInfo("password") != null) {
            loginEtPhone.setText(userInfo.getStringInfo("name"));
            loginEtPass.setText(userInfo.getStringInfo("password"));
        }

        //设置监听器
        loginEtPhone.addTextChangedListener(this);
        loginEtPass.addTextChangedListener(this);
        dialog = new ProgressDialog(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#24242424"));

        }

        userInfo.setUserInfo("openid","123456789");
        Log.i("------>openidssssssss","openid:"+userInfo.getStringInfo("openid"));
    }




    Intent intent ;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }



    /**
     * 找回方式
     * */
    private void showMyDialog() {

        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.pass_find_type,null);
        builder = new MyDialog(LoginActivity.this,  view, R.style.MyDialog,0);
        builder.setCanceledOnTouchOutside(true);
        builder.setCancelable(true);
        TextView tvPhone = (TextView) view.findViewById(R.id.dialog_phone);
        TextView tvEmail = (TextView) view.findViewById(R.id.dialog_email);
        TextView tvCancel = (TextView) view.findViewById(R.id.dialog_cancel);

        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent= new Intent(LoginActivity.this, SMSActivity.class);
                startActivity(intent);
                builder.dismiss();
            }
        });



        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent= new Intent(LoginActivity.this, EmailActivity.class);
                startActivity(intent);
                builder.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        builder.show();

    }


    @OnClick({R.id.login_btn, R.id.login_tv_reigst, R.id.longin_tv_repass, R.id.login_im_xinlang, R.id.login_im_qq, R.id.login_im_weixin,R.id.IMG_dervice})
    public void onViewClicked(View view) {
        switch (view.getId()) {


            case R.id.IMG_dervice:
                startActivity(new Intent(this,MainActivity.class));


            case R.id.login_btn:
                showMyLoadingDialog();
                Login();

//                login();
                break;
            case R.id.login_tv_reigst:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);

                break;
            case R.id.longin_tv_repass:
                showMyDialog();
                break;
            case R.id.login_im_xinlang:
                showMyLoadingDialog();
                UMShareAPI.get(LoginActivity.this).doOauthVerify(LoginActivity.this,SHARE_MEDIA.SINA, authListener);
                break;
            case R.id.login_im_qq:
                showMyLoadingDialog();
//              UMShareAPI.get(LoginActivity.this).deleteOauth(LoginActivity.this, SHARE_MEDIA.QQ, authListener);
                UMShareAPI.get(LoginActivity.this).doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ, authListener);
                break;
            case R.id.login_im_weixin:
                showMyLoadingDialog();
                UMShareAPI.get(LoginActivity.this).doOauthVerify(LoginActivity.this,  SHARE_MEDIA.WEIXIN, authListener);
                break;
        }
    }


    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */

        @Override
        public void onComplete(SHARE_MEDIA platform, int action,  final Map<String, String> data) {
            Toast.makeText(LoginActivity.this, "成功了", Toast.LENGTH_SHORT).show();
            dissMissMyLoadingDialogTrue(LoginActivity.this);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    List<String> keys = new ArrayList<String>(data.keySet());
                    Collections.sort(keys);
                    for (String key : keys) {
                        System.out.println(key + " = " + data.get(key));
                        if (key.contains("openid")){
//                          676BDACB66C983C9B2CCBB7944138FA6
                            openId = data.get(key);
                            Log.i("----->openId",openId);
                        }
                    }
                    /**
                     *  成功后去拿 appkey
                     * */
                    getToken();
                }
            },1000);
        }
        /**
         *  成功后去拿 appkey
         * */

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(LoginActivity.this, "失败了"+t.toString(), Toast.LENGTH_SHORT).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {

            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_SHORT).show();
        }
    };




    /**
     *   QQ 登录
     * **/

    private void okQQHttPLogin() {

        final String reigstStr = StringUtils.LOGIN_TYPE_THREE;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("threeId",openId);
            Log.i("------->传参","qqId");
            jsonObject.put("Type","qq_id");
            Log.i("------->传参","123"+md5);
            jsonObject.put("loginKey",md5);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        final Request request = new Request.Builder()
                .url(reigstStr)
                .post(body).addHeader("cookie",userInfo.getStringInfo("cookie"))
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
                    Log.i("--->全部成功后去登录了", result);

                    /**
                     *       到这里就是最关键的步骤了。
                     *
                     *                1. 如果 code ==1  代表的是 已经有 QQ 已经绑定了账号了
                     *                 2. 如果为2  代表的是  没有绑定账号
                     *
                     *
                     * */

                    try {
                        JSONObject json = new JSONObject(result);
                        String code = json.getString("code");
                        // 1. 如果 code ==1  代表的是 已经有 QQ 已经绑定了账号了
                        if (code.equals("1")) {
                            logOut("openid");
                            Message msg =handler.obtainMessage();
                            msg.what=1;
                            handler.sendMessage(msg);


                        }else {
                            //  没有 绑定账号
                            Message msg =handler.obtainMessage();
                            msg.what=2;
                            handler.sendMessage(msg);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Log.i("--->", "错误的修改信息" + response.body().string());

                }
            }
        });




    }


    private  void   login(){

        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", loginEtPhone.getText().toString());
        params.put("password", loginEtPass.getText().toString());

        String str= HTTPRequest.Request(LOGIN, header, params, "POST", new HTTPUtil() {
            @Override
            public void doSome(String str) {
                Log.i("\n",str);
            }
        });
        loginEtPhone.setText(str);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (loginEtPhone.getText().toString().contains(userInfo.getStringInfo("name")) &&
                loginEtPass.getText().toString().contains(userInfo.getStringInfo("password"))) {
            loginBtn.setEnabled(true);
            loginBtn.setBackgroundColor(Color.parseColor("#0E755C"));

        }

    }

    Login login;
    String cookic;
    private void Login(){
        final String reigstStr = LOGIN;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", loginEtPhone.getText().toString());
            jsonObject.put("password", loginEtPass.getText().toString());

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
                        // 登录错误显示弹窗

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
                        Log.d("------->", "cookie:login " + str);
                        userInfo.setUserInfo("cookie",cookic);
                        userInfo.setUserInfo("name",loginEtPhone.getText().toString());
                        userInfo.setUserInfo("password",loginEtPass.getText().toString());
                        userInfo.setUserInfo("id",login.getBody().getUser().getId());


                        Message msg =handler.obtainMessage();
                        msg.what=1;
                        handler.sendMessage(msg);


                    }else {

                        // 登录错误显示弹窗
                        Message msg =handler.obtainMessage();
                        msg.what=0;
                        handler.sendMessage(msg);


                    }

                } else {
                    Log.i("--->", "错误的修改信息" + response.body().string());

                }
            }
        });


    }
    /**
     * 登录错误显示弹窗
     * */
    private void showEerroDialog() {

        LemonBubble.hide();
        View view =LayoutInflater.from(LoginActivity.this).inflate(R.layout.qiangzhi_xiaxian_layout,null);
        final MyDialog builder = new MyDialog(LoginActivity.this, 0, 0, view, R.style.MyDialog);
        builder.setCancelable(false);

        TextView for_tv_titles = (TextView) view.findViewById(R.id.for_tv_titles);
        TextView text_for_tv = (TextView) view.findViewById(R.id.text_for_tv);
        Button  btn_no_xian_ss= (Button) view.findViewById(R.id.btn_no_xian_ss);
        Button  btn_yes_xiaxian_ss= (Button) view.findViewById(R.id.btn_yes_xiaxian_ss);
        btn_no_xian_ss.setVisibility(View.GONE);
        for_tv_titles.setText(R.string.login_error);

        text_for_tv.setText(R.string.Error);
        //确认按钮
        btn_yes_xiaxian_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.dismiss();

            }
        });

        builder.show();


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
        if ( loginEtPass.length()>=6 ) {
            user_num =loginEtPhone.getText().toString().trim();
            if (isMobile(user_num) == true  || isEmail(user_num)  ) {

                loginBtn.setEnabled(true);
                loginBtn.setBackgroundColor(Color.parseColor("#0E755C"));
            }else {
                loginBtn.setBackgroundColor(Color.parseColor("#BFE7DD"));
            }
        }else {
            loginBtn.setEnabled(false);
            loginBtn.setBackgroundColor(Color.parseColor("#BFE7DD"));
        }
    }


    //判断email格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();

    }
    /**
     * 验证是否是这个app
     * */
    public void getToken(){

        final String reigstStr = StringUtils.PANDUAN_SHIFOU_APP;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("methodAs","APP");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        final Request request = new Request.Builder()
                .url(reigstStr)
                .post(body).addHeader("methodAs","APP")
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
                    try {
                        JSONObject json = new JSONObject(result);
                        String code = json.getString("code");

                        if (code.equals("1")) {
                            JSONObject bobyJson = json.getJSONObject("body");
                            String key =bobyJson.getString("key");

                            Log.i("-------->key",key);
                            md5= key;

                            //*************存 token
                            Headers headers = response.headers();
                            List<String> cookies = headers.values("Set-Cookie");
                            Log.d("------>", "onResponse: " + cookies.size());
                            for (String str : cookies) {
                                if (str.startsWith("JSESSIONID")) {
                                    //将sessionId保存到本地
                                    Log.d("------->", "onResponse: " + str);
                                }
                                cookic = str;
                            }
                            String d = cookic;
                            String[] str = d.split(";");
                            System.out.println(str[0]);
                            cookic = str[0];
                            Log.d("------->", "cookie:login " + str);
                            userInfo.setUserInfo("cookie", cookic);
                            userInfo.setUserInfo("openId", openId);


                            okQQHttPLogin();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Log.i("--->", "错误的修改信息" + response.body().string());

                }
            }
        });
    }



    protected void showDialog() {

        View view =LayoutInflater.from(this).inflate(R.layout.qiangzhi_xiaxian_layout,null);
        final MyDialog builder = new MyDialog(this, 0, 0, view, R.style.MyDialog);
        builder.setCancelable(false);
        Button btn_no_xian_ss= (Button) view.findViewById(R.id.btn_no_xian_ss);
        Button  btn_yes_xiaxian_ss= (Button) view.findViewById(R.id.btn_yes_xiaxian_ss);

        TextView  tv_title = (TextView) view.findViewById(R.id.for_tv_titles);
        TextView  tv_vis = (TextView) view.findViewById(R.id.text_for_tv);

        tv_title.setText(getResources().getString(R.string.number_No_binder));
        tv_vis.setVisibility(View.GONE);
        btn_no_xian_ss.setText(getResources().getString(R.string.have_number));
        btn_yes_xiaxian_ss.setText(getResources().getString(R.string.go_reigst));



        //  有账号，需要去绑定ID
        btn_no_xian_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *   去登录 这个时候  还是有openId的 ，需要用id 去绑定账号
                 * */
                Login();

                builder.dismiss();
            }
        });


        //确认按钮
        btn_yes_xiaxian_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("---->aaa","123"+userInfo.getStringInfo("aaa"));

                Intent inten =new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(inten);
                builder.dismiss();

            }
        });

        builder.show();
    }




    public void logOut(String aaa) {
        String USER_INFO = "userInfo";
        SharedPreferences sp = LoginActivity.this.getSharedPreferences(USER_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(aaa);
        editor.commit();
    }



}
