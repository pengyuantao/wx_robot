package main.java.com.wuji.entity.wxupload;

import com.google.gson.annotations.SerializedName;

public class SendImgMsgBean {

    @SerializedName("BaseRequest")
    private ImgBaseRequest imgBaseRequest;

    @SerializedName("Msg")
    private ImgMsg msg;

    public SendImgMsgBean(ImgBaseRequest imgBaseRequest, ImgMsg msg) {

        this.imgBaseRequest = imgBaseRequest;
        this.msg = msg;
    }

    public ImgBaseRequest getImgBaseRequest() {
    
        return imgBaseRequest;
    }

    public void setImgBaseRequest(ImgBaseRequest imgBaseRequest) {
    
        this.imgBaseRequest = imgBaseRequest;
    }

    public ImgMsg getMsg() {

        return msg;
    }

    public void setMsg(ImgMsg msg) {

        this.msg = msg;
    }

}
