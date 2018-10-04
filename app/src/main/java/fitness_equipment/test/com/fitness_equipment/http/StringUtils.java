package fitness_equipment.test.com.fitness_equipment.http;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * Created by 陈姣姣 on 2017/11/30.
 */

public class StringUtils {


//    public static  String HTTP_SERVICE=  "http://120.78.204.231/";
//    http://120.78.204.231/images/interface.html
    //内网服务器
//        public static  String HTTP_SERVICE ="http://172.16.10.103:8081/FitnessEquipment1/";
        public static  String HTTP_SERVICE ="http://58.250.30.13:8086/FitnessEquipment1/";


    //请求图片
//    public  static  String  GET_PHOTO= "http://172.16.10.103:8081/images/";
    public  static  String  GET_PHOTO= "http://58.250.30.13:8086/images/";

    //注册的验证码
    public  static  String  GET_CODE =  HTTP_SERVICE+ "user/getMsg";
    //注册
    public  static  String  REIGST =  HTTP_SERVICE+ "user/regist";
    //短信找回密码
    public  static  String  PASS_FOR_PHONE = HTTP_SERVICE+"user/forgetPassword";
    //登录
    public  static  String LOGIN = HTTP_SERVICE+"user/login";
    //修改性别
    public  static  String RESET_USER =HTTP_SERVICE+ "user/updateusermessage";
    //我的挑战
    public  static  String MY_CHALLENGE = HTTP_SERVICE +"EquipmentData/querychallengerecord";
    //上传图片
    public  static  String UPDATE_PICTURE = HTTP_SERVICE +"user/uploadPic";
    //上传用户数据
    public  static  String USER_DATE = HTTP_SERVICE+"user/updateusermessage";
    //查询用户信息
    public  static  String QUERY_USER_DATE = HTTP_SERVICE+"user/queryUserbyid";
    //退出登录
    public  static  String EXIT_LOGIN = HTTP_SERVICE+"user/outlogin";
    //查询旧密码是否正确
    public  static  String QUERY_OLD_PASSWORD = HTTP_SERVICE+"messageController/isolepassword";
    //修改密码
    public static  String RE_OLD_PASS =HTTP_SERVICE+"messageController/updatepassword";
    //打卡记录
    public static  String  CALORIES =HTTP_SERVICE + "EquipmentData/BicycleCounts";
    //排行榜日查询
    public static  String BANGDAN_QUERY= HTTP_SERVICE+ "EquipmentData/queryrankingbyday";
    //排行榜周查询
    public static  String WEEK_BANGDAN_QUERY = HTTP_SERVICE +"EquipmentData/queryselfrankingbyweek";
    //排行榜月查询
    public static  String MONT_BANGDAN_QUERY = HTTP_SERVICE+"EquipmentData/queryrankingbymonth";
    //排行榜年查询
    public static  String YEAR_BANGDAN_QUERY = HTTP_SERVICE +"EquipmentData/queryrankingbyyear";
    //查询绑定的设备列表信息
    public static  String QUERY_BINDER_DERVICE=  HTTP_SERVICE+"equipment/getbangdinglist";
    //修改设备别名
    public static  String RENAME_DERVICE=HTTP_SERVICE+"equipment/updateequipmentname";
    //删除设备信息
    public static  String DELETE_DERVICE = HTTP_SERVICE+ "equipment/deletebangding";
    //查询历史记录
    public static  String QUERY_HISTORY =HTTP_SERVICE +"EquipmentData/querydetailhistorydatalist";
    //删除历史数据
    public static  String DELETE_DATE=HTTP_SERVICE+"EquipmentData/deletehistoryrecord";
    //删除挑战记录
    public static  String DELETE_CHALLENGE_DATE = HTTP_SERVICE+"EquipmentData/deleterecord";
    //批量上传图片
    public static  String UPDATE_PIC_MACUCH =HTTP_SERVICE+"messageController/suggestionphoto";
    //查询自行车最好的信息
    public static  String GOOD_BICYCLE=HTTP_SERVICE+"EquipmentData/bicyclebestrecord";
    //上传意见反馈
    public static  String YIJIANFANKUI =HTTP_SERVICE+"messageController/suggestandphotolist";
    //日榜单点赞
    public static  String DAY_DIANZANG=HTTP_SERVICE+"EquipmentData/dayvoting";
    //日榜单取消点赞
    public static  String CANCEL_DIANZANG= HTTP_SERVICE+"EquipmentData/cancledayvoting";
    //周榜单点赞
    public static  String WEEK_DIANZANG=HTTP_SERVICE+"EquipmentData/weekvoting";
    //周榜单取消点赞
    public static  String WEEK_CANCEL_DIANZANG = HTTP_SERVICE+"EquipmentData/cancleweekvoting";
    //月榜单点赞
    public static  String MONT_DIANZANG = HTTP_SERVICE+"EquipmentData/monthvoting";
    //月榜单取消点赞
    public static  String MONT_CANCEL_DIANZANG = HTTP_SERVICE+"EquipmentData/canclemonthvoting";
    //年榜单点赞
    public static  String YEAR_DIANZANG = HTTP_SERVICE+ "EquipmentData/yearvoting";
    //年榜单取消点赞
    public static  String YEAR_CANCEL_DIANZANG = HTTP_SERVICE+"EquipmentData/cancleyearvoting";
     //查询闹钟
    public static  String QUERY_NAOZHONG = HTTP_SERVICE +"messageController/querysportsmessage";
    //添加闹钟
    public static  String ADD_NAOZHONG = HTTP_SERVICE+"messageController/addsportsmessage";
    //删除闹钟
    public static  String DELETE_NAOZHONG = HTTP_SERVICE+"messageController/deletesportsmessage";
    //修改闹钟提醒
    public static  String RE_NAOZHONG_TIXING = HTTP_SERVICE + "messageController/updatesportsmessage";
    //查询历史记录按天查询（动感单车）
    public static  String QUERY_BICYCLE_DAY =HTTP_SERVICE+"EquipmentData/querybicycleDatabyday";
    //查询历史记录按天查询（活力跳绳）
    public static  String QUERY_ROPE_DAY = HTTP_SERVICE+ "EquipmentData/queryskipDatabyday";
    //查询周的历史记录（动感单车）
    public static  String QUERY_BICYCLE_WEEK = HTTP_SERVICE+"EquipmentData/querybicycleDatabyweek";
    //查询周的历史数据 (活力跳绳)
    public static  String QUERY_ROPE_WEEK = HTTP_SERVICE +"EquipmentData/queryskipDatabyweek";
    //查询年的历史数据（动感单车）
    public static  String QUERY_BICYCLE_YEAR = HTTP_SERVICE + "EquipmentData/querybicycleDatabyyear";
    //查询年的历史数据（活力跳绳）
    public static  String QUERY_ROPE_YEAR = HTTP_SERVICE + "EquipmentData/querybiskipDatabyyear";
    //查询月的历史数据（动感单车）
    public static  String QUERY_BICYCLE_MONT =HTTP_SERVICE +"EquipmentData/querybicycleDatabymonth";
    //查询月的历史数据（活力跳绳）
    public static  String QUERY_ROPE_MONT= HTTP_SERVICE + "EquipmentData/querybiskipDatabymonth";
     //绑定设备
    public static  String BINDER_DERVICE= HTTP_SERVICE+ "equipment/equipmentbangding";
    //获取token;
    public static  String PANDUAN_SHIFOU_APP =  HTTP_SERVICE +"ThreePartyLogin/getKey";
    //那种方式的第三方登录
    public static  String LOGIN_TYPE_THREE =  HTTP_SERVICE+ "ThreePartyLogin/login";
    //第三方绑定的接口
    public static  String THREE_LONGIN_BINDER = HTTP_SERVICE + "ThreePartyLogin/building";




    public  static  void showImage(Context context, String url, int erro , int loadpic, ImageView imageView){
        Glide.with(context).load(url).asBitmap().fitCenter().placeholder(loadpic).error(erro).dontAnimate().into(imageView);
    }
}
