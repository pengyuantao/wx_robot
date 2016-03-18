package main.java.com.wuji.entity.wxinit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import com.wuji.entity.BaseResponse;
import com.wuji.entity.SyncKey;
import com.wuji.entity.UserInfo;

/**
 * 
 * 用于组装初始化列表时的相关数据
 */
public class WebWxInitBean {

    @SerializedName("BaseResponse")
    private BaseResponse baseResponse = new BaseResponse();

    @SerializedName("Count")
    private int count;

    @SerializedName("SyncKey")
    private SyncKey syncKey;

    @SerializedName("User")
    private UserInfo user;

    private String ChatSet;

    @SerializedName("SKey")
    private String Skey;

    private long ClientVersion;

    @Override
    public int hashCode() {

        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.Skey);
        hash = 53 * hash + (int) (this.SystemTime ^ (this.SystemTime >>> 32));
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
        final WebWxInitBean other = (WebWxInitBean) obj;
        if (!Objects.equals(this.Skey, other.Skey)) {
            return false;
        }
        return true;
    }

    public int getCount() {

        return count;
    }

    public void setCount(int count) {

        this.count = count;
    }

    public SyncKey getSyncKey() {

        return syncKey;
    }

    public void setSyncKey(SyncKey syncKey) {

        this.syncKey = syncKey;
    }

    public UserInfo getUser() {

        return user;
    }

    public void setUser(UserInfo user) {

        this.user = user;
    }

    public String getChatSet() {

        return ChatSet;
    }

    public void setChatSet(String ChatSet) {

        this.ChatSet = ChatSet;
    }

    public String getSkey() {

        return Skey;
    }

    public void setSkey(String Skey) {

        this.Skey = Skey;
    }

    public long getClientVersion() {

        return ClientVersion;
    }

    public void setClientVersion(long ClientVersion) {

        this.ClientVersion = ClientVersion;
    }

    public long getSystemTime() {

        return SystemTime;
    }

    public void setSystemTime(long SystemTime) {

        this.SystemTime = SystemTime;
    }

    private long SystemTime;

    public BaseResponse getBaseResponse() {

        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {

        this.baseResponse = baseResponse;
    }

}
