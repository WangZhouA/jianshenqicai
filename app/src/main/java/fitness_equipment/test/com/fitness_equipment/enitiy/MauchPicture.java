package fitness_equipment.test.com.fitness_equipment.enitiy;

import java.util.List;

/**
 * Created by 陈姣姣 on 2017/12/15.
 */

public class MauchPicture {


    /**
     * code : 1
     * message : success
     * body : {"photolist":["/images/829848da-bc94-4eab-8748-c8a4c95b18c6.jpg","/images/c5c83214-6271-44c8-8334-74a154091360.jpg","/images/0f70bb5d-dab1-4767-965d-05576aa7106f.jpg"]}
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
        private List<String> photolist;

        public List<String> getPhotolist() {
            return photolist;
        }

        public void setPhotolist(List<String> photolist) {
            this.photolist = photolist;
        }
    }
}
