package fitness_equipment.test.com.fitness_equipment.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.adapter.MyAdapter;
import fitness_equipment.test.com.fitness_equipment.adapter.SmallMyAdapter;
import fitness_equipment.test.com.fitness_equipment.enitiy.NaoZhong;
import fitness_equipment.test.com.fitness_equipment.enitiy.Timing;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.interfaces.OnAddUapdateTimeListener;
import fitness_equipment.test.com.fitness_equipment.view.PickerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 定时管理
 *
 * @author Administrator
 */
public class TimingControlActivity extends BasActivity implements OnAddUapdateTimeListener{


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
    @BindView(R.id.lv_timing_listview)
    ListView lvTimingListview;

    //***************************************  闹钟时间************************************
    // 设置时间popupwindow
    private PopupWindow timePopupWindow;
    //用来装小时
    PickerView hour_pv;
    //用来装分钟
    PickerView minute_pv;
    //得到的小时
    String hour="12";
    //得到的分组
    String min="30";

    Timing item;

    SmallMyAdapter smallAdapter;
    //****************************************************************************************



    //星期初始化
    // 设置周期popupwindow
    private PopupWindow periodPopupWindow;
    ListView lvPeriodListview;
    /*
	 * 保存操作内容,默认为1
	 */
    private int lable = 2;
    //*********************标签 *********************
    // 设置标签popupwindow
    private PopupWindow lablePopupWindow;
    private List<NaoZhong.BodyBean.TrainlistBean> lists = new ArrayList<>();
    MyAdapter adapter;

    PickerView lable_pv;

    private ListView tListView;



    //我需要的时间

    String  MySaveTime;
    String  MySaveWeek;
    int flag=1;


    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                //查询
                case 0:

                    adapter.notifyDataSetChanged();
                    break;

                case 1:
                    //刷新
                    okHttpQueryTimes();

                    break;


            }
        }
    };





    @Override
    protected int getContentView() {
        return R.layout.timing_control;
    }

    @Override
    protected void init() {

        lists=new ArrayList<>();
        tListView = (ListView) findViewById(R.id.lv_timing_listview);
        tListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {// 长按删除

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int position, long arg3) {

                return false;
            }
        });


        headerText.setText(R.string.Remind_training);
        headerText.setTextColor(Color.parseColor("#19C49B"));
        headerRight.setVisibility(View.VISIBLE);
        headerRight.setImageResource(R.mipmap.add);
        timePopupWindow();
        periodPopupWindow();
        lablePopupWindow();
        okHttpQueryTimes();
        adapter = new MyAdapter( lists,TimingControlActivity.this);
        adapter.setOnAddUapdateTimeListener(this);
        tListView.setAdapter(adapter);
