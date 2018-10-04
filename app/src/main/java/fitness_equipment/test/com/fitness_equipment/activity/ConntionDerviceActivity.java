package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.adapter.BleAdapter;
import fitness_equipment.test.com.fitness_equipment.ble.BleBean;
import fitness_equipment.test.com.fitness_equipment.ble.BleManger;
import fitness_equipment.test.com.fitness_equipment.ble.Globals;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.photo.LogUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ConntionDerviceActivity extends BasActivity {


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
    @BindView(R.id.listviews)
    ListView listviews;

    @BindView(R.id.img_shua)
    ImageView imgShua;
    LoadingDailog dialog;
    String mac;

    // 新建一个list专门用来存储设备的名字,用于判断使用

    List<BleBean> bleBeanList = new ArrayList<>();
    boolean isSearch = false;
    BleAdapter bleAdapter;

    @Override
    protected int getContentView() {

        return R.layout.activity_conntion_dervice;
    }

    @Override
    protected void init() {
        headerText.setText(R.string.myDervice);
        headerText.setTextColor(Color.parseColor("#19C49B"));
        headerRight.setImageResource(R.mipmap.equipment);
        headerRight.setVisibility(View.VISIBLE);
        bleAdapter = new BleAdapter(this, bleBeanList);
        listviews.setAdapter(bleAdapter);
        searchBle();
        showMyLoadingDialog();

        listviews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(ConntionDerviceActivity.this)
                        .setMessage("加载中...")
                        .setCancelable(true)
                        .setCancelOutside(true);
                 dialog=loadBuilder.create();
                 dialog.show();


                mac = bleBeanList.get(position).getAddress();
                BleManger.getInstance().connectBle(bleBeanList.get(position).getAddress(), new BleConnectResponse() {
                    @Override
                    public void onResponse(int code, BleGattProfile data) {
                        LogUtils.e("code======" + code);

                        //成功
                        if (code == com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS) {
                            for (int i = 0; i < data.getServices().size(); i++) {
                                LogUtils.e("uuuid=====" + data.getServices().get(i).getUUID());
                            }
                            Toast.makeText(ConntionDerviceActivity.this, getResources().getString(R.string.ToConntion), Toast.LENGTH_SHORT).show();
                            okHttpAddDervice(mac);
                            userInfo.setUserInfo("mac", mac);
                            BleManger.getInstance().openNotify(mac, Globals.SERVICE_UUID, Globals.Notifi_UUID);

                        }
                        //失败
                        else if (code == com.inuker.bluetooth.library.Constants.REQUEST_FAILED) {

                            LogUtils.e("连接失败======");
                            Toast.makeText(ConntionDerviceActivity.this, getResources().getString(R.string.NoToConntion), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });


    }


    @OnClick({R.id.header_left, R.id.header_right, R.id.img_shua})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.img_shua:
                showMyLoadingDialog();
                searchBle();
                break;
            case R.id.header_right:

                Intent intent = new Intent(this, AddDerviceActivity.class);
                startActivity(intent);

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void searchBle() {
        bleBeanList.clear();
        bleAdapter.updataAdapter(bleBeanList);

        BleManger.getInstance().startSearch(new SearchResponse() {
            @Override
            public void onSearchStarted() {
                isSearch = true;
            }

            @Override
            public void onDeviceFounded(SearchResult device) {
                BleBean bleBean;
                if (device.getName().equals("NULL")) {
                    bleBean = new BleBean(device.getName(), device.getAddress());
                } else {
                    bleBean = new BleBean(device.getName(), device.getAddress());
                }

                if (!isContains(bleBeanList, bleBean)) {
                    bleBeanList.add(bleBean);
                }
                //
                bleAdapter.updataAdapter(bleBeanList);
            }

            @Override
            public void onSearchStopped() {
            }

            @Override
            public void onSearchCanceled() {
            }
        });
    }

    /**
     * 判断是否已经有了
     */
    boolean isContains(List<BleBean> beanList, BleBean bleBean) {
        boolean isContains = false;
        for (int i = 0; i < beanList.size(); i++) {
            if (beanList.get(i).isCommon(bleBean)) {
                isContains = true;
                break;
            }
        }
        return isContains;
    }


    private void okHttpAddDervice(String i) {
        client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        final String reigstStr = StringUtils.BINDER_DERVICE;



        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", String.valueOf(userInfo.getIntInfo("id")));
            jsonObject.put("mac", i);
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
                    Log.i("----->添加设备", "?");
                    if (result.contains("1")) {
                        Log.i("----->添加设备成功", "添加成功");
                        finish();
                    }
                } else {
                    Log.i("----->失败添加设备", "?");
                }
            }
        });
    }
}