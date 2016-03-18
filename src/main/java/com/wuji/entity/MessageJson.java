package main.java.com.wuji.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author WJ
 *
 */
public class MessageJson {

    private String MsgId;

    private String FromUserName = "";

    private String ToUserName = "";

    // 1文本消息
    private int MsgType;

    private String Content = "";

    private int Status;

    private int ImgStatus;

    private long CreateTime;

    private int VoiceLength = 0;

    private int PlayLength;

    private String FileName = "";

    private String FileSize = "";

    private String MediaId = "";

    private String Url = "";

    private int AppMsgType;

    private int StatusNotifyCode;

    private String StatusNotifyUserName;

    @SerializedName("RecommendInfo")
    private RecommendInfo recommendInfo;

    private int ForwardFlag;

    private int ImgHeight = 0;
    
    private int ImgWidth = 0;
    
    private String Ticket = "";

    public MessageJson() {

        recommendInfo = new RecommendInfo();
    }

    public String getMsgId() {

        return MsgId;
    }

    public void setMsgId(String MsgId) {

        this.MsgId = MsgId;
    }

    public String getFromUserName() {

        return FromUserName;
    }

    public void setFromUserName(String FromUserName) {

        this.FromUserName = FromUserName;
    }

    public String getToUserName() {

        return ToUserName;
    }

    public void setToUserName(String ToUserName) {

        this.ToUserName = ToUserName;
    }

    public int getMsgType() {

        return MsgType;
    }

    public void setMsgType(int MsgType) {

        this.MsgType = MsgType;
    }

    public String getContent() {

        return Content;
    }

    public void setContent(String Content) {

        this.Content = Content;
    }

    public int getStatus() {

        return Status;
    }

    public void setStatus(int Status) {

        this.Status = Status;
    }

    public int getImgStatus() {

        return ImgStatus;
    }

    public void setImgStatus(int ImgStatus) {

        this.ImgStatus = ImgStatus;
    }

    public long getCreateTime() {

        return CreateTime;
    }

    public void setCreateTime(long CreateTime) {

        this.CreateTime = CreateTime;
    }

    public int getVoiceLength() {

        return VoiceLength;
    }

    public void setVoiceLength(int VoiceLength) {

        this.VoiceLength = VoiceLength;
    }

    public int getPlayLength() {

        return PlayLength;
    }

    public void setPlayLength(int PlayLength) {

        this.PlayLength = PlayLength;
    }

    public String getFileName() {

        return FileName;
    }

    public void setFileName(String FileName) {

        this.FileName = FileName;
    }

    public String getFileSize() {

        return FileSize;
    }

    public void setFileSize(String FileSize) {

        this.FileSize = FileSize;
    }

    public String getMediaId() {

        return MediaId;
    }

    public void setMediaId(String MediaId) {

        this.MediaId = MediaId;
    }

    public String getUrl() {

        return Url;
    }

    public void setUrl(String Url) {

        this.Url = Url;
    }

    public int getAppMsgType() {

        return AppMsgType;
    }

    public void setAppMsgType(int AppMsgType) {

        this.AppMsgType = AppMsgType;
    }

    public int getStatusNotifyCode() {

        return StatusNotifyCode;
    }

    public void setStatusNotifyCode(int StatusNotifyCode) {

        this.StatusNotifyCode = StatusNotifyCode;
    }

    public String getStatusNotifyUserName() {

        return StatusNotifyUserName;
    }

    public void setStatusNotifyUserName(String StatusNotifyUserName) {

        this.StatusNotifyUserName = StatusNotifyUserName;
    }

    public RecommendInfo getRecommendInfo() {

        return recommendInfo;
    }

    public void setRecommendInfo(RecommendInfo recommendInfo) {

        this.recommendInfo = recommendInfo;
    }

    public int getForwardFlag() {

        return ForwardFlag;
    }

    public void setForwardFlag(int ForwardFlag) {

        this.ForwardFlag = ForwardFlag;
    }

    public int getImgHeight() {
    
        return ImgHeight;
    }
    
    public void setImgHeight(int imgHeight) {
    
        ImgHeight = imgHeight;
    }
    
    public int getImgWidth() {
    
        return ImgWidth;
    }
    
    public void setImgWidth(int imgWidth) {
    
        ImgWidth = imgWidth;
    }
    
    

    public String getTicket() {

        return Ticket;
    }

    public void setTicket(String Ticket) {

        this.Ticket = Ticket;
    }

    @Override
    public String toString() {

        return "MessageJson{" + "MsgId=" + MsgId + ", FromUserName=" + FromUserName + ", ToUserName=" + ToUserName
                + ", MsgType=" + MsgType + ", Content=" + Content + ", Status=" + Status + ", ImgStatus=" + ImgStatus
                + ", CreateTime=" + CreateTime + ", VoiceLength=" + VoiceLength + ", PlayLength=" + PlayLength
                + ", FileName=" + FileName + ", FileSize=" + FileSize + ", MediaId=" + MediaId + ", Url=" + Url
                + ", AppMsgType=" + AppMsgType + ", StatusNotifyCode=" + StatusNotifyCode + ", StatusNotifyUserName="
                + StatusNotifyUserName + ", recommendInfo=" + recommendInfo + ", ForwardFlag=" + ForwardFlag
                + ", Ticket=" + Ticket + '}';
    }

}
