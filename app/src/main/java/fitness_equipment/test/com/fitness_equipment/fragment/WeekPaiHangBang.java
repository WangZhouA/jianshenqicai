package fitness_equipment.test.com.fitness_equipment.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fitness_equipment.test.com.fitness_equipment.MyApplication;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.adapter.MyFragmentPagerAdapter;

/**
 * Created by 陈姣姣 on 2017/12/12.
 */

public class WeekPaiHangBang  extends Fragment implements View.OnClickListener{
    View view;
    @BindView(R.id.tv_tab_bicycle)
    TextView tv_tab_bicycle;
    @BindView(R.id.tv_tab_rope)
    TextView tv_tab_rope;
    @BindView(R.id.monment_time_tv)
    TextView monmentTimeTv;
    @BindView(R.id.viewpager)
    ViewPager mPager;
    Unbinder unbinder;


    //二个布局
    Fragment home_bicycle;
    Fragment home_rope;
    private int currIndex = 0;
    ArrayList<Fragment> fragmentsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_weekpaihangbang, null);
        MyApplication.getInstance().addActyToList(getActivity());
        unbinder = ButterKnife.bind(this, view);


        fragmentsList = new ArrayList<>();

        home_bicycle = new BicycleWeekPaiHangBangFragment();
        home_rope = new RopeWeekPaiHangBangFragment() ;


        fragmentsList.add(home_bicycle);
        fragmentsList.add(home_rope);

        mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentsList));
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mPager.setCurrentItem(0);
        tv_tab_rope.setTextColor(Color.parseColor("#242424"));
        tv_tab_bicycle.setTextColor(Color.parseColor("#18C49A"));
        tv_tab_bicycle.setTextSize(22);
        tv_tab_rope.setTextSize(16);

        tv_tab_bicycle.setOnClickListener (new  MyOnClickListener(0));
        tv_tab_rope.setOnClickListener (new  MyOnClickListener(1));


        IntentFilter intent = new IntentFilter("UPDATE_TIME");
        getActivity().registerReceiver(broadcastReceiver,intent);

        return view;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //动感单车
            case R.id.tv_tab_bicycle:
                mPager.setCurrentItem(0);
                Intent intent =new Intent("WEEKADD");
                getActivity().sendBroadcast(intent);
                tv_tab_rope.setTextColor(Color.parseColor("#242424"));
                tv_tab_bicycle.setTextColor(Color.parseColor("#18C49A"));
                tv_tab_bicycle.setTextSize(22);
                tv_tab_rope.setTextSize(16);
                break;

            //活力跳绳
            case R.id.tv_tab_rope:
                mPager.setCurrentItem(1);
                Intent intent1 =new Intent("WEEKADD");
                getActivity().sendBroadcast(intent1);
                tv_tab_bicycle.setTextColor(Color.parseColor("#18C49A"));
                tv_tab_rope.setTextColor(Color.parseColor("#242424"));
                tv_tab_bicycle.setTextSize(22);
                tv_tab_rope.setTextSize(16);
                break;


        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        getActivity().unregisterReceiver(broadcastReceiver);

    }

    /**
     * view pager
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {

            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        Intent intent =new Intent("WEEKADD");
                        getActivity().sendBroadcast(intent);
                        tv_tab_bicycle.setTextColor(Color.parseColor("#18C49A"));
                        tv_tab_rope.setTextColor(Color.parseColor("#242424"));
                        tv_tab_bicycle.setTextSize(22);
                        tv_tab_rope.setTextSize(16);
                    }

                    break;
                case 1:
                    if (currIndex == 0) {
                        Intent intent1 =new Intent("WEEKADD");
                        getActivity().sendBroadcast(intent1);
                        tv_tab_rope.setTextColor(Color.parseColor("#18C49A"));
                        tv_tab_bicycle.setTextColor(Color.parseColor("#242424"));
                        tv_tab_bicycle.setTextSize(16);
                        tv_tab_rope.setTextSize(22);
                    }

                    break;
            }
            currIndex = arg0;


        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action =intent.getAction();
            if (action.contains("UPDATE_TIME")){

                String Notime = intent.getStringExtra("TIME");
                monmentTimeTv.setText(Notime);
            }

        }
    };
    private  String  TimeZhuanHuan(String time){

        SimpleDateFormat f =new SimpleDateFormat("yyyy-mm-dd");
        try {
            System.out.println(f.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat f1 =new SimpleDateFormat("dd");
        String datestr = null;//格式化数据
        try {
            datestr = f1.format(f.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  datestr;
    }


}
