package fitness_equipment.test.com.fitness_equipment.enitiy;

import java.util.List;

/**
 * Created by 陈姣姣 on 2017/12/16.
 */

public class NaoZhong {


    /**
     * code : 1
     * message : success
     * body : {"trainlist":[{"tname":"自行车","id":"5","time":"15:40","content":"星期一","status":0},{"tname":"跳绳","id":"6","time":"15:40","content":"星期一","status":0}]}
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
         * id : 5
         * time : 15:40
         * content : 星期一
         * status : 0
         */

        private List<TrainlistBean> trainlist;

        public List<TrainlistBean> getTrainlist() {
            return trainlist;
        }

        public void setTrainlist(List<TrainlistBean> trainlist) {
            this.trainlist = trainlist;
        }

        public static class TrainlistBean {
            private String tname;
            private String id;
            private String time;
            private String content;
            private int status;

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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
