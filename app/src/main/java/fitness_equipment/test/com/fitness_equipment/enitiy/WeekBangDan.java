package fitness_equipment.test.com.fitness_equipment.enitiy;

import java.util.List;

/**
 * Created by 陈姣姣 on 2017/12/12.
 */

public class WeekBangDan {

    /**
     * code : 1
     * message : success
     * body : {"selfweeklist":[{"ballot":"0","number":"120","uid":"1","createtime":"2017-10-10 00:50:00","heading":"/images/aafa1d02-4267-4c85-b89c-957645ce2d0e.jpg","niken":"jiaomei","calorie":"435","rank":"2","tname":"自行车","id":"10","vote":null}]}
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
         * ballot : 0
         * number : 120
         * uid : 1
         * createtime : 2017-10-10 00:50:00
         * heading : /images/aafa1d02-4267-4c85-b89c-957645ce2d0e.jpg
         * niken : jiaomei
         * calorie : 435
         * rank : 2
         * tname : 自行车
         * id : 10
         * vote : null
         */

        private List<SelfweeklistBean> selfweeklist;

        public List<SelfweeklistBean> getSelfweeklist() {
            return selfweeklist;
        }

        public void setSelfweeklist(List<SelfweeklistBean> selfweeklist) {
            this.selfweeklist = selfweeklist;
        }

        public static class SelfweeklistBean {
            private String ballot;
            private String number;
            private String uid;
            private String createtime;
            private String heading;
            private String niken;
            private String calorie;
            private String rank;
            private String tname;
            private String id;
            private Object vote;

            public String getBallot() {
                return ballot;
            }

            public void setBallot(String ballot) {
                this.ballot = ballot;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
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

            public String getCalorie() {
                return calorie;
            }

            public void setCalorie(String calorie) {
                this.calorie = calorie;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getTname() {
                return tname;
            }

            public void setTname(String tname) {
                this.tname = tname;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Object getVote() {
                return vote;
            }

            public void setVote(Object vote) {
                this.vote = vote;
            }
        }
    }
}
