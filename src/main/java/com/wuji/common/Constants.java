package main.java.com.wuji.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {

    public static String confile = "/config.properties";

    public static Properties properties = null;

    static {
        properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = Constants.class.getClass().getResourceAsStream(confile);
            properties.load(inputStream);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 二维码路径
    public static final String QR_CODE_PATH = properties.getProperty("QR_CODE_PATH");

    // 头像图片保存路径
    public static final String HEADIMG_HOME = properties.getProperty("HEADIMG_HOME");

    // 头像图片保存路径
    public static final String CHATROOM_IMG_HOME = properties.getProperty("CHATROOM_IMG_HOME");
    
    // 发送图片文件存放路径
    public static final String IMG_DATA_PATH = properties.getProperty("IMG_DATA_PATH");
    
    // 接收图片文件存放路径
    public static final String IMG_MSG_PATH = properties.getProperty("IMG_MSG_PATH");
    
    // 接收语音文件存放路径
    public static final String VOICE_MSG_PATH = properties.getProperty("VOICE_MSG_PATH");

    // 加好友
    public static final String WECHAT_ADD_FRIEND = properties.getProperty("WECHAT_ADD_FRIEND");

    public static final String WECHAT_HEADIMG = properties.getProperty("WECHAT_HEADIMG");

    public static final String LOGIN_URL = properties.getProperty("LOGIN_URL");

    public static final String QRCODE_URL = properties.getProperty("QRCODE_URL");

    public static final String CHECK_LOGIN_STATUS_URL = properties.getProperty("CHECK_LOGIN_STATUS_URL");

    public static final String WECHAT_INIT_URL = properties.getProperty("WECHAT_INIT_URL");

    public static final String STATUS_NOTIFY_URL = properties.getProperty("STATUS_NOTIFY_URL");

    public static final String SYNC_CHECK_URL = properties.getProperty("SYNC_CHECK_URL");

    public static final String WEBWX_SYNC_URL = properties.getProperty("WEBWX_SYNC_URL");

    public static final String SEND_MSG_URL = properties.getProperty("SEND_MSG_URL");

    public static final String UPLOAD_MEDIA_URL = properties.getProperty("UPLOAD_MEDIA_URL");

    public static final String SEND_IMG_URL = properties.getProperty("SEND_IMG_URL");

    // 初始化好友关系接口
    public static final String WEBWX_GET_FRIEND = properties.getProperty("WEBWX_GET_FRIEND");

    // 获取群内昵称 头像等信息
    public static final String CHATROOM_CONTACT = properties.getProperty("CHATROOM_CONTACT");
    
    // 获取图片消息
    public static final String GET_MSG_IMG = properties.getProperty("GET_MSG_IMG");
    
    // 获取语音消息
    public static final String GET_MSG_VOICE = properties.getProperty("GET_MSG_VOICE");

    public static final int NOT_STARTED = 0;
    
    public static final int STARTING = 100;

    public static final int WAITING_FOR_SCANNING = 101;

    public static final int RUNNING = 200;

    public static final int EXCEPTION = 400;

    public static final String JPG_POSTFIX = ".jpg";
    
    // 连接最大尝试次数
    public static final int MAX_TRY_TIMES = 5;

    // 微信服务请求前缀
    public static final String WX_HTTPS_PREFIX = "https://wx.qq.com";

}
