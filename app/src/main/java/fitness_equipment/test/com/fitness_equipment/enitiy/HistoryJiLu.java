package fitness_equipment.test.com.fitness_equipment.enitiy;

import java.util.List;

/**
 * Created by 陈姣姣 on 2017/12/13.
 */

public class HistoryJiLu {


    /**
     * code : 1
     * message : success
     * body : {"detatilhistorylist":[{"number":null,"uid":1,"createtime":"2017-12-11 15:23:10","times":null,"heartrate":null,"calorie":120,"weight":null,"typeId":1,"id":1,"kilometre":null,"speed":null,"status":null},{"number":null,"uid":1,"createtime":"2017-12-11 15:13:10","times":null,"heartrate":null,"calorie":300,"weight":null,"typeId":1,"id":3,"kilometre":null,"speed":null,"status":null}]}
     */

    private String code;
    private String message;
    private BodyBean body;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * number : null
         * uid : 1
         * createtime : 2017-12-11 15:23:10
         * times : null
         * heartrate : null
         * calorie : 120
         * weight : null
         * typeId : 1
         * id : 1
         * kilometre : null
         * speed : null
         * status : null
         */

        private List<DetatilhistorylistBean> detatilhistorylist;

        public List<DetatilhistorylistBean> getDetatilhistorylist() {
            return detatilhistorylist;
        }

        public void setDetatilhistorylist(List<DetatilhistorylistBean> detatilhistorylist) {
            this.detatilhistorylist = detatilhistorylist;
        }

        public static class DetatilhistorylistBean {
            private Object number;
            private int uid;
            private String createtime;
            private String times;
            private Object heartrate;
            private int calorie;
            private Object weight;
            private int typeId;
            private int id;
            private Object kilometre;
            private Object speed;
            private Object status;

            public Object getNumber() {
                return number;
            }

            public void setNumber(Object number) {
                this.number = number;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getTimes() {
                return times;
            }

            public void setTimes(String times) {
                this.times = times;
            }

            public Object getHeartrate() {
                return heartrate;
            }

            public void setHeartrate(Object heartrate) {
                this.heartrate = heartrate;
            }

            public int getCalorie() {
                return calorie;
            }

            public void setCalorie(int calorie) {
                this.calorie = calorie;
            }

            public Object getWeight() {
                return weight;
            }

            public void setWeight(Object weight) {
                this.weight = weight;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getKilometre() {
                return kilometre;
            }

            public void setKilometre(Object kilometre) {
                this.kilometre = kilometre;
            }

            public Object getSpeed() {
                return speed;
            }

            public void setSpeed(Object speed) {
                this.speed = speed;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }
        }
    }
}
