package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by 陈姣姣 on 2017/12/22.
 */

public class TestNaoZhong extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if("com.pxd.alarmandnotice.RING".equals(intent.getAction())){
            Log.i("test","闹钟响了");
            //跳转到Activity n //广播接受者中（跳转Activity）
            Intent intent1=new Intent(context,TestNaoZhong.class);
            //给Intent设置标志位
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}
