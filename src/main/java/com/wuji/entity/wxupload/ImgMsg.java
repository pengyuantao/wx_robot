package main.java.com.wuji.entity.wxupload;

import java.io.UnsupportedEncodingException;
import org.apache.commons.lang3.RandomStringUtils;
import com.wuji.common.DateUtils;

/**
 * @author WJ 发送图片消息本体类
 */
public class ImgMsg {

    private int Type;

    private String MediaId;

    private String FromUserName;

    private String ToUserName;

    private String LocalID;

    private String ClientMsgId;

    public ImgMsg(String FromUserName, String ToUserName, String MediaId) throws UnsupportedEncodingException {

        String ID = DateUtils.getCurrentTimeMillsStr() + RandomStringUtils.randomNumeric(4);
        this.ClientMsgId = this.LocalID = ID;
        // 微信默认值
        this.Type = 3;
        this.FromUserName = FromUserName;
        this.ToUserName = ToUserName;
        this.MediaId = MediaId;
    }

    public String getClientMsgId() {

        return ClientMsgId;
    }

    public void setClientMsgId(String clientMsgId) {

        ClientMsgId = clientMsgId;
    }

    public String getMediaId() {

        return MediaId;
    }

    public void setMediaId(String mediaId) {

        MediaId = mediaId;
    }

    public String getFromUserName() {

        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {

        FromUserName = fromUserName;
    }

    public String getLocalID() {

        return LocalID;
    }

    public void setLocalID(String localID) {

        LocalID = localID;
    }

    public String getToUserName() {

        return ToUserName;
    }

    public void setToUserName(String toUserName) {

        ToUserName = toUserName;
    }

    public int getType() {

        return Type;
    }

    public void setType(int type) {

        Type = type;
    }

}
