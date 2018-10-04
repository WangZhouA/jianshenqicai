package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.photo.LogUtils;
import fitness_equipment.test.com.fitness_equipment.photo.PhotoClipActivity;
import fitness_equipment.test.com.fitness_equipment.photo.PhotoUtil;
import fitness_equipment.test.com.fitness_equipment.utils.TimeUtil;
import fitness_equipment.test.com.fitness_equipment.view.adress_seletor.OptionsPickerView;
import fitness_equipment.test.com.fitness_equipment.view.widget.CustomDatePicker;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 陈姣姣 on 2017/11/16.
 */

public class UserSetActivity extends BasActivity {
    @BindView(R.id.relative_Head)
    RelativeLayout relativeHead;
    @BindView(R.id.tv_set_name)
    TextView tvSetName;
    @BindView(R.id.relative_name)
    RelativeLayout relativeName;
    @BindView(R.id.tv_set_gender)
    TextView tvSetGender;
    @BindView(R.id.relative_gender)
    RelativeLayout relativeGender;
    @BindView(R.id.tv_set_age)
    TextView tvSetAge;
    @BindView(R.id.relative_age)
    RelativeLayout relativeAge;
    @BindView(R.id.tv_set_email)
    TextView tvSetEmail;
    @BindView(R.id.relative_email)
    RelativeLayout relativeEmail;
    @BindView(R.id.tv_set_adress)
    TextView tvSetAdress;
    @BindView(R.id.relative_adress)
    RelativeLayout relativeAdress;

    Intent intent;
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

    @BindView(R.id.img_ss)
    ImageView imgSS;


    //头像处理类
    private PhotoUtil photoUtil;
    //时间选择器
    private CustomDatePicker customDatePicker;


    // 省数据集合
    private ArrayList<String> mListProvince = new ArrayList<String>();
    // 市数据集合
    private ArrayList<ArrayList<String>> mListCiry = new ArrayList<ArrayList<String>>();
    // 区数据集合
    private ArrayList<ArrayList<ArrayList<String>>> mListArea = new ArrayList<ArrayList<ArrayList<String>>>();

    private OptionsPickerView<String> mOpv;

    private int  flag;  //点击的是哪个选择框
    @BindView(R.id.ldid)
    TextView ldid_tv;


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
                            LemonBubble.showRight(UserSetActivity.this, getResources().getString(R.string.trueRequset), 1000);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    OkHttpUpdateUi();
                                }
                            },1000);
                        }
                    }, 2000);

                    break;
                case 1:

