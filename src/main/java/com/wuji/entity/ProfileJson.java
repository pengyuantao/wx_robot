package main.java.com.wuji.entity;

import com.google.gson.annotations.SerializedName;

public class ProfileJson {

    private int BitFlag;

    @SerializedName("UserName")
    private UserName userName;

    @SerializedName("NickName")
    private NickName nickName;

    private int BindUin;

    @SerializedName("BindEmail")
    private BindEmail be;

    @SerializedName("BindMobile")
    private BindMobile bm;

    private int Status;

    private int Sex;

    private int PersonalCard;

    private String Alias = "";

    private String HeadImgUpdateFlag = "";

    private String HeadImgUrl = "";

    private String Signature = "";

    public int getBitFlag() {

        return BitFlag;
    }

    public void setBitFlag(int BitFlag) {

        this.BitFlag = BitFlag;
    }

    public UserName getUserName() {

        return userName;
    }

    public void setUserName(UserName userName) {

        this.userName = userName;
    }

    public NickName getNickName() {

        return nickName;
    }

    public void setNickName(NickName nickName) {

        this.nickName = nickName;
    }

    public int getBindUin() {

        return BindUin;
    }

    public void setBindUin(int BindUin) {

        this.BindUin = BindUin;
    }

    public BindEmail getBe() {

        return be;
    }

    public void setBe(BindEmail be) {

        this.be = be;
    }

    public BindMobile getBm() {

        return bm;
    }

    public void setBm(BindMobile bm) {

        this.bm = bm;
    }

    public int getStatus() {

        return Status;
    }

    public void setStatus(int Status) {

        this.Status = Status;
    }

    public int getSex() {

        return Sex;
    }

    public void setSex(int Sex) {

        this.Sex = Sex;
    }

    public int getPersonalCard() {

        return PersonalCard;
    }

    public void setPersonalCard(int PersonalCard) {

        this.PersonalCard = PersonalCard;
    }

    public String getAlias() {

        return Alias;
    }

    public void setAlias(String Alias) {

        this.Alias = Alias;
    }

    public String getHeadImgUpdateFlag() {

        return HeadImgUpdateFlag;
    }

    public void setHeadImgUpdateFlag(String HeadImgUpdateFlag) {

        this.HeadImgUpdateFlag = HeadImgUpdateFlag;
    }

    public String getHeadImgUrl() {

        return HeadImgUrl;
    }

    public void setHeadImgUrl(String HeadImgUrl) {

        this.HeadImgUrl = HeadImgUrl;
    }

    public String getSignature() {

        return Signature;
    }

    public void setSignature(String Signature) {

        this.Signature = Signature;
    }

    public ProfileJson() {

        userName = new UserName();
        nickName = new NickName();
        be = new BindEmail();
        bm = new BindMobile();
    }

    @Override
    public String toString() {

        return "ProfileJson{" + "BitFlag=" + BitFlag + ", userName=" + userName + ", nickName=" + nickName
                + ", BindUin=" + BindUin + ", be=" + be + ", bm=" + bm + ", Status=" + Status + ", Sex=" + Sex
                + ", PersonalCard=" + PersonalCard + ", Alias=" + Alias + ", HeadImgUpdateFlag=" + HeadImgUpdateFlag
                + ", HeadImgUrl=" + HeadImgUrl + ", Signature=" + Signature + '}';
    }

    class UserName {

        private String Buff = "";

        public String getBuff() {

            return Buff;
        }

        public void setBuff(String Buff) {

            this.Buff = Buff;
        }
    }

    class NickName {

        private String Buff = "";

        public String getBuff() {

            return Buff;
        }

        public void setBuff(String Buff) {

            this.Buff = Buff;
        }
    }

    class BindEmail {

        private String Buff = "";

        public String getBuff() {

            return Buff;
        }

        public void setBuff(String Buff) {

            this.Buff = Buff;
        }
    }

    class BindMobile {

        private String Buff = "";

        public String getBuff() {

            return Buff;
        }

        public void setBuff(String Buff) {

            this.Buff = Buff;
        }
    }
}
