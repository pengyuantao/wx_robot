package main.java.com.wuji.entity.sendmsg;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.RandomStringUtils;

import com.wuji.common.DateUtils;
import com.wuji.http.HttpUtils;

/**
 * @author WJ 发送消息本体类
 */
public class Msg {

    private String ClientMsgId;

    private String Content;

    private String FromUserName;

    private String LocalID;

    private String ToUserName;

    private int Type;

    public Msg(int Type, String FromUserName, String ToUserName, String Content) throws UnsupportedEncodingException {

        String ID = DateUtils.getCurrentTimeMillsStr() + RandomStringUtils.randomNumeric(4);
        this.ClientMsgId = this.LocalID = ID;
        this.Type = Type;
        this.FromUserName = FromUserName;
        this.ToUserName = ToUserName;
        if (Content == null) {
            Content = "";
        }
        else {
            this.Content = new String(Content.getBytes(HttpUtils.UTF8));
        }
    }

    @Override
    public String toString() {

        return "SendMsgJson{" + "ClientMsgId=" + ClientMsgId + ", FromUserName=" + FromUserName + ", ToUserName="
                + ToUserName + ", Content=" + Content + ", LocalID=" + LocalID + '}';
    }

    public String getClientMsgId() {

        return ClientMsgId;
    }

    public void setClientMsgId(String clientMsgId) {

        ClientMsgId = clientMsgId;
    }

    public String getContent() {

        return Content;
    }

    public void setContent(String content) {

        Content = content;
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
