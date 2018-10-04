package fitness_equipment.test.com.fitness_equipment.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;

public class AboutActivity extends BasActivity {


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
    @BindView(R.id.re_scoring)
    RelativeLayout reScoring;
    @BindView(R.id.views)
    View views;

    @Override
    protected int getContentView() {
        return R.layout.activity_about;
    }

    @Override
    protected void init() {
        headerText.setText(R.string.about_we);
        headerText.setTextColor(Color.parseColor("#19C49B"));

    }


    @OnClick({R.id.header_left, R.id.header_text, R.id.re_scoring})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.header_text:

                break;
            case R.id.re_scoring:

               showToast(getResources().getString(R.string.nuli));
                break;
        }
    }
}
