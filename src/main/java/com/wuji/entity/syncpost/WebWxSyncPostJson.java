package main.java.com.wuji.entity.syncpost;

import com.google.gson.annotations.SerializedName;
import com.wuji.entity.SyncKey;

public class WebWxSyncPostJson {

    @SerializedName("BaseRequest")
    private BaseRequest br;

    @SerializedName("SyncKey")
    private SyncKey synKey;

    @SerializedName("rr")
    private long rr;

    public WebWxSyncPostJson(String sid, String uin) {

        br = new BaseRequest(sid, uin);
        synKey = new SyncKey();
        rr = System.currentTimeMillis();
    }

    public BaseRequest getBr() {

        return br;
    }

    public void setBr(BaseRequest br) {

        this.br = br;
    }

    public SyncKey getSynKey() {

        return synKey;
    }

    public void setSynKey(SyncKey synKey) {

        this.synKey = synKey;
    }

    public long getRr() {

        return rr;
    }

    public void setRr(long rr) {

        this.rr = rr;
    }

    class BaseRequest {

        String Sid = "";

        String Uin = "";

        public BaseRequest(String sid, String uin) {

            Sid = sid;
            Uin = uin;
        }

        public String getSid() {

            return Sid;
        }

        public void setSid(String Sid) {

            this.Sid = Sid;
        }

        public String getUin() {

            return Uin;
        }

        public void setUin(String Uin) {

            this.Uin = Uin;
        }
    }
}