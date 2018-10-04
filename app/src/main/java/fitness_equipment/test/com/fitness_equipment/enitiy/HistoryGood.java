package fitness_equipment.test.com.fitness_equipment.enitiy;

import java.util.List;

/**
 * Created by 陈姣姣 on 2017/12/15.
 */

public class HistoryGood {


    /**
     * code : 1
     * message : success
     * body : {"bestrecordlist":[{"times":"221","heartrate":null,"calorie":"435","days":"2017-10-10","tname":null,"id":"10","kilometre":null,"speed":null}]}
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
         * times : 221
         * heartrate : null
         * calorie : 435
         * days : 2017-10-10
         * tname : null
         * id : 10
         * kilometre : null
         * speed : null
         */

        private List<BestrecordlistBean> bestrecordlist;

        public List<BestrecordlistBean> getBestrecordlist() {
            return bestrecordlist;
        }

        public void setBestrecordlist(List<BestrecordlistBean> bestrecordlist) {
            this.bestrecordlist = bestrecordlist;
        }

        public static class BestrecordlistBean {
            private String times;
            private Object heartrate;
            private String calorie;
            private String days;
            private Object tname;
            private String id;
            private Object kilometre;
            private Object speed;

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

            public String getCalorie() {
                return calorie;
            }

            public void setCalorie(String calorie) {
                this.calorie = calorie;
            }

            public String getDays() {
                return days;
            }

            public void setDays(String days) {
                this.days = days;
            }

            public Object getTname() {
                return tname;
            }

            public void setTname(Object tname) {
                this.tname = tname;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
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
        }
    }
}
