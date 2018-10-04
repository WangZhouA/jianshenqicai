package fitness_equipment.test.com.fitness_equipment.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fitness_equipment.test.com.fitness_equipment.R;


public class SmallMyAdapter extends BaseAdapter {
	private int[] time;
	private Activity context;
	private int tag = 0;

	public SmallMyAdapter(Activity context, int[] time) {
		this.time = time;
		this.context = context;
	}

	@Override
	public int getCount() {
		return time.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder1 vh;
		if (convertView == null) {
			vh = new ViewHolder1();
			convertView = View.inflate(context, R.layout.period_item, null);
			vh.cb = (CheckBox) convertView.findViewById(R.id.checkbox_period);
			vh.tv = (TextView) convertView.findViewById(R.id.textView_period);
			vh.rl = (RelativeLayout) convertView.findViewById(R.id.rl_all);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder1) convertView.getTag();
		}

		int code = time[position];

		if (code == 1) {
			vh.cb.setChecked(true);
		} else {
			vh.cb.setChecked(false);
		}
		if (position == 0) {
			vh.tv.setText("星期一");
		} else if (position == 1) {
			vh.tv.setText("星期二");
		} else if (position == 2) {
			vh.tv.setText("星期三");
		} else if (position == 3) {
			vh.tv.setText("星期四");
		} else if (position == 4) {
			vh.tv.setText("星期五");
		} else if (position == 5) {
			vh.tv.setText("星期六");
		} else if (position == 6) {
			vh.tv.setText("星期日");
		}
		

		vh.cb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (vh.cb.isChecked()) {
					time[position] = 1;
				} else {
					time[position] = 0;
				}
				
			}
		});

		return convertView;
	}

	public String gettimeStr() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < time.length; i++) {
			sb.append(time[i]);
		}
		String s = sb.toString();
		Log.i("", "修改星期后的字符串---------->  " + s);
		return s;
	}

	static final class ViewHolder1 {
		CheckBox cb;
		TextView tv;
		RelativeLayout rl;
	}
}