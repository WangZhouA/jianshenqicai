package fitness_equipment.test.com.fitness_equipment.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fitness_equipment.test.com.fitness_equipment.MyApplication;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.activity.ChallengeRecordActivity;
import fitness_equipment.test.com.fitness_equipment.activity.DaKaJiLuActivity;
import fitness_equipment.test.com.fitness_equipment.activity.MonmentListActivity;
import fitness_equipment.test.com.fitness_equipment.activity.MyMedalActivity;
import fitness_equipment.test.com.fitness_equipment.adapter.MyFragmentPagerAdapter;
import fitness_equipment.test.com.fitness_equipment.enitiy.Calories;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.interfaces.OnUpdateFirstTabTextListener;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import fitness_equipment.test.com.fitness_equipment.view.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static fitness_equipment.test.com.fitness_equipment.R.id.linearlayout_medal;

/**
 * Created by 陈姣姣 on 2017/11/14.
 */

public class FirstFragment extends Fragment implements View.OnClickListener{


    View view;
    ViewPager mPager;
    /**
     * 俩个标题  选项
     */
    TextView tv_tab_bicycle;
    TextView tv_tab_rope;
    ArrayList<Fragment> fragmentsList;

    //二个布局
    Fragment home_bicycle;
    Fragment home_rope;
    @BindView(R.id.tv_tab_bicycle)
    TextView tvTabBicycle;
    @BindView(R.id.tv_tab_rope)
    TextView tvTabRope;
    @BindView(R.id.viepager)
    ViewPager viepager;
    @BindView(R.id.img_first)
    ImageView imgFirst;
    @BindView(linearlayout_medal)
    LinearLayout linearlayoutMedal;
    @BindView(R.id.img_Challenge)
    ImageView imgChallenge;
    @BindView(R.id.linearlayout_Challenge)
    LinearLayout linearlayoutChallenge;
    @BindView(R.id.img_Clock)
    ImageView imgClock;
    @BindView(R.id.linearlayout_Clock)
    LinearLayout linearlayoutClock;
    @BindView(R.id.img_Movement_list)
    ImageView imgMovementList;
    @BindView(R.id.linearlayout_Movement_list)
    LinearLayout linearlayoutMovementList;
    @BindView(R.id.linearlayout_btn)
    LinearLayout linearlayoutBtn;
    Unbinder unbinder;

    @BindView(R.id.daka_img)
    CircleImageView daka_img;
    @BindView(R.id.daka_name)
    TextView daka_name;
    @BindView(R.id.daka_type)
    TextView daka_type;
    @BindView(R.id.daka_time)
    TextView daka_time;

    private UserInfo userInfo;

    private int currIndex = 0;  //fragment 下标

    OnUpdateFirstTabTextListener onUpdateFirstTabTextListener;


    private int flag =0;  //用于确定是那一个运动类型进来的


    public void setOnUpdateFirstTabTextListener(OnUpdateFirstTabTextListener onUpdateFirstTabTextListener) {
        this.onUpdateFirstTabTextListener = onUpdateFirstTabTextListener;
    }


    private Handler handler =  new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0:
//
//                    Calories Ecalories;
//                    String  calories;
//                    String  counts;
//                    String  times;
//                    String  kilometre;
//
//                    String tname;
//                    String createtime;
//                    String heading;
//                    String niken;
//

                    if (!TextUtils.isEmpty(tname)){

                        daka_type.setText(tname);
                    }else {

                    }

                    if (!TextUtils.isEmpty(createtime)){

                        daka_time.setText(createtime);
                    }else {

                    }

                    if (!TextUtils.isEmpty(heading)){

                        StringUtils.showImage(getActivity(),StringUtils.GET_PHOTO+heading,R.mipmap.male_head2,R.mipmap.male_head2,daka_img);

                    }else {

                    }

                    if (!TextUtils.isEmpty(niken)){

                        daka_name.setText(niken);
                    }else {

                    }


                    break;
                case 10:

                    daka_type.setText("未打卡");
                    daka_time.setText("");
                    if (!TextUtils.isEmpty(niken)){
                        daka_name.setText(niken);
                    }else {
                    }
                    break;
            }

        }
    };




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.first_fragment, null);
        MyApplication.getInstance().addActyToList(getActivity());

        userInfo=new UserInfo(getActivity());

        tv_tab_bicycle = (TextView) view.findViewById(R.id.tv_tab_bicycle);
        tv_tab_rope = (TextView) view.findViewById(R.id.tv_tab_rope);
        InitViewPager(view);
        unbinder = ButterKnife.bind(this, view);

        setListener();

        /**
         *  查询大卡记录
         * */
        okHttpQueryDaka(flag);

