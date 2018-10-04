package fitness_equipment.test.com.fitness_equipment.enitiy;

import java.util.List;

/**
 * Created by 陈姣姣 on 2017/12/18.
 */

public class HistoryWeek {


    /**
     * code : 1
     * message : success
     * body : {"countlist":[{"id":null,"days":"0","tname":"自行车","calorie":"23","kilometre":"23","times":"32","heartrate":null,"speed":null},{"id":null,"days":"1","tname":"自行车","calorie":"523","kilometre":"53","times":"54","heartrate":"45","speed":"54"}]}
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
         * id : null
         * days : 0
         * tname : 自行车
         * calorie : 23
         * kilometre : 23
         * times : 32
         * heartrate : null
         * speed : null
         */

        private List<CountlistBean> countlist;

        public List<CountlistBean> getCountlist() {
            return countlist;
        }

        public void setCountlist(List<CountlistBean> countlist) {
            this.countlist = countlist;
        }

        public static class CountlistBean {
            private Object id;
            private String days;
            private String tname;
            private String calorie;
            private String kilometre;
            private String times;
            private Object heartrate;
            private Object speed;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public String getDays() {
                return days;
            }

            public void setDays(String days) {
                this.days = days;
            }

            public String getTname() {
                return tname;
            }

            public void setTname(String tname) {
                this.tname = tname;
            }

            public String getCalorie() {
                return calorie;
            }

            public void setCalorie(String calorie) {
                this.calorie = calorie;
            }

            public String getKilometre() {
                return kilometre;
            }

            public void setKilometre(String kilometre) {
                this.kilometre = kilometre;
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

            public Object getSpeed() {
                return speed;
            }

            public void setSpeed(Object speed) {
                this.speed = speed;
            }
        }
    }
}
