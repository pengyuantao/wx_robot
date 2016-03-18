package main.java.com.wuji.entity.wxinit;

import com.google.gson.annotations.SerializedName;
import com.wuji.entity.BaseRequest;

import java.util.Objects;

/**
 * 
 * 用于封装好请求用的json，发送给https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r={time}&skey=该地址
 */
public class WebWxInitJson {

    @SerializedName("BaseRequest")
    private BaseRequest baseRequest;

    @Override
    public String toString() {

        return "WebWxInitJson{" + "baseRequest=" + baseRequest + '}';
    }

    @Override
    public int hashCode() {

        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.baseRequest);
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
        final WebWxInitJson other = (WebWxInitJson) obj;
        if (!Objects.equals(this.baseRequest, other.baseRequest)) {
            return false;
        }
        return true;
    }

    public WebWxInitJson(BaseRequest b) {

        baseRequest = b;
    }

    public BaseRequest getBasicRequest() {

        return baseRequest;
    }

    public void setBasicRequest(BaseRequest basicRequest) {

        this.baseRequest = basicRequest;
    }

}
