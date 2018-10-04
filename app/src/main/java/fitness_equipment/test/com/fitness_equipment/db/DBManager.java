package fitness_equipment.test.com.fitness_equipment.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fitness_equipment.test.com.fitness_equipment.enitiy.Timing;


public class DBManager {
	private DBHelper helper;
	private SQLiteDatabase db;

	public DBManager(Context context) {
		helper = new DBHelper(context);
		// 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,
		// mFactory);
		// 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		db = helper.getWritableDatabase();
	}

	/**
	 * add persons
	 * 
	 * @param
	 */
	public void add(List<Timing> items) {
		db.beginTransaction(); // 开始事务
		try {
			for (Timing item : items) {
				db.execSQL("INSERT INTO naozhong VALUES(null, ?, ?, ?, ?)",
						new Object[] { item.tvtimingtime, item.lable,
								item.alarmclockswitch, item.weekStr });
			}
			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
		}
	}

	/**
	 * 更改 时间
	 */
	public void updateTime(Timing item) {
		ContentValues cv = new ContentValues();
		cv.put("time", item.getTvtimingtime());
		db.update("naozhong", cv, "_id = ?", new String[] { item.id + "" });
	}

	/**
	 * 更改 标签
	 */
	public void updateTable(Timing item) {
		ContentValues cv = new ContentValues();
		cv.put("lable", item.getLable());
		db.update("naozhong", cv, "_id = ?", new String[] { item.id + "" });
	}

	/**
	 * 更改 星期
	 */
	public void updateWeektime(Timing item) {
		ContentValues cv = new ContentValues();
		Log.i("", "更改数据库星期值为：    " + item.getWeekStr());
		cv.put("weektime", item.getWeekStr());
		db.update("naozhong", cv, "_id = ?", new String[] { item.id + "" });
	}

	/**
	 * 删除
	 */
	public void deleteOldPerson(Timing item) {
		db.delete("naozhong", "_id = ?",
				new String[] { String.valueOf(item.id) });
	}

	/**
	 * 获取所有数据
	 */
	public List<Timing> query() {
		ArrayList<Timing> items = new ArrayList<Timing>();
		Cursor c = queryTheCursor();
		while (c.moveToNext()) {
			Timing item = new Timing();
			item.id = c.getInt(c.getColumnIndex("_id"));
			item.tvtimingtime = c.getString(c.getColumnIndex("time"));
			item.lable = c.getInt(c.getColumnIndex("lable"));
			item.alarmclockswitch = c.getInt(c.getColumnIndex("clockisopen"));
			item.weekStr = c.getString(c.getColumnIndex("weektime"));
			Log.i("", "标签------------》" + item.lable);
			char[] ch = item.weekStr.toCharArray();
			for (int i = 0; i < ch.length; i++) {

				// Log.i("", "拆分结果=======>"+ch[i]);
				item.weektime[i] = Integer.valueOf(ch[i] + "");
				Log.i("", "000---------->  " + item.weektime[i]);
			}

			items.add(item);
		}
		c.close();

		return items;
	}

	/** 
     */
	public Cursor queryTheCursor() {
		Cursor c = db.rawQuery("SELECT * FROM naozhong", null);
		return c;
	}

	/**
	 * close database
	 */
	public void closeDB() {
		db.close();
	}
}