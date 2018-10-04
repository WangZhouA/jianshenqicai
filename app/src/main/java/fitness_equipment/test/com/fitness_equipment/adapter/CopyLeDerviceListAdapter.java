package fitness_equipment.test.com.fitness_equipment.adapter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.enitiy.Device;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by 陈姣姣 on 2017/9/12.
 */

public class CopyLeDerviceListAdapter extends BaseAdapter{
    // Adapter for holding devices found through scanning.
    private UserInfo userInfo ;
    private List<Device> mLeDevices;
    private LayoutInflater mInflator;
    private Activity mContext;


    public CopyLeDerviceListAdapter(List<Device> mLeDevices, Activity mContext) {
        this.mLeDevices = mLeDevices;
        this.mInflator = mInflator.from(mContext);
        this.mContext = mContext;
        userInfo=new UserInfo(mContext);

        IntentFilter filter = new IntentFilter();
        filter.addAction("CHENGGONG");//扫描完成

        mContext.registerReceiver(broadcastReceiver, filter);

    }

    @Override
    public int getCount() {
        return mLeDevices.size();
    }

    @Override
    public Object getItem(int i) {
        return mLeDevices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private boolean conntion;
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;

        if (view == null) {
            view = mInflator.inflate(R.layout.copy_item_search_dervice , null);
            viewHolder = new ViewHolder();
            viewHolder.deviceName = (TextView) view.findViewById(R.id.dervice_name);
            viewHolder.tv_type = (TextView) view.findViewById(R.id.contion_type);
            viewHolder.rlItem = (RelativeLayout) view.findViewById(R.id.rl_item );


            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.deviceName.setText(mLeDevices.get(i).getDname());

//        viewHolder.rlItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (conntion==false) {
//                    Globals.toothConnect.ConnectTODevice(mLeDevices.get(i).getDmac());
//                    conntion=true;
//                }else if (conntion==true){
//                    Globals.toothConnect.close();
//                    conntion=false;
//                }
//            }
//        });
//
//        viewHolder.tv_type.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Globals.toothConnect.disconnect();
//            }
//        });

        return view;
    }



    OkHttpClient client;
    private void okHttpAddDervice(String i) {
        client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        final String reigstStr = StringUtils.BINDER_DERVICE;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId",String.valueOf(userInfo.getIntInfo("id")));
            jsonObject.put("mac",i);
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
                    Log.i("----->添加设备","?");
                    if (result.contains("1")){
                        Log.i("----->添加设备成功","添加成功");
                        mContext.finish();
                    }
                }else {
                    Log.i("----->失败添加设备","?");
                }
            }
        });

    }


    class ViewHolder {
        TextView deviceName;
        RelativeLayout rlItem;
        TextView tv_type;


    }




    private BroadcastReceiver broadcastReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action =intent.getAction();
            if (action.contains("CHENGGONG")){
                String address =intent.getStringExtra("address");
                okHttpAddDervice(address);
            }
        }
    };

}
