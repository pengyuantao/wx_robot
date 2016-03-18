package main.java.com.wuji.entity.syncpost;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.wuji.entity.BaseResponse;
import com.wuji.entity.MessageJson;
import com.wuji.entity.ProfileJson;
import com.wuji.entity.SyncKey;

/**
 * 
 * @author Administrator
 */
public class WebWxSyncPostBean {

    @SerializedName("BaseResponse")
    private BaseResponse br;

    private int AddMsgCount;

    private List<MessageJson> AddMsgList;

    private int ModContactCount;

    private int DelContactCount;

    private int ModChatRoomMemberCount;

    private ProfileJson Profile;

    private int ContinueFlag;

    @SerializedName("SyncKey")
    private SyncKey syncKey;

    private String SKey = "";

    public WebWxSyncPostBean() {

        br = new BaseResponse();
        AddMsgList = new ArrayList<MessageJson>();
        Profile = new ProfileJson();
        syncKey = new SyncKey();
    }

    public BaseResponse getBr() {

        return br;
    }

    public void setBr(BaseResponse br) {

        this.br = br;
    }

    public int getAddMsgCount() {

        return AddMsgCount;
    }

    public void setAddMsgCount(int AddMsgCount) {

        this.AddMsgCount = AddMsgCount;
    }

    public List<MessageJson> getAddMsgList() {

        return AddMsgList;
    }

    public void setAddMsgList(List<MessageJson> AddMsgList) {

        this.AddMsgList = AddMsgList;
    }

    public int getModContactCount() {

        return ModContactCount;
    }

    public void setModContactCount(int ModContactCount) {

        this.ModContactCount = ModContactCount;
    }

    public int getDelContactCount() {

        return DelContactCount;
    }

    public void setDelContactCount(int DelContactCount) {

        this.DelContactCount = DelContactCount;
    }

    public int getModChatRoomMemberCount() {

        return ModChatRoomMemberCount;
    }

    public void setModChatRoomMemberCount(int ModChatRoomMemberCount) {

        this.ModChatRoomMemberCount = ModChatRoomMemberCount;
    }

    public ProfileJson getProfile() {

        return Profile;
    }

    public void setProfile(ProfileJson Profile) {

        this.Profile = Profile;
    }

    public int getContinueFlag() {

        return ContinueFlag;
    }

    public void setContinueFlag(int ContinueFlag) {

        this.ContinueFlag = ContinueFlag;
    }

    public SyncKey getSyncKey() {

        return syncKey;
    }

    public void setSyncKey(SyncKey syncKey) {

        this.syncKey = syncKey;
    }

    public String getSKey() {

        return SKey;
    }

    public void setSKey(String SKey) {

        this.SKey = SKey;
    }
}