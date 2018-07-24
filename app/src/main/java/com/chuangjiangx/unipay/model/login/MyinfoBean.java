package com.chuangjiangx.unipay.model.login;

public class MyinfoBean {

    /**
     * mainSearchMyDetailsDto : {"realname":"潘志平","storeName":null,"mobilePhone":"17896543210","merchantName":"宜宾市翠屏区直达车眼镜有限公司","sex":null,"portraitUrl":"","username":"pzp8222867","type":0,"editUsernameCount":0}
     */

    private MainSearchMyDetailsDtoBean mainSearchMyDetailsDto;

    public MainSearchMyDetailsDtoBean getMainSearchMyDetailsDto() {
        return mainSearchMyDetailsDto;
    }

    public void setMainSearchMyDetailsDto(MainSearchMyDetailsDtoBean mainSearchMyDetailsDto) {
        this.mainSearchMyDetailsDto = mainSearchMyDetailsDto;
    }

    public static class MainSearchMyDetailsDtoBean {
        /**
         * realname : 潘志平
         * storeName : null
         * mobilePhone : 17896543210
         * merchantName : 宜宾市翠屏区直达车眼镜有限公司
         * sex : null
         * portraitUrl :
         * username : pzp8222867
         * type : 0
         * editUsernameCount : 0
         */

        private String realname;
        private String storeName;
        private String mobilePhone;
        private String merchantName;
        private int sex;
        private String portraitUrl;
        private String username;
        private int type;
        private int editUsernameCount;

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getPortraitUrl() {
            return portraitUrl;
        }

        public void setPortraitUrl(String portraitUrl) {
            this.portraitUrl = portraitUrl;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getEditUsernameCount() {
            return editUsernameCount;
        }

        public void setEditUsernameCount(int editUsernameCount) {
            this.editUsernameCount = editUsernameCount;
        }
    }
}
