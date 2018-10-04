package fitness_equipment.test.com.fitness_equipment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.enitiy.WeekBangDan;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.view.CircleImageView;

/**
 * Created by 陈姣姣 on 2017/12/12.
 */

public class WeekBangDanAdapter extends BaseAdapter{

    List<WeekBangDan .BodyBean.SelfweeklistBean> lists;
    Context context;
    LayoutInflater layoutInflater;


    public WeekBangDanAdapter(List<WeekBangDan.BodyBean.SelfweeklistBean> lists, Context context) {
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

        WeekBangDan.BodyBean.SelfweeklistBean bangdan= lists.get(position);
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_day_paihangbang,null);
            viewHolder.bangdan_number= (TextView) convertView.findViewById(R.id.bangdan_number_id);
            viewHolder.bangdan_img= (CircleImageView) convertView.findViewById(R.id.bangdan_img);
            viewHolder.bangdan_name= (TextView) convertView.findViewById(R.id.bangdan_name);
            viewHolder.bangdan_kaicls= (TextView) convertView.findViewById(R.id.bangdan_kaicls);
            viewHolder.bangdan_people= (TextView) convertView.findViewById(R.id.bangdan_people);
            viewHolder.bangdan_dianzang= (ImageView) convertView.findViewById(R.id.bangdan_xin);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.bangdan_people.setText(bangdan.getBallot());
        viewHolder.bangdan_number.setText(bangdan.getRank());
        if (bangdan.getNiken() !=null) {
            viewHolder.bangdan_name.setText((String) bangdan.getNiken());
        }
        if (bangdan.getHeading()!=null){
            StringUtils.showImage(context,StringUtils.GET_PHOTO+bangdan.getHeading(), R.mipmap.male_head2,R.mipmap.male_head2,viewHolder.bangdan_img);
        }
        if (bangdan.getCalorie()!=null){
            viewHolder.bangdan_kaicls.setText(bangdan.getCalorie()+context.getResources().getString(R.string.kical));
        }

        if (bangdan.getVote()!=null){
            viewHolder.bangdan_dianzang.setImageResource(R.mipmap.praise_pre);
        }else {
            viewHolder.bangdan_dianzang.setImageResource(R.mipmap.praise_s);
        }


        return convertView;
    }

    class  ViewHolder{

        TextView bangdan_number;
        CircleImageView bangdan_img;
        TextView  bangdan_name;
        TextView  bangdan_kaicls;
        TextView  bangdan_people;
        ImageView bangdan_dianzang;



    }

}
