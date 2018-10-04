package fitness_equipment.test.com.fitness_equipment.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fitness_equipment.test.com.fitness_equipment.MyApplication;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.activity.QueryHistoryActivity;
import fitness_equipment.test.com.fitness_equipment.enitiy.WeekHistory;
import fitness_equipment.test.com.fitness_equipment.http.StringUtils;
import fitness_equipment.test.com.fitness_equipment.sharedPreferences.UserInfo;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 陈姣姣 on 2017/11/30.
 */

public class YearRopeFragment extends Fragment {



    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case 0:
                    /**
                     *  请求成功
                     * */
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LemonBubble.showRight(getActivity(), getResources().getString(R.string.trueRequset), 1000);

                        }
                    }, 2000);
                case 1:


                    tvZongkm.setText(""+getZongHe(score));
                    tvZongTime.setText(""+getZongHe(TimeScore));
                    tvZongKical.setText(""+getZongHe(kicalScore));

                    tvPingjunKm.setText(String.valueOf(getZongHe(score)/score.length));
                    tvPingjunTime.setText(String.valueOf(getZongHe(TimeScore)/TimeScore.length));
                    tvPingjunKical.setText(String.valueOf(getZongHe(kicalScore)/kicalScore.length));


                    break;
            }
        }
    };




    View view;
    //=========================   公里   ==================================================
    private LineChartView lineChart;
    String[] date = {"1", "2", "3", "4", "5", "6", "7","8", "9", "10", "11", "12"};//X轴的标注
    int[] score = {0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0};//图表的数据
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private float minY = 0f;//Y轴坐标最小值
    private float maxY = 10000f;//Y轴坐标最大值
    private boolean hasAxesY = true; //是否需要Y坐标
    private List<AxisValue> mAxisYValues = new ArrayList<AxisValue>();

    //=========================   时间   ==================================================
    private LineChartView TimelineChart;
    int[] TimeScore = {0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0};//图表的数据
    private List<PointValue> TimemPointValues = new ArrayList<PointValue>();
    private List<AxisValue> TimemAxisXValues = new ArrayList<AxisValue>();
    private List<AxisValue> TimemAxisYValues = new ArrayList<AxisValue>();

    //=======================   卡路里 =====================================================

    private float minKical = 0f;//Y轴坐标最小值
    private float maxKicalY = 10000f;//Y轴坐标最大值

    private LineChartView kicallineChart;
    int[] kicalScore = {0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0};//图表的数据
    private List<PointValue> kicalmPointValues = new ArrayList<PointValue>();
    private List<AxisValue>  kicalmAxisXValues = new ArrayList<AxisValue>();
    private List<AxisValue>  kicalmAxisYValues = new ArrayList<AxisValue>();

    private UserInfo userInfo;

    TextView tvPingjunKm;
    TextView tvZongkm;
    TextView tvPingjunTime;
    TextView tvZongTime;
    TextView tvPingjunKical;
    TextView tvZongKical;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_year_rope,null);
        MyApplication.getInstance().addActyToList(getActivity());

        tvPingjunKm= (TextView) view.findViewById(R.id.tv_pingjun_km);
        tvZongkm= (TextView) view.findViewById(R.id.tv_zong_km);
        tvPingjunTime= (TextView) view.findViewById(R.id.tv_pingjun_time);
        tvZongTime= (TextView) view.findViewById(R.id.tv_zong_time);
        tvPingjunKical= (TextView) view.findViewById(R.id.tv_pingjun_kical);
        tvZongKical= (TextView) view.findViewById(R.id.tv_zong_kical);





        IntentFilter intentFilter =new IntentFilter("TOUPDATE");
        getActivity().registerReceiver(BroadcastReceiver,intentFilter);
        userInfo=new UserInfo(getActivity());


        //=========================   公里   ==================================================
        lineChart = (LineChartView) view.findViewById(R.id.lineChartViewKm);
        getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化

        // =========================   时间   ==================================================
        TimelineChart=(LineChartView) view.findViewById(R.id.lineChartViewTime);
        getAxisXLablesTime();//获取x轴的标注
        getAxisPointsTime();//获取坐标点
        initLineChartTime();//初始化

        //======================== 卡路里 ==================================================
        kicallineChart =(LineChartView) view.findViewById(R.id.lineChartViewKical);
        getAxisXLablesKical();//获取x轴的标注
        getAxisPointsKical();//获取坐标点
        initLineChartKical();//初始化


        okHttpQueryWeekKM();


        return view;
    }


    /**
     *  查询周的数据
     * **/
    WeekHistory historyWeek;

    List<String>listWeek=new ArrayList<>();

    private void okHttpQueryWeekKM() {
        OkHttpClient client;
        client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        final String reigstStr = StringUtils.QUERY_ROPE_YEAR  ;

        JSONObject jsonObject = new JSONObject();
        try {
            /**
             * 先给一个假时间
             * */
            jsonObject.put("time", QueryHistoryActivity.getNowTime());
            jsonObject.put("typeId", "2");
            jsonObject.put("uid", String.valueOf(userInfo.getIntInfo("id")));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        final Request request = new Request.Builder()
                .url(reigstStr)
                .post(body).addHeader("cookie", userInfo.getStringInfo("cookie"))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("--->", "" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();
                    Log.i("---->每周的单车数据result",result);
                    Gson gson=new Gson();
                    historyWeek=gson.fromJson(result,WeekHistory.class);
                    //请求成功
                    if (historyWeek.getCode().equals("1")){
                        //请求的时间有相应的数据
                        listWeek.clear();
                        mPointValues.clear();
                        TimemPointValues.clear();
                        kicalmPointValues.clear();
                        if (historyWeek.getBody().getCountlist().size()>0){

                            Message msg =new Message();
                            msg.what=0;
                            handler.sendMessage(msg);
                            //去循环list里面的数据
                            for (int i=0;i<historyWeek.getBody().getCountlist().size();i++){
                                //循环去日期放进一个list集合
                                int  a = Integer.parseInt(historyWeek.getBody().getCountlist().get(i).getDays())-1;
                                if (historyWeek.getBody().getCountlist().get(i).getNumber()!=null) {
                                    score[a] = Integer.parseInt((historyWeek.getBody().getCountlist().get(i)).getNumber());
                                }
                                if (historyWeek.getBody().getCountlist().get(i).getTimes()!=null) {
                                    TimeScore[a] = Integer.parseInt((String) historyWeek.getBody().getCountlist().get(i).getTimes());
                                }
                                if ( historyWeek.getBody().getCountlist().get(i).getCalorie()!=null) {
                                    kicalScore[a] = Integer.parseInt(historyWeek.getBody().getCountlist().get(i).getCalorie());
                                }
                            }
                            Log.i("------->放日期的list集合", Arrays.toString(score));

                            getAxisPoints();//获取坐标点
                            initLineChart();//初始化

                            getAxisPointsTime();//获取坐标点
                            initLineChartTime();//初始化

                            getAxisPointsKical();//获取坐标点
                            initLineChartKical();//初始化


                            Message  message =handler.obtainMessage();
                            message.what=1;
                            handler.sendMessage(message);


                        }
                    }



                } else {
                    Log.i("--->", "错误的修改信息" + response.body().string());

                }
            }
        });

    }



    /**
     * 初始化LineChart的一些设置
     */
    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFFFFF"));  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.setCubic(false);//曲线是否平滑
        line.setStrokeWidth(1);//线条的粗细，默认是3
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//		line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true).setPointRadius(3);//是否显示圆点 如果为false 则没有原点只有点显示,后面的是设置点的半径
        line.setStrokeWidth(1);
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴


        axisX.setHasTiltedLabels(false);  //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
