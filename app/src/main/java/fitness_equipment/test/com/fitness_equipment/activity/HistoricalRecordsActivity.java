package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
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
import fitness_equipment.test.com.fitness_equipment.adapter.HistoryAdapter;
import fitness_equipment.test.com.fitness_equipment.chlibrary.src.com.baoyz.swipemenulistview.SwipeMenu;
import fitness_equipment.test.com.fitness_equipment.chlibrary.src.com.baoyz.swipemenulistview.SwipeMenuCreator;
import fitness_equipment.test.com.fitness_equipment.chlibrary.src.com.baoyz.swipemenulistview.SwipeMenuItem;
import fitness_equipment.test.com.fitness_equipment.chlibrary.src.com.baoyz.swipemenulistview.SwipeMenuListView;
import fitness_equipment.test.com.fitness_equipment.enitiy.HistoryJiLu;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HistoricalRecordsActivity extends BasActivity {


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

    @BindView(R.id.listviews)
    SwipeMenuListView listviews;
    private List<HistoryJiLu.BodyBean.DetatilhistorylistBean> listChallenge;
    HistoryAdapter challengeAdapter;



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
                            LemonBubble.showRight(HistoricalRecordsActivity.this, getResources().getString(R.string.trueRequset), 1000);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    challengeAdapter = new HistoryAdapter(listChallenge, HistoricalRecordsActivity.this);
                                    listviews.setAdapter(challengeAdapter);
                                    challengeAdapter.notifyDataSetChanged();
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
        return R.layout.activity_historical_records;
    }
   public  static   String Type;
    @Override
    protected void init() {
        headerText.setText(R.string.history_record);
        headerText.setTextColor(Color.parseColor("#19C49B"));
        headerRight.setImageResource(R.mipmap.historical_record);
        headerRight.setVisibility(View.VISIBLE);
        headerHaoyou.setImageResource(R.mipmap.trophiessmall);
        headerHaoyou.setVisibility(View.VISIBLE);
        listChallenge = new ArrayList<>();

        initView();


        showMyLoadingDialog();
        okHttpQueryHistory();

        Intent intent =getIntent();
        if (intent.getStringExtra("TYPE")!=null) {

            if (intent.getStringExtra("TYPE").contains("1")) {
                //单车进来的
                Type="1";
            } else {
                 //跳绳进来的
                Type="2";
            }
        }

        listviews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent intent =new Intent(HistoricalRecordsActivity.this,MotionRecordsActivity.class);
                 startActivity(intent);
            }
        });



    }

    HistoryJiLu historyJiLu;
    private void okHttpQueryHistory( ) {

        /**
         * 查询历史记录
         * */
        final String reigstStr = StringUtils.QUERY_HISTORY;
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("uid", String.valueOf(userInfo.getIntInfo("id")));

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
                    Log.i("----->查询历史的信息",result);

                    Gson gson =new Gson();
                    historyJiLu=gson.fromJson(result,HistoryJiLu.class);
                    if (historyJiLu.getCode().equals("1")){
                        listChallenge.addAll(historyJiLu.getBody().getDetatilhistorylist());
                        Message msg = new Message();
                        msg.what=0;
                        handler.sendMessage(msg);
                    }

                }
            }
        });

    }


    @OnClick({R.id.header_left, R.id.header_right,R.id.header_haoyou})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.header_haoyou:
                Intent intent1 =new Intent(this,HistoryGoodActivity.class);
                startActivity(intent1);
                break;
            case R.id.header_right:

                Intent intent =new Intent(this,QueryHistoryActivity.class);
                startActivity(intent);

                break;
        }
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void initView() {


        //加入侧滑显示的菜单
        //1.首先实例化SwipeMenuCreator对象
        SwipeMenuCreator creater = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {


                SwipeMenuItem deleteItem = new SwipeMenuItem(HistoricalRecordsActivity.this);
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFE, 0x38, 0x24)));
                deleteItem.setWidth(dp2px(72));
                deleteItem.setTitle(getResources().getString(R.string.delete));
                deleteItem.setTitleSize(17);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);

            }
        };
        // set creator
        listviews.setMenuCreator(creater);
        listviews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(HistoricalRecordsActivity.this,MotionRecordsActivity.class);
                intent.putExtra("FLAG","HISTORY");
                intent.putExtra("TYPE","3");
                startActivity(intent);
            }
        });


        //2.菜单点击事件
        listviews.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        //删除设备
                        //
                        okHttpAddPersionToDervice(position);
                        listChallenge.remove(position);
                        challengeAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

    }
    /**
     * 删除
     * */
    private void okHttpAddPersionToDervice(int position) {

        OkHttpClient client;
        client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        final String reigstStr = StringUtils.DELETE_DATE;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",String.valueOf(listChallenge.get(position).getId()));

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

                }
            }
        });

    }

     public   static  String  getType(){

         return  Type;
     }

}
