package fitness_equipment.test.com.fitness_equipment.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fitness_equipment.test.com.fitness_equipment.MyApplication;
import fitness_equipment.test.com.fitness_equipment.R;

/**
 * Created by 陈姣姣 on 2017/12/2.
 */

public class BicycleSportsChartsFragment extends Fragment{
     View view;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_bicycle_sports_chart, null);
        MyApplication.getInstance().addActyToList(getActivity());


        return view;
    }

}