//                    /**
//                     *  请求成功
//                     * */
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            LemonBubble.showRight(UserSetActivity.this, getResources().getString(R.string.trueRequset), 1000);
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {

                    ldid = ldid.replaceAll("3","*");
                    ldid = ldid.replaceAll("4","*");
                    ldid_tv.setText(ldid);

                    if (!TextUtils.isEmpty(name)){
                        tvSetName.setText(name);
                    }else {
                        tvSetName.setText(ldid);
                    }

                    if (sex.contains("null") ){
                        tvSetGender.setText("未填写");
                    }else {
                        tvSetGender.setText(sex);
                    }

                    if (age.contains("null")){
                        tvSetAge.setText("未填写");
                    }else {
                        tvSetAge.setText(age);
                    }
                    if (heading.contains("null")){
                    }else {
                        StringUtils.showImage(UserSetActivity.this,StringUtils.GET_PHOTO+heading,R.mipmap.female_head3,R.mipmap.female_head3,imgSS);
                    }



                    if (TextUtils.isEmpty(email)){
                        tvSetEmail.setText("未填写");
                    }else {
                        tvSetEmail.setText(email);
                    }


                    if (TextUtils.isEmpty(address)){
                        tvSetAdress.setText("未填写");
                    }else {
                        tvSetAdress.setText(address);
                    }

                    if (userInfo.getStringInfo("Num").contains("1")){
                    }else {
                        tvSetEmail.setText(email);
                        tvSetEmail.setEnabled(false);
                    }





//
//                                }
//                            }, 1000);
//                        }
//                    }, 2000);

                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(broadcastReceiver);
    }

    /**
     * 去选择性别
     * */
    private OptionsPickerView<String> mOpvsGender;
    private ArrayList<String> mListGender = new ArrayList<String>();
    private  void setGender(){
        mOpvsGender = new OptionsPickerView<String>(this);
        // 设置标题
        mOpvsGender.setTitle(getResources().getString(R.string.Select_gender));
        mListGender.add(getResources().getString(R.string.boboby));
        mListGender.add(getResources().getString(R.string.girl));

        // 设置三级联动效果
        mOpvsGender.setPicker(mListGender);

        // 设置是否循环滚动
        mOpvsGender.setCyclic(false);
        // 设置默认选中的三级项目
        mOpvsGender.setSelectOptions(0);

        // 监听确定选择按钮
        mOpvsGender.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                if (options1==0){
                    tvSetGender.setText(R.string.boboby);
                    OkHttpUpUserMsg();
                }else {
                    tvSetGender.setText(R.string.girl);
                    OkHttpUpUserMsg();
                }
            }
        });
    }



    //设置城市选择器
    private void setCityPickView(){

        // 初始化Json对象
        initJsonData();

        // 初始化Json数据
        try {
            initJsonDatas();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        mOpv = new OptionsPickerView<String>(this);

        // 设置标题
        mOpv.setTitle(getResources().getString(R.string.Select_city));

        // 设置三级联动效果
        mOpv.setPicker(mListProvince, mListCiry, mListArea, true);

        // 设置是否循环滚动
        mOpv.setCyclic(false, false, false);

        // 设置默认选中的三级项目
        mOpv.setSelectOptions(0, 0, 0);


        // 监听确定选择按钮
        mOpv.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                // 返回的分别是三个级别的选中位置
                String tx = mListProvince.get(options1)
                        + mListCiry.get(options1).get(option2)
                        + mListArea.get(options1).get(option2).get(options3);
//				babyEd.putString("Mailingcity",mListCiry.get(options1).get(option2));
//				babyEd.commit();
                tvSetAdress.setText(tx);
                OkHttpUpUserMsg();
            }
        });

    }



    @Override
    protected int getContentView() {
        return R.layout.activity_userset;
    }

    @Override
    protected void init() {
        headerText.setText(getResources().getString(R.string.user_set));
        headerText.setTextColor(Color.parseColor("#19C49B"));
        initDatePicker(); //设置时间
        setCityPickView(); //设置城市
        setGender();//设置性别

        IntentFilter intentFilter =new IntentFilter("SHUAXIN");
        registerReceiver(broadcastReceiver,intentFilter);

        /**
         * 刷新Ui
         * */

        if (userInfo.getStringInfo("Num").contains("2")){

            relativeEmail.setEnabled(false);
        }

        tvSetAge.setText(TimeUtil.getCurrentTime());
        OkHttpUpdateUi();

    }

    String ldid;
    String name;
    String sex;
    String age;
    String email;
    String address;
    String heading;



    private void OkHttpUpdateUi() {

        OkHttpClient client; client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",userInfo.getIntInfo("id"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        Log.d("------->", "cookie:challenge " + userInfo.getStringInfo("cookie"));
        final Request request = new Request.Builder()
                .url(StringUtils.QUERY_USER_DATE)
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
                    Log.i("----->查询用户信息",result);
                    try {
                        JSONObject jsonObject =new JSONObject(result);
                        JSONObject twoJsonOject =jsonObject.getJSONObject("body");
                        JSONObject itemJson =twoJsonOject.getJSONObject("Userinfo");
                        ldid = itemJson.getString("ldid");
                        name=itemJson.getString("niken");
                        sex=itemJson.getString("sex");
                        age=itemJson.getString("birthday");
                        email=itemJson.getString("email");
                        address=itemJson.getString("address");
                        heading= itemJson.getString("heading");
                        Message msg =handler.obtainMessage();
                        msg.what=1;
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });




    }


    @OnClick({R.id.relative_Head, R.id.relative_name, R.id.relative_gender, R.id.relative_age, R.id.relative_email, R.id.relative_adress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_Head:
                flag=0;
                photoUtil = new PhotoUtil(UserSetActivity.this);
                photoUtil.showDialog("图册","拍照");
                break;
            case R.id.relative_name:
                flag=1;
                intent = new Intent(this, SetUserMessageActivity.class);
                intent.putExtra("flag","name");
                startActivity(intent);
                break;
            case R.id.relative_gender:
                flag=2;
                mOpvsGender.show();
                break;
            case R.id.relative_age:

                flag=3;
                // 日期格式为yyyy-MM-dd
                customDatePicker.show(tvSetAge.getText().toString());
                break;
            case R.id.relative_email:

                flag=4;
                intent = new Intent(this, SetUserMessageActivity.class);
                intent.putExtra("flag","email");
                startActivity(intent);
                break;
            case R.id.relative_adress:
                flag=5;
                mOpv.show();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        // 相册返回
        if (PhotoUtil.CAMRA_SETRESULT_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                // 相册选中图片路径
                String cameraPath = photoUtil.getCameraPath(data);
                Bitmap bitmap = photoUtil.readBitmapAutoSize(cameraPath);
                String str = photoUtil.bitmaptoString(bitmap);
                LogUtils.d("相相册选中路径  = " + cameraPath);
                startClipActivity(cameraPath);
            }
        }
        // 相机返回
        else if (PhotoUtil.PHOTO_SETRESULT_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                String photoPath = photoUtil.getPhotoPath();
                Bitmap bitmap = photoUtil.readBitmapAutoSize(photoPath);
                String str = photoUtil.bitmaptoString(bitmap);
                LogUtils.d("相机选中路径  = " + photoPath);
                startClipActivity(photoPath);

            }
        }
        // 裁剪返回
        else if (PhotoUtil.PHOTO_CORPRESULT_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                LogUtils.d("裁剪返回  = ");
                String path = data.getStringExtra("path");
//                BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
//                Bitmap bitmap = bitmapUtils.decodeFile(path);
//                user_touxiang.setImageBitmap(bitmap);
//                //吧图片转成了base64字符串
//                base64code= BitmapUtil.bitmaptoString(bitmap);
                /**
                 *  //去上传图片
                 * */
                File file =new File(path);
                Log.i("--->pathss",path);
                okHttpUpload("pic",file);

            }
        }

    }


    public static final String MULTIPART_FORM_DATA = "image/jpg";       // 指明要上传的文件格式

    //拿到返回的图片参数给下个接口请求使用
    String url;
    public  void okHttpUpload(String partName, File file){
        // 需要上传的文件
        RequestBody requestFile =               // 根据文件格式封装文件
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);

        // 初始化请求体对象，设置Content-Type以及文件数据流
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)            // multipart/form-data
                .addFormDataPart(partName, file.getName(), requestFile)
                .build();

        // 封装OkHttp请求对象，初始化请求参数
        Request request = new Request.Builder()
                .url(StringUtils.UPDATE_PICTURE)   // 上传url地址
                .post(requestBody).addHeader("cookie", userInfo.getStringInfo("cookie"))              // post请求体
                .build();
