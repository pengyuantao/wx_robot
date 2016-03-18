package main.java.com.wuji.entity.wxupload;

public class ImgBaseRequest {

    private long Uin;

    private String Sid = "";

    private String Skey = "";

    private String DeviceID = "";

    public ImgBaseRequest(long Uin, String Sid, String Skey, String DeviceID) {

        this.Uin = Uin;
        this.Sid = Sid;
        this.Skey = Skey;
        this.DeviceID = DeviceID;

    }
    
    public long getUin() {

        return Uin;
    }

    public void setUin(long Uin) {

        this.Uin = Uin;
    }

    public String getSid() {

        return Sid;
    }

    public void setSid(String Sid) {

        this.Sid = Sid;
    }

    public String getSkey() {

        return Skey;
    }

    public void setSkey(String Skey) {

        this.Skey = Skey;
    }

    public String getDeviceID() {

        return DeviceID;
    }

    public void setDeviceID(String DeviceID) {

        this.DeviceID = DeviceID;
    }
    
}