//	    axisX.setTextColor(Color.WHITE);  //设置字体颜色
        axisX.setTextColor(Color.parseColor("#D6D6D9"));//灰色
//	    axisX.setName("未来几天的天气");  //表格名称
        axisX.setTextSize(8);//设置字体大小
        axisX.setMaxLabelChars(2); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisValues.length

        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
//	    data.setAxisXTop(axisX);  //x 轴在顶部
//      axisX.setHasLines(true); //x 轴分割线


        if (hasAxesY==true) {
            Axis axisY = new Axis();  //Y轴
            axisY.setHasLines(true); //y 轴分割线
            axisY.setValues(mAxisYValues);

            axisY.setName("");//y轴标注
            axisY.setTextSize(16);//设置字体大小

            data.setValueLabelBackgroundEnabled(false);
//        如果想要原来的效果可以不用这两句话，我的显示的是透明的
            data.setValueLabelBackgroundColor(Color.TRANSPARENT);
            data.setValueLabelsTextColor(Color.parseColor("#FFFFFF"));
//        data.setAxisYLeft(axisY);  //Y轴设置在左边
            data.setAxisYRight(axisY);  //y轴设置在右边
        }
        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(false);
        lineChart.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
        lineChart.setMaxZoom((float) 1);//缩放比例
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 尼玛搞的老子好辛苦！！！见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         * 下面几句可以设置X轴数据的显示个数（x轴0-7个数据），当数据点个数小于（29）的时候，缩小到极致hellochart默认的是所有显示。当数据点个数大于（29）的时候，
         * 若不设置axisX.setMaxLabelChars(int count)这句话,则会自动适配X轴所能显示的尽量合适的数据个数。
         * 若设置axisX.setMaxLabelChars(int count)这句话,
         * 33个数据点测试，若 axisX.setMaxLabelChars(10);里面的10大于v.right= 7; 里面的7，则
         刚开始X轴显示7条数据，然后缩放的时候X轴的个数会保证大于7小于10
         若小于v.right= 7;中的7,反正我感觉是这两句都好像失效了的样子 - -!
         * 并且Y轴是根据数据的大小自动设置Y轴上限
         * 若这儿不设置 v.right= 7; 这句话，则图表刚开始就会尽可能的显示所有数据，交互性太差
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        lineChart.setCurrentViewport(v);
    }
    /**
     * 初始化LineChart的一些设置
     */
    private void initLineChartTime() {
        Line line = new Line(TimemPointValues).setColor(Color.parseColor("#FFFFFF"));  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.setCubic(false);//曲线是否平滑
        line.setStrokeWidth(1);//线条的粗细，默认是3
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//		line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true).setPointRadius(3);//是否显示圆点 如果为false 则没有原点只有点显示
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴


        axisX.setHasTiltedLabels(false);  //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
//	    axisX.setTextColor(Color.WHITE);  //设置字体颜色
        axisX.setTextColor(Color.parseColor("#D6D6D9"));//灰色
//	    axisX.setName("未来几天的天气");  //表格名称
        axisX.setTextSize(8);//设置字体大小
        axisX.setMaxLabelChars(2); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisValues.length

        axisX.setValues(TimemAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
//	    data.setAxisXTop(axisX);  //x 轴在顶部
//      axisX.setHasLines(true); //x 轴分割线


        if (hasAxesY==true) {
            Axis axisY = new Axis();  //Y轴
            axisY.setHasLines(true); //y 轴分割线
            axisY.setValues(TimemAxisYValues);

            axisY.setName("");//y轴标注
            axisY.setTextSize(16);//设置字体大小

            data.setValueLabelBackgroundEnabled(false);
//        如果想要原来的效果可以不用这两句话，我的显示的是透明的
            data.setValueLabelBackgroundColor(Color.TRANSPARENT);
            data.setValueLabelsTextColor(Color.parseColor("#FFFFFF"));
//        data.setAxisYLeft(axisY);  //Y轴设置在左边
            data.setAxisYRight(axisY);  //y轴设置在右边
        }
        //设置行为属性，支持缩放、滑动以及平移
        TimelineChart.setInteractive(false);
        TimelineChart.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
        TimelineChart.setMaxZoom((float) 1);//缩放比例
        TimelineChart.setLineChartData(data);
        TimelineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 尼玛搞的老子好辛苦！！！见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         * 下面几句可以设置X轴数据的显示个数（x轴0-7个数据），当数据点个数小于（29）的时候，缩小到极致hellochart默认的是所有显示。当数据点个数大于（29）的时候，
         * 若不设置axisX.setMaxLabelChars(int count)这句话,则会自动适配X轴所能显示的尽量合适的数据个数。
         * 若设置axisX.setMaxLabelChars(int count)这句话,
         * 33个数据点测试，若 axisX.setMaxLabelChars(10);里面的10大于v.right= 7; 里面的7，则
         刚开始X轴显示7条数据，然后缩放的时候X轴的个数会保证大于7小于10
         若小于v.right= 7;中的7,反正我感觉是这两句都好像失效了的样子 - -!
         * 并且Y轴是根据数据的大小自动设置Y轴上限
         * 若这儿不设置 v.right= 7; 这句话，则图表刚开始就会尽可能的显示所有数据，交互性太差
         */
        Viewport v = new Viewport(TimelineChart.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        TimelineChart.setCurrentViewport(v);
    }
    /**
     * 初始化LineChart的一些设置
     */
    private void initLineChartKical() {
        Line line = new Line(kicalmPointValues).setColor(Color.parseColor("#FFFFFF"));  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.setCubic(false);//曲线是否平滑
        line.setStrokeWidth(1);//线条的粗细，默认是3
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//		line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true).setPointRadius(3);//是否显示圆点 如果为false 则没有原点只有点显示
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴


        axisX.setHasTiltedLabels(false);  //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
//	    axisX.setTextColor(Color.WHITE);  //设置字体颜色
        axisX.setTextColor(Color.parseColor("#D6D6D9"));//灰色
//	    axisX.setName("未来几天的天气");  //表格名称
        axisX.setTextSize(8);//设置字体大小
        axisX.setMaxLabelChars(2); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisValues.length

        axisX.setValues(kicalmAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
//	    data.setAxisXTop(axisX);  //x 轴在顶部
//      axisX.setHasLines(true); //x 轴分割线


        if (hasAxesY==true) {
            Axis axisY = new Axis();  //Y轴
            axisY.setHasLines(true); //y 轴分割线
            axisY.setValues(kicalmAxisYValues);

            axisY.setName("");//y轴标注
            axisY.setTextSize(16);//设置字体大小

            data.setValueLabelBackgroundEnabled(false);
//        如果想要原来的效果可以不用这两句话，我的显示的是透明的
            data.setValueLabelBackgroundColor(Color.TRANSPARENT);
            data.setValueLabelsTextColor(Color.parseColor("#FFFFFF"));
//        data.setAxisYLeft(axisY);  //Y轴设置在左边
            data.setAxisYRight(axisY);  //y轴设置在右边
        }
        //设置行为属性，支持缩放、滑动以及平移
        kicallineChart.setInteractive(false);
        kicallineChart.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
        kicallineChart.setMaxZoom((float) 1);//缩放比例
        kicallineChart.setLineChartData(data);
        kicallineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 尼玛搞的老子好辛苦！！！见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         * 下面几句可以设置X轴数据的显示个数（x轴0-7个数据），当数据点个数小于（29）的时候，缩小到极致hellochart默认的是所有显示。当数据点个数大于（29）的时候，
         * 若不设置axisX.setMaxLabelChars(int count)这句话,则会自动适配X轴所能显示的尽量合适的数据个数。
         * 若设置axisX.setMaxLabelChars(int count)这句话,
         * 33个数据点测试，若 axisX.setMaxLabelChars(10);里面的10大于v.right= 7; 里面的7，则
         刚开始X轴显示7条数据，然后缩放的时候X轴的个数会保证大于7小于10
         若小于v.right= 7;中的7,反正我感觉是这两句都好像失效了的样子 - -!
         * 并且Y轴是根据数据的大小自动设置Y轴上限
         * 若这儿不设置 v.right= 7; 这句话，则图表刚开始就会尽可能的显示所有数据，交互性太差
         */
        Viewport v = new Viewport(kicallineChart .getMaximumViewport());
        v.left = 0;
        v.right = 7;
        kicallineChart.setCurrentViewport(v);
    }

    /**
     *  公里 X 轴的显示
     */
    private void getAxisXLables() {
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }

        for (float i = minY; i <= maxY; i += 1000) {
            mAxisYValues.add(new AxisValue(i).setLabel(i + ""));
        }

    }
    /**
     *  时间 X 轴的显示
     */
    private void getAxisXLablesTime() {
        for (int i = 0; i < date.length; i++) {
            TimemAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }

        for (float i = minY; i <= maxY; i += 1000) {
            TimemAxisYValues.add(new AxisValue(i).setLabel(i + ""));
        }

    }
    /**
     *  卡路里 X 轴的显示
     */
    private void getAxisXLablesKical() {
        for (int i = 0; i < date.length; i++) {
            kicalmAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }

        for (float i = minKical; i <= maxKicalY; i += 1000) {
            kicalmAxisYValues.add(new AxisValue(i).setLabel(i + ""));
        }

    }



    /**
     *  公里  图表的每个点的显示
     */

    private void getAxisPoints(){
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
    }

    /**
     *   时间    图表的每个点的显示
     */

    private void getAxisPointsTime(){
        for (int i = 0; i < TimeScore.length; i++) {
            TimemPointValues.add(new PointValue(i, TimeScore[i]));
        }
    }
    /**
     *     卡路里    图表的每个点的显示
     */

    private void getAxisPointsKical(){
        for (int i = 0; i < kicalScore.length; i++) {
            kicalmPointValues.add(new PointValue(i, kicalScore[i]));
        }
    }
    public  void  showMyLoadingDialog(){
        LemonBubble.getRoundProgressBubbleInfo()
                .setTitle("Loading...").show(getActivity(),5000);
    }

    private BroadcastReceiver BroadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action =intent.getAction();
            if (action.contains("TOUPDATE")){
                showMyLoadingDialog();
                okHttpQueryWeekKM();
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(BroadcastReceiver);
    }


    /**
     *  总数
     * */
    private int  getZongHe(int []  a ){
        int count=0;
        for (int i=0;i<a.length;i++){
            count+=a[i];
        }

        return  count;
    }

}
