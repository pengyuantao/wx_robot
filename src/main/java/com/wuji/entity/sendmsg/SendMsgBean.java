package main.java.com.wuji.entity.sendmsg;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.wuji.entity.BaseRequest;

public class SendMsgBean {

    @SerializedName("BaseRequest")
    private BaseRequest baseRequest;

    @SerializedName("Msg")
    private Msg msg;

    public SendMsgBean(BaseRequest baseRequest, Msg msg) {

        this.baseRequest = baseRequest;
        this.msg = msg;
    }

    @Override
    public String toString() {

        return "SendMsgBean{" + "BaseRequest=" + baseRequest + ", Msg=" + msg + '}';
    }

    public BaseRequest getBaseRequest() {

        return baseRequest;
    }

    public void setBaseRequest(BaseRequest baseRequest) {

        this.baseRequest = baseRequest;
    }

    public Msg getMsg() {

        return msg;
    }

    public void setMsg(Msg msg) {

        this.msg = msg;
    }

}