//        showLoadDialog("图片上传中...", true);

        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient  = httpBuilder
                .connectTimeout(10000, TimeUnit.SECONDS)          // 设置请求超时时间
                .writeTimeout(10000,TimeUnit.SECONDS)
                .build();
        // 发起异步网络请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()){
                    String result =response.body().string();
                    Log.i("--->result",result);
                    try {
                        JSONObject jsonObject =new JSONObject(result);
                        String code = jsonObject.getString("code");
                        JSONObject itemJobject =jsonObject.getJSONObject("body");
                        url = itemJobject.getString("url");
                        showToast(url);
                        if (code.contains("1")){
                            OkHttpUpUserMsg();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("--->","失败"+e.toString());
            }
        });
    }

    private void OkHttpUpUserMsg() {

//        showMyLoadingDialog();

        JSONObject jsonObject = new JSONObject();
        try {
            if (flag==0) {
                jsonObject.put("id", userInfo.getIntInfo("id"));
                jsonObject.put("heading", url);
            }else if (flag==2){
                jsonObject.put("id", userInfo.getIntInfo("id"));
                jsonObject.put("sex", tvSetGender.getText().toString());
            }else if (flag==3){
                jsonObject.put("id", userInfo.getIntInfo("id"));
                jsonObject.put("birthday", tvSetAge.getText().toString());
            }else if (flag==5){
                jsonObject.put("id", userInfo.getIntInfo("id"));
                jsonObject.put("address", tvSetAdress.getText().toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        Log.d("------->", "cookie:challenge " + userInfo.getStringInfo("cookie"));
        final Request request = new Request.Builder()
                .url(StringUtils.USER_DATE)
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
                    Log.i("---->图片是否上传成功","true"+result);
                    Message msg = handler.obtainMessage();
                    msg.what=0;
                    handler.sendMessage(msg);
                }
            }
        });
    }


    //点击跳转到图片处理的界面
    public void startClipActivity(String path) {

        Intent intent = new Intent(this, PhotoClipActivity.class);
        intent.putExtra("path", path);
        startActivityForResult(intent, PhotoUtil.PHOTO_CORPRESULT_CODE);
    }


    @OnClick(R.id.header_left)
    public void onViewClicked() {
        finish();
    }




    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        tvSetAge.setText(now.split(" ")[0]);
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvSetAge.setText(time.split(" ")[0]);
                OkHttpUpUserMsg();
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(false); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动


    }


    private JSONArray mjsonArray;
    /** 初始化Json数据，并释放Json对象 */
    private void initJsonDatas() throws JSONException {
        try {
            for (int i = 0; i < mjsonArray.length(); i++) {
                JSONObject jsonObject = mjsonArray.getJSONObject(i);
                String province = jsonObject.getString("stateName");

                ArrayList<String> options2Items_01 = new ArrayList<String>();
                ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
                JSONArray jsonCs = jsonObject.getJSONArray("cities");
                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonC = jsonCs.getJSONObject(j);// 获取每个市的Json对象
                    String city = jsonC.getString("cityName");
                    options2Items_01.add(city);// 添加市数据

                    ArrayList<String> options3Items_01_01 = new ArrayList<String>();
                    JSONArray jsonAs = jsonC.getJSONArray("areas");


                    for (int k = 0; k < jsonAs.length(); k++) {
                        JSONObject obj = jsonAs.getJSONObject(k);
                        String areaName = obj.getString("areaName");
                        options3Items_01_01.add(areaName);// 添加区数据
                        Log.i("--->obj",obj.toString());

                    }
                    options3Items_01.add(options3Items_01_01);

                }

                mListProvince.add(province);// 添加省数据
                mListCiry.add(options2Items_01);
                mListArea.add(options3Items_01);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        mjsonArray = null;
    }



    /** 从assert文件夹中读取省市区的json文件，然后转化为json对象 */
    private void initJsonData() {
        try {
            getFileContent(getResources().getAssets().open("state.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = getAssets().open("state.json");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "UTF-8"));
            }
            is.close();
            mjsonArray=new JSONArray(content);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    InputStream  is =null;
    String content= null;
    private String  getFileContent( InputStream is){
        try {

            if (is!=null){

                ByteArrayOutputStream baos =new ByteArrayOutputStream();
                int i =-1;
                while ((i=is.read())!=-1){
                    baos.write(i);
                }
                content=baos.toString();
                baos.flush();
                baos.close();
                is.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return  content;
    }

    private BroadcastReceiver broadcastReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action =intent.getAction();

            if (action.contains("SHUAXIN")){

                OkHttpUpdateUi();
            }

        }
    };

}
