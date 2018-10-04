package fitness_equipment.test.com.fitness_equipment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fitness_equipment.test.com.fitness_equipment.activity.AddDerviceActivity;
import fitness_equipment.test.com.fitness_equipment.fragment.FirstFragment;
import fitness_equipment.test.com.fitness_equipment.fragment.FirstTwoFragment;
import fitness_equipment.test.com.fitness_equipment.fragment.UserFragment;
import fitness_equipment.test.com.fitness_equipment.interfaces.OnUpdateFirstTabTextListener;
import fitness_equipment.test.com.fitness_equipment.interfaces.OnUpdateUserTabTextListener;
import fitness_equipment.test.com.fitness_equipment.service.MyService;
import fitness_equipment.test.com.fitness_equipment.utils.TimeUtil;

public class MainActivity extends BasActivity implements OnUpdateUserTabTextListener ,OnUpdateFirstTabTextListener{


    @BindView(R.id.header_left)
    ImageButton headerLeft;
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
    @BindView(R.id.fragment)
    FrameLayout fragment;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.img_first)
    ImageView imgFirst;
    @BindView(R.id.linearlayout_first)
    LinearLayout linearlayoutFirst;
    @BindView(R.id.img_look)
    ImageView imgLook;
    @BindView(R.id.linearlayout_look)
    LinearLayout linearlayoutLook;
    @BindView(R.id.img_Keeping_health)
    ImageView imgKeepingHealth;
    @BindView(R.id.linearlayout_me)
    LinearLayout linearlayoutMe;
    @BindView(R.id.linearlayout_btn)
    LinearLayout linearlayoutBtn;


    FirstFragment firstFragment;
    FirstTwoFragment circleFragment;
    UserFragment userFragment;
    int flag=1;


    @BindView(R.id.tv_first)
    TextView tvFirst;
    @BindView(R.id.tv_circle)
    TextView tvCircle;


    //把所有的view装进去
    TextView[] views;
    @BindView(R.id.tv_user)
    TextView tvUser;

    private FragmentTransaction mFragmentTransaction;//fragment事务
    private FragmentManager mFragmentManager;//fragment管理者

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        headerText.setText(getResources().getString(R.string.app_names));

        Intent intent = new Intent(this,MyService.class); //启动服务
        startService(intent);

        headerLeft.setImageResource(R.mipmap.music);
        headerRight.setImageResource(R.mipmap.equipment);
        headerRight.setVisibility(View.VISIBLE);
        headerText.setTextColor(Color.parseColor("#19C49B"));
        views = new TextView[]{tvFirst, tvCircle, tvUser};
        mFragmentManager = getSupportFragmentManager();//获取到fragment的管理对象


    }


    @Override
    protected void onResume() {
        super.onResume();



        //设置图片显示
        setImageView(flag);
        //设置字体显示
        viewflag(views, flag);
        setClick(0);
    }

    @OnClick({R.id.header_left, R.id.header_right, R.id.linearlayout_first, R.id.linearlayout_look, R.id.linearlayout_me})
    public void onViewClicked(View view) {



        switch (view.getId()) {
            case R.id.header_left:
                showToast(getResources().getString(R.string.nuli));

                break;
            case R.id.header_right:

                Intent intent =new Intent(this, AddDerviceActivity.class);
                startActivity(intent);
                break;
            case R.id.linearlayout_first:
                //用于确定是那一个界面，用来跟换图标显示的
                flag = 1;
                //设置图片显示
                setImageView(flag);
                //设置字体显示
                viewflag(views, flag);

                linearlayoutFirst.setSelected(true);
                linearlayoutLook.setSelected(false);
                linearlayoutMe.setSelected(false);

                setClick(0);

                break;
            case R.id.linearlayout_look:



                //用于确定是那一个界面，用来跟换图标显示的
                flag = 2;
                //设置图片显示
                setImageView(flag);
                //设置字体显示
                viewflag(views, flag);
                linearlayoutFirst.setSelected(false);
                linearlayoutLook.setSelected(true);
                linearlayoutMe.setSelected(false);

                setClick(1);


                break;
            case R.id.linearlayout_me:


                //用于确定是那一个界面，用来跟换图标显示的
                flag = 3;
                //设置图片显示
                setImageView(flag);
                //设置字体显示
                viewflag(views, flag);
                linearlayoutFirst.setSelected(false);
                linearlayoutLook.setSelected(false);
                linearlayoutMe.setSelected(true);

                setClick(2);


                break;
        }



    }



    private void setImageView(int flag) {
        if (flag == 1) {
            imgFirst.setImageResource(R.mipmap.personal);
            imgLook.setImageResource(R.mipmap.circle_s);
            imgKeepingHealth.setImageResource(R.mipmap.personal_s);
            tvFirst.setTextColor(Color.parseColor("#19C49B"));
        } else if (flag == 2) {
            imgFirst.setImageResource(R.mipmap.personal_s);
            imgLook.setImageResource(R.mipmap.circle);
            imgKeepingHealth.setImageResource(R.mipmap.personal_s);
        } else if (flag == 3) {
            imgFirst.setImageResource(R.mipmap.personal_s);
            imgLook.setImageResource(R.mipmap.circle_s);
            imgKeepingHealth.setImageResource(R.mipmap.personal);

        }

    }


    /***
     *
     *    利用循环来做，一个显示其他的影藏
     */


    private void viewflag(TextView[] views, int flag) {

        for (int i = 0; i < views.length; i++) {
            if (i == (flag - 1)) {
                views[i].setTextColor(Color.parseColor("#19C49B"));
            } else {
                views[i].setTextColor(Color.parseColor("#929292"));
            }
        }
    }


    private void setClick(int type) {
        //开启事务
        mFragmentTransaction = mFragmentManager.beginTransaction();
        //显示之前将所有的fragment都隐藏起来,在去显示我们想要显示的fragment
        hideFragment(mFragmentTransaction);
        switch (type) {
            case 0://
                //如果fragment是null的话,就创建一个
                if (firstFragment == null) {
                    firstFragment = new FirstFragment();
                    firstFragment.setOnUpdateFirstTabTextListener(this);
                    //加入事务
                    mFragmentTransaction.add(R.id.fragment, firstFragment);
                } else {
                    //如果王超fragment不为空就显示出来
                    mFragmentTransaction.show(firstFragment);
                }
                break;
            case 1:
                if (circleFragment == null) {
                    circleFragment = new FirstTwoFragment();
                    //加入事务
                    mFragmentTransaction.add(R.id.fragment, circleFragment);
                } else {
                    //如果fragment不为空就显示出来
                    mFragmentTransaction.show(circleFragment);
                }
                break;
            case 2:
                if (userFragment == null) {
                    userFragment = new UserFragment();
                    userFragment.setOnUpdateUserTabTextListener(this);
                    //加入事务
                    mFragmentTransaction.add(R.id.fragment, userFragment);
                } else {
                    //如果fragment不为空就显示出来
                    mFragmentTransaction.show(userFragment);
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
        if (firstFragment != null) {
            fragmentTransaction.hide(firstFragment);
        }
        if (circleFragment != null) {
            fragmentTransaction.hide(circleFragment);
        }
        if (userFragment != null) {
            fragmentTransaction.hide(userFragment);
        }
    }



    @Override
    public void setOnUpdateUserTabTextListener() {
        headerText.setText(TimeUtil.getMountCurrentTime(getResources().getString(R.string.month),getResources().getString(R.string.day)));
        headerRight.setVisibility(View.GONE);
    }

    @Override
    public void setOnUpdateFirstTabTextListener() {
        headerText.setText(getResources().getString(R.string.app_names));
        headerRight.setVisibility(View.VISIBLE);
    }


    /**
     * 按两次退出
     */
    private long mExitTime;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                showToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
