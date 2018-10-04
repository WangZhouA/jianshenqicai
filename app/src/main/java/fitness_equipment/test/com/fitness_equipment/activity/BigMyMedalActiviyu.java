package fitness_equipment.test.com.fitness_equipment.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.view.MyDialog;

public class BigMyMedalActiviyu extends BasActivity {


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
    @BindView(R.id.bigtv)
    TextView bigtv;
    @BindView(R.id.smalltv)
    TextView smalltv;


    MyDialog dialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_big_my_medal_activiyu;
    }

    @Override
    protected void init() {

        headerLeft.setImageResource(R.mipmap.medal_close );
        headerText.setText(R.string.myMedal);
        headerText.setTextColor(Color.parseColor("#19C49B"));
        headerRight.setImageResource(R.mipmap.share);
        headerRight.setVisibility(View.VISIBLE);

    }



    @OnClick({R.id.header_left, R.id.header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.header_right:

//

                showDialogA();



                break;
        }
    }




}
