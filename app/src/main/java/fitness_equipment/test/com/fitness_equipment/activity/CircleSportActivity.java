package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.view.secetor.WheelView;

import static fitness_equipment.test.com.fitness_equipment.R.id.tv_MovementType;

/**
 * Created by 陈姣姣 on 2018/3/22.
 */

public class CircleSportActivity extends BasActivity {


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
    @BindView(tv_MovementType)
    TextView tvMovementType;
    @BindView(R.id.tv_ChallengeType)
    TextView tvChallengeType;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.start_btn)
    TextView startBtn;
    @BindView(R.id.left_show)
    LinearLayout leftShow;
    @BindView(R.id.Re_right)
    RelativeLayout ReRight;


    private String Min = "01";
    private String Count = "51";
    private String[] PLANETS=new String[31];
    private String[] PLANET=new  String[31];

    WheelView wv;
    WheelView wvss;

    @Override
    protected int getContentView() {
        return R.layout.circle_sport_activity;
    }

    @Override
    protected void init() {

        headerText.setText(R.string.Skip_rope);
        headerLeft.setImageResource(R.mipmap.back_w);
        headerRightMsg.setVisibility(View.VISIBLE);
        headerRightMsg.setText(R.string.conntionDervice);
        headerRightMsg.setTextSize(20);
        headerAll.setBackgroundColor(Color.parseColor("#373843"));



        leftShow.setVisibility(View.GONE);
        tvChallengeType.setTextColor(Color.parseColor("#19C49B"));
        tvMovementType.setTextColor(Color.parseColor("#9C9DA2"));
        ReRight.setVisibility(View.VISIBLE);

        for (int i =0;i<=30;i++){

            PLANETS[i]=String.valueOf(i)+getResources().getString(R.string.minuteS);
        }
        for (int i =0;i<=30;i++){

            PLANET[i]=String.valueOf(i+50)+getResources().getString(R.string.ci);
        }

        wv = (WheelView) findViewById(R.id.wheelview);
        wv.setOffset(1);
        wv.setItems(Arrays.asList(PLANETS));
        wv.setSeletion(1);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d("====>", "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                Min = item;
            }
        });


        wvss = (WheelView) findViewById(R.id.wheelviews);
        wvss.setOffset(1);
        wvss.setItems(Arrays.asList(PLANET));
        wvss.setSeletion(1);
        wvss.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d("====>", "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                Count = item;
            }
        });

    }

    Intent intent;

    @OnClick({R.id.header_left, R.id.header_right_msg, tv_MovementType, R.id.tv_ChallengeType, R.id.tv_delete, R.id.tv_add, R.id.start_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.header_right_msg:
                Intent intent =new Intent(this,ConntionDerviceActivity.class);
                startActivity(intent);
                break;
            case tv_MovementType:
                leftShow.setVisibility(View.VISIBLE);
                tvMovementType.setTextColor(Color.parseColor("#19C49B"));
                tvChallengeType.setTextColor(Color.parseColor("#9C9DA2"));
                ReRight.setVisibility(View.GONE);

                break;
            case R.id.tv_ChallengeType:
                leftShow.setVisibility(View.GONE);
                tvChallengeType.setTextColor(Color.parseColor("#19C49B"));
                tvMovementType.setTextColor(Color.parseColor("#9C9DA2"));
                ReRight.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_delete:
                int number =Integer.parseInt(tvNumber.getText().toString())-1;
                if (number<=0) {
                    tvNumber.setText("0");
                }else {

                    tvNumber.setText(""+number);
                }

                break;
            case R.id.tv_add:
                int numberAdd =Integer.parseInt(tvNumber.getText().toString())+1;
                if (numberAdd>=100) {
                    tvNumber.setText("0");
                }else {

                    tvNumber.setText(""+numberAdd);
                }
                break;
            case R.id.start_btn:
                intent = new Intent(this, CircleStartMonment .class);
                intent.putExtra("min",Min+"");
                intent.putExtra("count",Count+"");
                startActivity(intent);
                break;
        }
    }




}

