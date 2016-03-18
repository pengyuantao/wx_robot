package main.java.com.wuji.wechat.handler;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.wuji.common.Constants;
import com.wuji.common.DateUtils;
import com.wuji.common.RegexUtils;
import com.wuji.entity.BaseRequest;
import com.wuji.entity.HeartBeatBean;
import com.wuji.entity.SyncKey;
import com.wuji.entity.UserInfo;
import com.wuji.entity.sendmsg.Msg;
import com.wuji.entity.sendmsg.SendMsgBean;
import com.wuji.entity.syncpost.WebWxSyncPostBean;
import com.wuji.entity.syncpost.WebWxSyncPostJson;
import com.wuji.entity.wxinit.WebWxInitBean;
import com.wuji.entity.wxinit.WebWxInitJson;
import com.wuji.entity.wxupload.ImgBaseRequest;
import com.wuji.entity.wxupload.ImgMsg;
import com.wuji.entity.wxupload.SendImgMsgBean;
import com.wuji.entity.wxupload.UploadMediaRequest;
import com.wuji.http.CustomRetryHandler;
import com.wuji.http.HttpUtils;
import com.wuji.http.WechatHttpUtils;

/**
 * @author WJ
 * 
 */
public class RobotHandler {

    private static Logger log = Logger.getLogger(RobotHandler.class);

    /**
     * 二维码登录地址
     */
    private String loginUrl;

    /**
     * 扫描二维码后微信返回的登录地址
     */
    private String loginInfoUrl;

    private Map<String, String> cookieMap = new HashMap<String, String>();

    private String uuid = StringUtils.EMPTY;

    private String passticket = StringUtils.EMPTY;

    private WebWxInitBean webWxInitBean;

    private SyncKey syncKey;

    private UserInfo userInfo;

    private CookieStore cookieStore;

    private String wxuin;

    private String wxsid;

    private String deviceID;