//
//        for (int i=0 ;i<100;i++) {
//            okHttpDelete(i);
//        }

    }



    /**
     * 选择时间初始化
     */
    private void timePopupWindow() {
        Button btnWeightCancel, btnWeightDone;

        View contentViewHM = LayoutInflater.from(TimingControlActivity.this)
                .inflate(R.layout.time_popupwindow, null);
        timePopupWindow = new PopupWindow(contentViewHM,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        timePopupWindow.setContentView(contentViewHM);
        // 设置宽度充满
        timePopupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        timePopupWindow.setOutsideTouchable(false);
        timePopupWindow.setFocusable(false);
        // 取消按钮
        btnWeightCancel = (Button) contentViewHM
                .findViewById(R.id.btn_weight_cancel);
        btnWeightCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // 消失當前窗口
                timePopupWindow.dismiss();
            }
        });

        // 完成按钮
        btnWeightDone = (Button) contentViewHM
                .findViewById(R.id.btn_weight_done);

        hour_pv = (PickerView) contentViewHM.findViewById(R.id.hour_pv);
        minute_pv = (PickerView) contentViewHM.findViewById(R.id.minute_pv);

        List<String> HH = new ArrayList<String>();

        for (int i = 0; i < 24; i++) {
            HH.add(i < 10 ? "0" + i : "" + i);
        }

        List<String> MM = new ArrayList<String>();
        // 设置时间mm
        for (int i = 0; i < 60; i++) {
            MM.add(i < 10 ? "0" + i : "" + i);
        }
        // 时间装进 pickview
        hour_pv.setData(HH);
        hour_pv.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String texthh) {
                hour = texthh;
                Toast.makeText(TimingControlActivity.this, ""+texthh, Toast.LENGTH_SHORT).show();

            }
        });

        minute_pv.setData(MM);
        minute_pv.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String textmm) {
                min = textmm;
                Toast.makeText(TimingControlActivity.this, ""+textmm, Toast.LENGTH_SHORT).show();
            }

        });

        btnWeightDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                timePopupWindow.dismiss();// 隐藏选择时间窗口
                item = new Timing();
                item.setTvtimingtime(hour + ":" + min);

                Log.i("-----我需要的时间",hour + ":" + min);

                MySaveTime =hour + ":" + min;

                smallAdapter = new SmallMyAdapter(TimingControlActivity.this,
                        item.getWeektime());
                lvPeriodListview.setAdapter(smallAdapter);
                showperiodPopupWindow();// 打开选择星期窗口
            }
        });

    }


    /**
     * 星期选择初始化
     *
     * @param
     */
    private void periodPopupWindow() {

        Button btnperiodCancel, btnperiodDone;

        View contentViewperiod = LayoutInflater
                .from(TimingControlActivity.this).inflate(
                        R.layout.period_popupwindow, null);

        periodPopupWindow = new PopupWindow(contentViewperiod,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

        periodPopupWindow.setContentView(contentViewperiod);

        periodPopupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        periodPopupWindow.setOutsideTouchable(false);
        periodPopupWindow.setFocusable(false);
        lvPeriodListview = (ListView) contentViewperiod
                .findViewById(R.id.lv_period_listview);

        // 完成按钮
        btnperiodDone = (Button) contentViewperiod
                .findViewById(R.id.btn_period_done);

        // 取消按钮
        btnperiodCancel = (Button) contentViewperiod
                .findViewById(R.id.btn_period_cancel);
        btnperiodCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // 消失當前窗口
                periodPopupWindow.dismiss();
            }
        });
        btnperiodDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                periodPopupWindow.dismiss();
                item.setWeekStr(smallAdapter.gettimeStr());

                Log.i("-----我需要的周",smallAdapter.gettimeStr());

                MySaveWeek =smallAdapter.gettimeStr();

                showlablePopupWindow();
            }
        });

    }


    /**
     *
     * */
    private int []  StringToIntArray(String d) {
        int [] a =new int [d.length()];
        char[] charArray = d.toCharArray();
        for (int i=0;i<charArray.length;i++) {
            int parseInt = Integer.parseInt(String.valueOf(charArray[i]));
            a[i]=parseInt;
        }
        System.out.println(Arrays.toString(a));

        return a;
    }

    public static String indexOfArr(int[] arr, int value2) {
        String rust = "";
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value2) {
                if (i == 0) {
                    rust = rust + "星期一  ";
                } else if (i == 1) {
                    rust = rust + "星期二  ";
                } else if (i == 2) {
                    rust = rust + "星期三  ";
                } else if (i == 3) {
                    rust = rust + "星期四  ";
                } else if (i == 4) {
                    rust = rust + "星期五  ";
                } else if (i == 5) {
                    rust = rust + "星期六  ";
                } else if (i == 6) {
                    rust = rust + "星期日  ";
                }
            }
        }
        return rust;
    }





    /**
     * 选择标签初始化
     */
    private void lablePopupWindow() {

        Button btnLableCancel, btnLableDone;

        View contentViewlable = LayoutInflater.from(TimingControlActivity.this)
                .inflate(R.layout.lable_popupwindow, null);
        lablePopupWindow = new PopupWindow(contentViewlable,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        lablePopupWindow.setContentView(contentViewlable);
        // 设置宽度充满
        lablePopupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        lablePopupWindow.setOutsideTouchable(false);
        lablePopupWindow.setFocusable(false);
        // 完成按钮
        btnLableDone = (Button) contentViewlable
                .findViewById(R.id.btn_lable_done);

        // 取消按钮
        btnLableCancel = (Button) contentViewlable
                .findViewById(R.id.btn_lable_cancel);
        btnLableCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // 消失當前窗口
                lablePopupWindow.dismiss();
            }
        });

        btnLableDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // 消失當前窗口
                lablePopupWindow.dismiss();

                item.setLable(lable);

                Log.i("-----我需要的标签", "" + lable);

                okHttpAddNaoZhong();

            }
        });

        // 选择标签
        lable_pv = (PickerView) contentViewlable.findViewById(R.id.lable_pv);

        List<String> lb = new ArrayList<String>();
        // 设置标签
        for (int i = 1; i < 3; i++) {
            if (i == 1) {
                lb.add("动感单车");
            } else if (i == 2) {
                lb.add("活力跳绳");

            }

            lable_pv.setData(lb);
            lable_pv.setOnSelectListener(new PickerView.onSelectListener() {

                @Override
                public void onSelect(String text) {

                    if (text.equals("动感单车")) {
                        lable = 1;
                    } else if (text.equals("动感单车")) {
                        lable = 2;
                    }

                }
            });

        }
    }

    private void okHttpAddNaoZhong() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", String.valueOf(userInfo.getIntInfo("id")));
            jsonObject.put("version", "LD1.0");
            jsonObject.put("status", "1");
            jsonObject.put("typeId", String.valueOf(lable));
            if (MySaveTime==null || MySaveTime.contains("null:null")){
                jsonObject.put("time", "12:30");
            }else {
                jsonObject.put("time",MySaveTime);
            }
            jsonObject.put("content", indexOfArr(StringToIntArray(MySaveWeek),1));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        final Request request = new Request.Builder()
                .url(StringUtils.ADD_NAOZHONG)
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
                    Log.i("----->add result",result);
                    Message msg =handler.obtainMessage();
                    msg.what=1;
                    handler.sendMessage(msg);

                } else {
                    Log.i("--->", "错误的修改信息" + response.body().string());

                }
            }
        });
    }


    NaoZhong naoZhong;
    private void  okHttpQueryTimes(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", String.valueOf(userInfo.getIntInfo("id")));
            jsonObject.put("version", "LD1.0");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        final Request request = new Request.Builder()
                .url(StringUtils.QUERY_NAOZHONG)
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
                    Gson gson =new Gson();
                    naoZhong =gson.fromJson(result,NaoZhong.class);
                    if (!TextUtils.isEmpty(naoZhong.getCode())) {
                        if (naoZhong.getCode().equals("1")) {
                            lists.clear();
                            lists.addAll(naoZhong.getBody().getTrainlist());
                            Message msg = handler.obtainMessage();
                            msg.what = 0;
                            handler.sendMessage(msg);
                        }
                    }
                } else {
                    Log.i("--->", "错误的修改信息" + response.body().string());

                }
            }
        });
    }


    /**
     * 显示选择时间窗口
     */
    private void showtimepopuwindows() {

        View rootviewhm = LayoutInflater.from(TimingControlActivity.this)
                .inflate(R.layout.timing_control, null);
        timePopupWindow.showAtLocation(rootviewhm, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 显示星期窗口
     */
    private void showperiodPopupWindow() {
        View rootviewhm = LayoutInflater.from(TimingControlActivity.this)
                .inflate(R.layout.timing_control, null);
        periodPopupWindow.showAtLocation(rootviewhm, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 显示标签窗口
     */
    private void showlablePopupWindow() {
        View rootviewlable = LayoutInflater.from(TimingControlActivity.this)
                .inflate(R.layout.timing_control, null);
        lablePopupWindow.showAtLocation(rootviewlable, Gravity.BOTTOM, 0, 0);

    }




    @OnClick({R.id.header_left, R.id.header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.header_right:

                showtimepopuwindows();
                break;
        }

    }

    @Override
    public void add() {
        okHttpQueryTimes();
    }
}