//        QueryDaka(flag);
        return view;
    }


    Calories Ecalories;
    String  calories;
    String  counts;
    String  times;
    String  kilometre;

    String tname;
    String createtime;
    String heading;
    String niken;
    RequestBody body;
    private void okHttpQueryDaka(int flag) {

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json");

        JSONObject jsonObject = new JSONObject();
        try {
            if (flag==0) {
                Log.i("----->id", "123" + userInfo.getIntInfo("id"));
                jsonObject.put("uid", String.valueOf(userInfo.getIntInfo("id")));
                jsonObject.put("typeId", "1");
            }else {
                jsonObject.put("uid", String.valueOf(userInfo.getIntInfo("id")));
                jsonObject.put("typeId", "2");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        Log.d("------->", " jsonobject " + ""+jsonObject.toString());


        Log.d("------->", "cookie:challenge " + userInfo.getStringInfo("cookie"));

        Request request = new Request.Builder()
                .url(StringUtils.CALORIES)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cookie", userInfo.getStringInfo("cookie"))
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
                    Log.i("----->dakajilu",result);
                    Gson gson =new Gson();
                    Ecalories=gson.fromJson(result,Calories.class);
                    if (Ecalories!=null)
                        if (Ecalories.getCode().equals("1")) {
                            calories = Ecalories.getBody().getCountslist().get(0).getCalorie();
                            Log.i("---->123", "c" + calories);
                            times = Ecalories.getBody().getCountslist().get(0).getTimes();
                            Log.i("---->123", "c" + times);
                            counts = Ecalories.getBody().getCountslist().get(0).getCounts();
                            Log.i("---->123", "c" + counts);
                            kilometre = Ecalories.getBody().getCountslist().get(0).getKilometre();
                            Log.i("---->123", "c" + kilometre);
                            Intent intent =new Intent("DAKA");
                            intent.putExtra("calories",calories);
                            intent.putExtra("times",times);
                            intent.putExtra("counts",counts);
                            intent.putExtra("kilometre",kilometre);
                            getActivity().sendBroadcast(intent);


                            if (Ecalories.getBody().getCountslist().size() > 0  &&  Ecalories.getBody().getCountslist()!=null ) {
                                if (Ecalories.getBody().getRecordlist().size()>0  &&  Ecalories.getBody().getRecordlist()!=null) {
                                    tname = Ecalories.getBody().getRecordlist().get(0).getTname();
                                    createtime = Ecalories.getBody().getRecordlist().get(0).getCreatetime();
                                    heading = Ecalories.getBody().getRecordlist().get(0).getHeading();
                                    niken = Ecalories.getBody().getRecordlist().get(0).getNiken();
                                    Message msg = handler.obtainMessage();
                                    msg.what = 0;
                                    handler.sendMessage(msg);

                                }else {
                                    Message msg = handler.obtainMessage();
                                    msg.what = 10;
                                    handler.sendMessage(msg);
                                }
                            }
                        }
                }
            }
        });
    }


    private void setListener() {

        tv_tab_bicycle.setOnClickListener(new MyOnClickListener(0));
        tv_tab_rope.setOnClickListener(new MyOnClickListener(1));
        linearlayoutMedal.setOnClickListener(this);
        linearlayoutChallenge.setOnClickListener(this);
        linearlayoutMovementList.setOnClickListener(this);
        linearlayoutClock.setOnClickListener(this);
    }




    private void InitViewPager(View parentView) {
        mPager = (ViewPager) parentView.findViewById(R.id.viepager);
        fragmentsList = new ArrayList<>();

        home_bicycle = new ActionBicycleFragment();
        home_rope = new CircleFragment();

        fragmentsList.add(home_bicycle);
        fragmentsList.add(home_rope);

        mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentsList));
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mPager.setCurrentItem(0);
        tv_tab_rope.setTextColor(Color.parseColor("#242424"));
        tv_tab_bicycle.setTextColor(Color.parseColor("#18C49A"));
        tv_tab_bicycle.setTextSize(22);
        tv_tab_rope.setTextSize(16);

    }


    Intent intent;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //动感单车
            case R.id.tv_tab_bicycle:

                //类型0 是动感单车

                mPager.setCurrentItem(0);
                okHttpQueryDaka(0);
                tv_tab_rope.setTextColor(Color.parseColor("#242424"));
                tv_tab_bicycle.setTextColor(Color.parseColor("#18C49A"));
                tv_tab_bicycle.setTextSize(22);
                tv_tab_rope.setTextSize(16);
                break;

            //活力跳绳
            case R.id.tv_tab_rope:


                //类型1 是活力跳绳
                mPager.setCurrentItem(1);
                okHttpQueryDaka(1);
                tv_tab_bicycle.setTextColor(Color.parseColor("#18C49A"));
                tv_tab_rope.setTextColor(Color.parseColor("#242424"));
                tv_tab_bicycle.setTextSize(22);
                tv_tab_rope.setTextSize(16);
                break;

            case  R.id.linearlayout_medal:
                intent=new Intent(getActivity(), MyMedalActivity.class);
                startActivity(intent);
                break;


            case R.id.linearlayout_Challenge:

                //这里用于判断是那个运动类型，然后进去的
                intent=new Intent(getActivity(), ChallengeRecordActivity.class);

                intent.putExtra("Movment_Type",String.valueOf(flag));
                startActivity(intent);


                break;

            case R.id.linearlayout_Clock:

                //这里用于判断是那个运动类型，然后进去的
                intent=new Intent(getActivity(), DaKaJiLuActivity.class);

                intent.putExtra("Movment_Type",String.valueOf(flag));
                startActivity(intent);


                break;
            case R.id.linearlayout_Movement_list:
                intent=new Intent(getActivity(), MonmentListActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            //跟新
            onUpdateFirstTabTextListener.setOnUpdateFirstTabTextListener();
            okHttpQueryDaka(flag);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * view pager
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {

            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        flag=0;
                        okHttpQueryDaka(0);
                        tv_tab_bicycle.setTextColor(Color.parseColor("#18C49A"));
                        tv_tab_rope.setTextColor(Color.parseColor("#242424"));
                        tv_tab_bicycle.setTextSize(22);
                        tv_tab_rope.setTextSize(16);
                    }

                    break;
                case 1:
                    if (currIndex == 0) {
                        flag=1;
                        okHttpQueryDaka(1);
                        tv_tab_rope.setTextColor(Color.parseColor("#18C49A"));
                        tv_tab_bicycle.setTextColor(Color.parseColor("#242424"));
                        tv_tab_bicycle.setTextSize(16);
                        tv_tab_rope.setTextSize(22);
                    }

                    break;
            }
            currIndex = arg0;

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

}
