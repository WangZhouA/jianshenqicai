package fitness_equipment.test.com.fitness_equipment.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fitness_equipment.test.com.fitness_equipment.MyApplication;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.activity.CircleSportActivity;
import fitness_equipment.test.com.fitness_equipment.activity.HistoricalRecordsActivity;

/**
 * Created by 陈姣姣 on 2017/11/14.
 */

public class CircleFragment extends Fragment {

    View view;
    @BindView(R.id.img_movement_count)
    TextView imgMovementCount;
    @BindView(R.id.img_movment_history)
    ImageView imgMovmentHistory;
    @BindView(R.id.tv_Movement_time)
    TextView tvMovementTime;
    @BindView(R.id.tv_Total_km)
    TextView tvTotalKm;
    @BindView(R.id.tv_Total_calories)
    TextView tvTotalCalories;
    @BindView(R.id.linearlayout_btn)
    LinearLayout linearlayoutBtn;
    @BindView(R.id.start_movment)
    Button startMovment;
    Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.circle_fragment,null);
        MyApplication.getInstance().addActyToList(getActivity());
        unbinder = ButterKnife.bind(this, view);

        IntentFilter intentFilter =new IntentFilter("DAKA");
        getActivity().registerReceiver(broadcastReceiver,intentFilter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(broadcastReceiver);
        unbinder.unbind();

    }
    Intent intent;
    @OnClick({R.id.img_movment_history, R.id.start_movment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_movment_history:

                intent =new Intent(getActivity(), HistoricalRecordsActivity.class);
                intent.putExtra("TYPE","2");
                startActivity(intent);

                break;
            case R.id.start_movment:

                intent =new Intent(getActivity(), CircleSportActivity.class);
                startActivity(intent);
                break;
        }
    }

    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

//            intent.putExtra("calories",calories);
//            intent.putExtra("times",times);
//            intent.putExtra("counts",counts);
//            intent.putExtra("kilometre",kilometre);

            if (action.contains("DAKA")){

                String calories =intent.getStringExtra("calories");
                String times =intent.getStringExtra("times");
                String counts =intent.getStringExtra("counts");
                String kilometre =intent.getStringExtra("kilometre");
                if (calories!=null){
                    tvTotalCalories.setText(kilometre);
                }
                if (times!=null){
                    tvMovementTime.setText(times);
                }
                if (calories!=null){
                    tvTotalKm.setText(kilometre);
                }
                if (counts!=null){
                    imgMovementCount.setText(counts);
                }

            }
        }
    };



}
