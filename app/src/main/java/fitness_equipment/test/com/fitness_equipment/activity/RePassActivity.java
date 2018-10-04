package fitness_equipment.test.com.fitness_equipment.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RePassActivity extends BasActivity implements TextWatcher {


    @BindView(R.id.header_left)
    ImageButton headerLeft;
    @BindView(R.id.header_left_text)
    TextView headerLeftText;
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
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.editText3)
    EditText editText3;
    @BindView(R.id.btn_determine)
    Button btnDetermine;


    private Handler handler =new Handler(){

        @Override
        public void handleMessage(Message msg) {

                switch (msg.what){
                    case 0:

                        /**
                         *  请求成功
                         * */
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LemonBubble.showRight(RePassActivity.this, getResources().getString(R.string.trueRequset), 1000);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        finish();
                                    }
                                },1000);
                            }
                        }, 2000);

                        break;
                    case 1:

                        /**
                         *  请求失败
                         * */
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LemonBubble.showError(RePassActivity.this, getResources().getString(R.string.NoRequest), 1000);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        showToast(getResources().getString(R.string.NopasswordTwo));

                                    }
                                },1000);
                            }
                        }, 2000);

                        break;
                    case 2:

                        /**
                         *  请求失败
                         * */
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LemonBubble.showError(RePassActivity.this, getResources().getString(R.string.oldPass), 1000);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
//                                        showToast(getResources().getString(R.string.NopasswordTwo));

                                    }
                                },1000);
                            }
                        }, 2000);

                        break;
                }
        }
    };


    @Override
    protected int getContentView() {
        return R.layout.activity_re_pass;
    }

    @Override
    protected void init() {
        headerText.setText(R.string.makeHelp);
        headerText.setTextColor(Color.parseColor("#19C49B"));

        editText.addTextChangedListener(this);
        editText2.addTextChangedListener(this);
        editText3.addTextChangedListener(this);

        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        editText3.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }


    @OnClick({R.id.header_left, R.id.btn_determine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.btn_determine:
                showMyLoadingDialog();
                okHttpQueryOldPassword();
                break;
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //1.同时监听俩个输入框，监听长度
        if ( editText.length()>=6 && editText2.length()>=6  && editText3.length()>=6)  {
            btnDetermine.setEnabled(true);
            btnDetermine.setBackgroundColor(Color.parseColor("#0E755C"));
        }else {
            btnDetermine.setEnabled(false);
            btnDetermine.setBackgroundColor(Color.parseColor("#BFE7DD"));
        }
    }


    /**
    *  先验证旧密码是否正确
    */
    private void okHttpQueryOldPassword () {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",userInfo.getIntInfo("id"));
            jsonObject.put("password", editText.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        final Request request = new Request.Builder()
                .url(StringUtils.QUERY_OLD_PASSWORD)
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
                    Log.i("----->旧密码",result);
                    try {
                        JSONObject jsonObject =new JSONObject(result);
                        String code = jsonObject.getString("code");
                        if (code.equals("1")){
                            if (editText2.getText().toString().equals(editText3.getText().toString())){
                                okHttpToRePass();
                            }else {
                                Message msg =handler.obtainMessage();
                                msg.what=1;
                                handler.sendMessage(msg);

                            }

                        }else {
                            Message msg =handler.obtainMessage();
                            msg.what=2;
                            handler.sendMessage(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
     /**
      * 修改密码
      * */
    private void okHttpToRePass() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",userInfo.getIntInfo("id"));
            jsonObject.put("password", editText2.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        final Request request = new Request.Builder()
                .url(StringUtils.RE_OLD_PASS)
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
                    Log.i("-----",result);
                    try {
                        JSONObject jsonObject =new JSONObject(result);
                        String code = jsonObject.getString("code");
                        if (code.equals("1")){
                            Message msg =handler.obtainMessage();
                            msg.what=0;
                            handler.sendMessage(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
