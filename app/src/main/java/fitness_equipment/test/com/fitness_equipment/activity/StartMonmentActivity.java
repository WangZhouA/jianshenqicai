package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.ble.Globals;
import fitness_equipment.test.com.fitness_equipment.view.MyDialog;

public class StartMonmentActivity extends BasActivity {


    @BindView(R.id.img_start)
    ImageView imgStart;
    @BindView(R.id.tv_end_btn)
    TextView tvEndBtn;
    @BindView(R.id.img_end)
    ImageView imgEnd;
    @BindView(R.id.two_btn)
    RelativeLayout two_btn;
    @BindView(R.id.time_chronometer)
    Chronometer timeChronometer;


    long recordingTime; //记录暂停的时间

    StringBuffer sb = new StringBuffer();
    String cDate;
    @BindView(R.id.time_tvA)
    TextView timeTvA;
    @BindView(R.id.tv_sd)
    TextView tvSd;
    @BindView(R.id.tv_hearts)
    TextView tvHearts;
    @BindView(R.id.tv_kmS)
    TextView tvKmS;
    @BindView(R.id.tv_kaluli)
    TextView tvKaluli;
    @BindView(R.id.ditu)
    ImageView ditu;
    @BindView(R.id.luxian)
    ImageView luxian;
    @BindView(R.id.guiji)
    ImageView guiJi;

    @Override
    protected int getContentView() {
        return R.layout.activity_start_monment;
    }

    @Override
    protected void init() {
        int hour = (int) ((SystemClock.elapsedRealtime() - timeChronometer.getBase()) / 1000 / 60);
        timeChronometer.setFormat("0" + String.valueOf(hour) + ":%s");
        timeChronometer.start();

        sb = new StringBuffer();
        IntentFilter intentFilter = new IntentFilter(Globals.WWW_BLEDATE_COM);
        registerReceiver(myBroadcastReceiver, intentFilter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(myBroadcastReceiver);

    }

    @OnClick({R.id.img_start, R.id.tv_end_btn, R.id.img_end,R.id.ditu,R.id.luxian,R.id.guiji})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ditu:

                showToast(getResources().getString(R.string.nuli));

                break;

            case R.id.guiji:

                showToast(getResources().getString(R.string.nuli));

                break;
            case R.id.luxian:

                showToast(getResources().getString(R.string.nuli));

                break;



            case R.id.img_start:
                timeChronometer.stop();
                recordingTime = SystemClock.elapsedRealtime() - timeChronometer.getBase();// 保存这次记录了的时
                two_btn.setVisibility(View.VISIBLE);
                imgStart.setVisibility(View.GONE);
                tvSd.setEnabled(false);
                tvHearts.setEnabled(false);
                tvKaluli.setEnabled(false);
                tvKmS.setEnabled(false);


                break;
            case R.id.tv_end_btn:
                ShowDialog();
                break;
            case R.id.img_end:
                timeChronometer.setBase(SystemClock.elapsedRealtime() - recordingTime);// 跳过已经记录了的时间
                timeChronometer.start();
                two_btn.setVisibility(View.GONE);
                imgStart.setVisibility(View.VISIBLE);

                tvSd.setEnabled(true);
                tvHearts.setEnabled(true);
                tvKaluli.setEnabled(true);
                tvKmS.setEnabled(true);

                break;
        }
    }


    private void ShowDialog() {


        View view = LayoutInflater.from(StartMonmentActivity.this).inflate(R.layout.qiangzhi_xiaxian_layout, null);
        final MyDialog builder = new MyDialog(StartMonmentActivity.this, 0, 0, view, R.style.MyDialog);
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
                        //                      if (bleData(data)[i].substring(0,2).equals("02")){
//
////                          0xFE:0x02:0x20:0x05:0x22:0x07:0xFE---------时间：5:22，0X07=是校验码（异或）
////                          0xFE:0x02:0x10:0x00:0x00:0x00:0x10:0xFE---距离：0.0，0x10=是校验码（异或）
////                          0xFE:0x02:0x30:0x00:0x00:0x30:0xFE----------心率：0   ，0x30=是校验码（异或）
////                          0xFE:0x02:0x50:0x00:0x01:0x05:0x54:0xFE----总距离：1.05 ，0x54=是校验码（异或）
////                          0xFE:0x02:0x00:0x00:0x11:0x20:0x31:0xFE----速度：11.2， 0x31=是校验码（异或）
////                          0xFE:0x02:0x40:0x00:0x00:0x14:0x54:0xEF-----卡路里：0.14，0x54 =是校验码（异或）
////                          0X02---数据头
//
//
                        if (bleData.substring(2, 4).equals("20")) {
                            /**
                             *  去解析蓝牙数据(时间)
                             * */
                            Log.e("--->20", "你开心就好(时间)");

//                        0220122311FE

                            String timeHour = bleData.substring(4, 6);
                            String timeMin = bleData.substring(6, 8);

                            timeTvA.setText(timeHour + ":" + timeMin);
                            /**
                             *  去解析蓝牙数据（距离）
                             * */
                        } else if (bleData.substring(2, 4).equals("10")) {
                            Log.e("--->10", "你开心就好（距离）");
                            /**
                             *  去解析蓝牙数据（心率）
                             * */
                            //0200011837FE
                            //118.37

                        } else if (bleData.substring(2, 4).equals("30")) {
                            Log.e("--->30", "你开心就好（心率）");
                            String timeHour = bleData.substring(4, 6);
                            int a = Integer.parseInt(timeHour);
                            String timeMin = bleData.substring(6, 8);
                            int b = Integer.parseInt(timeMin);
                            tvHearts.setText(String.valueOf(a) + String.valueOf(b));
                            /**
                             *  去解析蓝牙数据（总距离）
                             * */


                        } else if (bleData.substring(2, 4).equals("50")) {
                            Log.e("--->50", "你开心就好（总距离）");
                            SetMonmentDate(bleData, tvKmS);
                            /**
                             *  去解析蓝牙数据（速度）
                             * */
                        } else if (bleData.substring(2, 4).equals("00")) {
                            Log.e("--->00", "你开心就好（速度）");

                            SetMonmentDate(bleData, tvSd);

                            /**
                             *  去解析蓝牙数据（卡路里）
                             * */

                        } else if (bleData.substring(2, 4).equals("40")) {
                            Log.e("--->40", "你开心就好（卡路里）");

                            SetMonmentDate(bleData, tvKaluli);


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


}
