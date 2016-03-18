package main.java.com.wuji.entity.wxupload;

import com.google.gson.annotations.SerializedName;
import com.wuji.entity.BaseResponse;


public class UploadResponse {
    
    @SerializedName("BaseResponse")
    private BaseResponse baseResponse;

    private String MediaId;
    
    private int StartPos;
    
    private int CDNThumbImgHeight;
    
    private int CDNThumbImgWidth;
    
    public BaseResponse getBaseResponse() {
    
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
    
        this.baseResponse = baseResponse;
    }
    
    public String getMediaId() {
    
        return MediaId;
    }

    public void setMediaId(String mediaId) {
    
        MediaId = mediaId;
    }

    public int getStartPos() {
    
        return StartPos;
    }

    public void setStartPos(int startPos) {
    
        StartPos = startPos;
    }

    public int getCDNThumbImgHeight() {
    
        return CDNThumbImgHeight;
    }

    public void setCDNThumbImgHeight(int cDNThumbImgHeight) {
    
        CDNThumbImgHeight = cDNThumbImgHeight;
    }

    public int getCDNThumbImgWidth() {
    
        return CDNThumbImgWidth;
    }

    public void setCDNThumbImgWidth(int cDNThumbImgWidth) {
    
        CDNThumbImgWidth = cDNThumbImgWidth;
    }
    
}
