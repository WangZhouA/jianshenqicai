package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.view.ScaleRulerView;

public class HealthRecordsTwoActivity extends BasActivity {


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
    @BindView(R.id.scaleWheelView_height)
    ScaleRulerView scaleWheelViewHeight;
    @BindView(R.id.tv_user_height_value)
    TextView tvUserHeightValue;
    @BindView(R.id.scaleWheelView_weight)
    ScaleRulerView scaleWheelViewWeight;
    @BindView(R.id.tv_user_weight_value)
    TextView tvUserWeightValue;
    @BindView(R.id.last_btn)
    Button lastBtn;
    @BindView(R.id.next_btn)
    Button nextBtn;

    @BindView(R.id.img_type)
    ImageView img_type;

    /***
     *  身高
     *
     */

    private static float mHeight = 170;
    private float mMaxHeight = 220;
    private float mMinHeight = 100;


    private  static float mHeights=170;
    private  static float mWeights=40;


    /**
     * 体重
     */

    private static float mWeight = 40;
    private float mMaxWeight = 150;
    private float mMinWeight = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_health_records_two;
    }

    @Override
    protected void init() {

        headerText.setText(R.string.Health_records);
        headerText.setTextColor(Color.parseColor("#19C49B"));


        /**
         * 身高
         * */
        tvUserHeightValue.setText((int) mHeight + "");


        scaleWheelViewHeight.initViewParam(mHeight, mMaxHeight, mMinHeight);
        scaleWheelViewHeight.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tvUserHeightValue.setText((int) value + "");
                mHeight = value;
                mHeights=value;
            }
        });


        /**
         * 体重
         * */
        tvUserWeightValue.setText((int) mWeight + "");
        scaleWheelViewWeight.initViewParam(mWeight, mMaxWeight, mMinWeight);
        scaleWheelViewWeight.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tvUserWeightValue.setText((int) value + "");
                mWeight = value;
                mWeights=value;
            }
        });



        if (HealthRecordsActivity.getFlag()==1){
            img_type.setImageResource(R.mipmap.female_head);
        }else {
            img_type.setImageResource(R.mipmap.male_head);
        }

    }


     Intent intent;
    @OnClick({R.id.header_left, R.id.last_btn, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.last_btn:

               intent=new Intent(this,HealthRecordsActivity.class);
                startActivity(intent);
                break;
            case R.id.next_btn:
                intent=new Intent(this,HealthRecordsActivityActivity.class);
                startActivity(intent);
                break;
        }
    }

    public  static  float  getmHeights(){


        return  mHeights;
    }
    public  static  float  getmWeights(){


        return  mWeights;
    }

}