    private String skey = StringUtils.EMPTY;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d yyyy HH:mm:ss 'GMT+0800'",
            java.util.Locale.US);

    // 发送图片编号
    private int sendImgCount = 0;

    public RobotHandler() {

        // 避免Jdk1.7抛出异常javax.net.ssl.SSLProtocolException: handshake alert: unrecognized_name
        System.setProperty("jsse.enableSNIExtension", "false");
        this.deviceID = "e" + RandomStringUtils.randomNumeric(10);
    }

    /**
     * 获取微信网页版登录UUID
     * 
     */
    public String getLoginUUID() {

        String url = Constants.LOGIN_URL.replace("{time}", DateUtils.getCurrentTimeMillsStr());
        String result = HttpUtils.getHtmlByHttpGet(url, Constants.MAX_TRY_TIMES, null);
        List<String> list = RegexUtils.findMatchers("(?<=\").+(?=\")", result);
        if (list != null && !list.isEmpty()) {
            this.uuid = list.get(0);
        }
        return this.uuid;
    }

    /**
     * 获取登录二维码
     * 
     */
    public String getQRCodeImage(String uuid) {

        String url = Constants.QRCODE_URL.replace("{uuid}", uuid);
        String qrcodeName = HttpUtils.getResourceByHttpGet(url, Constants.QR_CODE_PATH, null);
        return qrcodeName;
    }

    /**
     * 初始化生成:查询二维码是否扫描轮询地址
     * 
     */
    public void initLoginUrl() {

        String url = Constants.CHECK_LOGIN_STATUS_URL.replace("{uuid}", this.uuid).replace("{tip}", "1")
                .replace("{timestamp}", DateUtils.getCurrentTimeMillsStr())
                .replace("{time}", DateUtils.getCurrentTimeStr());
        this.loginUrl = url;
    }

    /**
     * 获取登录状态 未扫描二维码返回window.code=408;扫描后未点确认window.code=201;扫描后返回window.code=200; window.redirect_uri="xxxx"
     * 
     * @return true已扫描
     */
    public boolean getLoginInfoStatus() {

        String result = HttpUtils.getHtmlByHttpGet(loginUrl, 0, null);
        String redirectUri = StringUtils.EMPTY;
        if (result.contains("redirect_uri")) {
            List<String> list = RegexUtils.findMatchers("(?<=\").+(?=\")", result);
            if (list != null && !list.isEmpty()) {
                redirectUri = list.get(0);
                this.loginInfoUrl = redirectUri;
                return true;
            }
        }
        return false;
    }

    /**
     * 进入聊天页面并获取登录cookie
     * 
     */
    public void getLoginInfo() {

        CustomRetryHandler retryHandler = new CustomRetryHandler(Constants.MAX_TRY_TIMES);
        CloseableHttpClient httpclient = HttpClients.custom().setRetryHandler(retryHandler).build();
        try {
            HttpGet httpget = new HttpGet(loginInfoUrl);
            httpget.setHeader(HTTP.USER_AGENT, HttpUtils.USER_AGENT);
            HttpClientContext context = HttpClientContext.create();
            httpclient.execute(httpget, context);
            this.cookieStore = context.getCookieStore();
            List<Cookie> cookieList = cookieStore.getCookies();
            for (Cookie cookie : cookieList) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
            this.wxsid = cookieMap.get("wxsid");
            this.wxuin = cookieMap.get("wxuin");
        }
        catch (IOException e) {
            log.error(e);
        }
        finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            }
            catch (IOException e) {
                log.error(e);
            }
        }
    }

    /**
     * 再次请求授权地址以获取登录信息查询地址 正确返回window.code=200; window.redirect_uri="xxxx"
     * 
     * @return 获取pass_ticket等信息的URL
     */
    public String getNewLoginPageUrl() {

        String url = Constants.CHECK_LOGIN_STATUS_URL.replace("{uuid}", this.uuid).replace("{tip}", "0")
                .replace("{time}", DateUtils.getCurrentTimeStr())
                .replace("{timestamp}", DateUtils.getCurrentTimeMillsStr());
        String result = HttpUtils.getHtmlByHttpGet(url, 0, null);
        log.info(result);
        String redirectUri = StringUtils.EMPTY;
        if (result.contains("redirect_uri")) {
            List<String> list = RegexUtils.findMatchers("(?<=\").+(?=\")", result);
            if (list != null && !list.isEmpty()) {
                redirectUri = list.get(0);
            }
        }
        return redirectUri;
    }

    /**
     * 获取登录状态 未扫描二维码返回window.code=201; 扫描后返回window.code=200; window.redirect_uri="xxxx"
     * 
     * @return 获取pass_ticket等信息的URL
     * @throws UnsupportedEncodingException
     */
    public void getPassticketFrom(String url) {

        url = url + "&fun=new&version=v2&lang=zh_CN";
        String result = HttpUtils.getHtmlByHttpGet(url, 0, this.cookieStore);
        if (result.contains("<ret>0</ret>")) {
            List<String> list = RegexUtils.findMatchers("(?<=<pass_ticket>).+(?=</pass_ticket>)", result);
            if (list != null && !list.isEmpty()) {
                try {
                    this.passticket = URLEncoder.encode(list.get(0), HttpUtils.UTF8);
                }
                catch (UnsupportedEncodingException e) {
                }
            }
            // 在initWechatInfo()方法中获取
            /*
             * list = RegexUtils.findMatchers("(?<=<skey>).+(?=</skey>)", result); if (list != null && list.size() > 0)
             * { this.skey = list.get(0); }
             */
        }
    }

    /**
     * 获取登录信息
     * 
     * @throws UnsupportedEncodingException
     * 
     */
    public void initWechatInfo() {

        BaseRequest baseRequest = new BaseRequest(this.wxuin, this.wxsid, this.skey, this.deviceID);
        WebWxInitJson initJson = new WebWxInitJson(baseRequest);
        Gson gson = new Gson();
        String jsonbody = gson.toJson(initJson);
        String url = Constants.WECHAT_INIT_URL.replace("{time}", DateUtils.getCurrentTimeStr()).replace("{passticket}",
                this.passticket);
        String result = HttpUtils.getContentByHttpPost(url, null, jsonbody, Constants.MAX_TRY_TIMES,
                this.cookieStore);

        this.webWxInitBean = gson.fromJson(result, WebWxInitBean.class);
        this.syncKey = webWxInitBean.getSyncKey();
        this.skey = webWxInitBean.getSkey();
        this.userInfo = webWxInitBean.getUser();
    }

    /**
     * 轮询同步微信服务器
     * 
     * @throws UnsupportedEncodingException
     * 
     */
    public HeartBeatBean syncCheck() {

        String key = this.syncKey.getSyncKeyStr();
        try {
            key = URLEncoder.encode(key, HttpUtils.UTF8);
        }
        catch (UnsupportedEncodingException e) {
        }

        String url = Constants.SYNC_CHECK_URL.replace("{time}", DateUtils.getCurrentTimeMillsStr())
                .replace("{skey}", this.skey).replace("{wxsid}", this.wxsid)
                .replace("{wxuin}", this.wxuin).replace("{deviceid}", this.deviceID)
                .replace("{synckey}", key);
        // log.info(url);
        String result = HttpUtils.getHtmlByHttpGet(url, Constants.MAX_TRY_TIMES, this.cookieStore);
        return HeartBeatBean.parseString(result);
    }

    /**
     * 同步获取syncKey
     * 
     * @throws CustomException
     * 
     */
    public WebWxSyncPostBean loadMsgAndUpdateSyncKey() {

        WebWxSyncPostJson postJson = new WebWxSyncPostJson(this.wxsid, this.wxuin);
        postJson.setRr(DateUtils.getCurrentTimeMills());
        postJson.setSynKey(this.syncKey);

        Gson gson = new Gson();
        String url = Constants.WEBWX_SYNC_URL.replace("{wxsid}", this.wxsid)
                .replace("{skey}", this.skey).replace("{passticket}", this.passticket);
        String result = HttpUtils.getContentByHttpPost(url, null, gson.toJson(postJson), Constants.MAX_TRY_TIMES,
                this.cookieStore);
        WebWxSyncPostBean bean = gson.fromJson(result, WebWxSyncPostBean.class);
        if (bean.getSyncKey().getCount() != 0) {
            this.syncKey = bean.getSyncKey();
        }
        return bean;
    }

    /**
     * 发送消息
     * 
     * @throws CustomException
     * 
     */
    public String sendMsg(Msg msg) {

        BaseRequest baseRequest = new BaseRequest(this.wxuin, this.wxsid, this.skey, this.deviceID);
        msg.setFromUserName(userInfo.getUserName());
        SendMsgBean sendMsgBean = new SendMsgBean(baseRequest, msg);
        Gson gson = new Gson();

        String url = Constants.SEND_MSG_URL.replace("{passticket}", this.passticket);
        String result = HttpUtils.getContentByHttpPost(url, null, gson.toJson(sendMsgBean), Constants.MAX_TRY_TIMES,
                this.cookieStore);
        return result;
    }

    /**
     * 上传图片文件到微信服务器
     * 
     * @throws CustomException
     * 
     */
    public String uploadImg(String imgName) {

        ImgBaseRequest imgBaseRequest = new ImgBaseRequest(Long.valueOf(this.wxuin), this.wxsid, this.skey, this.deviceID);
        // 测试
        File file = new File(Constants.IMG_DATA_PATH + imgName);
        long fileSize = file.length();

        UploadMediaRequest mediaRequest = new UploadMediaRequest(imgBaseRequest);
        mediaRequest.setClientMediaId(DateUtils.getCurrentTimeMills());
        mediaRequest.setDataLen(fileSize);
        // 定值
        mediaRequest.setMediaType(4);
        mediaRequest.setTotalLen(fileSize);
        Gson gson = new Gson();
        String uploadmediarequest = gson.toJson(mediaRequest);

        String ticket = this.passticket;
        try {
            ticket = URLEncoder.encode(ticket, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
        }

        String date = dateFormat.format(new Date(file.lastModified()));
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("id", "WU_FILE_" + sendImgCount++);
        dataMap.put("type", "image/" + FilenameUtils.getExtension(file.getName()));
        dataMap.put("lastModifiedDate", date);
        dataMap.put("uploadmediarequest", uploadmediarequest);
        dataMap.put("webwx_data_ticket", this.cookieMap.get("webwx_data_ticket"));
        dataMap.put("pass_ticket", ticket);
        String result = WechatHttpUtils.uploadImgToWechatByPost(dataMap, file);
        return result;
    }

    /**
     * 发送图片消息
     * 
     * @throws CustomException
     * 
     */
    public String sendImgMsg(ImgMsg imgMsg) {

        ImgBaseRequest imgBaseRequest = new ImgBaseRequest(Long.valueOf(this.wxuin), this.wxsid, this.skey, this.deviceID);
        SendImgMsgBean sendImgMsgBean = new SendImgMsgBean(imgBaseRequest, imgMsg);
        Gson gson = new Gson();
        log.info(gson.toJson(sendImgMsgBean));

        String url = Constants.SEND_IMG_URL.replace("{passticket}", this.passticket);
        String result = HttpUtils.getContentByHttpPost(url, null, gson.toJson(sendImgMsgBean), Constants.MAX_TRY_TIMES,
                this.cookieStore);
        return result;
    }

    
    public UserInfo getUserInfo() {
    
        return userInfo;
    }

    
    public CookieStore getCookieStore() {
    
        return cookieStore;
    }
    
}
