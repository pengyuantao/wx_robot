package main.java.com.wuji.entity;

/**
 * 
 * 用于标记使用人自己的相关信息
 */
public class UserInfo {

    private long Uin;

    private String UserName = "";

    private String NickName = "";

    private String HeadImgUrl = "";

    private String RemarkName = "";

    private String PYInitial = "";

    private String PYQuanPin = "";

    private String RemarkPYInitial = "";

    private String RemarkPYQuanPin = "";

    private int HideInputBarFlag;

    private int StarFriend;

    private int Sex;

    private String Signature = "";

    private int AppAccountFlag;

    private int VerifyFlag;

    private int ContactFlag;

    private int WebWxPluginSwitch;

    private int HeadImgFlag;

    private int SnsFlag;

    public long getUin() {

        return Uin;
    }

    public void setUin(long Uin) {

        this.Uin = Uin;
    }

    public String getUserName() {

        return UserName;
    }

    public void setUserName(String UserName) {

        this.UserName = UserName;
    }

    public String getNickName() {

        return NickName;
    }

    public void setNickName(String NickName) {

        this.NickName = NickName;
    }

    public String getHeadImgUrl() {

        return HeadImgUrl;
    }

    public void setHeadImgUrl(String HeadImgUrl) {

        this.HeadImgUrl = HeadImgUrl;
    }

    public String getRemarkName() {

        return RemarkName;
    }

    public void setRemarkName(String RemarkName) {

        this.RemarkName = RemarkName;
    }

    public String getPYInitial() {

        return PYInitial;
    }

    public void setPYInitial(String PYInitial) {

        this.PYInitial = PYInitial;
    }

    public String getPYQuanPin() {

        return PYQuanPin;
    }

    public void setPYQuanPin(String PYQuanPin) {

        this.PYQuanPin = PYQuanPin;
    }

    public String getRemarkPYInitial() {

        return RemarkPYInitial;
    }

    public void setRemarkPYInitial(String RemarkPYInitial) {

        this.RemarkPYInitial = RemarkPYInitial;
    }

    public String getRemarkPYQuanPin() {

        return RemarkPYQuanPin;
    }

    public void setRemarkPYQuanPin(String RemarkPYQuanPin) {

        this.RemarkPYQuanPin = RemarkPYQuanPin;
    }

    public int getHideInputBarFlag() {

        return HideInputBarFlag;
    }

    public void setHideInputBarFlag(int HideInputBarFlag) {

        this.HideInputBarFlag = HideInputBarFlag;
    }

    public int getStarFriend() {

        return StarFriend;
    }

    public void setStarFriend(int StarFriend) {

        this.StarFriend = StarFriend;
    }

    public int getSex() {

        return Sex;
    }

    public void setSex(int Sex) {

        this.Sex = Sex;
    }

    public String getSignature() {

        return Signature;
    }

    public void setSignature(String Signature) {

        this.Signature = Signature;
    }

    public int getAppAccountFlag() {

        return AppAccountFlag;
    }

    public void setAppAccountFlag(int AppAccountFlag) {

        this.AppAccountFlag = AppAccountFlag;
    }

    public int getVerifyFlag() {

        return VerifyFlag;
    }

    public void setVerifyFlag(int VerifyFlag) {

        this.VerifyFlag = VerifyFlag;
    }

    public int getContactFlag() {

        return ContactFlag;
    }

    public void setContactFlag(int ContactFlag) {

        this.ContactFlag = ContactFlag;
    }

    public int getWebWxPluginSwitch() {

        return WebWxPluginSwitch;
    }

    public void setWebWxPluginSwitch(int WebWxPluginSwitch) {

        this.WebWxPluginSwitch = WebWxPluginSwitch;
    }

    public int getHeadImgFlag() {

        return HeadImgFlag;
    }

    public void setHeadImgFlag(int HeadImgFlag) {

        this.HeadImgFlag = HeadImgFlag;
    }

    public int getSnsFlag() {

        return SnsFlag;
    }

    public void setSnsFlag(int SnsFlag) {

        this.SnsFlag = SnsFlag;
    }

    @Override
    public int hashCode() {

        int hash = 7;
        hash = 79 * hash + (int) (this.Uin ^ (this.Uin >>> 32));
        return hash;
    }

    @Override
    public String toString() {

        return "UserInfo{" + "Uin=" + Uin + ", UserName=" + UserName + ", NickName=" + NickName + ", HeadImgUrl="
                + HeadImgUrl + ", RemarkName=" + RemarkName + ", PYInitial=" + PYInitial + ", PYQuanPin=" + PYQuanPin
                + ", RemarkPYInitial=" + RemarkPYInitial + ", RemarkPYQuanPin=" + RemarkPYQuanPin
                + ", HideInputBarFlag=" + HideInputBarFlag + ", StarFriend=" + StarFriend + ", Sex=" + Sex
                + ", Signature=" + Signature + ", AppAccountFlag=" + AppAccountFlag + ", VerifyFlag=" + VerifyFlag
                + ", ContactFlag=" + ContactFlag + ", WebWxPluginSwitch=" + WebWxPluginSwitch + ", HeadImgFlag="
                + HeadImgFlag + ", SnsFlag=" + SnsFlag + '}';
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserInfo other = (UserInfo) obj;
        if (this.Uin != other.Uin) {
            return false;
        }
        return true;
    }

}
