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
import fitness_equipment.test.com.fitness_equipment.utils.TimeUtil;

public class MotionRecordsActivity extends BasActivity {


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
    @BindView(R.id.icon_img)
    ImageView iconImg;
    @BindView(R.id.icon_text)
    TextView iconText;
    @BindView(R.id.icon_time)
    TextView iconTime;
    @BindView(R.id.icon_timeHH)
    TextView iconTimeHH;
    @BindView(R.id.zhuanhuan_img)
    ImageView zhuanhuanImg;
    @BindView(R.id.tv_KmNumber)
    TextView tvKmNumber;
    @BindView(R.id.tv_heart)
    TextView tvHeart;
    @BindView(R.id.tv_velocity)
    TextView tvVelocity;
    @BindView(R.id.tv_bigHeart)
    TextView tvBigHeart;
    @BindView(R.id.tv_yunsu)
    TextView tvYunsu;

    @Override
    protected int getContentView() {
        return R.layout.activity_motion_records;
    }

    @Override
    protected void init() {

        headerText.setText(R.string.monment_date);
        headerText.setTextColor(Color.parseColor("#19C49B"));
        headerRight.setVisibility(View.VISIBLE);
        headerRight.setImageResource(R.mipmap.share);
        Intent intent = getIntent();
        iconTime.setText(TimeUtil.getCurrentAllTime());


        if (intent.getStringExtra("FLAG") != null && intent.getStringExtra("TYPE") != null) {

            if (intent.getStringExtra("TYPE").contains("0")) {
                  iconImg.setImageResource(R.mipmap.motion_records_bike);
            }else {
//                iconImg.setImageResource(R.mipmap.xiao_tiaosheng);
            }

            if (intent.getStringExtra("FLAG").contains("CHALL")) {

            }
        }


    }


    @OnClick({R.id.header_left, R.id.header_right, R.id.zhuanhuan_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.header_right:
                break;
            case R.id.zhuanhuan_img:
                break;
        }
    }
}
