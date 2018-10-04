package fitness_equipment.test.com.fitness_equipment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.saiyi.library.XPermissionActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import fitness_equipment.test.com.fitness_equipment.ble.BleManger;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import fitness_equipment.test.com.fitness_equipment.utils.DialogUtils;
import fitness_equipment.test.com.fitness_equipment.view.HandyTextView;
import fitness_equipment.test.com.fitness_equipment.view.MyDialog;
import okhttp3.OkHttpClient;


/**
 * Created by 陈姣姣 on 2017/10/17.
 */

public abstract class BasActivity extends XPermissionActivity{
    protected Dialog dialog;
    private Toast mToast;
    public OkHttpClient client;

    public UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getContentView());
        initState();
//        StatusBarCompat.compat(this, Color.WHITE);

        dialog = DialogUtils.createLoadingDialog(this);


        userInfo=new UserInfo(this);

        getSupportActionBar().hide();
        client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        MyApplication.getInstance() .addActyToList(this);
//        JPushInterface.init(getApplicationContext());
        checkAndroidMPermission();
        BleManger.getInstance().openBle(this);
        ButterKnife.bind(this);
        //用于显示当前位于哪个活动
        Log.e("BaseActivity", getClass().getSimpleName());
        init();

        /**
         * 第一步先判断是否有网络
         * */
        if (TureOrFalseNetwork() == true) {

        } else {

            Toast.makeText(this, getResources().getString(R.string.not_networks_conntion), Toast.LENGTH_SHORT).show();

        }
    }
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     *
     *  判断手机号码是否合法
     * */

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy销毁了", this.getPackageName());
        MyApplication.getInstance().removeActyFromList(this);
    }

    //注入布局
    protected abstract int getContentView();

    //初始化
    protected abstract void init();

    protected void showProgressDialog() {
        dialog.show();
    }

    public void finish() {
        super.finish();
        MyApplication.getInstance().removeActyFromList(this);
    }

    //控制返回

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            Log.e("onKeyDown销毁了", this.getPackageName());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 隐藏一个ProgressDialog
     */
    protected void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 方便的吐司显示，已经做了线程处理，因此可以直接在子线程中使用
     *
     * @param c
     *            需要显示的提示的内容
     */
    public void showToast(final CharSequence c) {
        if (Thread.currentThread().getName().equals("main")) {
            showCustomToast(c.toString());
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showCustomToast(c.toString());
                }
            });
        }
    }
    /** 显示自定义Toast提示(来自String) **/
    protected void showCustomToast(String text) {

        // 判断程序是否在前台运行 如果程序是在后台运行 不显示toast
        // if (!MyApplication.getInstance().isTopActivity()) {
        // return;
        // }
        View toastRoot = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.common_toast, null);
        ((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
        if (mToast != null) {
            ((HandyTextView) toastRoot.findViewById(R.id.toast_text))
                    .setText(text);
        } else {
            mToast = new Toast(getApplicationContext());
        }
        // mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(toastRoot);
        mToast.show(); // 显示toast信息

    }
    /**
     * 判断网络连接
     * */
    public boolean TureOrFalseNetwork() {

        ConnectivityManager mConnectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mTelephony = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        //检查网络连接
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();
        if (info == null || !mConnectivity.getBackgroundDataSetting()) {
            return false;
        }
        return true;
    }



    /**
     * 设置页面的Title
     *
     * @param title
     */
    public ImageButton header_left;
    public TextView header_title;
    public TextView header_right_title;
    public ImageButton header_right;

    public void initToolbar(String title) {

        View view = LayoutInflater.from(this).inflate(R.layout.header_bar, null);
        header_left = (ImageButton) view.findViewById(R.id.header_left);
        header_title = (TextView) view.findViewById(R.id.header_text);
        header_right = (ImageButton) view.findViewById(R.id.header_right);
        header_right_title = (TextView) view.findViewById(R.id.header_right_msg);
        if (TextUtils.isEmpty(title)) {
            header_title.setText(title);

        }
    }


    private void checkAndroidMPermission(){
        requestPermission(new String[]{
                Manifest.permission.CAMERA,//相机权限
                Manifest.permission.WRITE_EXTERNAL_STORAGE,//SD卡图库权限
                Manifest.permission.ACCESS_FINE_LOCATION//定位权限
        }, new PermissionHandler() {
            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied() {
                super.onDenied();
                Toast.makeText(BasActivity.this, getResources().getString(R.string.Refused_permission), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onNeverAsk() {
                permissionDialog(getResources().getString(R.string.Permission_to_apply),getResources().getString(R.string.Permission_prompting));
                return super.onNeverAsk();
            }
        });
    }

    protected void showDialog() {

        View view =LayoutInflater.from(this).inflate(R.layout.qiangzhi_xiaxian_layout,null);
        final MyDialog builder = new MyDialog(this, 0, 0, view, R.style.MyDialog);
        builder.setCancelable(false);
        Button btn_no_xian_ss= (Button) view.findViewById(R.id.btn_no_xian_ss);
        Button  btn_yes_xiaxian_ss= (Button) view.findViewById(R.id.btn_yes_xiaxian_ss);

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


            }
        });

        builder.show();
    }


      public  void  showMyLoadingDialog(){
          LemonBubble.getRoundProgressBubbleInfo()
                  .setTitle("Loading...").show(this,5000);
      }


      public  void  dissMissMyLoadingDialogTrue( final Activity activity){


          new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  LemonBubble.showRight(activity, "请求成功", 2000);
              }
          }, 2000);
      }

       public  void dissMissMyLoadingDialogFalse(){
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   LemonBubble.showError(getApplicationContext(), "请求失败", 2000);
               }
           }, 2000);
       }


    public static String getMD5(String info)
    {
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++)
            {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1)
                {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                }
                else
                {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            return "";
        }
        catch (UnsupportedEncodingException e)
        {
            return "";
        }
    }

    private File screenshot() {
        File file=null;
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if (bmp != null)
        {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + "screenshot.png";
                Log.i("------->截图路径","路径："+filePath);
                file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();


            } catch (Exception e) {
            }
        }
        return  file;
    }

    public   void   showDialogA(){


        View view = LayoutInflater.from(this).inflate(R.layout.dialog_shara,null);
        final MyDialog builder = new MyDialog(this, view, R.style.MyDialog,0);
        TextView tvXL = (TextView) view.findViewById(R.id.tv_xl);
        TextView tvQQ = (TextView) view.findViewById(R.id.tv_qq);
        TextView tvWX = (TextView) view.findViewById(R.id.tv_wx);
        TextView tvCancle = (TextView) view.findViewById(R.id.tv_cancleA);
        TextView tvZone  = (TextView) view.findViewById(R.id.tv_zone);
        TextView tvWechatt  = (TextView) view.findViewById(R.id.tv_wechatt);


        tvXL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                shareSina();
            }
        });
        tvQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                shareQQ();



            }
        });
        tvWX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();

            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();

            }
        });

        tvZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                shareQQZone();
            }
        });

        tvWechatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });



        builder.setCancelable(false);
        builder.show();




    }
    /**
     * 分享到QQ
     */
    public void shareQQZone(){
        UMImage image = new UMImage(this,screenshot());
        new ShareAction(this).setPlatform(SHARE_MEDIA.QZONE).
                withText("健身器材").withMedia(image)
                .setCallback(umShareListener).share();
    }
    /**
     * 分享到新浪
     */
    public void shareSina(){
        UMImage image = new UMImage(this,screenshot());
        new ShareAction(this).setPlatform(SHARE_MEDIA.SINA).
                withText("健身器材").withMedia(image)
                .setCallback(umShareListener).share();
    }

    /**
     * 分享到QQ空间
     */
    public void shareQQ(){
        UMImage image = new UMImage(this,screenshot());
        new ShareAction(this).setPlatform(SHARE_MEDIA.QQ).
                withText("健身器材").withMedia(image)
                .setCallback(umShareListener).share();
    }
    /**
     * 分享回调
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.e("------onStart","");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Log.e("------onResult","");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Log.e("------onError","");
            showToast(throwable.toString());

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.e("------onCancel","");
            showToast("取消了");
        }
    } ;



}
