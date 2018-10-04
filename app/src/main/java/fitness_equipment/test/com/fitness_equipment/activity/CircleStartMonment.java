package fitness_equipment.test.com.fitness_equipment.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.ble.Globals;
import fitness_equipment.test.com.fitness_equipment.interfaces.OnUpdateDaoJiShi;
import fitness_equipment.test.com.fitness_equipment.view.AdvancedCountdownTimer;
import fitness_equipment.test.com.fitness_equipment.view.MyDialog;

import static fitness_equipment.test.com.fitness_equipment.R.id.two_btn;

public class CircleStartMonment extends BasActivity implements OnUpdateDaoJiShi{


    @BindView(R.id.time_chronometer)
    TextView timeChronometer;
    @BindView(R.id.tv_ci)
    TextView tvCi;
    @BindView(R.id.heart_tvA)
    TextView heartTvA;
    @BindView(R.id.tv_kaluli)
    TextView tvKaluli;
    @BindView(R.id.img_start)
    ImageView imgStart;
    @BindView(R.id.tv_end_btn)
    TextView tvEndBtn;
    @BindView(R.id.img_end)
    ImageView imgEnd;
    @BindView(two_btn)
    RelativeLayout twoBtn;


    //时间变量
    private long hour = 0;//时
    private long minute = 0;//分
    private long second = 0;//秒
    private long time = 0;//总时间
    MyCount count;
    //定时器
    private CountDownTimer timer;

    @Override
    protected int getContentView() {
        return R.layout.activity_circle_start_monment;
    }


    public void startTime(){
        count.start();  //开始计时  ！！！！！！！！！！
    }

    public void restartTime(){
        count.resume();//重新计时
    }
    public void cancleTime(){
        count.cancel();//取消计时
    }


