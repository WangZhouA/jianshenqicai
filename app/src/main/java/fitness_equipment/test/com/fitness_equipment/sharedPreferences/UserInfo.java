package fitness_equipment.test.com.fitness_equipment.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 陈姣姣 on 2017/5/19.
 */

public class UserInfo {

    private String USER_INFO = "userInfo";

    private Context context;

    public UserInfo() {
    }

    public UserInfo(Context context) {

        this.context = context;
    }
    // 存放字符串型的值
    public void setUserInfo(String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(USER_INFO,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.putString(key, value);
        editor.commit();
    }

    // 存放整形的值
    public void setUserInfo(String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(USER_INFO,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.putInt(key, value);
        editor.commit();
    }

    // 存放长整形值
    public void setUserInfo(String key, Long value) {
        SharedPreferences sp = context.getSharedPreferences(USER_INFO,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.putLong(key, value);
        editor.commit();
    }

    // 存放布尔型值
    public void setUserInfo(String key, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences(USER_INFO,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.putBoolean(key, value);
        editor.commit();
    }

    // 清空记录
    public void clear() {
        SharedPreferences sp = context.getSharedPreferences(USER_INFO,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    // 只销毁某一个，或者几个时候
     public void logOut(String ACCOUNT) {
     SharedPreferences sp = context.getSharedPreferences(USER_INFO,
     MODE_PRIVATE);
     SharedPreferences.Editor editor = sp.edit();
     editor.remove(ACCOUNT);
     editor.commit();
     }

    // 获得用户信息中某项字符串型的值
    public String getStringInfo(String key) {
        SharedPreferences sp = context.getSharedPreferences(USER_INFO,
                MODE_PRIVATE);
        return sp.getString(key, "");
    }

    // 获得用户息中某项整形参数的值
    public int getIntInfo(String key) {
        SharedPreferences sp = context.getSharedPreferences(USER_INFO,
                MODE_PRIVATE);
        return sp.getInt(key, -1);
    }

    // 获得用户信息中某项长整形参数的值
    public Long getLongInfo(String key) {
        SharedPreferences sp = context.getSharedPreferences(USER_INFO,
                MODE_PRIVATE);
        return sp.getLong(key, -1);
    }

    // 获得用户信息中某项布尔型参数的值
    public boolean getBooleanInfo(String key) {
        SharedPreferences sp = context.getSharedPreferences(USER_INFO,
                MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }



        //存数组
    public void setSharedPreference(String key, String[] values) {
        String regularEx = "#";
        String str = "";
        SharedPreferences sp = context.getSharedPreferences("data", MODE_PRIVATE);
        if (values != null && values.length > 0) {
            for (String value : values) {
                str += value;
                str += regularEx;
            }
            SharedPreferences.Editor et = sp.edit();
            et.putString(key, str);
            et.commit();
        }
    }


    public String[] getSharedPreference(String key) {
        String regularEx = "#";
        String[] str = null;
        SharedPreferences sp = context.getSharedPreferences("data", MODE_PRIVATE);
        String values;
        values = sp.getString(key, "");
        str = values.split(regularEx);

        return str;
    }


    public void saveInfo(Context context, String key, List<Map<String, String>> datas) {
        JSONArray mJsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            Map<String, String> itemMap = datas.get(i);
            Iterator<Map.Entry<String, String>> iterator = itemMap.entrySet().iterator();

            JSONObject object = new JSONObject();

            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                try {
                    object.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {

                }
            }
            mJsonArray.put(object);
        }

        SharedPreferences sp = context.getSharedPreferences("finals", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, mJsonArray.toString());
        editor.commit();
    }


    //存集合List中包含的Map键值对
    public List<Map<String, String>> getInfo(Context context, String key) {
        List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
        SharedPreferences sp = context.getSharedPreferences("finals", MODE_PRIVATE);
        String result = sp.getString(key, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                Map<String, String> itemMap = new HashMap<String, String>();
                JSONArray names = itemObject.names();
                if (names != null) {
                    for (int j = 0; j < names.length(); j++) {
                        String name = names.getString(j);
                        String value = itemObject.getString(name);
                        itemMap.put(name, value);
                    }
                }
                datas.add(itemMap);
            }
        } catch (JSONException e) {

        }

        return datas;
    }


}
