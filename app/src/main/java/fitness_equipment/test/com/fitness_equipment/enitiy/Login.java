package fitness_equipment.test.com.fitness_equipment.enitiy;

/**
 * Created by 陈姣姣 on 2017/12/7.
 */

public class Login {

    /**
     * code : 1
     * message : success
     * body : {"user":{"id":21,"name":null,"heading":null,"niken":null,"telphone":null,"password":null,"address":null,"status":null,"sex":null,"hight":null,"weight":null,"birthday":null,"qqId":null,"weixinId":null,"xinlangId":null,"facebookId":null,"twitterId":null,"whatsappId":null,"email":"514077686@qq.com","ldid":"LD10003392","eMail":"514077686@qq.com"}}
     */

    private String code;
    private String message;
    /**
     * user : {"id":21,"name":null,"heading":null,"niken":null,"telphone":null,"password":null,"address":null,"status":null,"sex":null,"hight":null,"weight":null,"birthday":null,"qqId":null,"weixinId":null,"xinlangId":null,"facebookId":null,"twitterId":null,"whatsappId":null,"email":"514077686@qq.com","ldid":"LD10003392","eMail":"514077686@qq.com"}
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
        /**
         * id : 21
         * name : null
         * heading : null
         * niken : null
         * telphone : null
         * password : null
         * address : null
         * status : null
         * sex : null
         * hight : null
         * weight : null
         * birthday : null
         * qqId : null
         * weixinId : null
         * xinlangId : null
         * facebookId : null
         * twitterId : null
         * whatsappId : null
         * email : 514077686@qq.com
         * ldid : LD10003392
         * eMail : 514077686@qq.com
         */

        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            private int id;
            private Object name;
            private Object heading;
            private Object niken;
            private Object telphone;
            private Object password;
            private Object address;
            private Object status;
            private Object sex;
            private Object hight;
            private Object weight;
            private Object birthday;
            private Object qqId;
            private Object weixinId;
            private Object xinlangId;
            private Object facebookId;
            private Object twitterId;
            private Object whatsappId;
            private String email;
            private String ldid;
            private String eMail;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
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

            public Object getTelphone() {
                return telphone;
            }

            public void setTelphone(Object telphone) {
                this.telphone = telphone;
            }

            public Object getPassword() {
                return password;
            }

            public void setPassword(Object password) {
                this.password = password;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public Object getSex() {
                return sex;
            }

            public void setSex(Object sex) {
                this.sex = sex;
            }

            public Object getHight() {
                return hight;
            }

            public void setHight(Object hight) {
                this.hight = hight;
            }

            public Object getWeight() {
                return weight;
            }

            public void setWeight(Object weight) {
                this.weight = weight;
            }

            public Object getBirthday() {
                return birthday;
            }

            public void setBirthday(Object birthday) {
                this.birthday = birthday;
            }

            public Object getQqId() {
                return qqId;
            }

            public void setQqId(Object qqId) {
                this.qqId = qqId;
            }

            public Object getWeixinId() {
                return weixinId;
            }

            public void setWeixinId(Object weixinId) {
                this.weixinId = weixinId;
            }

            public Object getXinlangId() {
                return xinlangId;
            }

            public void setXinlangId(Object xinlangId) {
                this.xinlangId = xinlangId;
            }

            public Object getFacebookId() {
                return facebookId;
            }

            public void setFacebookId(Object facebookId) {
                this.facebookId = facebookId;
            }

            public Object getTwitterId() {
                return twitterId;
            }

            public void setTwitterId(Object twitterId) {
                this.twitterId = twitterId;
            }

            public Object getWhatsappId() {
                return whatsappId;
            }

            public void setWhatsappId(Object whatsappId) {
                this.whatsappId = whatsappId;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getLdid() {
                return ldid;
            }

            public void setLdid(String ldid) {
                this.ldid = ldid;
            }

            public String getEMail() {
                return eMail;
            }

            public void setEMail(String eMail) {
                this.eMail = eMail;
            }
        }
    }
}
