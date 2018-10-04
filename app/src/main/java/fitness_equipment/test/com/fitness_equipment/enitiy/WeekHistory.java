package fitness_equipment.test.com.fitness_equipment.enitiy;

import java.util.List;

/**
 * Created by 陈姣姣 on 2017/12/19.
 */

public class WeekHistory {

    /**
     * code : 1
     * message : success
     * body : {"countlist":[{"id":null,"days":"0","tname":"跳绳","number":"4326","times":null,"calorie":"2017"},{"id":null,"days":"1","tname":"跳绳","number":"25","times":"32","calorie":"353"}]}
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
         * tname : 跳绳
         * number : 4326
         * times : null
         * calorie : 2017
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
            private String number;
            private Object times;
            private String calorie;

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

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public Object getTimes() {
                return times;
            }

            public void setTimes(Object times) {
                this.times = times;
            }

            public String getCalorie() {
                return calorie;
            }

            public void setCalorie(String calorie) {
                this.calorie = calorie;
            }
        }
    }
}
