package fitness_equipment.test.com.fitness_equipment.enitiy;

/**
 * Created by 陈姣姣 on 2017/12/6.
 */

public class Reigst {

    /**
     * code : 1
     * message : success
     * body : {"status":"恭喜您，注册成功"}
     */

    private String code;
    private String message;
    /**
     * status : 恭喜您，注册成功
     */

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
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
