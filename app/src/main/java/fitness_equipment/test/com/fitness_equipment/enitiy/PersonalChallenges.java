package fitness_equipment.test.com.fitness_equipment.enitiy;

import java.util.List;

/**
 * Created by 陈姣姣 on 2017/12/7.
 */

public class PersonalChallenges {


    /**
     * code : 1
     * message : success
     * body : {"challengrecordlist":[{"times":"24","calorie":"234","tname":"自行车","days":"2017-10-10","id":1,"kilometre":"24"}]}
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
         * times : 24
         * calorie : 234
         * tname : 自行车
         * days : 2017-10-10
         * id : 1
         * kilometre : 24
         */

        private List<ChallengrecordlistBean> challengrecordlist;

        public List<ChallengrecordlistBean> getChallengrecordlist() {
            return challengrecordlist;
        }

        public void setChallengrecordlist(List<ChallengrecordlistBean> challengrecordlist) {
            this.challengrecordlist = challengrecordlist;
        }

        public static class ChallengrecordlistBean {
            private String times;
            private String calorie;
            private String tname;
            private String days;
            private int id;
            private String kilometre;

            public String getTimes() {
                return times;
            }

            public void setTimes(String times) {
                this.times = times;
            }

            public String getCalorie() {
                return calorie;
            }

            public void setCalorie(String calorie) {
                this.calorie = calorie;
            }

            public String getTname() {
                return tname;
            }

            public void setTname(String tname) {
                this.tname = tname;
            }

            public String getDays() {
                return days;
            }

            public void setDays(String days) {
                this.days = days;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getKilometre() {
                return kilometre;
            }

            public void setKilometre(String kilometre) {
                this.kilometre = kilometre;
            }
        }
    }
}
