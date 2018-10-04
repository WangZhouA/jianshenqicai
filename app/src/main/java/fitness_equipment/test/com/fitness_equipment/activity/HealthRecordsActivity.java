package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;

public class HealthRecordsActivity extends BasActivity {


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
    @BindView(R.id.girl_gender)
    ImageView girlGender;
    @BindView(R.id.boboy_gender)
    ImageView boboyGender;

    @Override
    protected int getContentView() {
        return R.layout.activity_health_records;
    }

    @Override
    protected void init() {

        headerText.setText(R.string.Health_records);
        headerText.setTextColor(Color.parseColor("#19C49B"));
    }


    Intent intent;
    @OnClick({R.id.header_left, R.id.girl_gender, R.id.boboy_gender})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.girl_gender:
                flag=1;
                intent=new Intent(this,HealthRecordsTwoActivity .class);
                startActivity(intent);
                break;
            case R.id.boboy_gender:
                flag=2;
                intent=new Intent(this,HealthRecordsTwoActivity.class);
                startActivity(intent);
                break;
        }
    }


    private static  int  flag =1;
    public  static int getFlag(){
     return     flag;
    }


}
