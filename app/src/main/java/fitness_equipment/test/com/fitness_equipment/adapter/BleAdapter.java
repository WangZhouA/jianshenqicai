package fitness_equipment.test.com.fitness_equipment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.ble.BleBean;


/**
 * Created by Administrator on 2017/9/23.
 */

public class BleAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<BleBean> datas;
    DelCallback delCallbacl;

    public BleAdapter(Context context, List<BleBean> datas) {
        this.context = context;
        setDatas(datas);
        this.inflater = LayoutInflater.from(context);
    }

    public void setDatas(List<BleBean> datas) {
        if (datas != null)
            this.datas = datas;
        else
            this.datas = new ArrayList<BleBean>();
    }

    //数据更新的方法，用于数据改变时刷新数据
    public void updataAdapter(List<BleBean> datas) {
        setDatas(datas);
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public BleBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.copy_item_search_dervice, null);
            viewHolder = new ViewHolder();
            viewHolder.num = (TextView) convertView.findViewById(R.id.dervice_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (datas.size() > 0) {
            viewHolder.num.setText(datas.get(position).getName());

        }

        return convertView;
    }

    private class ViewHolder {
        TextView num;
    }


    public void setDelCallbacl(DelCallback delCallbacl) {
        this.delCallbacl = delCallbacl;
    }


    //
    public interface DelCallback {
        void onDel(BleBean devBean);
    }

}



