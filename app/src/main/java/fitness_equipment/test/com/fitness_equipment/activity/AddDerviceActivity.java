package fitness_equipment.test.com.fitness_equipment.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.adapter.Addadapter;
import fitness_equipment.test.com.fitness_equipment.chlibrary.src.com.baoyz.swipemenulistview.SwipeMenu;
import fitness_equipment.test.com.fitness_equipment.chlibrary.src.com.baoyz.swipemenulistview.SwipeMenuCreator;
import fitness_equipment.test.com.fitness_equipment.chlibrary.src.com.baoyz.swipemenulistview.SwipeMenuItem;
import fitness_equipment.test.com.fitness_equipment.chlibrary.src.com.baoyz.swipemenulistview.SwipeMenuListView;
import fitness_equipment.test.com.fitness_equipment.enitiy.AddDervice;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.view.MyDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddDerviceActivity extends BasActivity {


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
    @BindView(R.id.tv_table_bicycle)
    TextView tvTableBicycle;
    @BindView(R.id.bicycle_listviews)
    SwipeMenuListView bicycleListviews;
    @BindView(R.id.lin_bicycle)
    LinearLayout linBicycle;
    @BindView(R.id.tv_table_rope)
    TextView tvTableRope;
    @BindView(R.id.rope_listviews)
    SwipeMenuListView ropeListviews;
    @BindView(R.id.lin_rope)
    LinearLayout linRope;


    Addadapter addadapter;
    List<AddDervice.BodyBean.BangdinglistBean.EquListBean> lists;
    List<AddDervice.BodyBean.BangdinglistBean.EquListBean> listsTname;
    @BindView(R.id.rl_mai_shebei)
    RelativeLayout rlMaiShebei;


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    linBicycle.setVisibility(View.VISIBLE);
                    addadapter = new Addadapter(lists, AddDerviceActivity.this);
                    bicycleListviews.setAdapter(addadapter);
                    addadapter.notifyDataSetChanged();

                    break;
                case 1:
                    linRope.setVisibility(View.VISIBLE);
                    addadapter = new Addadapter(listsTname, AddDerviceActivity.this);
                    ropeListviews.setAdapter(addadapter);
                    addadapter.notifyDataSetChanged();

                    break;
                case 2:

                    linBicycle.setVisibility(View.GONE);
                    linRope.setVisibility(View.GONE);
                    showToast(getResources().getString(R.string.No_data));
                    break;
                case 3:

                    /**
                     *  请求成功
                     * */
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LemonBubble.showRight(AddDerviceActivity.this, getResources().getString(R.string.trueRequset), 1000);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    lists.clear();
                                    listsTname.clear();
                                    if (addDervice.getBody().getBangdinglist().size() > 0) {
                                        for (int i = 0; i < addDervice.getBody().getBangdinglist().size(); i++) {
                                            if (addDervice.getBody().getBangdinglist().get(i).getTname().contains("自行车")) {
                                                lists.addAll(addDervice.getBody().getBangdinglist().get(i).getEquList());
                                                Message msg = handler.obtainMessage();
                                                msg.what = 0;
                                                handler.sendMessage(msg);
                                            }
                                            if (addDervice.getBody().getBangdinglist().get(i).getTname().contains("跳绳")) {
                                                listsTname.addAll(addDervice.getBody().getBangdinglist().get(i).getEquList());
                                                Message msg = handler.obtainMessage();
                                                msg.what = 1;
                                                handler.sendMessage(msg);
                                            }
                                        }
                                    } else {

                                        Message msg = handler.obtainMessage();
                                        msg.what = 2;
                                        handler.sendMessage(msg);

                                    }
                                }
                            }, 1000);
                        }
                    }, 2000);

                    break;

                case 4:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LemonBubble.showRight(AddDerviceActivity.this, getResources().getString(R.string.trueRequset), 1000);

                        }
                    }, 2000);

                    okHttpQueryDerviceBinder();

                    break;

            }
        }
    };


    @Override
    protected int getContentView() {
        return R.layout.activity_add_dervice;
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void initView() {


        //加入侧滑显示的菜单
        //1.首先实例化SwipeMenuCreator对象
        SwipeMenuCreator creater = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {


                SwipeMenuItem item1 = new SwipeMenuItem(AddDerviceActivity.this);
                item1.setBackground(new ColorDrawable(Color.parseColor("#FFCD00")));
                item1.setWidth(dp2px(72));
                item1.setTitle(getResources().getString(R.string.ReName));
                item1.setTitleSize(17);
                item1.setTitleColor(Color.WHITE);
                menu.addMenuItem(item1);


                SwipeMenuItem reName = new SwipeMenuItem(AddDerviceActivity.this);
                reName.setBackground(new ColorDrawable(Color.parseColor("#FE3824")));
                reName.setWidth(dp2px(72));
                reName.setTitle(getResources().getString(R.string.delete));
                reName.setTitleSize(17);
                reName.setTitleColor(Color.WHITE);
                menu.addMenuItem(reName);


            }
        };
        // set creator
        bicycleListviews.setMenuCreator(creater);
        ropeListviews.setMenuCreator(creater);


        //2.菜单点击事件
        bicycleListviews.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                AddDervice.BodyBean.BangdinglistBean.EquListBean item = lists.get(position);

                switch (index) {

                    case 0:
                        showMyDialog(position);
                        break;
                    case 1:

                        //删除设备
//                        deleteDervice(position);
                        lists.remove(position);
                        addadapter.notifyDataSetChanged();
                        break;

                }
                return false;
            }
        });
        ropeListviews.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                AddDervice.BodyBean.BangdinglistBean.EquListBean item = lists.get(position);

                switch (index) {

                    case 0:
                        showMyDialog(position);

                        break;
                    case 1:
                        //修改设备名字
                        deleteDervice(position);


                        break;

                }
                return false;
            }
        });

    }

    private void deleteDervice(final int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.for_play_item_dialog, null);
        final MyDialog builder = new MyDialog(this, 0, 0, view, R.style.MyDialog);
        builder.setCancelable(false);
        Button btn_no_xian_ss = (Button) view.findViewById(R.id.btn_no_o);
        Button btn_yes_xiaxian_ss = (Button) view.findViewById(R.id.btn_yes_o);
        final EditText for_ed_s = (EditText) view.findViewById(R.id.for_ed_s);
        TextView for_tv_s = (TextView) view.findViewById(R.id.for_tv_s);
        for_tv_s.setText(R.string.ToDeleteDervice);
        for_ed_s.setVisibility(View.GONE);

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
                if (for_ed_s.getText().toString() != null) {
                    showMyLoadingDialog();
                    okHttpDelet(position);
                    builder.dismiss();
                } else {
                    showToast(getResources().getString(R.string.inputNoNull));
                }
            }
        });

        builder.show();


    }


    private void okHttpDelet(final int position) {
        final String reigstStr = StringUtils.DELETE_DERVICE;
        JSONObject jsonObject = new JSONObject();

        Log.i("------>设备id", "" + lists.get(position).getId());
        try {

            jsonObject.put("equipmentId", String.valueOf(lists.get(position).getId()));
            jsonObject.put("userId", String.valueOf(userInfo.getIntInfo("id")));

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

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("--->", "" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.i("------>result删除设备id", result);
                    try {
                        JSONObject obj = new JSONObject(result);
                        if (obj.getString("code").equals("1")) {

                            Message msg = handler.obtainMessage();
                            msg.what = 5;
                            handler.sendMessage(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * 修改设备名字
     */
    private void showMyDialog(final int postion) {

        View view = LayoutInflater.from(this).inflate(R.layout.for_play_item_dialog, null);
        final MyDialog builder = new MyDialog(this, 0, 0, view, R.style.MyDialog);
        builder.setCancelable(false);
        Button btn_no_xian_ss = (Button) view.findViewById(R.id.btn_no_o);
        Button btn_yes_xiaxian_ss = (Button) view.findViewById(R.id.btn_yes_o);
        final EditText for_ed_s = (EditText) view.findViewById(R.id.for_ed_s);

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
                if (for_ed_s.getText().toString() != null) {
                    showMyLoadingDialog();
                    okHttpRePhone(postion, for_ed_s.getText().toString());
                    builder.dismiss();
                } else {
                    showToast(getResources().getString(R.string.inputNoNull));
                }
            }
        });

        builder.show();

    }


    /***
     * 修改名字哦
     * */

    private void okHttpRePhone(int position, String name) {

        final String reigstStr = StringUtils.RENAME_DERVICE;
        JSONObject jsonObject = new JSONObject();

        Log.i("------>设备id", "" + lists.get(position).getId());
        try {

            jsonObject.put("id", String.valueOf(lists.get(position).getId()));
            jsonObject.put("name", name);

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

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("--->", "" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.i("------>result修改设备id", result);
                    try {
                        JSONObject obj = new JSONObject(result);
                        if (obj.getString("code").equals("1")) {
                            Message msg = handler.obtainMessage();
                            msg.what = 4;
                            handler.sendMessage(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    @Override
    protected void init() {

        lists = new ArrayList<>();
        listsTname = new ArrayList<>();
        headerText.setText(R.string.myDervice);
        headerText.setTextColor(Color.parseColor("#19C49B"));

        initView();

        showMyLoadingDialog();
        okHttpQueryDerviceBinder();
    }

    AddDervice addDervice;

    private void okHttpQueryDerviceBinder() {

        final String reigstStr = StringUtils.QUERY_BINDER_DERVICE;
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("userId", String.valueOf(userInfo.getIntInfo("id")));

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

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("--->", "" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.i("------>result设备", result);
                    Gson gson = new Gson();
                    addDervice = gson.fromJson(result, AddDervice.class);
                    if (addDervice.getCode().equals("1")) {
                        Message msgs = new Message();
                        msgs.what = 3;
                        handler.sendMessage(msgs);

                    }
                }
            }
        });
    }

    @OnClick({R.id.header_left,R.id.rl_mai_shebei})
    public void onViewClicked(View view) {

      switch (view.getId()){

          case R.id.header_left:
              finish();
              break;

          case R.id.rl_mai_shebei:
            showToast(getResources().getString(R.string.nuli));
              break;



      }

    }


}
