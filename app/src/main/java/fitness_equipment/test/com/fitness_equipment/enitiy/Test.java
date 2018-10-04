package fitness_equipment.test.com.fitness_equipment.enitiy;

import java.util.List;

/**
 * Created by 陈姣姣 on 2017/12/9.
 */

public class Test {

    /**
     * code : 1
     * message : success
     * body : {"bangdinglist":[{"tname":"自行车","equList":[{"name":"南方ID","tname":"自行车","id":39,"mac":"ffff"}]},{"tname":"跳绳","equList":[{"name":"124","tname":"跳绳","id":44,"mac":"fda"}]}]}
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
         * tname : 自行车
         * equList : [{"name":"南方ID","tname":"自行车","id":39,"mac":"ffff"}]
         */

        private List<BangdinglistBean> bangdinglist;

        public List<BangdinglistBean> getBangdinglist() {
            return bangdinglist;
        }

        public void setBangdinglist(List<BangdinglistBean> bangdinglist) {
            this.bangdinglist = bangdinglist;
        }

        public static class BangdinglistBean {
            private String tname;
            /**
             * name : 南方ID
             * tname : 自行车
             * id : 39
             * mac : ffff
             */

            private List<EquListBean> equList;

            public String getTname() {
                return tname;
            }

            public void setTname(String tname) {
                this.tname = tname;
            }

            public List<EquListBean> getEquList() {
                return equList;
            }

            public void setEquList(List<EquListBean> equList) {
                this.equList = equList;
            }

            public static class EquListBean {
                private String name;
                private String tname;
                private int id;
                private String mac;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getTname() {
                    return tname;
                }

                public void setTname(String tname) {
                    this.tname = tname;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getMac() {
                    return mac;
                }

                public void setMac(String mac) {
                    this.mac = mac;
                }
            }
        }
    }
}