    @Override
    protected void init() {


        IntentFilter intentFilter = new IntentFilter(Globals.WWW_BLEDATE_COM);
        registerReceiver(myBroadcastReceiver, intentFilter);

           Intent  intent =getIntent();
            hour = Long.parseLong("00");
            String  [] a = intent.getStringExtra("min").split("分钟");
            minute = Long.parseLong(a[0]);
            second = Long.parseLong("00");
            time = (hour * 3600 + minute * 60 + second) * 1000;  //因为以ms为单位，所以乘以1000.
            count = new MyCount(time, 1000);//延时时间为1s
            count.setOnUpdateDaoJiShi(this);
            startTime();

//            String  [] m = intent.getStringExtra("count").split("次");
//
//            //定时器
//            timer = new CountDownTimer(Integer.parseInt(m[0]) * 1000, 1000) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    //时间期间
//                    tvCi.setText(millisUntilFinished / 1000 + "");
//                }
//
//                @Override
//                public void onFinish() {
//                    //时间结束后
//                    tvCi.setText("0");
//
//                }
//            };
//
//            timer.start();



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(myBroadcastReceiver);

    }


    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.contains(Globals.WWW_BLEDATE_COM)) {
                /**
                 * 获取蓝牙传输过来的数据
                 * */
                String data = intent.getStringExtra("value");
                Log.e("------>接收的数据 data", data);
                if (data.length() > 14 && !data.substring(0, 2).equals("FF")) {

                    if (data.contains("02") && data.contains("FE")) {

                        String bleData = bleDataA(data);

                        if (bleData.substring(2, 4).equals("30")) {
                            /**
                             *  去解析蓝牙数据(时间)
                             * */
                            Log.e("--->20", "你开心就好（心率)");
                            String timeHour = bleData.substring(4, 6);
                            int a = Integer.parseInt(timeHour);
                            String timeMin = bleData.substring(6, 8);
                            int b = Integer.parseInt(timeMin);
                            heartTvA.setText(String.valueOf(a) + String.valueOf(b));


                        } else if (bleData.substring(2, 4).equals("40")) {
                            Log.e("--->40", "你开心就好（卡路里）");
                            SetMonmentDate(bleData,tvKaluli);


                        }else if (bleData.substring(2, 4).equals("70")) {
                            Log.e("--->40", "你开心就好（次数）");
                            SetMonmentDate(bleData,tvCi);


                        }
                    }
                }

            }

        }
    };


    /**
     * 对接收蓝牙过来的数据进行封装
     */

    private String[] bleData(String data) {
        String dataStr;
        dataStr = data.replaceAll(":", ",");
        System.out.println("1:" + dataStr);

        String[] arry = dataStr.split(",");
        System.out.println(Arrays.toString(arry));

        dataStr = "";
        String[] spiltStr;
        for (int i = 0; i < arry.length; i++) {
            if (arry[i].contains("0x")) {
                spiltStr = arry[i].split("0x");
                dataStr += spiltStr[1];
            }
        }
        System.out.println(dataStr);
        String[] endArray = dataStr.split("FE");
        System.out.println(Arrays.toString(endArray));

        return endArray;
    }

    /**
     * 去解析我想要的数据
     */

    private String bleDataA(String str) {
//        str = "02000022333330000FE";
        if (str.substring(0, 2).contains("02")) {

            str = "FE" + str;
        }
        String a = str.substring(0, str.indexOf("FE", str.indexOf("FE") + 1));
        String b = a.substring(a.indexOf("E") + 1);
        System.out.println(b + "FE");
        return b + "FE";
    }


    /**
     * 还要去最终的数据显示数据
     */

    private void SetMonmentDate(String str, TextView textView) {

        String q = str.substring(4, 6);
        System.out.println(q);
        String b = str.substring(6, 8);
        System.out.println(b);
        String g = str.substring(8, 10);
        int a = Integer.parseInt(q);

        String s = String.valueOf(a) + b + "." + g;
        textView.setText(Double.valueOf(s) + "");


    }



    @OnClick({R.id.img_start, R.id.tv_end_btn, R.id.img_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_start:
                cancleTime();
//                timer.cancel();
                twoBtn.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_end_btn:
                ShowDialog();
                break;
            case R.id.img_end:
                restartTime();

//                //定时器
//                timer = new CountDownTimer(Integer.parseInt(tvCi.getText().toString()) * 1000, 1000) {
//                    @Override
//                    public void onTick(long millisUntilFinished) {
//                        //时间期间
//                        tvCi.setText(millisUntilFinished / 1000 + "");
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        //时间结束后
//                        tvCi.setText("0");
//                    }
//                };
//
//                timer.start();


                imgStart.setVisibility(View.VISIBLE);
                twoBtn.setVisibility(View.GONE);

             break;

        }
    }


    private void ShowDialog() {

        View view = LayoutInflater.from(CircleStartMonment.this).inflate(R.layout.qiangzhi_xiaxian_layout, null);
        final MyDialog builder = new MyDialog(CircleStartMonment.this, 0, 0, view, R.style.MyDialog);
        builder.setCancelable(false);
        Button btn_no_xian_ss = (Button) view.findViewById(R.id.btn_no_xian_ss);
        Button btn_yes_xiaxian_ss = (Button) view.findViewById(R.id.btn_yes_xiaxian_ss);
        TextView for_tv_titles = (TextView) view.findViewById(R.id.for_tv_titles);
        TextView text_for_tv = (TextView) view.findViewById(R.id.text_for_tv);
        for_tv_titles.setText(getResources().getString(R.string.daka_record));
        text_for_tv.setText(getResources().getString(R.string.ToFlasedaka_record));


        //取消按钮
        btn_no_xian_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });


        //确认按钮
        btn_yes_xiaxian_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.dismiss();
                showToast(getResources().getString(R.string.sure));
                finish();
            }
        });

        builder.show();


    }




    @Override
    public void updateDaoJiShi(String a) {
        timeChronometer.setText(a);
    }
}





    class MyCount extends AdvancedCountdownTimer {

        OnUpdateDaoJiShi OnUpdateDaoJiShi;

        public void setOnUpdateDaoJiShi(fitness_equipment.test.com.fitness_equipment.interfaces.OnUpdateDaoJiShi onUpdateDaoJiShi) {
            OnUpdateDaoJiShi = onUpdateDaoJiShi;
        }

        public MyCount(long millisInFuture, long countDownInterval) {  //这两个参数在AdvancedCountdownTimer.java中均有(在“构造函数”中).
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            OnUpdateDaoJiShi.updateDaoJiShi("00:00:00");

        }

        //更新剩余时间
        String a = null;
        @Override
        public void onTick(long millisUntilFinished, int percent) {
            long myhour = (millisUntilFinished / 1000) / 3600;
            long myminute = ((millisUntilFinished / 1000) - myhour * 3600) / 60;
            long mysecond = millisUntilFinished / 1000 - myhour * 3600
                    - myminute * 60;
            if(mysecond<10){
                a = "0" + mysecond;
                OnUpdateDaoJiShi.updateDaoJiShi("0"+ myhour + ":" +"0"+myminute + ":" + a);

            }else{
                OnUpdateDaoJiShi.updateDaoJiShi("0"+ myhour + ":" +"0"+myminute + ":" + mysecond);

            }

        }

}
