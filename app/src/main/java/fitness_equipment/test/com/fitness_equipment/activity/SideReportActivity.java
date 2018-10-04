package fitness_equipment.test.com.fitness_equipment.activity;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.utils.TimeUtil;
import fitness_equipment.test.com.fitness_equipment.view.CircleImageView;

import static fitness_equipment.test.com.fitness_equipment.activity.HealthRecordsTwoActivity.getmHeights;
import static fitness_equipment.test.com.fitness_equipment.activity.HealthRecordsTwoActivity.getmWeights;

public class SideReportActivity extends BasActivity {


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
    @BindView(R.id.pic)
    CircleImageView pic;
    @BindView(R.id.side_age)
    TextView sideAge;
    @BindView(R.id.side_height)
    TextView sideHeight;
    @BindView(R.id.side_wight)
    TextView sideWight;
    @BindView(R.id.side_size)
    TextView sideSize;
    @BindView(R.id.tv_side_subdivision)
    TextView tvSideSubdivision;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.btn_fenxiang)
    Button btnFenxiang;

    @BindView(R.id.tv_tizhi)
    TextView tvTiZhi;
    @BindView(R.id.tv_bmi)
    TextView tvBmi;

    private int  gender;
    float  floatTime;

    @Override
    protected int getContentView() {
        return R.layout.activity_side_report;
    }

    @Override
    protected void init() {
        headerText.setText(R.string.Side_report);
        headerText.setTextColor(Color.parseColor("#19C49B"));
        headerAll.setBackgroundColor(Color.parseColor("#373843"));


        if (HealthRecordsActivity.getFlag()==1){
            pic.setImageResource(R.mipmap.female_head2);
            gender=0;
        }else {
            pic.setImageResource(R.mipmap.male_head2);
            gender=1;
        }

        //年龄传值
        if (HealthRecordsActivityActivity.getmAges()!=0){
//            sideAge.setText(""+HealthRecordsActivityActivity.getmAges());

            float NowTime= Float.valueOf(TimeUtil.getCurrentTimeYear());
            floatTime = NowTime-(HealthRecordsActivityActivity.getmAges());
            sideAge.setText(""+floatTime);


            //身高传值
            if (HealthRecordsTwoActivity.getmHeights()!=0){
                sideHeight.setText(""+ getmHeights());
            }
            //体重传值
            if (HealthRecordsTwoActivity.getmWeights()!=0) {
                sideWight.setText("" + getmWeights());

                //Bmi
                float w = HealthRecordsTwoActivity.getmWeights() ;
                Log.i("------->w",""+w);
                float bmi =  w/((HealthRecordsTwoActivity.getmHeights()/100)*2);
                Log.i("------->h",""+((HealthRecordsTwoActivity.getmHeights()/100)*2));
                Log.i("------->bmi",""+bmi);
                int  intbmi = (int)bmi;
                tvRight.setText("" + intbmi);


                if (gender==0){
                    if (bmi>14 && bmi<26){
                        tvBmi.setText("BMI(正常)");
                    }else if (bmi>24 && bmi<31){
                        tvBmi.setText("BMI(中)");
                    }else if (bmi>30){
                        tvBmi.setText("BMI(高)");
                    }
                }else if (gender==1){
                    if (bmi>9 && bmi<21){
                        tvBmi.setText("BMI(正常)");
                    }else if (bmi>19 && bmi<26){
                        tvBmi.setText("BMI(中)");
                    }else if (bmi>26){
                        tvBmi.setText("BMI(高)");
                    }
                }

                //体脂肪
                double tiZhi = (1.20 * bmi) + (0.23 * floatTime) - (10.8 * gender) - 5.4;
                int tizhi = (int) tiZhi;
                tvLeft.setText("" + tizhi);


                if (gender == 0) {
                    if (tizhi > 4 && tiZhi < 21) {
                        tvTiZhi.setText("体脂（偏瘦）");
                        tvSideSubdivision.setText("70");
                    } else if (tizhi > 20 && tiZhi < 35) {
                        tvTiZhi.setText("体脂（正常）");
                        tvSideSubdivision.setText("90");
                    } else if (tizhi > 34 && tizhi < 40) {
                        tvTiZhi.setText("体脂（偏胖）");
                        tvSideSubdivision.setText("80");
                    } else if (tizhi > 39 && tizhi < 46) {
                        tvTiZhi.setText("体脂（过胖）");
                        tvSideSubdivision.setText("75");
                    }

                } else if (gender == 1) {
                    if (tizhi > 4 && tiZhi < 11) {
                        tvTiZhi.setText("体脂（偏瘦）");
                        tvSideSubdivision.setText("70");
                    } else if (tizhi > 10 && tiZhi < 22) {
                        tvTiZhi.setText("体脂（正常）");
                        tvSideSubdivision.setText("90");
                    } else if (tizhi > 21 && tizhi < 27) {
                        tvTiZhi.setText("体脂（偏胖）");
                        tvSideSubdivision.setText("80");
                    } else if (tizhi > 26 && tizhi < 46) {
                        tvTiZhi.setText("体脂（过胖）");
                        tvSideSubdivision.setText("75");
                    }
                }
            }

            //基础代谢
            if (gender==0){

                textView3.setText(""+ HealthRecordsTwoActivity.getmWeights()*24*0.9);
            }else {
                textView3.setText(""+ HealthRecordsTwoActivity.getmWeights()*24*1);
            }

        }
        if (figureTestActivity.getTiXingZhuangTai()!=null){
            sideSize.setText(""+figureTestActivity.getTiXingZhuangTai());
        }

    }



    @OnClick({R.id.header_left, R.id.btn_fenxiang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.btn_fenxiang:
                break;
        }
    }
}
