package main.java.com.wuji.wechat.demo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.wuji.common.Constants;
import com.wuji.common.RegexUtils;
import com.wuji.entity.HeartBeatBean;
import com.wuji.entity.MessageJson;
import com.wuji.entity.baidu.BaiduImg;
import com.wuji.entity.syncpost.WebWxSyncPostBean;
import com.wuji.entity.wxupload.ImgMsg;
import com.wuji.entity.wxupload.UploadResponse;
import com.wuji.http.HttpUtils;
import com.wuji.wechat.handler.RobotHandler;

public class ImgResponser {

    private static Logger log = Logger.getLogger(ImgResponser.class);

    private RobotHandler handler;

    // 网页微信掉线重连次数
    private int reconnctTimes = 1;

    // 最大尝试次数
    public final static int MAX_TRY_TIMES = 5;

    private final static String BAIDU_IMG = "http://image.baidu.com/search/avatarjson?tn=resultjsonavatarnew&ie=utf-8&word=";

    public ImgResponser() {

        this.handler = new RobotHandler();
    }

    /**
     * 生成二维码
     * 
     */
    public void generateQRCode() throws InterruptedException {

        String uuid = handler.getLoginUUID();
        handler.getQRCodeImage(uuid);
    }

    /**
     * 等待扫描
     * 
     */
    public void waitForScan() throws InterruptedException {

        handler.initLoginUrl();
        boolean isQRCodeScanned = false;
        while (!isQRCodeScanned) {
            isQRCodeScanned = handler.getLoginInfoStatus();
            Thread.sleep(2000);
        }
    }

    /**
     * 初始化信息
     * 
     */
    public void initRobot() throws InterruptedException, UnsupportedEncodingException {

        String redirectUrl = handler.getNewLoginPageUrl();
        log.info(redirectUrl);
        if (!redirectUrl.equals(StringUtils.EMPTY)) {
            handler.getPassticketFrom(redirectUrl);
        }
        handler.getLoginInfo();
        Thread.sleep(1000);
        handler.initWechatInfo();
    }

    /**
     * 运行
     * 
     */
    public void runRobot() throws InterruptedException, UnsupportedEncodingException {

        if (reconnctTimes > MAX_TRY_TIMES) {
            log.error("synccheck error more than 5 times");
            return;
        }

        handler.loadMsgAndUpdateSyncKey();
        // if(handler.wxStatusNotify()){
        // log.info("状态同步成功");
        // }
        HeartBeatBean beat;

        while (true) {
            beat = handler.syncCheck();
            if (beat.getRetcode().equals("0")) {// 信息通讯正确
                if (!beat.getSelector().equals("0")) {// 有需要处理的消息
                    WebWxSyncPostBean syncBean = handler.loadMsgAndUpdateSyncKey();
                    if (syncBean.getAddMsgCount() != 0) {
                        List<MessageJson> list = syncBean.getAddMsgList();
                        MessageJson json = list.get(0);
                        int MsgType = json.getMsgType();
                        if (MsgType == 1) {
                            String fromUserName = json.getFromUserName();
                            String content = json.getContent();
                            content = content.replaceAll("<br/>", StringUtils.EMPTY);
                            String name = content.split(":")[0];
                            content = content.substring(content.indexOf(":") + 1);
                            content = RegexUtils.formatWechatText(content);
                            if (content.equals(StringUtils.EMPTY)) {
                                continue;
                            }
                            List<String> wordList = RegexUtils.findMatchers("(?<=((qt|QT|求图)\\s))\\S+", content);
                            if (!wordList.isEmpty()) {
                                String keyword = wordList.get(0);
                                String url = BAIDU_IMG + keyword;
                                String baidujson = HttpUtils.getHtmlByHttpGet(url, 5, null);
                                List<String> imgList = getImages(baidujson);
                                if (imgList.isEmpty()) {
                                    continue;
                                }
                                String imgName = StringUtils.EMPTY;
                                for (String imgUrl : imgList) {
                                    imgName = HttpUtils.getImageByHttpGetWithFormatCheck(imgUrl,
                                            Constants.IMG_DATA_PATH, handler.getCookieStore());
                                    if (StringUtils.isNoneEmpty(imgName)) {
                                        break;
                                    }
                                }
                                if (StringUtils.isNoneEmpty(imgName)) {
                                    String uploadResult = handler.uploadImg(imgName);
                                    Gson gson = new Gson();
                                    UploadResponse resp = gson.fromJson(uploadResult, UploadResponse.class);
                                    if (resp.getBaseResponse().getRet() == 0) {
                                        String mediaId = resp.getMediaId();
                                        ImgMsg imgMsg = new ImgMsg(handler.getUserInfo().getUserName(), fromUserName,
                                                mediaId);
                                        String result = handler.sendImgMsg(imgMsg);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else {
                reconnctTimes++;
                log.error("synccheck error " + beat.getRetcode());
                if (reconnctTimes <= MAX_TRY_TIMES) {
                    initRobot();
                    runRobot();
                    break;
                }
                else {
                    log.error("synccheck error more than 5 times");
                    break;
                }
            }
            Thread.sleep(2000);
        }
    }

    private static List<String> getImages(String baidujson) {

        List<String> imgList = new ArrayList<String>();
        if (StringUtils.isBlank(baidujson)) {
            return imgList;
        }
        Gson gson = new Gson();
        BaiduImg img = gson.fromJson(baidujson, BaiduImg.class);
        List<Map<String, String>> imgs = img.getImgs();
        for (Map<String, String> map : imgs) {
            String objURL = map.get("objURL");
            if (objURL.indexOf("imgtn.bdimg.com") < 0) {
                imgList.add(objURL);
            }
        }
        return imgList;
    }

    public static void main(String args[]) throws InterruptedException, UnsupportedEncodingException {

        ImgResponser responser = new ImgResponser();
        responser.generateQRCode();
        responser.waitForScan();
        responser.initRobot();
        responser.runRobot();
    }

}
