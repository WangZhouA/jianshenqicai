package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.adapter.HistoryGoodAdapter;
import fitness_equipment.test.com.fitness_equipment.enitiy.HistoryGood;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 陈姣姣 on 2017/11/21.
 */

public class HistoryGoodActivity extends BasActivity {
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
    @BindView(R.id.history_good_listview)
    ListView historyGoodListview;
    List<HistoryGood.BodyBean.BestrecordlistBean>lists;

    HistoryGoodAdapter adapter;

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
                         LemonBubble.showRight(HistoryGoodActivity.this, getResources().getString(R.string.trueRequset), 1000);
                         new Handler().postDelayed(new Runnable() {
                             @Override
                             public void run() {
                                 adapter=new HistoryGoodAdapter(lists,HistoryGoodActivity.this);
                                 historyGoodListview.setAdapter(adapter);
                                 adapter.notifyDataSetChanged();
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
        return R.layout.activity_goodhistory;
    }

    int flag =1;
    @Override
    protected void init() {
        headerRight.setImageResource(R.mipmap.share);
        headerRight.setVisibility(View.VISIBLE);
        headerText.setText(getResources().getString(R.string.history_good_results));
        headerText.setTextColor(Color.parseColor("#19C49B"));
        lists=new ArrayList<>();
        showMyLoadingDialog();
        if (HistoricalRecordsActivity.getType()!=null){

            if (HistoricalRecordsActivity.getType().contains("1")){
                flag =1;
                okHttpQueryHistory(flag);
            }else {
                flag =2;
                okHttpQueryHistory(flag);
            }
        }

        historyGoodListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(HistoryGoodActivity.this,MotionRecordsActivity.class);
                startActivity(intent);
            }
        });




    }
    HistoryGood historyGood;
    private void okHttpQueryHistory(int flag) {


        OkHttpClient client;
        client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        final String reigstStr = StringUtils.GOOD_BICYCLE;

        JSONObject jsonObject = new JSONObject();
        try {

            if (flag==1) {
                jsonObject.put("typeId", "1");
                jsonObject.put("uid", String.valueOf(userInfo.getIntInfo("id")));
            }else {
                jsonObject.put("typeId", "2");
                jsonObject.put("uid", String.valueOf(userInfo.getIntInfo("id")));
            }

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
                    historyGood=gson.fromJson(result,HistoryGood.class);

                    if (historyGood.getCode().equals("1")){
                        lists.clear();
                        lists.addAll(historyGood.getBody().getBestrecordlist());
                        Message msg  =handler.obtainMessage();
                        msg.what=0;
                        handler.sendMessage(msg);
                    }
                }
            }
        });

    }


    @OnClick({R.id.header_left, R.id.header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.header_right:
                showDialogA();
                break;
        }
    }
}
