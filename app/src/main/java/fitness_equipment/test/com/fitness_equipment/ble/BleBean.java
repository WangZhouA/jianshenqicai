package fitness_equipment.test.com.fitness_equipment.ble;

/**
 * Created by Administrator on 2017/9/15.
 * 蓝牙实体类
 */

public class BleBean {

    String address;
    String Name;


    public BleBean() {

    }

    public BleBean(String name, String address) {
        this.Name = name;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    //是否是同一个
    public boolean isCommon(BleBean bleBean) {
        if (this.Name.equals(bleBean.getName()) && this.address.equals(bleBean.getAddress())) {
            return true;
        }
        return false;
    }




}
