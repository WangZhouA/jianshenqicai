package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReigstGreader extends BasActivity {


    @BindView(R.id.girl_gender)
    ImageView girlGender;
    @BindView(R.id.boboy_gender)
    ImageView boboyGender;


    private UserInfo userInfo;

    @Override
    protected int getContentView() {
        return R.layout.activity_reigst_greader;
    }

    @Override
    protected void init() {
        userInfo=new UserInfo(this);

    }


    Intent  intent;
    String gender;
    @OnClick({R.id.girl_gender, R.id.boboy_gender})
    public void onViewClicked(View view)  {
        switch (view.getId()) {
            case R.id.girl_gender:
                gender ="女";
                try {
                    ReUserGender(gender);
                }catch (Exception e){
                    e.printStackTrace();
                }
                intent=new Intent(this,StatureActivity.class);
                intent.putExtra("Gender","girl");
                startActivity(intent);

                break;
            case R.id.boboy_gender:
                gender ="男";
                try {
                    ReUserGender(gender);
                }catch (Exception e){
                    e.printStackTrace();
                }
                intent=new Intent(this,StatureActivity.class);
                intent.putExtra("Gender","boboy");
                startActivity(intent);

                break;
        }
    }

    private  void  ReUserGender(final String gender) throws  Exception {


        final String reigstStr = StringUtils.RESET_USER;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", userInfo.getStringInfo("id"));
            jsonObject.put("sex", gender);

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
        Log.i("---------->修改的cookic", userInfo.getStringInfo("cookie"));

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("--->", "" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    try {
                        JSONObject object = new JSONObject(result);
                        String code = object.getString("code");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }
}
