package fitness_equipment.test.com.fitness_equipment.JarPhoto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.MyApplication;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.enitiy.MauchPicture;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import fitness_equipment.test.com.fitness_equipment.utils.HTTPRequestT;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static fitness_equipment.test.com.fitness_equipment.JarPhoto.Bimp.drr;

public class PublishedActivity extends BasActivity implements OnClickListener{

    @BindView(R.id.header_left)
    ImageButton headerLeft;
    @BindView(R.id.header_text)
    TextView headerText;
    @BindView(R.id.header_right_msg)
    TextView headerRightMsg;
    private NoScrollGridView noScrollgridview;
    private GridAdapter adapter;
    private TextView activity_selectimg_send;

    @BindView(R.id.ed_Published)
    EditText ed_Published;

    @BindView(R.id.login_btn)
    Button btn;

    List<String>listpic;



    private UserInfo userInfo;

    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
//                case 0:
//                    showToast("上传成功");
//                    finish();
//                    break;
//                case 1:
//                    showToast("上传失败");
//                    finish();
//                    break;
                case 2:

                    /**
                     *  请求成功
                     * */
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LemonBubble.showRight(PublishedActivity.this, getResources().getString(R.string.trueRequset), 1000);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
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
        return R.layout.activity_selectimg;
    }

    @Override
    protected void init() {
        Init();
    }

    public void Init() {
        headerText.setText(R.string.yijianfankui);
        headerRightMsg.setVisibility(View.VISIBLE);
        headerText.setTextColor(Color.parseColor("#19C49B"));

        listpic=new ArrayList<>();
        userInfo=new UserInfo(this);
        noScrollgridview = (NoScrollGridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.bmp.size()) {
                    new PopupWindows(PublishedActivity.this, noScrollgridview);
                } else {
                    Intent intent = new Intent(PublishedActivity.this,
                            PhotoActivity.class);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);

                }
            }
        });
        activity_selectimg_send = (TextView) findViewById(R.id.activity_selectimg_send);
        activity_selectimg_send.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

            }
        });
    }

    List<String> list;
    MauchPicture mauchPicture;
    @OnClick({R.id.header_left,R.id.header_right_msg,R.id.login_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.header_left:
                finish();
                MyApplication.getInstance().removeActyFromList(this);
                break;
            case R.id.login_btn:


                final List<Map>lists =new ArrayList<>();
                if (!TextUtils.isEmpty(ed_Published.getText().toString())) {
                    showMyLoadingDialog();
                    list = new ArrayList<String>();
                    for (int i = 0; i < drr.size(); i++) {
                        String str = drr.get(i);
                        HashMap<String,String> hashMaps=new HashMap<String,String>();
                        hashMaps.put("pic",str);
                        lists.add(hashMaps);
                    }

                    try {
                        new Thread(){
                            public void run(){
                                HashMap<String,Object>hashMap =new HashMap<>();
                                hashMap.put("file",lists);
                                HashMap<String, String>  map= new HashMap<>();
                                map.put("cookie", userInfo.getStringInfo("cookie"));
                                try {
                                    HttpURLConnection ht=      HTTPRequestT.getImgUpdateConn(StringUtils.UPDATE_PIC_MACUCH,map,hashMap,"POST");
                                    String resut= HTTPRequestT.getResponseString(ht);

                                    Log.i("---->resu","多图片"+resut);
                                    Gson gson =new Gson();
                                    mauchPicture=gson.fromJson(resut,MauchPicture.class);
                                    if (mauchPicture.getCode().equals("1")) {
                                        listpic =mauchPicture.getBody().getPhotolist();
                                        /**
                                         *  上传 内容
                                         * */

                                        okHttpContext();

                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    FileUtils.deleteDir();

                }else {

                    showToast("动态不能为空！");
                }
                break;

        }
    }

    private void okHttpContext() {
        JSONObject jsonObject = new JSONObject();
        try {
            // 返回一个JSONArray对象
            JSONArray jsonArray = new JSONArray();
            for (int i =0;i<listpic.size();i++){
                jsonArray.put(i,listpic.get(i));
            }
            jsonObject.put("photolist",jsonArray);
            jsonObject.put("content",ed_Published.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Log.i("---->jsonObject",jsonObject.toString());
        final Request request = new Request.Builder()
                .url(StringUtils.YIJIANFANKUI)
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
                    Log.i("-------->意见反馈",result);
                    Message msg =handler.obtainMessage();
                    msg.what=2;
                    handler.sendMessage(msg);
                }
            }
        });
    }


    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater; // 视图容器
        private int selectedPosition = -1;// 选中的位置
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            return (Bimp.bmp.size() + 1);
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        /**
         * ListView Item设置
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int coord = position;
            ViewHolder holder = null;
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.bmp.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.mipmap.add_images));
                if (position == 4) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.bmp.get(position));
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == drr.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            try {
                                String path = drr.get(Bimp.max);
                                System.out.println(path);
                                Bitmap bm = Bimp.revitionImageSize(path);
                                Bimp.bmp.add(bm);
                                String newStr = path.substring(
                                        path.lastIndexOf("/") + 1,
                                        path.lastIndexOf("."));
                                FileUtils.saveBitmap(bm, "" + newStr);
                                Bimp.max += 1;
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            } catch (IOException e) {

                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View
                    .inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(LayoutParams.FILL_PARENT);
            setHeight(LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(PublishedActivity.this,
                            TestPicActivity.class);
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    private static final int TAKE_PICTURE = 0x000000;
    Uri photoUri;


    String strPhotoName = System.currentTimeMillis()+".jpg";
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/MyPhoto/";
    String filePath  = savePath + strPhotoName; //图片路径

    public void photo() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            photoUri = patrUri("file2");
            startCamera(TAKE_PICTURE,photoUri);
        }else{

            // TODO: 2017/10/25  没有相机权限
        }
    }
    //相机
    private void startCamera(int requestCode, Uri photoUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
        startActivityForResult(intent, requestCode);

    }
    private Uri patrUri(String fileName) {
        // TODO Auto-generated method stub
        String strPhotoName = fileName+System.currentTimeMillis()+".jpg";
        String savePath = Environment.getExternalStorageDirectory().getPath()
                + "/MyPhoto/";
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        filePath = savePath + strPhotoName;
        return Uri.fromFile(new File(dir, strPhotoName));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    if (drr.size() < 9 && resultCode == -1) {
                        drr.add(filePath);
                    }

                }
                break;
        }
    }

    boolean toback;
    @Override
    public void onBackPressed() {
        if (toback == false) {
            finish();
        }

        else  if (toback==true){
            super.onBackPressed();
        }
    }
}
