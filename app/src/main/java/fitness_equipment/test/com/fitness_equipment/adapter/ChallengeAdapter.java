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
import fitness_equipment.test.com.fitness_equipment.enitiy.PersonalChallenges;

/**
 * Created by 陈姣姣 on 2017/11/28.
 */

public class ChallengeAdapter extends BaseAdapter {


    List<PersonalChallenges.BodyBean.ChallengrecordlistBean>listChallenge;
    Context context;
    LayoutInflater layoutInflater;

    public ChallengeAdapter(List<PersonalChallenges.BodyBean.ChallengrecordlistBean> listChallenge, Context context) {
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

        PersonalChallenges.BodyBean.ChallengrecordlistBean  challenge=listChallenge.get(position);
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.challenge_record,null);
            viewHolder.typeImg= (ImageView) convertView.findViewById(R.id.typeImg);
            viewHolder.monment_rili= (TextView) convertView.findViewById(R.id.monment_rili);
            viewHolder.tvkm= (TextView) convertView.findViewById(R.id.tvkm);
            viewHolder.tvTime= (TextView) convertView.findViewById(R.id.tvTime);
            viewHolder.tvKical= (TextView) convertView.findViewById(R.id.tvKical);
            convertView.setTag(viewHolder);
        }else {

            viewHolder= (ViewHolder) convertView.getTag();
        }

        if (challenge.getTname().contains("自行车")){
            viewHolder.typeImg.setImageResource(R.mipmap.historical_record_bike);
        }else if (challenge.getTname().contains("跳绳")){
            viewHolder.typeImg.setImageResource(R.mipmap.historical_record_jump);
        }
        if (challenge.getDays()!=null) {
            viewHolder.monment_rili.setText(TimeZhuanHuan(challenge.getDays()));
        }
        if (challenge.getKilometre()!=null) {
            viewHolder.tvkm.setText(challenge.getKilometre());
        }
        if (challenge.getTimes()!=null) {
            viewHolder.tvTime.setText(challenge.getTimes());
        }
        if (challenge.getCalorie()!=null) {
            viewHolder.tvKical.setText(challenge.getCalorie() + context.getResources().getString(R.string.kical));
        }
        return convertView;
    }

    class  ViewHolder{

        ImageView typeImg;
        TextView  monment_rili;
        TextView  tvkm;
        TextView  tvTime;
        TextView  tvKical;



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
