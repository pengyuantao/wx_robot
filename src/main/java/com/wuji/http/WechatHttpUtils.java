package main.java.com.wuji.http;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import com.wuji.common.Constants;

public class WechatHttpUtils {

    private static Logger log = Logger.getLogger(WechatHttpUtils.class);

    /**
     * POST上传媒体资源到微信
     * 
     * @param dataMap
     *            数据
     * @param file
     *            文件
     * @throws CustomException 
     */
    public static String uploadImgToWechatByPost(Map<String, String> dataMap, File file){

        String response = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        HttpsURLConnection conn = null;
        try {

            // headers
            String boundary = "----WebKitFormBoundary" + RandomStringUtils.random(16);
            String freFix = "--";
            String newLine = "\r\n";

            URL urlObject = new URL(Constants.UPLOAD_MEDIA_URL);
            conn = (HttpsURLConnection) urlObject.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Length", Long.toString(file.length()));
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("Host", "file.wx.qq.com");
            conn.setRequestProperty("Origin", "https://wx.qq.com");
            conn.setRequestProperty("Pragma", "no-cache");
            conn.setRequestProperty("Referer", "https://wx.qq.com/");
            conn.setRequestProperty("User-Agent", HttpUtils.USER_AGENT);

            // post body
            StringBuffer sb = new StringBuffer();

            sb.append(freFix + boundary).append(newLine);
            sb.append("Content-Disposition: form-data; name=\"id\"");
            sb.append(newLine).append(newLine);
            sb.append(dataMap.get("id")).append(newLine);

            sb.append(freFix + boundary).append(newLine);
            sb.append("Content-Disposition: form-data; name=\"name\"");
            sb.append(newLine).append(newLine);
            sb.append(file.getName()).append(newLine);

            sb.append(freFix + boundary).append(newLine);
            sb.append("Content-Disposition: form-data; name=\"lastModifiedDate\"");
            sb.append(newLine).append(newLine);
            sb.append(dataMap.get("lastModifiedDate")).append(newLine);

            sb.append(freFix + boundary).append(newLine);
            sb.append("Content-Disposition: form-data; name=\"size\"");
            sb.append(newLine).append(newLine);
            sb.append(file.length()).append(newLine);

            sb.append(freFix + boundary).append(newLine);
            sb.append("Content-Disposition: form-data; name=\"mediatype\"");
            sb.append(newLine).append(newLine);
            sb.append("pic").append(newLine);

            sb.append(freFix + boundary).append(newLine);
            sb.append("Content-Disposition: form-data; name=\"uploadmediarequest\"");
            sb.append(newLine).append(newLine);
            sb.append(dataMap.get("uploadmediarequest")).append(newLine);

            sb.append(freFix + boundary).append(newLine);
            sb.append("Content-Disposition: form-data; name=\"type\"");
            sb.append(newLine).append(newLine);
            sb.append(dataMap.get("type")).append(newLine);

            sb.append(freFix + boundary).append(newLine);
            sb.append("Content-Disposition: form-data; name=\"filename\"; filename=\"" + file.getName() + "\"");
            sb.append(newLine);
            sb.append("Content-Type: " + dataMap.get("type"));
            sb.append(newLine).append(newLine);

            OutputStream outputStream = new DataOutputStream(conn.getOutputStream());
            // 写入post body
            outputStream.write(sb.toString().getBytes("utf-8"));

            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            // 写入图片
            while ((bytes = dis.read(bufferOut)) != -1) {
                outputStream.write(bufferOut, 0, bytes);
            }
            outputStream.write(newLine.getBytes());
            // end post body
            outputStream.write((freFix + boundary + freFix + newLine).getBytes("utf-8"));

            dis.close();
            outputStream.close();

            // 读取响应信息
            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            response = buffer.toString();
        }
        catch (Exception e) {
            log.error(e);
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
            try {
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
            }
            catch (IOException e) {
                log.error(e);
            }
        }
        return response;
    }

}
