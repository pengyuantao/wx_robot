package main.java.com.wuji.entity;

import java.util.Objects;


public class BaseRequest {

    private String Uin = "";

    private String Sid = "";

    private String Skey = "";

    private String DeviceID = "";

    // Default Construction
    public BaseRequest() {

    };

    // Full Construction
    public BaseRequest(String Uin, String Sid, String Skey, String DeviceID) {

        this.Uin = Uin;
        this.Sid = Sid;
        this.Skey = Skey;
        this.DeviceID = DeviceID;

    }
    
    @Override
    public String toString() {

        return "BaseRequest{" + "Uin=" + Uin + ", Sid=" + Sid + ", Skey=" + Skey + ", DeviceID=" + DeviceID + '}';
    }

    @Override
    public int hashCode() {

        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.Uin);
        hash = 59 * hash + Objects.hashCode(this.Sid);
        hash = 59 * hash + Objects.hashCode(this.Skey);
        hash = 59 * hash + Objects.hashCode(this.DeviceID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseRequest other = (BaseRequest) obj;
        if (!Objects.equals(this.Uin, other.Uin)) {
            return false;
        }
        if (!Objects.equals(this.Sid, other.Sid)) {
            return false;
        }
        if (!Objects.equals(this.Skey, other.Skey)) {
            return false;
        }
        if (!Objects.equals(this.DeviceID, other.DeviceID)) {
            return false;
        }
        return true;
    }

    public String getUin() {

        return Uin;
    }

    public void setUin(String Uin) {

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
