package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.fragment.DayFragment;
import fitness_equipment.test.com.fitness_equipment.fragment.MontFragment;
import fitness_equipment.test.com.fitness_equipment.fragment.WeekFirstFragment;
import fitness_equipment.test.com.fitness_equipment.fragment.YearFragment;
import fitness_equipment.test.com.fitness_equipment.utils.TimeUtil;

import static fitness_equipment.test.com.fitness_equipment.R.id.tv_time;

public class QueryHistoryActivity extends BasActivity implements View.OnClickListener {


    View view;

    /**
     * 四个标题  选项
     */

    DayFragment dayFragment;
    WeekFirstFragment weekBicycleFragment;
    MontFragment montFragment;
    YearFragment yearFragment;
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


    //四个Button

    private int flag = 0;

    Button btnDay;
    Button btnWeek;
    Button btnMont;
    Button btnYear;
    Button[] btnArrays;


    ImageView imageViewLeft;
    ImageView imageViewRight;
    TextView tvTime;

    private FragmentTransaction mFragmentTransaction;//fragment事务
    private FragmentManager mFragmentManager;//fragment管理者

    public static String NowTime;


    /***
     *
     *    利用循环来做，一个显示其他的影藏
     */


    private void viewflag(Button[] views, int flag) {

        for (int i = 0; i < views.length; i++) {
            if (i == (flag - 1)) {
                views[i].setBackgroundColor(Color.parseColor("#19C49B"));
                views[i].setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                views[i].setBackground(null);
                views[i].setTextColor(Color.parseColor("#19C49B"));
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_query_history;
    }

    @Override
    protected void init() {

        /**
         *
         *   日  周  月  年
         * */

        btnDay = (Button) findViewById(R.id.btnDay);
        btnWeek = (Button) findViewById(R.id.btnWeek);
        btnMont = (Button) findViewById(R.id.btnMont);
        btnYear = (Button) findViewById(R.id.btnYear);

        imageViewLeft = (ImageView) findViewById(R.id.imageViewLeft);
        imageViewRight = (ImageView) findViewById(R.id.imageViewRight);
        tvTime = (TextView) findViewById(tv_time);


        mFragmentManager = getSupportFragmentManager();//获取到fragment的管理对象
        btnArrays = new Button[]{btnDay, btnWeek, btnMont, btnYear};

        btnDay.setOnClickListener(this);
        btnWeek.setOnClickListener(this);
        btnMont.setOnClickListener(this);
        btnYear.setOnClickListener(this);

        imageViewLeft.setOnClickListener(this);
        imageViewRight.setOnClickListener(this);

        viewflag(btnArrays, flag + 1);
        setClick(flag);

        headerText.setText(R.string.history_record);
        headerText.setTextColor(Color.parseColor("#19C49B"));
        headerRight.setImageResource(R.mipmap.share);
        headerRight.setVisibility(View.VISIBLE);
        headerHaoyou.setImageResource(R.mipmap.refresh);
        headerHaoyou.setVisibility(View.VISIBLE);


        tvTime.setText(TimeUtil.getCurrentTime());
        NowTime = TimeUtil.getCurrentTime();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDay:
                flag = 0;
                viewflag(btnArrays, flag + 1);
                setClick(flag);
                break;
            case R.id.btnWeek:
                flag = 1;
                viewflag(btnArrays, flag + 1);
                setClick(flag);
                break;
            case R.id.btnMont:
                flag = 2;
                viewflag(btnArrays, flag + 1);
                setClick(flag);
                break;
            case R.id.btnYear:
                flag = 3;
                viewflag(btnArrays, flag + 1);
                setClick(flag);
                break;
            case R.id.imageViewLeft:
                try {
                    tvTime.setText(deleteNowTime(flag, tvTime.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                NowTime = tvTime.getText().toString();
                Intent intent = new Intent("UPDATE_TIME");
                intent.putExtra("TIME", NowTime);
                sendBroadcast(intent);


                break;
            case R.id.imageViewRight:
                try {
                    tvTime.setText(addNowTime(flag, tvTime.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                NowTime = tvTime.getText().toString();
                Intent intent1 = new Intent("UPDATE_TIME");
                intent1.putExtra("TIME", NowTime);
                sendBroadcast(intent1);

                break;

        }

    }


    /**
     * //     * +一天
     * //
     */


    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();

    private String addNowTime(int Type, String d) throws ParseException {

        if (Type == 0) {
            Date dd = df.parse(d);
            calendar.setTime(dd);
            calendar.add(Calendar.DAY_OF_MONTH, 1);//加一天
            return df.format(calendar.getTime());
        } else if (Type == 1) {

            Date dd = df.parse(d);
            calendar.setTime(dd);
            calendar.add(Calendar.DAY_OF_MONTH, 7);//加7天
            return df.format(calendar.getTime());

        } else if (Type == 2) {
            Date dd = df.parse(d);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dd);
            calendar.add(Calendar.MONTH, 1);
            return df.format(calendar.getTime());
        } else if (Type == 3) {
            Date dd = df.parse(d);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dd);
            calendar.add(Calendar.YEAR, 1);
            return df.format(calendar.getTime());
        }
        return null;
    }

    private String deleteNowTime(int Type, String d) throws ParseException {
/**
 * 减一天
 */if (Type == 0) {

            long dif = df.parse(d).getTime() - 86400 * 1000;//减一天
            Date date = new Date();
            date.setTime(dif);
            return df.format(date);
        } else if (Type == 1) {
            long dif = df.parse(d).getTime() - 86400 * 7000;//减7天
            Date date = new Date();
            date.setTime(dif);
            return df.format(date);
        } else if (Type == 2) {
            //减少一个月
            Date date = null;
            try {
                date = df.parse(d);// 初始日期
            } catch (Exception e) {

            }
            calendar.setTime(date);// 设置日历时间
            calendar.add(Calendar.MONTH, -1);
            return df.format(calendar.getTime());
        } else if (Type == 3) {
            //减少一个月
            Date date = null;
            try {
                date = df.parse(d);// 初始日期
            } catch (Exception e) {

            }
            calendar.setTime(date);// 设置日历时间
            calendar.add(Calendar.YEAR, -1);
            return df.format(calendar.getTime());
        }
        return null;
    }


    private void setClick(int type) {
        //开启事务
        mFragmentTransaction = mFragmentManager.beginTransaction();
        //显示之前将所有的fragment都隐藏起来,在去显示我们想要显示的fragment
        hideFragment(mFragmentTransaction);
        switch (type) {
            case 0://
                //如果fragment是null的话,就创建一个
                if (dayFragment == null) {
                    dayFragment = new DayFragment();
                    //加入事务
                    mFragmentTransaction.add(R.id.fragment, dayFragment);
                } else {
                    //如果王超fragment不为空就显示出来
                    mFragmentTransaction.show(dayFragment);
                }
                break;
            case 1:
                if (weekBicycleFragment == null) {
                    weekBicycleFragment = new WeekFirstFragment();
                    //加入事务
                    mFragmentTransaction.add(R.id.fragment, weekBicycleFragment);
                } else {
                    //如果fragment不为空就显示出来
                    mFragmentTransaction.show(weekBicycleFragment);
                }
                break;
            case 2:
                if (montFragment == null) {
                    montFragment = new MontFragment();
                    //加入事务
                    mFragmentTransaction.add(R.id.fragment, montFragment);
                } else {
                    //如果fragment不为空就显示出来
                    mFragmentTransaction.show(montFragment);
                }
                break;
            case 3:
                if (yearFragment == null) {
                    yearFragment = new YearFragment();
                    //加入事务
                    mFragmentTransaction.add(R.id.fragment, yearFragment);
                } else {
                    //如果fragment不为空就显示出来
                    mFragmentTransaction.show(yearFragment);
                }
                break;

        }
        //提交事务
        mFragmentTransaction.commit();
    }

    /**
     * 用来隐藏fragment的方法
     *
     * @param fragmentTransaction
     */
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        //如果此fragment不为空的话就隐藏起来
        if (dayFragment != null) {
            fragmentTransaction.hide(dayFragment);
        }
        if (weekBicycleFragment != null) {
            fragmentTransaction.hide(weekBicycleFragment);
        }
        if (montFragment != null) {
            fragmentTransaction.hide(montFragment);
        }
        if (yearFragment != null) {
            fragmentTransaction.hide(yearFragment);
        }
    }

    public static String getNowTime() {

        return NowTime;
    }



    @OnClick({R.id.header_left, R.id.header_text, R.id.header_haoyou, R.id.header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                finish();
                break;

            case R.id.header_haoyou:

                Intent intent =new Intent("TOUPDATE");
                sendBroadcast(intent);

                break;
            case R.id.header_right:

                showDialogA();
                break;
        }
    }
}
