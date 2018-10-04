package fitness_equipment.test.com.fitness_equipment.enitiy;

import java.util.List;

/**
 * Created by 陈姣姣 on 2017/12/9.
 */

public class Calories {

    /**
     * code : 1
     * message : success
     * body : {"countslist":[{"calorie":"2370","times":"32","counts":"2","kilometre":"345"}],"recordlist":[{"tname":"跳绳","createtime":"2017-10-22 14:34:23","id":"1","heading":"/images/aafa1d02-4267-4c85-b89c-957645ce2d0e.jpg","niken":"jiaomei"}]}
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
         * calorie : 2370
         * times : 32
         * counts : 2
         * kilometre : 345
         */

        private List<CountslistBean> countslist;
        /**
         * tname : 跳绳
         * createtime : 2017-10-22 14:34:23
         * id : 1
         * heading : /images/aafa1d02-4267-4c85-b89c-957645ce2d0e.jpg
         * niken : jiaomei
         */

        private List<RecordlistBean> recordlist;

        public List<CountslistBean> getCountslist() {
            return countslist;
        }

        public void setCountslist(List<CountslistBean> countslist) {
            this.countslist = countslist;
        }

        public List<RecordlistBean> getRecordlist() {
            return recordlist;
        }

        public void setRecordlist(List<RecordlistBean> recordlist) {
            this.recordlist = recordlist;
        }

        public static class CountslistBean {
            private String calorie;
            private String times;
            private String counts;
            private String kilometre;

            public String getCalorie() {
                return calorie;
            }

            public void setCalorie(String calorie) {
                this.calorie = calorie;
            }

            public String getTimes() {
                return times;
            }

            public void setTimes(String times) {
                this.times = times;
            }

            public String getCounts() {
                return counts;
            }

            public void setCounts(String counts) {
                this.counts = counts;
            }

            public String getKilometre() {
                return kilometre;
            }

            public void setKilometre(String kilometre) {
                this.kilometre = kilometre;
            }
        }

        public static class RecordlistBean {
            private String tname;
            private String createtime;
            private String id;
            private String heading;
            private String niken;

            public String getTname() {
                return tname;
            }

            public void setTname(String tname) {
                this.tname = tname;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getHeading() {
                return heading;
            }

            public void setHeading(String heading) {
                this.heading = heading;
            }

            public String getNiken() {
                return niken;
            }

            public void setNiken(String niken) {
                this.niken = niken;
            }
        }
    }
}
