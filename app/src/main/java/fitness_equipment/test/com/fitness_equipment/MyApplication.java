package fitness_equipment.test.com.fitness_equipment;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

import java.util.ArrayList;
import java.util.List;

import fitness_equipment.test.com.fitness_equipment.ble.BleManger;

/**
 * Created by 覃微
 * Data:2017/3/23.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    public static List<Activity> activitiesList = new ArrayList<Activity>(); // 活动管理集合
    //public static BluetoothDevice linkBLE;


    @Override
    public void onCreate() {
        super.onCreate();

        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);

        BleManger.getInstance().intiBle(this);
        Log.e("------->执行？","appliciton");


    }

    /**
     * 获取单例
     *
     * @return
     *
     */
    public MyApplication() {
    }

    //单例模式中获取唯一的MyApplication实例
    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }



    /**
     * 把活动添加到活动管理集合
     *
     * @param acty
     */
    public void addActyToList(Activity acty) {
        if (!activitiesList.contains(acty))
            activitiesList.add(acty);
    }

    /**
     * 把活动从活动管理集合移除
     *
     * @param acty
     */
    public void removeActyFromList(Activity acty) {
        if (activitiesList.contains(acty))
            activitiesList.remove(acty);
    }

    /**
     * 程序退出
     */
    public void clearAllActies() {
        for (Activity acty : activitiesList) {
            if (acty != null)
                acty.finish();
        }
    }


    {

//      PlatformConfig.setQQZone("1106559840", "IgW73WV13ayq8kpV");
        PlatformConfig.setQQZone("1106630784", "ZnleeDQ3Eq3mChYp");
        PlatformConfig.setWeixin("wxdae051c2043869a7", "1fff1bd65b049ef51f1c119038995381");
        PlatformConfig.setSinaWeibo("4291312682", "c1567081738d29daacb18bf5be7c1809","http://open.weibo.com/apps/4291312682/privilege/oauth");

    }
}
