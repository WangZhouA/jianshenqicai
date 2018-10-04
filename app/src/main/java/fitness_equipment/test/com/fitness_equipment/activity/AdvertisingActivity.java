package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.os.Handler;

import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;


public class AdvertisingActivity extends BasActivity {
    private Handler handler=new Handler();


    @Override
    protected int getContentView() {
        return R.layout.activity_advertising;
    }

    @Override
    protected void init() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(AdvertisingActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }
}
