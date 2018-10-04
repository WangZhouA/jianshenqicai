package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.JarPhoto.PublishedActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.view.MyDialog;

public class SettingActivity extends BasActivity implements View.OnClickListener{

    @BindView(R.id.header_left)
    ImageButton headerLeft;
    @BindView(R.id.header_text)
    TextView headerText;
    @BindView(R.id.reClearcache)
    RelativeLayout reClearcache;
    @BindView(R.id.reRepass)
    RelativeLayout reRepass;
    @BindView(R.id.reHelp)
    RelativeLayout reHelp;
    @BindView(R.id.reFeedback)
    RelativeLayout reFeedback;
     private Handler handler =new Handler(){
         @Override
         public void handleMessage(Message msg) {
         switch (msg.what){
             case 0:

                 showMyLoadingDialog();

                 handler.postDelayed(new Runnable() {
                     @Override
                     public void run() {

                         showToast(getResources().getString(R.string.No_data));
                     }
                 },2000);
                 break;
         }
         }
     };





    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        headerText.setText(R.string.set);
        headerText.setTextColor(Color.parseColor("#19C49B"));
    }

    View  inflate;
    Intent intent ;

    private MyDialog dialog;



    @OnClick({R.id.header_left, R.id.reClearcache, R.id.reRepass, R.id.reHelp, R.id.reFeedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;
            case R.id.reClearcache:


                show();
                break;
            case R.id.reRepass:
                intent=new Intent(this,RePassActivity.class);
                startActivity(intent);
                break;
            case R.id.reHelp:
                break;
            case R.id.reFeedback:
                intent=new Intent(this,PublishedActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_left:
                finish();
                break;
        }
    }

    public void show(){
        inflate = LayoutInflater.from(SettingActivity.this).inflate(R.layout.bottom_dialog, null);
        dialog = new MyDialog(SettingActivity.this,inflate,R.style.MyDialog,0);
        TextView takePhoto= (TextView) inflate.findViewById(R.id.takePhoto);
        takePhoto.setTextColor(Color.parseColor("#FE3824"));
        TextView takePhotos=(TextView) inflate.findViewById(R.id.takePhotos);
        takePhotos.setTextColor(Color.parseColor("#18C49A"));
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg =new Message();
                msg.what=0;
                handler.sendMessage(msg);
                dialog.dismiss();
            }
        });

        takePhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
