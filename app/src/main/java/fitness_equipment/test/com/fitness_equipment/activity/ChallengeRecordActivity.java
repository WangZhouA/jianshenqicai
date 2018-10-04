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

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.adapter.ChallengeAdapter;
import fitness_equipment.test.com.fitness_equipment.chlibrary.src.com.baoyz.swipemenulistview.SwipeMenu;
import fitness_equipment.test.com.fitness_equipment.chlibrary.src.com.baoyz.swipemenulistview.SwipeMenuCreator;
import fitness_equipment.test.com.fitness_equipment.chlibrary.src.com.baoyz.swipemenulistview.SwipeMenuItem;
import fitness_equipment.test.com.fitness_equipment.chlibrary.src.com.baoyz.swipemenulistview.SwipeMenuListView;
import fitness_equipment.test.com.fitness_equipment.enitiy.PersonalChallenges;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ChallengeRecordActivity extends BasActivity {


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

    ChallengeAdapter challengeAdapter;

    int flag=0; //判断类别是那一个


    private Handler handler=new Handler(){
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
                            LemonBubble.showRight(ChallengeRecordActivity.this, getResources().getString(R.string.trueRequset), 1000);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    challengeAdapter=new ChallengeAdapter(listChallengeDate,ChallengeRecordActivity.this);
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
        return R.layout.activity_challenge_record;
    }


    PersonalChallenges personalChallenges;
    List<PersonalChallenges.BodyBean.ChallengrecordlistBean>listChallengeDate;




    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void initView() {


        //加入侧滑显示的菜单
        //1.首先实例化SwipeMenuCreator对象
        SwipeMenuCreator creater = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {


                SwipeMenuItem deleteItem = new SwipeMenuItem(ChallengeRecordActivity.this);
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
                Intent intent =new Intent(ChallengeRecordActivity.this,MotionRecordsActivity.class);
                intent.putExtra("FLAG","CHALL");
                intent.putExtra("TYPE",flag);
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
                        Log.i("------>position",":"+position);
                        listChallengeDate.remove(position);
                        challengeAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

    }

    private void okHttpAddPersionToDervice(int position) {
        final String reigstStr = StringUtils.DELETE_CHALLENGE_DATE;
        JSONObject jsonObject = new JSONObject();
        try {
                jsonObject.put("id",String.valueOf(listChallengeDate.get(position).getId()));
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

                }
            }
        });
    }


    @Override
    protected void init() {

        Intent intent =getIntent();
        if (intent.getStringExtra("Movment_Type")!=null){
            if (intent.getStringExtra("Movment_Type").contains("0")){
                flag=0;
                showMyLoadingDialog();
                okokHttpQueryChallenge(flag);



            }else if (intent.getStringExtra("Movment_Type").contains("1")){
                flag=1;
                showMyLoadingDialog();
                okokHttpQueryChallenge(flag);

            }
        }


        headerText.setText(R.string.Challenge_record);
        headerText.setTextColor(Color.parseColor("#19C49B"));
        headerRight.setImageResource(R.mipmap.share);
        headerRight.setVisibility(View.VISIBLE);
        listChallengeDate=new ArrayList<>();
        initView();
    }


    @OnClick({R.id.header_left, R.id.header_text, R.id.header_right})
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

    private void okokHttpQueryChallenge( int flag ) {

        final String reigstStr = StringUtils.MY_CHALLENGE;
        JSONObject jsonObject = new JSONObject();
        try {
            if (flag==0) {
                jsonObject.put("uid",userInfo.getIntInfo("id"));
                jsonObject.put("typeId",1);

            }else {
                jsonObject.put("uid",userInfo.getIntInfo("id"));
                jsonObject.put("typeId",2);

            }
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

                    listChallengeDate.clear();
                    Gson gson = new Gson();
                    personalChallenges = gson.fromJson(result,PersonalChallenges.class);
                    if (personalChallenges.getCode().contains("1")){
                        listChallengeDate.addAll(personalChallenges.getBody().getChallengrecordlist());

                       Log.i("-----?listChallengeDate",""+listChallengeDate.size());
                        Message msg =new Message();
                        msg.what=0;
                        handler.sendMessage(msg);
                    }
                } else {

                }
            }
        });
    }


}
