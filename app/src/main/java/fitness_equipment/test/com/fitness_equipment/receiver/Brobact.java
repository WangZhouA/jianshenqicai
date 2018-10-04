package fitness_equipment.test.com.fitness_equipment.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by 陈姣姣 on 2017/12/24.
 */

public class Brobact extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("----->闹钟要响了","响了么");

        Intent intent1 =new Intent("XIANG");
        context.sendBroadcast(intent1);

    }
}
