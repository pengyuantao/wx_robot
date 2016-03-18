package main.java.com.wuji.entity.wxupload;

import com.google.gson.annotations.SerializedName;
import com.wuji.entity.BaseRequest;

public class UploadMediaRequest {

    @SerializedName("BaseRequest")
    private ImgBaseRequest br;

    private long ClientMediaId;

    private long TotalLen;

    private int StartPos = 0;

    private long DataLen;

    private int MediaType;
    
    public UploadMediaRequest(ImgBaseRequest br){
        
        this.br =br;
    }

    public ImgBaseRequest getBr() {

        return br;
    }

    public void setBr(ImgBaseRequest br) {

        this.br = br;
    }

    public long getClientMediaId() {

        return ClientMediaId;
    }

    public void setClientMediaId(long clientMediaId) {

        ClientMediaId = clientMediaId;
    }

    public long getTotalLen() {

        return TotalLen;
    }

    public void setTotalLen(long totalLen) {

        TotalLen = totalLen;
    }

    public int getStartPos() {

        return StartPos;
    }

    public void setStartPos(int startPos) {

        StartPos = startPos;
    }

    public long getDataLen() {

        return DataLen;
    }

    public void setDataLen(long dataLen) {

        DataLen = dataLen;
    }

    public int getMediaType() {

        return MediaType;
    }

    public void setMediaType(int mediaType) {

        MediaType = mediaType;
    }

}
