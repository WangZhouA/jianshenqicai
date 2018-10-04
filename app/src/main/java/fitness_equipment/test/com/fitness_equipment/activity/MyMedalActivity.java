package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;

public class MyMedalActivity extends BasActivity {

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
    @BindView(R.id.imgOne)
    TextView imgOne;
    @BindView(R.id.imgTwo)
    TextView imgTwo;
    @BindView(R.id.imgThree)
    TextView imgThree;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_medal;
    }

    @Override
    protected void init() {
        headerText.setText(R.string.myMedal);
        headerText.setTextColor(Color.parseColor("#19C49B"));

    }



    Intent intent;
    @OnClick({R.id.header_left, R.id.imgOne, R.id.imgTwo, R.id.imgThree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.imgOne:
                intent=new Intent(this,BigMyMedalActiviyu.class);
                startActivity(intent);
                break;
            case R.id.imgTwo:
                intent=new Intent(this,BigMyMedalActiviyu.class);
                startActivity(intent);
                break;
            case R.id.imgThree:
                intent=new Intent(this,BigMyMedalActiviyu.class);
                startActivity(intent);
                break;
        }
    }
}
