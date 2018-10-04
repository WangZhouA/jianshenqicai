package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import fitness_equipment.test.com.fitness_equipment.view.ScaleRulerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StatureActivity extends BasActivity {


    @BindView(R.id.scaleWheelView_height)
    ScaleRulerView mHeightWheelView;
    @BindView(R.id.tv_user_height_value)
    TextView mHeightValue;
    @BindView(R.id.scaleWheelView_weight)
    ScaleRulerView scaleWheelViewWeight;
    @BindView(R.id.tv_user_weight_value)
    TextView tvUserWeightValue;
    @BindView(R.id.scaleWheelView_age)

    ScaleRulerView scaleWheelViewAge;
    @BindView(R.id.tv_user_age_value)
    TextView tvUserAgeValue;
    @BindView(R.id.last_btn)
    Button lastBtn;
    @BindView(R.id.next_btn)
    Button nextBtn;

    /***
     *  身高
     *
     */

    private float mHeight = 170;
    private float mMaxHeight = 220;
    private float mMinHeight = 100;


    /**
     * 体重
     */

    private float mWeight = 50;
    private float mMaxWeight = 300;
    private float mMinWeight = 1;


    /**
     * 年龄
     */

    private float mAge = 1990;
    private float mMaxAge = 2017;
    private float mMinAge = 1950;

    private UserInfo userInfo;

    ImageView user_gender_img;

    private  String gender;

    @Override
    protected int getContentView() {
        return R.layout.activity_stature;
    }

    @Override
    protected void init() {
        userInfo=new UserInfo(this);
        initData();

        user_gender_img= (ImageView) findViewById(R.id.user_gender_img);

        Intent intent =getIntent();
        if (intent.getStringExtra("Gender")!=null){
            if (intent.getStringExtra("Gender").contains("girl"))  {
                gender="girl";
                user_gender_img.setImageResource(R.mipmap.female_head);
                flag=1;
            }else {
                gender=getResources().getString(R.string.boboby);
                gender="boboby";
                user_gender_img.setImageResource(R.mipmap.male_head);
                flag=2;
            }
        }
    }


    private void initData() {

        /**
         * 身高
         * */
        mHeightValue.setText((int) mHeight + "");


        mHeightWheelView.initViewParam(mHeight, mMaxHeight, mMinHeight);
        mHeightWheelView.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                mHeightValue.setText((int) value + "");
                mHeight = value;
            }
        });


        /**
         * 体重
         * */
        tvUserWeightValue.setText((int) mWeight + "");
        scaleWheelViewWeight.initViewParam(mWeight, mMaxWeight, mMinWeight);
        scaleWheelViewWeight.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tvUserWeightValue.setText((int) value + "");
                mWeight = value;
            }
        });


        /**
         * 年龄
         * */
        tvUserAgeValue.setText((int) mAge + "");


        scaleWheelViewAge.initViewParam(mAge, mMaxAge, mMinAge);
        scaleWheelViewAge.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tvUserAgeValue.setText((int) value + "");
                mAge = value;
            }
        });


    }


    Intent intent;
    @OnClick({R.id.last_btn, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.last_btn:

                intent=new Intent(StatureActivity.this,ReigstGreader.class);
                startActivity(intent);
                finish();
                break;
            case R.id.next_btn:
                try {
                    ReUser();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private  void  ReUser() throws  Exception {




        final String reigstStr = StringUtils.RESET_USER;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",Integer.parseInt(userInfo.getStringInfo("id").toString()));
            jsonObject.put("hight", mHeightValue.getText().toString());
            jsonObject.put("weight",tvUserWeightValue.getText().toString());
            jsonObject.put("birthday", tvUserAgeValue.getText().toString()+"-0-0");

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
        Log.i("---------->修改的cookic",userInfo.getStringInfo("cookie"));

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
                            intent=new Intent(StatureActivity.this,UserReigstMessageActivity.class);
                            intent.putExtra("Gender",gender);
                            startActivity(intent);

                        }else{

                            Log.e("------>","失败");
                            intent=new Intent(StatureActivity.this,UserReigstMessageActivity.class);
                            intent.putExtra("Gender",gender);
                            startActivity(intent);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }


    private  static  int flag =1;

    public  static  int  getflag(){
        return   flag;
    }


}
