package fitness_equipment.test.com.fitness_equipment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.enitiy.AddDervice;

/**
 * Created by 陈姣姣 on 2017/12/13.
 */

public class Addadapter extends BaseAdapter {

    List<AddDervice.BodyBean.BangdinglistBean.EquListBean>lists;
    Context context;
    LayoutInflater layoutInflater;


    public Addadapter(List<AddDervice.BodyBean.BangdinglistBean.EquListBean> lists, Context context) {
        this.lists = lists;
        this.context = context;
        this.layoutInflater=layoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_add_dervice,null);
            viewHolder.derviceName= (TextView) convertView.findViewById(R.id.derviceName);
            viewHolder.derviceType= (TextView) convertView.findViewById(R.id.derviceType);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.derviceName.setText(lists.get(position).getName());







        return convertView;
    }

    class  ViewHolder{

        TextView derviceName;
        TextView derviceType;

    }
}
