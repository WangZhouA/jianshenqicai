package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

/**
 * Created by 陈姣姣 on 2017/11/16.
 */

public class SetUserMessageActivity extends BasActivity {
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

    Intent intent;
    @BindView(R.id.delet)
    ImageView delet;
    @BindView(R.id.user_input)
    EditText userInput;

    private int flag;

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
                              LemonBubble.showRight(SetUserMessageActivity.this, getResources().getString(R.string.trueRequset), 1000);
                              new Handler().postDelayed(new Runnable() {
                                  @Override
                                  public void run() {

                                      Intent intent =new Intent("SHUAXIN");
                                      sendBroadcast(intent);

                                      finish();
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
        return R.layout.activity_setusermessage;
    }

    @Override
    protected void init() {

        headerLeft.setVisibility(View.GONE);
        headerLeftText.setVisibility(View.VISIBLE);
        headerLeftText.setText(getResources().getString(R.string.cancel));
        headerRightMsg.setVisibility(View.VISIBLE);
        headerRightMsg.setText(getResources().getString(R.string.save));
        headerLeftText.setTextColor(Color.parseColor("#19C49B"));
        headerText.setTextColor(Color.parseColor("#19C49B"));
        headerRightMsg.setTextColor(Color.parseColor("#19C49B"));
        headerLeftText.setTextSize(20);
        headerText.setTextSize(20);
        headerRightMsg.setTextSize(20);

        //判断是哪个控件跳过来的
        intent = getIntent();
        if (intent.getStringExtra("flag") != null) {
            String action = intent.getStringExtra("flag");
            if (action.contains("name")) {
                headerText.setText(getResources().getString(R.string.name));
                flag=1;
            }

        }
        if (intent.getStringExtra("flag") != null) {
            String action = intent.getStringExtra("flag");
            if (action.contains("email")) {
                headerText.setText(getResources().getString(R.string.email));
                userInput.setHint(getResources().getString(R.string.email));
                flag=2;
            }

        }

    }


    @OnClick({R.id.header_left_text, R.id.header_right_msg, R.id.delet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left_text:
                finish();
                break;
            case R.id.header_right_msg:
                //上传到服务器然后去保存
                    if (userInput.getText().toString()!=null) {
                        showMyLoadingDialog();
                        OkHttpUpUserMsg(flag);
                    }else {  showToast(getResources().getString(R.string.inputNoNull)); }

                break;
            case R.id.delet:
                userInput.setText("");
                break;
        }
    }

    private void OkHttpUpUserMsg( int  flag ) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (flag == 1) {
                jsonObject.put("id", userInfo.getIntInfo("id"));
                jsonObject.put("niken", userInput.getText().toString());
            } else {

                jsonObject.put("id", userInfo.getIntInfo("id"));
                jsonObject.put("eMail", userInput.getText().toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        Log.d("------->", "cookie:challenge " + userInfo.getStringInfo("cookie"));
        final Request request = new Request.Builder()
                .url(StringUtils.USER_DATE)
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
                    Log.i("---->是否上传成功", "true" + result);
                    Message msg =handler.obtainMessage();
                    msg.what=0;
                    handler.sendMessage(msg);
                }
            }
        });
    }
}
