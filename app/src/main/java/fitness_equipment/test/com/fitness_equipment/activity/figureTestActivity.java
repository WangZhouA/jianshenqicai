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

public class figureTestActivity extends BasActivity {

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
    @BindView(R.id.peopele_img)
    ImageView peopeleImg;
    @BindView(R.id.img_one)
    ImageView imgOne;
    @BindView(R.id.img_two)
    ImageView imgTwo;
    @BindView(R.id.img_three)
    ImageView imgThree;
    @BindView(R.id.img_four)
    ImageView imgFour;
    @BindView(R.id.img_five)
    ImageView imgFive;
    @BindView(R.id.img_six)
    ImageView imgSix;
    @BindView(R.id.img_seven)
    ImageView imgSeven;
    @BindView(R.id.img_eight)
    ImageView imgEight;
    @BindView(R.id.onClickOne)
    ImageView onClickOne;
    @BindView(R.id.onClickTwo)
    ImageView onClickTwo;
    @BindView(R.id.onClickThree)
    ImageView onClickThree;
    @BindView(R.id.onClickFour)
    ImageView onClickFour;
    @BindView(R.id.onClickFive)
    ImageView onClickFive;
    @BindView(R.id.onClickSix)
    ImageView onClickSix;
    @BindView(R.id.onClickSeven)
    ImageView onClickSeven;
    @BindView(R.id.onClickEight)
    ImageView onClickEight;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.tv_four)
    TextView tvFour;
    @BindView(R.id.tv_five)
    TextView tvFive;
    @BindView(R.id.tv_six)
    TextView tvSix;
    @BindView(R.id.tv_seven)
    TextView tvSeven;
    @BindView(R.id.tv_eight)
    TextView tvEight;
    @BindView(R.id.last_btn)
    Button lastBtn;
    @BindView(R.id.next_btn)
    Button nextBtn;

    private   int  flag;
    TextView [] views;
    ImageView [] btnviews;
    ImageView [] Imgviews;

    private int genderType=1;

    private  static String TiXingZT="匀称";


    @Override
    protected int getContentView() {
        return R.layout.activity_figure_test;
    }

    @Override
    protected void init() {
        views=new TextView[]{tvOne,tvTwo,tvThree,tvFour,tvFive,tvSix,tvSeven,tvEight};
        btnviews=new ImageView[]{onClickOne,onClickTwo,onClickThree,onClickFour,onClickFive,onClickSix,onClickSeven,onClickEight};
        Imgviews=new ImageView[]{imgOne,imgTwo,imgThree,imgFour,imgFive,imgSix,imgSeven,imgEight};

        headerText.setText(R.string.Health_records);
        headerText.setTextColor(Color.parseColor("#19C49B"));

        if (HealthRecordsActivity.getFlag()==1){
            genderType=1;

            peopeleImg.setImageResource(R.mipmap.g_six);
        }else {
            genderType=2;
            peopeleImg.setImageResource(R.mipmap.m_six);
        }




    }






    /**
     *  文字显示隐藏
     * */

    private void viewflag(TextView[] views, int flag) {

        for (int i = 0; i < views.length; i++) {
            if (i == (flag - 1)) {
                views[i].setVisibility(View.VISIBLE);

                TiXingZT=views[i].getText().toString();

            } else {
                views[i].setVisibility(View.INVISIBLE);
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



    @OnClick({R.id.header_left, R.id.onClickOne, R.id.onClickTwo, R.id.onClickThree, R.id.onClickFour, R.id.onClickFive, R.id.onClickSix, R.id.onClickSeven, R.id.onClickEight, R.id.last_btn, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.onClickOne:
                flag=1;
                viewflag(views,flag);
                setImgLeftviewflag(Imgviews,flag);
                buttonflag(btnviews,flag);
              if (genderType==2) {
                  peopeleImg.setImageResource(R.mipmap.m_one);
              }else {
                  peopeleImg.setImageResource(R.mipmap.g_one);
              }
                break;
            case R.id.onClickTwo:
                flag=2;
                viewflag(views,flag);
                setImgLeftviewflag(Imgviews,flag);
                buttonflag(btnviews,flag);
                if (genderType==2) {
                peopeleImg.setImageResource(R.mipmap.m_two);
                }else {
                    peopeleImg.setImageResource(R.mipmap.g_two);
                }
                break;
            case R.id.onClickThree:
                flag=3;
                viewflag(views,flag);
                setImgLeftviewflag(Imgviews,flag);
                buttonflag(btnviews,flag);
                if (genderType==2) {
                peopeleImg.setImageResource(R.mipmap.m_three);
                }else {
                    peopeleImg.setImageResource(R.mipmap.g_three);
                }
                break;
            case R.id.onClickFour:
                flag=4;
                viewflag(views,flag);
                setImgLeftviewflag(Imgviews,flag);
                buttonflag(btnviews,flag);
                if (genderType==2) {
                    peopeleImg.setImageResource(R.mipmap.m_four);
                }else {
                    peopeleImg.setImageResource(R.mipmap.g_four);
                }
                break;
            case R.id.onClickFive:
                flag=5;
                viewflag(views,flag);
                setImgLeftviewflag(Imgviews,flag);
                buttonflag(btnviews,flag);
                if (genderType==2) {
                peopeleImg.setImageResource(R.mipmap.m_five);
                }else {
                    peopeleImg.setImageResource(R.mipmap.g_five);
                }
                break;
            case R.id.onClickSix:
                flag=6;
                viewflag(views,flag);
                setImgLeftviewflag(Imgviews,flag);
                buttonflag(btnviews,flag);
                if (genderType==2) {
                peopeleImg.setImageResource(R.mipmap.m_six);
                }else {
                    peopeleImg.setImageResource(R.mipmap.g_six);
                }
                break;
            case R.id.onClickSeven:
                flag=7;
                viewflag(views,flag);
                setImgLeftviewflag(Imgviews,flag);
                buttonflag(btnviews,flag);
                if (genderType==2) {
                peopeleImg.setImageResource(R.mipmap.m_seven);
                }else {
                    peopeleImg.setImageResource(R.mipmap.g_seven);
                }
                break;
            case R.id.onClickEight:
                flag=8;
                viewflag(views,flag);
                setImgLeftviewflag(Imgviews,flag);
                buttonflag(btnviews,flag);
                if (genderType==2) {
                peopeleImg.setImageResource(R.mipmap.m_nine);
                }else {
                    peopeleImg.setImageResource(R.mipmap.g_nine);
                }
                break;
            case R.id.last_btn:

                Intent intent1 =new Intent(this,HealthRecordsActivityActivity.class);
                startActivity(intent1);
                break;
            case R.id.next_btn:
                Intent intent =new Intent(this,SideReportActivity.class);
                startActivity(intent);
                break;
        }
    }

    public  static  String   getTiXingZhuangTai(){


     return   TiXingZT;
    }


}
