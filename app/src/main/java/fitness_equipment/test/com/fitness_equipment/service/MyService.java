package fitness_equipment.test.com.fitness_equipment.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fitness_equipment.test.com.fitness_equipment.MainActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.enitiy.NaoZhong;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.receiver.Brobact;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import fitness_equipment.test.com.fitness_equipment.utils.TimeUtil;
import fitness_equipment.test.com.fitness_equipment.view.MyDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by 陈姣姣 on 2017/12/20.
 */

public class MyService extends Service {


    private Notification.Builder builder;
    private NotificationManager notificationManager;
    private Intent nfIntent;

    private UserInfo userInfo;

    private AlarmManager alarmManager;
    Calendar c ;

    //创建震动服务对象
    private Vibrator mVibrator;
    private MediaPlayer mediaPlayer;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("服务service了======", "===");
//        handler.postDelayed(runnable,1000);
        //注册Receiver，设置过滤器
        IntentFilter fliter = new IntentFilter();
        fliter.addAction("FUWU");
        fliter.addAction("XIANG");
        fliter.addAction("BLE_END");
        registerReceiver(broadcastReceiver, fliter);

        mVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE); //同上

        //获取闹钟的管理者
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        c= Calendar.getInstance();

        userInfo=new UserInfo(MyService.this);

        okHttpQueryTimes();
        /**
         * 测试闹钟
         * */
