package fitness_equipment.test.com.fitness_equipment.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.activity.MonmentListActivity;
import fitness_equipment.test.com.fitness_equipment.enitiy.BangDan;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import fitness_equipment.test.com.fitness_equipment.view.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 陈姣姣 on 2017/12/15.
 */

public class MontBangdangAdapter  extends BaseAdapter{

    List<BangDan.BodyBean.ListBean> lists;
    Context context;
    LayoutInflater layoutInflater;
    boolean ischeck;
    private UserInfo userInfo;



    private Handler handler =new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case 0:
                    /**
                     *  去刷新排行榜
                     * */
                    Intent inten =new Intent("DELETE");
                    inten.putExtra("DELETE", MonmentListActivity.getNowTime());
                    context.sendBroadcast(inten);
                    break;

            }
        }
    };


    public MontBangdangAdapter(List<BangDan.BodyBean.ListBean> lists, Context context) {
        this.lists = lists;
        this.context = context;
        this.layoutInflater=layoutInflater.from(context);
        userInfo=new UserInfo(context);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final   BangDan.BodyBean.ListBean bangdan= lists.get(position);
        final   ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_day_paihangbang,null);
            viewHolder.bangdan_number= (TextView) convertView.findViewById(R.id.bangdan_number_id);
            viewHolder.bangdan_img= (CircleImageView) convertView.findViewById(R.id.bangdan_img);
            viewHolder.bangdan_name= (TextView) convertView.findViewById(R.id.bangdan_name);
            viewHolder.bangdan_kaicls= (TextView) convertView.findViewById(R.id.bangdan_kaicls);
            viewHolder.bangdan_people= (TextView) convertView.findViewById(R.id.bangdan_people);
            viewHolder.bangdan_dianzang= (ImageView) convertView.findViewById(R.id.bangdan_xin);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.bangdan_people.setText(bangdan.getBallot());
        viewHolder.bangdan_number.setText(bangdan.getRank());
        if (bangdan.getNiken() !=null) {
            viewHolder.bangdan_name.setText((String) bangdan.getNiken());
        }
        if (bangdan.getHeading()!=null){
            StringUtils.showImage(context,StringUtils.GET_PHOTO+bangdan.getHeading(),R.mipmap.male_head2,R.mipmap.male_head2,viewHolder.bangdan_img);
        }
        if (bangdan.getCalorie()!=null){
            viewHolder.bangdan_kaicls.setText(bangdan.getCalorie()+context.getResources().getString(R.string.kical));
        }

        if (bangdan.getVote()!=null){
            viewHolder.bangdan_dianzang.setImageResource(R.mipmap.praise_pre);
        }else {
            viewHolder.bangdan_dianzang.setImageResource(R.mipmap.praise_s);
        }



        /**
         * 点赞
         * */

        viewHolder.bangdan_dianzang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ischeck == false) {
                    okHttpDianZang(position);
                    viewHolder.bangdan_dianzang.setImageResource(R.mipmap.praise_pre);
                    ischeck = true;
                    viewHolder.bangdan_people.setText(String.valueOf(Integer.parseInt(bangdan.getBallot())+1));


                } else {
                    okHttpQuXiao(position);
                    viewHolder.bangdan_dianzang.setImageResource(R.mipmap.praise_s);
                    ischeck = false;
                    viewHolder.bangdan_people.setText(String.valueOf(Integer.parseInt(bangdan.getBallot())));
                }


            }
        });




        return convertView;
    }

    class  ViewHolder{

        TextView  bangdan_number;
        CircleImageView bangdan_img;
        TextView  bangdan_name;
        TextView  bangdan_kaicls;
        TextView  bangdan_people;
        ImageView bangdan_dianzang;



    }
    /**
     *  点赞
     * */
    private void okHttpDianZang(int position) {

        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", String.valueOf(userInfo.getIntInfo("id")));

            jsonObject.put("dataId", lists.get(position).getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        final Request request = new Request.Builder()
                .url(StringUtils.MONT_DIANZANG)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("--->", "" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    String results = response.body().string();
                    Log.i("--->", "正确信息" + results);
                    Message msg =handler.obtainMessage();
                    msg.what=1;
                    handler.sendMessage(msg);
                } else {
                    Log.i("--->", "错误的修改信息" + response.body().string());
                }
            }
        });


    }


    /**
     * 取消点赞
     * */
    private void okHttpQuXiao(int position) {
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", String.valueOf(userInfo.getIntInfo("id")));
            jsonObject.put("dataId", lists.get(position).getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        final Request request = new Request.Builder()
                .url(StringUtils.MONT_CANCEL_DIANZANG)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("--->", "" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    Log.i("--->", "正确的信息" + response.body().string());
                    Message msg = handler.obtainMessage();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } else {
                    Log.i("--->", "错误的修改信息" + response.body().string());
                }
            }
        });
    }

}
