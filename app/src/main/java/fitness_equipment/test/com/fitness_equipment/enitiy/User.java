package fitness_equipment.test.com.fitness_equipment.enitiy;

/**
 * Created by 陈姣姣 on 2017/12/6.
 */

public class User {
    /**
     * code : 1
     * message : success
     * body : {"Userinfo":{"id":1,"name":"王小二","heading":"agag.jpg","niken":"大佬","telphone":"123","password":"123","address":"34","status":"1","sex":"女","hight":"45","weight":0,"birthday":"2011-10-12","qqId":null,"weixinId":null,"xinlangId":null,"facebookId":null,"twitterId":null,"whatsappId":null,"email":"3@","ldid":null,"eMail":"3@"}}
     */

    private String code;
    private String message;
    /**
     * Userinfo : {"id":1,"name":"王小二","heading":"agag.jpg","niken":"大佬","telphone":"123","password":"123","address":"34","status":"1","sex":"女","hight":"45","weight":0,"birthday":"2011-10-12","qqId":null,"weixinId":null,"xinlangId":null,"facebookId":null,"twitterId":null,"whatsappId":null,"email":"3@","ldid":null,"eMail":"3@"}
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
         * id : 1
         * name : 王小二
         * heading : agag.jpg
         * niken : 大佬
         * telphone : 123
         * password : 123
         * address : 34
         * status : 1
         * sex : 女
         * hight : 45
         * weight : 0
         * birthday : 2011-10-12
         * qqId : null
         * weixinId : null
         * xinlangId : null
         * facebookId : null
         * twitterId : null
         * whatsappId : null
         * email : 3@
         * ldid : null
         * eMail : 3@
         */

        private UserinfoBean Userinfo;

        public UserinfoBean getUserinfo() {
            return Userinfo;
        }

        public void setUserinfo(UserinfoBean Userinfo) {
            this.Userinfo = Userinfo;
        }

        public static class UserinfoBean {
            private int id;
            private String name;
            private String heading;
            private String niken;
            private String telphone;
            private String password;
            private String address;
            private String status;
            private String sex;
            private String hight;
            private int weight;
            private String birthday;
            private Object qqId;
            private Object weixinId;
            private Object xinlangId;
            private Object facebookId;
            private Object twitterId;
            private Object whatsappId;
            private String email;
            private Object ldid;
            private String eMail;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public String getTelphone() {
                return telphone;
            }

            public void setTelphone(String telphone) {
                this.telphone = telphone;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getHight() {
                return hight;
            }

            public void setHight(String hight) {
                this.hight = hight;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
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

            public Object getLdid() {
                return ldid;
            }

            public void setLdid(Object ldid) {
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
