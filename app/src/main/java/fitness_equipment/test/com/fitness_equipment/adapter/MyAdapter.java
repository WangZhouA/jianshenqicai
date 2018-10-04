package fitness_equipment.test.com.fitness_equipment.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.enitiy.NaoZhong;
import fitness_equipment.test.com.fitness_equipment.enitiy.Timing;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.interfaces.OnAddUapdateTimeListener;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import fitness_equipment.test.com.fitness_equipment.view.PickerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyAdapter extends BaseAdapter {

    private List<NaoZhong.BodyBean.TrainlistBean> tTimingitem;
    private Activity context;
    private int flag=1;  //判断是那一个按钮的点击事件
    private UserInfo userInfo;
    View contentViewperiod;
    View contentViewlable;
    View contentViewHM;



    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                //查询
                case 0:
                    onAddUapdateTimeListener.add();
                    break;




            }
        }
    };




    public MyAdapter(List<NaoZhong.BodyBean.TrainlistBean> tTimingitem, Activity context) {
        this.tTimingitem = tTimingitem;
        this.context = context;
        contentViewperiod = LayoutInflater
                .from(context).inflate(R.layout.period_popupwindow, null);

        contentViewlable = LayoutInflater.from(context)
                .inflate(R.layout.lable_popupwindow, null);

        contentViewHM = LayoutInflater.from(context)
                .inflate(R.layout.time_popupwindow, null);


        timePopupWindow();
        periodPopupWindow();
        lablePopupWindow();
        itemS =new Timing();
        userInfo=new UserInfo(context);

    }

    @Override
    public int getCount() {

        return tTimingitem.size();
    }

    @Override
    public Object getItem(int position) {

        return tTimingitem.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    boolean isOpen;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        // 如果缓存convertView为空，则需要创建View
        if (convertView == null) {
            holder = new ViewHolder();
            // 根据自定义的Item布局加载布局
            convertView = View.inflate(context, R.layout.timing_item, null);
            holder.tvtimingtime = (TextView) convertView
                    .findViewById(R.id.tv_timing_time);
            holder.tvperiod = (TextView) convertView
                    .findViewById(R.id.tv_alarm_clock_period);
            holder.tvlable = (TextView) convertView
                    .findViewById(R.id.tv_timing_label);
            holder.voice = (TextView) convertView.findViewById(R.id.tv_voice);

            holder.rl_birth = (RelativeLayout) convertView
                    .findViewById(R.id.rl_birth);
            holder.rl_birth2 = (RelativeLayout) convertView
                    .findViewById(R.id.rl_birth2);
            holder.rl_birth3 = (RelativeLayout) convertView
                    .findViewById(R.id.rl_birth3);


            holder.btnOff_Open = (ImageView) convertView
                    .findViewById(R.id.off_open);



            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }


        final NaoZhong.BodyBean.TrainlistBean item = tTimingitem.get(position);


        //  那几天的闹钟
        if (item.getContent()!=null){
            holder.tvperiod.setText(item.getContent());
        }
        //判断是否提醒
        if (item.getStatus()!=0){

            holder.btnOff_Open.setImageResource(R.mipmap.ic_switch_on);

        }else {

            holder.btnOff_Open.setImageResource(R.mipmap.ic_switch_off);

        }

        //获取时间
        if (item.getTime()!=null){

            holder.tvtimingtime.setText(item.getTime());
        }

        //获取类型
        if (item.getTname()!=null) {
            if (item.getTname().contains("自行车")) {
                holder.tvlable.setText(context.getResources().getString(R.string.action_bicycle));
            } else {
                holder.tvlable.setText(context.getResources().getString(R.string.Skip_rope));
            }
        }

        final ViewHolder finalHolder1 = holder;
        holder.tvtimingtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo.setUserInfo("TIME_ID",item.getId());
                showtimepopuwindows();

            }
        });


        holder.rl_birth2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=5;
                userInfo.setUserInfo("TIME_ID",item.getId());
                showlablePopupWindow();
            }
        });

        holder.rl_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=4;
                userInfo.setUserInfo("TIME_ID",item.getId());
                showperiodPopupWindow();
            }
        });

        final ViewHolder finalHolder = holder;
        holder.btnOff_Open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen){
                    flag=2;
                    finalHolder.btnOff_Open.setImageResource(R.mipmap.ic_switch_on);
                    okHttpRemessage(item.getId(),flag);
                    isOpen=!isOpen;
                }else {
                    flag=3;
                    finalHolder.btnOff_Open.setImageResource(R.mipmap.ic_switch_off);
                    okHttpRemessage(item.getId(),flag);
                    isOpen=!isOpen;
                }
            }
        });



        btnWeightDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {       //时间
                flag=1;
                timePopupWindow.dismiss();// 隐藏选择时间窗口
                itemS = new Timing();
                itemS.setTvtimingtime(hour + ":" + min);
                MySaveTime =hour + ":" + min;
                Log.i("------->AdapterTime",MySaveTime);
//                finalHolder1.tvtimingtime.setText(MySaveTime);
                okHttpRemessage(userInfo.getStringInfo("TIME_ID"),flag);


            }
        });


        btnperiodDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {        //周
                flag=4;
                periodPopupWindow.dismiss();
                itemS.setWeekStr(smallAdapter.gettimeStr());
                Log.i("-----Adapter我需要的周",smallAdapter.gettimeStr());
                MySaveWeek =smallAdapter.gettimeStr();
                indexOfArr(StringToIntArray(MySaveWeek),1);
                okHttpRemessage(userInfo.getStringInfo("TIME_ID"),flag);


            }
        });
        btnLableDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {             //标签
                flag=5;
                // 消失當前窗口
                lablePopupWindow.dismiss();
                Log.i("-----Adapter我需要的标签", "" + lable);

                okHttpRemessage(userInfo.getStringInfo("TIME_ID"),flag);
            }
        });



        holder.voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                okHttpDelete(item.getId());
            }
        });





        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public ImageView btnOff_Open;
        // 定时时间
        public TextView tvtimingtime;
        // 周期
        public TextView tvperiod;
        // 标签
        public TextView tvlable;
        //铃声
        public TextView voice;
        public RelativeLayout rl_birth;
        public RelativeLayout rl_birth2;
        public RelativeLayout rl_birth3;

        Button deleteBtn;
    }

    /**
     * 选择时间初始化
     */

    //***************************************  闹钟时间************************************
    // 设置时间popupwindow
    private PopupWindow timePopupWindow;
    //用来装小时
    PickerView hour_pv;
    //用来装分钟
    PickerView minute_pv;
    //得到的小时
    String hour;
    //得到的分组
    String min;

    Timing itemS;

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

    //我需要的时间
    String  MySaveTime="";
    String  MySaveWeek;
    Button btnWeightCancel, btnWeightDone;
    private void timePopupWindow() {

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
                if (hour==null || hour.contains("null")){
                    hour="12";
                }else {
                    hour = texthh;
                }
                Log.i("------->hour",hour);

            }
        });

        minute_pv.setData(MM);
        minute_pv.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String textmm) {
                min = textmm;
                if (min==null || min.contains("null")){
                    min="30";
                }else {
                    min = textmm;
                }
                Log.i("------->min",min);
            }

        });

    }


    /**
     * 选择标签初始化
     */
    Button btnLableCancel, btnLableDone;
    private void lablePopupWindow() {

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

                    } else if (text.equals("活力跳绳")) {
                        lable = 2;
                    }
                }
            });

        }
    }

    /**
     * 星期选择初始化
     *
     * @param
     */
    Button btnperiodCancel, btnperiodDone;
    private void periodPopupWindow() {

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

    }

    /**
     * 显示选择时间窗口
     */
    private void showtimepopuwindows() {

        View rootviewhm = LayoutInflater.from(context)
                .inflate(R.layout.timing_control, null);
        timePopupWindow.showAtLocation(rootviewhm, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 显示星期窗口
     */

    private void showperiodPopupWindow() {

        smallAdapter = new SmallMyAdapter(context,
                itemS.getWeektime());
        lvPeriodListview.setAdapter(smallAdapter);
        View rootviewhm = LayoutInflater.from(context)
                .inflate(R.layout.timing_control, null);
        periodPopupWindow.showAtLocation(rootviewhm, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 显示标签窗口
     */
    private void showlablePopupWindow() {
        View rootviewlable = LayoutInflater.from(context)
                .inflate(R.layout.timing_control, null);
        lablePopupWindow.showAtLocation(rootviewlable, Gravity.BOTTOM, 0, 0);

    }

    OkHttpClient client;
    private void okHttpRemessage( String  i,int flag) {

        client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        JSONObject jsonObject = new JSONObject();
        try {
            if (flag==1) {  //修改时间
                jsonObject.put("id", i);
                jsonObject.put("time",MySaveTime);

            }else if (flag==2){  //开启提醒
                jsonObject.put("id", i);
                jsonObject.put("status", "1");
            }else if (flag==3){   //关闭提醒
                jsonObject.put("id",i);
                jsonObject.put("status", "0");
            }else if (flag==4){ //选择哪个那天
                jsonObject.put("id", i);
                jsonObject.put("content", indexOfArr(StringToIntArray(MySaveWeek),1));
            }else if (flag==5){ //标签
                jsonObject.put("id", i);
                jsonObject.put("typeId", String.valueOf(lable));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        final Request request = new Request.Builder()
                .url(StringUtils.RE_NAOZHONG_TIXING)
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
                    Log.i("----->delete result",result);

                    try {
                        JSONObject jsonObject1 =new JSONObject(result);
                        String code =jsonObject1.getString("code");
                        if (code.equals("1")){

                            Message msg =handler.obtainMessage();
                            msg.what=0;
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
    OnAddUapdateTimeListener onAddUapdateTimeListener;

    public void setOnAddUapdateTimeListener(OnAddUapdateTimeListener onAddUapdateTimeListener) {
        this.onAddUapdateTimeListener = onAddUapdateTimeListener;
    }

    /**
     * 字符串转成int 数组
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
    private void okHttpDelete( String  i) {
        client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        final Request request = new Request.Builder()
                .url(StringUtils.DELETE_NAOZHONG)
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
                    Log.i("----->delete result",result);

                    try {
                        JSONObject jsonObject1 =new JSONObject(result);
                        String code =jsonObject1.getString("code");
                        if (code.equals("1")){

                            Message msg =handler.obtainMessage();
                            msg.what=0;
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

}