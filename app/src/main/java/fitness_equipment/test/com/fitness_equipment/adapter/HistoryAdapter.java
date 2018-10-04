package fitness_equipment.test.com.fitness_equipment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.enitiy.HistoryJiLu;

/**
 * Created by 陈姣姣 on 2017/12/1.
 */

public class HistoryAdapter extends BaseAdapter {
    List<HistoryJiLu.BodyBean.DetatilhistorylistBean> listChallenge;
    Context context;
    LayoutInflater layoutInflater;

    public HistoryAdapter(List<HistoryJiLu.BodyBean.DetatilhistorylistBean> listChallenge, Context context) {
        this.listChallenge = listChallenge;
        this.context = context;
        this.layoutInflater=layoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return listChallenge.size();
    }

    @Override
    public Object getItem(int position) {
        return listChallenge.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HistoryJiLu.BodyBean.DetatilhistorylistBean challenge=listChallenge.get(position);
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.challenge_record,null);
            viewHolder.typeImg= (ImageView) convertView.findViewById(R.id.typeImg);
            viewHolder.monment_rili= (TextView) convertView.findViewById(R.id.monment_rili);
            viewHolder.tvkm= (TextView) convertView.findViewById(R.id.tvkm);
            viewHolder.tvTime= (TextView) convertView.findViewById(R.id.tvTime);
            viewHolder.tvKical= (TextView) convertView.findViewById(R.id.tvKical);
            viewHolder.tv_name_type= (TextView) convertView.findViewById(R.id.tv_name_type);
            convertView.setTag(viewHolder);
        }else {

            viewHolder= (ViewHolder) convertView.getTag();
        }

        if (challenge.getTypeId()==1){
            viewHolder.typeImg.setImageResource(R.mipmap.historical_record_bike);
        }else if (challenge.getTypeId()==2){
            viewHolder.typeImg.setImageResource(R.mipmap.historical_record_jump);
        }

        if (challenge.getCreatetime()!=null) {
            viewHolder.monment_rili.setText(TimeZhuanHuan(challenge.getCreatetime()));
        }
        if (challenge.getTypeId()==1) {
//            if (challenge.getKilometre()!=null || challenge.getKilometre().equals("null") ) {
                viewHolder.tvkm.setText(""+ challenge.getKilometre());
              if ( viewHolder.tvkm.getText().toString().contains("null")){
                  viewHolder.tvkm.setText("0");
              }


//            }else {
//                viewHolder.tvkm.setText("0");
//            }

            viewHolder.tvKical.setText(""+challenge.getCalorie()+context.getResources().getString(R.string.kical));

        }else {
            viewHolder.typeImg.setImageResource(R.mipmap.historical_record_jump);
            viewHolder.tvkm.setText(""+ challenge.getKilometre());
            viewHolder.tvKical.setVisibility(View.GONE);
            viewHolder.tv_name_type.setText(context.getResources().getString(R.string.count));


        }
          if (challenge.getTimes()!=null) {
              viewHolder.tvTime.setText(""+ challenge.getTimes());
          }else {

          }
        return convertView;
    }

    class  ViewHolder{

        ImageView typeImg;
        TextView  monment_rili;
        TextView  tvkm;
        TextView  tvTime;
        TextView  tvKical;
        TextView tv_name_type;



    }


    private  String  TimeZhuanHuan(String time){

        SimpleDateFormat f =new SimpleDateFormat("yyyy-mm-dd");
        try {
            System.out.println(f.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat f1 =new SimpleDateFormat("mm-dd");
        String datestr = null;//格式化数据
        try {
            datestr = f1.format(f.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  datestr;
    }

}
