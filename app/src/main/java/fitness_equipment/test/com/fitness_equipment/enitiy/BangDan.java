package fitness_equipment.test.com.fitness_equipment.enitiy;

import java.util.List;

/**
 * Created by 陈姣姣 on 2017/12/11.
 */

public class BangDan {


    /**
     * code : 1
     * message : success
     * body : {"list":[{"ballot":"0","number":null,"uid":null,"createtime":"2017-10-26 18:11:05","heading":null,"niken":null,"calorie":"1200","rank":"1","tname":"自行车","id":"15","vote":null}]}
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
         * number : null
         * uid : null
         * createtime : 2017-10-26 18:11:05
         * heading : null
         * niken : null
         * calorie : 1200
         * rank : 1
         * tname : 自行车
         * id : 15
         * vote : null
         */

        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String ballot;
            private Object number;
            private Object uid;
            private String createtime;
            private Object heading;
            private Object niken;
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

            public Object getNumber() {
                return number;
            }

            public void setNumber(Object number) {
                this.number = number;
            }

            public Object getUid() {
                return uid;
            }

            public void setUid(Object uid) {
                this.uid = uid;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public Object getHeading() {
                return heading;
            }

            public void setHeading(Object heading) {
                this.heading = heading;
            }

            public Object getNiken() {
                return niken;
            }

            public void setNiken(Object niken) {
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
