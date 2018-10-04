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

public class HealthRecordsActivityActivity extends BasActivity {


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
    @BindView(R.id.img_left)
    ImageView imgLeft;
    @BindView(R.id.img_centen)
    ImageView imgCenten;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.onClickLeft)
    ImageView onClickLeft;
    @BindView(R.id.onClickcenter)
    ImageView onClickcenter;
    @BindView(R.id.onClickRight)
    ImageView onClickRight;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.last_btn)
    Button lastBtn;
    @BindView(R.id.next_btn)
    Button nextBtn;

    @BindView(R.id.img_type)
    ImageView imgType;


    TextView [] views;
    ImageView [] btnviews;
    ImageView [] Imgviews;

    private int  Type =1; //点击的那一个


    /**
     * 年龄
     */

    private float mAge = 1990;
    private float mMaxAge = 2017;
    private float mMinAge = 1950;


    private static  float mAges=1990;
    private static  int  mCounts;

    @Override
    protected int getContentView() {
        return R.layout.activity_health_records_activity;
    }

    @Override
    protected void init() {
        views=new TextView[]{tvLeft,tvCenter,tvRight};
        btnviews=new ImageView[]{onClickLeft,onClickcenter,onClickRight};
        Imgviews=new ImageView[]{imgLeft,imgCenten,imgRight};
        headerText.setText(R.string.Health_records);
        headerText.setTextColor(Color.parseColor("#19C49B"));



        /**
         * 年龄
         * */
        tvUserHeightValue.setText((int) mAge + "");


        scaleWheelViewHeight.initViewParam(mAge, mMaxAge, mMinAge);
        scaleWheelViewHeight.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tvUserHeightValue.setText((int) value + "");
                mAge = value;
                mAges=value;
            }
        });


        if (HealthRecordsActivity.getFlag()==1){
            imgType.setImageResource(R.mipmap.female_head);
        }else {
            imgType.setImageResource(R.mipmap.male_head);
        }




    }
    /**
     *  文字显示隐藏
     * */

    private void viewflag(TextView[] views, int flag) {

        for (int i = 0; i < views.length; i++) {
            if (i == (flag - 1)) {
                views[i].setVisibility(View.VISIBLE);

            } else {
                views[i].setVisibility(View.GONE);
            }
        }
    }

    /**
     *  图片显示隐藏
     * */
    private void setImgLeftviewflag(ImageView[] views, int flag) {

        for (int i = 0; i < views.length; i++) {
            if (i == (flag - 1)) {
                views[i].setVisibility(View.VISIBLE);

            } else {
                views[i].setVisibility(View.GONE);
            }
        }

    }


    /**
     *  控件切换
     * */
    private void buttonflag(ImageView[] views, int flag) {

        for (int i = 0; i < views.length; i++) {
            if (i == (flag - 1)) {
                views[i].setImageResource(R.mipmap.circle_green);

            } else {
                views[i].setImageResource(R.mipmap.circle_white);
            }
        }
    }

    @OnClick({R.id.header_left, R.id.onClickLeft, R.id.onClickcenter, R.id.onClickRight, R.id.last_btn, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.onClickLeft:
                Type=1;
                mCounts=1;
                viewflag(views,Type);
                setImgLeftviewflag(Imgviews,Type);
                buttonflag(btnviews,Type);

                break;
            case R.id.onClickcenter:
                Type=2;
                mCounts=2;
                viewflag(views,Type);
                setImgLeftviewflag(Imgviews,Type);
                buttonflag(btnviews,Type);
                break;
            case R.id.onClickRight:
                Type=3;
                mCounts=3;
                viewflag(views,Type);
                setImgLeftviewflag(Imgviews,Type);
                buttonflag(btnviews,Type);
                break;
            case R.id.last_btn:
                Intent intent1 =new Intent(this,HealthRecordsTwoActivity.class);
                startActivity(intent1);
                break;
            case R.id.next_btn:
                Intent intent =new Intent(this,figureTestActivity.class);
                startActivity(intent);
                break;
        }
    }

    public  static float getmAges(){
        return mAges;
    }
    public  static int  getmCounts(){
        return  mCounts;
    }
}
