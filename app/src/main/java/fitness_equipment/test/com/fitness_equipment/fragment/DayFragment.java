package fitness_equipment.test.com.fitness_equipment.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fitness_equipment.test.com.fitness_equipment.MyApplication;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.adapter.MyFragmentPagerAdapter;

/**
 * Created by 陈姣姣 on 2017/11/29.
 */

public class DayFragment extends Fragment implements View.OnClickListener {
    View view;
    @BindView(R.id.tv_tab_bicycle)
    TextView tv_tab_bicycle;
    @BindView(R.id.tv_tab_rope)
    TextView tv_tab_rope;
    @BindView(R.id.viepager)
    ViewPager mPager;
    Unbinder unbinder;
//    @BindView(R.id.imageViewLeft)
//    ImageView imageViewLeft;
//    @BindView(R.id.imageViewRight)
//    ImageView imageViewRight;
//    @BindView(R.id.tv_time)
//    TextView tv_time;

    //二个布局
    Fragment home_bicycle;
    Fragment home_rope;
    private int currIndex = 0;
    ArrayList<Fragment> fragmentsList;

//
//    private String Nowtime;
//    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_day, null);
        MyApplication.getInstance().addActyToList(getActivity());
        unbinder = ButterKnife.bind(this, view);
        fragmentsList = new ArrayList<>();

        home_bicycle = new BicycleDateFragment();
        home_rope = new RopeFragment();


        fragmentsList.add(home_bicycle);
        fragmentsList.add(home_rope);

        mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentsList));
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mPager.setCurrentItem(0);
        tv_tab_rope.setTextColor(Color.parseColor("#242424"));
        tv_tab_bicycle.setTextColor(Color.parseColor("#18C49A"));
        tv_tab_bicycle.setTextSize(22);
        tv_tab_rope.setTextSize(16);
//
//        imageViewLeft.setOnClickListener(this);
//        imageViewRight.setOnClickListener(this);
//
//        Nowtime = tv_time.getText().toString();

        tv_tab_bicycle.setOnClickListener(new MyOnClickListener(0));
        tv_tab_rope.setOnClickListener(new MyOnClickListener(1));


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //动感单车
            case R.id.tv_tab_bicycle:
                mPager.setCurrentItem(0);
                tv_tab_rope.setTextColor(Color.parseColor("#242424"));
                tv_tab_bicycle.setTextColor(Color.parseColor("#18C49A"));
                tv_tab_bicycle.setTextSize(22);
                tv_tab_rope.setTextSize(16);
                break;

            //活力跳绳
            case R.id.tv_tab_rope:
                mPager.setCurrentItem(1);
                tv_tab_bicycle.setTextColor(Color.parseColor("#18C49A"));
                tv_tab_rope.setTextColor(Color.parseColor("#242424"));
                tv_tab_bicycle.setTextSize(22);
                tv_tab_rope.setTextSize(16);
                break;

        }
    }


    /**
     *  view pager
     * */
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
                        Intent intent =new Intent("TOUPDATE");
                        getActivity().sendBroadcast(intent);
                        tv_tab_bicycle.setTextColor(Color.parseColor("#18C49A"));
                        tv_tab_rope.setTextColor(Color.parseColor("#242424"));
                        tv_tab_bicycle.setTextSize(22);
                        tv_tab_rope.setTextSize(16);
                    }

                    break;
                case 1:
                    if (currIndex == 0) {
                        Intent intent =new Intent("TOUPDATE");
                        getActivity().sendBroadcast(intent);
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
}