//        NaoZhong();

        mediaPlayer = MediaPlayer.create(MyService.this, R.raw.one);

        //监听音频播放完的代码，实现音频的自动循环播放
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer arg0) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });


    }


    private void  showNotifi(){

        /**
         * 此处设将Service为前台，不然当APP结束以后很容易被GC给干掉，这也就是大多数音乐播放器会在状态栏设置一个
         * 原理大都是相通的
         */
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //获取一个Notification构造器
        builder = new Notification.Builder(this.getApplicationContext());
        /**
         * 设置点击通知栏打开的界面，此处需要注意了，如果你的计步界面不在主界面，则需要判断app是否已经启动，
         * 再来确定跳转页面，这里面太多坑，（别问我为什么知道 - -）
         * 总之有需要的可以和我交流
         */
        nfIntent = new Intent(this, MainActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
                .setContentTitle("健身器材提醒你要运动哦！") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                .setContentText("加油，要记得勤加运动"); // 设置上下文内容
        // 获取构建好的Notification
        Notification stepNotification = builder.build();
        notificationManager.notify(110,stepNotification);
        // 参数一：唯一的通知标识；参数二：通知消息。
        startForeground(110, stepNotification);// 开始前台服务

    }




    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private BroadcastReceiver broadcastReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action  = intent.getAction();
         if (action.contains("XIANG")){
                mediaPlayer = MediaPlayer.create(MyService.this, R.raw.one);
                //  闹钟的时间到了
                showNotifi();
                mVibrator.vibrate(new long[]{100,100,100,1000},2);
                //时间一到跳转Activity,在这个Activity中播放音乐
                mediaPlayer.start();
                showDialogService();
            }else if (action.contains("BLE_END")){
//             View view = LayoutInflater.from(context).inflate(R.layout.qiangzhi_xiaxian_layout, null);
//
//                 final   MyDialog builderA = new MyDialog(context, 0, 0, view, R.style.MyDialog);
//                 builderA.setCancelable(false);
//                 Button btn_no_xian_ss = (Button) view.findViewById(R.id.btn_no_xian_ss);
//                 Button btn_yes_xiaxian_ss = (Button) view.findViewById(R.id.btn_yes_xiaxian_ss);
//                 TextView for_tv_titles = (TextView) view.findViewById(R.id.for_tv_titles);
//                 TextView text_for_tv = (TextView) view.findViewById(R.id.text_for_tv);
//
//                 for_tv_titles.setText("设备已断开");
//                 text_for_tv.setText("是否重新连接");
//                 //取消按钮
//                 btn_no_xian_ss.setOnClickListener(new View.OnClickListener() {
//                     @Override
//                     public void onClick(View v) {
//                         builderA.dismiss();
//                     }
//                 });
//
//
//                 //确认按钮
//                 btn_yes_xiaxian_ss.setOnClickListener(new View.OnClickListener() {
//                     @Override
//                     public void onClick(View v) {
//
//                         if (!TextUtils.isEmpty(userInfo.getStringInfo("mac")) && userInfo.getStringInfo("mac") != null) {
//                             Log.i("--->lastAddress", userInfo.getStringInfo("mac"));
//
//
//                             BleManger.getInstance().connectBle(userInfo.getStringInfo("mac"), new BleConnectResponse() {
//                                 @Override
//                                 public void onResponse(int code, BleGattProfile data) {
//                                     //成功
//                                     if (code == com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS) {
//                                         for (int i = 0; i < data.getServices().size(); i++) {
//                                             LogUtils.e("uuuid=====" + data.getServices().get(i).getUUID());
//                                         }
//                                         userInfo.setUserInfo("mac", userInfo.getStringInfo("mac"));
//                                         BleManger.getInstance().openNotify(userInfo.getStringInfo("mac"), Globals.SERVICE_UUID, Globals.Notifi_UUID);
//
//                                     }
//                                     //失败
//                                     else if (code == com.inuker.bluetooth.library.Constants.REQUEST_FAILED) {
//
//                                         LogUtils.e("连接失败======");
//                                     }
//                                 }
//                             });
//                         }
//                         builderA.dismiss();
//                     }
//                 });
//
//                 builderA.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//
//                 builderA.show();
//
         }
//
        }
    };

    private void showDialogService() {

        View view = LayoutInflater.from(this).inflate(R.layout.qiangzhi_xiaxian_layout,null);
        final MyDialog builder = new MyDialog(this, 0, 0, view, R.style.MyDialog);
        builder.setCancelable(false);
        Button btn_no_xian_ss= (Button) view.findViewById(R.id.btn_no_xian_ss);
        Button  btn_yes_xiaxian_ss= (Button) view.findViewById(R.id.btn_yes_xiaxian_ss);
        TextView  tvTitle = (TextView) view.findViewById(R.id.for_tv_titles);
        TextView  textForTv = (TextView) view.findViewById(R.id.text_for_tv);
        tvTitle.setText(getResources().getString(R.string.AppName));
        textForTv.setText(getResources().getString(R.string.sprotTime));

        //取消按钮
        btn_no_xian_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //声音停掉
                mediaPlayer.stop();
                //震动停掉
                mVibrator.cancel();
                builder.dismiss();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent1 =new Intent("XIANG");
                        sendBroadcast(intent1);
                    }
                },50000);



            }
        });


        //确认按钮
        btn_yes_xiaxian_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //声音停掉
                mediaPlayer.stop();
                //震动停掉
                mVibrator.cancel();

                builder.dismiss();

            }
        });
        builder.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        builder.show();

    }

    NaoZhong naoZhong;
    List<String> typeList =new ArrayList<>();
    List<String> listsContext =new ArrayList<>();
    List<String> listTime = new ArrayList<>();
    //当前时间
    Date date = new Date();

    private void  okHttpQueryTimes(){

        OkHttpClient client;
        client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();

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
                    if (naoZhong.getCode().equals("1")){
                        for (int i =0;i<naoZhong.getBody().getTrainlist().size();i++){
                            //先判断开关，如果开关状态是开的话
                            if (naoZhong.getBody().getTrainlist().get(i).getStatus()!=0){
                                //如果包含当前的时间
                                if (naoZhong.getBody().getTrainlist().get(i).getContent().contains(getWeekOfDate(date))){

                                    listTime.add(naoZhong.getBody().getTrainlist().get(i).getTime());
                                }
                            }

                        }

//                        Toast.makeText(MyService.this, "时间有哪些"+listTime.toString(), Toast.LENGTH_SHORT).show();
                        Log.i("------>时间有哪些",listTime.toString());
                        for (int i=0;i<listTime.size();i++){
                            String time =listTime.get(i);
                            c.set(Calendar.DATE, TimeUtil.ToPanduan(time));
                            String [] timeAry =time.split(":");
                            c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeAry[0]));
                            c.set(Calendar.MINUTE, Integer.parseInt(timeAry[1]));
                            //时间一到，发送广播（闹钟响了）
                            Intent intent = new Intent(MyService.this,Brobact.class);
                            //将来时态的跳转
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(MyService.this, 0x101, intent, 0);
                            //设置闹钟
                            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

                        }
                    }

                } else {
                    Log.i("--->", "错误的修改信息" + response.body().string());

                }
            }
        });
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    private  void  NaoZhong(){

        //获取当前系统的时间
        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        Log.i("------>hour",""+hour);
        int minute=calendar.get(Calendar.MINUTE);
        Log.i("------>minute",""+minute);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        //时间一到，发送广播（闹钟响了）
        Intent intent = new Intent(this,Brobact.class);
        //将来时态的跳转
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0x101, intent, 0);
        //设置闹钟
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }




}
