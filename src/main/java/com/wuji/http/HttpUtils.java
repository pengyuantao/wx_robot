package main.java.com.wuji.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;
import com.wuji.common.FileTypeUtils;
import com.wuji.common.Md5Token;
import com.wuji.common.RegexUtils;
import com.wuji.http.response.JsonResponseHandler;
import com.wuji.http.response.StringResponseHandler;

public class HttpUtils {

    public final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36";

    public static String UTF8 = "UTF-8";

    public static String JSON_CONTENT = "application/json; charset=UTF-8";

    public static String FORM_CONTENT = "application/x-www-form-urlencoded; charset=UTF-8";

    public static Charset CHARSET_UTF8 = Charset.forName(UTF8);

    private final static SimpleDateFormat FORMAT_FORNAME = new SimpleDateFormat("yyyyMMdd/HHmmss_");

    private static Logger log = Logger.getLogger(HttpUtils.class);

    public static RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT)
            .setSocketTimeout(30000).setConnectTimeout(30000).build();

    /**
     * GET获取html
     * 
     * @param url
     *            地址
     * @param maxTry
     *            最多尝试重连次数
     * @return 文本内容
     * @throws CustomException
     */
    public static String getHtmlByHttpGet(String url, int maxTry, CookieStore cookieStore) {

        CloseableHttpClient httpclient;
        HttpClientBuilder builder = HttpClients.custom();
        if (cookieStore != null) {
            builder = builder.setDefaultCookieStore(cookieStore);
        }
        if (maxTry > 0) {
            CustomRetryHandler retryHandler = new CustomRetryHandler(maxTry);
            httpclient = builder.setRetryHandler(retryHandler).build();
        }
        else {
            httpclient = builder.build();
        }
        String html = StringUtils.EMPTY;
        try {
            HttpGet httpget = new HttpGet(url);
            httpget.setConfig(requestConfig);
            httpget.setHeader(HTTP.USER_AGENT, USER_AGENT);
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                // 响应状态
                int code = response.getStatusLine().getStatusCode();
                if (entity != null && code < 300) {
                    html = EntityUtils.toString(entity);
                }
            }
            finally {
                response.close();
            }
        }
        catch (ClientProtocolException e) {
            log.error(e);
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
        return html;
    }

    /**
     * GET获取json
     * 
     * @param url
     *            地址
     * @param maxTry
     *            最多尝试重连次数
     * @return json
     * @throws CustomException
     */
    public static JsonObject getJsonByHttpGet(String url, int maxTry, CookieStore cookieStore) {

        CloseableHttpClient httpclient;
        HttpClientBuilder builder = HttpClients.custom();
        if (cookieStore != null) {
            builder = builder.setDefaultCookieStore(cookieStore);
        }
        if (maxTry > 0) {
            CustomRetryHandler retryHandler = new CustomRetryHandler(maxTry);
            httpclient = builder.setRetryHandler(retryHandler).build();
        }
        else {
            httpclient = builder.build();
        }
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(requestConfig);
        httpget.setHeader(HTTP.USER_AGENT, USER_AGENT);
        JsonResponseHandler<JsonObject> rh = new JsonResponseHandler<JsonObject>();
        JsonObject myjson = new JsonObject();
        try {
            myjson = httpclient.execute(httpget, rh);
        }
        catch (ClientProtocolException e) {
            log.error(e);
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
        return myjson;
    }

    /**
     * 下载资源
     * 
     * @param imgUrl
     *            资源路径
     * @param imgPath
     *            本地路径
     * @return 文件名
     */
    public static String getResourceByHttpGet(String imgUrl, String imgPath, CookieStore cookieStore) {

        CustomRetryHandler retryHandler = new CustomRetryHandler(5);
        HttpClientBuilder builder = HttpClients.custom();
        if (cookieStore != null) {
            builder = builder.setDefaultCookieStore(cookieStore);
        }
        CloseableHttpClient httpclient = builder.setRetryHandler(retryHandler).build();
        try {
            HttpGet httpget = new HttpGet(imgUrl);
            httpget.setConfig(requestConfig);
            httpget.setHeader(HTTP.USER_AGENT, USER_AGENT);
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                int code = response.getStatusLine().getStatusCode();
                if (entity != null && code == HttpStatus.SC_OK) {
                    String suffix;
                    // 无图片后缀名 取Content-Type:image/jpeg中jpeg
                    if (!RegexUtils.isImageFile(imgUrl)) {
                        suffix = entity.getContentType().getValue().split("/")[1];
                    }
                    else {
                        suffix = FilenameUtils.getExtension(imgUrl).toLowerCase();
                    }
                    String fileName = FilenameUtils.getName(imgUrl);
                    if ("x-icon".equals(suffix)) {
                        fileName = fileName + "." + suffix;
                    }
                    else {
                        int random = RandomUtils.nextInt(0, 1024 * 1024);
                        String md5Str = Md5Token.getInstance()
                                .getShortToken(random + FORMAT_FORNAME.format(new Date()));
                        fileName = md5Str + "." + suffix;
                    }
                    String path = imgPath + fileName;
                    File destFile = new File(path);
                    if (!destFile.getParentFile().exists()) {
                        destFile.getParentFile().mkdirs();
                    }
                    InputStream imgData = entity.getContent();
                    FileOutputStream fos = new FileOutputStream(destFile);
                    try {
                        IOUtils.copy(imgData, fos);
                    }
                    finally {
                        IOUtils.closeQuietly(imgData);
                        IOUtils.closeQuietly(fos);
                    }
                    return fileName;
                }
            }
            finally {
                response.close();
            }
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * POST获取response body
     * 
     * @param url
     *            地址
     * @param formparams
     *            form表单参数
     * @param json
     *            payload内容
     * @param maxTry
     *            最多尝试重连次数
     * @return json
     * @throws CustomException
     * @throws UnsupportedEncodingException
     */
    public static String getContentByHttpPost(String url, List<NameValuePair> formparams, String json, int maxTry,
            CookieStore cookieStore) {

        String content = StringUtils.EMPTY;
        try {
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);
            httppost.setHeader(HTTP.USER_AGENT, USER_AGENT);
            if (StringUtils.isNotEmpty(json)) {
                StringEntity entity = new StringEntity(json, UTF8);
                entity.setContentEncoding(UTF8);
                httppost.setEntity(entity);
                httppost.setHeader(HTTP.CONTENT_TYPE, JSON_CONTENT);
            }
            else {
                httppost.setHeader(HTTP.CONTENT_TYPE, FORM_CONTENT);
            }
            if (formparams != null) {
                httppost.setEntity(new UrlEncodedFormEntity(formparams, UTF8));
            }
            CloseableHttpClient httpclient;
            HttpClientBuilder builder = HttpClients.custom();
            if (cookieStore != null) {
                builder = builder.setDefaultCookieStore(cookieStore);
            }
            if (maxTry > 0) {
                CustomRetryHandler retryHandler = new CustomRetryHandler(maxTry);
                httpclient = builder.setRetryHandler(retryHandler).build();
            }
            else {
                httpclient = builder.build();
            }
            StringResponseHandler rh = new StringResponseHandler();
            try {
                content = httpclient.execute(httppost, rh);
            }
            catch (ClientProtocolException e) {
                log.error(e);
            }
            catch (IOException e) {
                log.error(e);
            }
        }
        catch (UnsupportedEncodingException ue) {
            log.error(ue);
        }
        return content;
    }

    /**
     * 下载图片并校验格式
     * 
     * @param imgUrl
     *            资源路径
     * @param imgPath
     *            本地路径
     * @return 文件名
     */
    public static String getImageByHttpGetWithFormatCheck(String imgUrl, String imgPath, CookieStore cookieStore) {

        CustomRetryHandler retryHandler = new CustomRetryHandler(5);
        HttpClientBuilder builder = HttpClients.custom();
        if (cookieStore != null) {
            builder = builder.setDefaultCookieStore(cookieStore);
        }
        CloseableHttpClient httpclient = builder.setRetryHandler(retryHandler).build();
        try {
            HttpGet httpget = new HttpGet(imgUrl);
            httpget.setConfig(requestConfig);
            httpget.setHeader(HTTP.USER_AGENT, USER_AGENT);
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                int code = response.getStatusLine().getStatusCode();
                if (entity != null && code == HttpStatus.SC_OK) {
                    String suffix;
                    // 无图片后缀名 取Content-Type:image/jpeg中jpeg
                    if (!RegexUtils.isImageFile(imgUrl)) {
                        suffix = entity.getContentType().getValue().split("/")[1];
                    }
                    else {
                        suffix = FilenameUtils.getExtension(imgUrl).toLowerCase();
                    }
                    String fileName = FilenameUtils.getName(imgUrl);
                    if ("x-icon".equals(suffix)) {
                        fileName = fileName + "." + suffix;
                    }
                    else {
                        int random = RandomUtils.nextInt(0, 1024 * 1024);
                        String md5Str = Md5Token.getInstance()
                                .getShortToken(random + FORMAT_FORNAME.format(new Date()));
                        fileName = md5Str + "." + suffix;
                    }
                    String path = imgPath + fileName;
                    File destFile = new File(path);
                    if (!destFile.getParentFile().exists()) {
                        destFile.getParentFile().mkdirs();
                    }
                    InputStream imgData = entity.getContent();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = imgData.read(buffer)) > -1) {
                        baos.write(buffer, 0, len);
                    }
                    baos.flush();
                    byte[] data = baos.toByteArray();
                    String type = FileTypeUtils.getFileTypeByStream(data);
                    if (StringUtils.isEmpty(type)) {
                        return StringUtils.EMPTY;
                    }
                    FileOutputStream fos = new FileOutputStream(destFile);
                    try {
                        InputStream imgDataCopy = new ByteArrayInputStream(baos.toByteArray());
                        IOUtils.copy(imgDataCopy, fos);
                    }
                    finally {
                        IOUtils.closeQuietly(imgData);
                        IOUtils.closeQuietly(fos);
                    }
                    return fileName;
                }
            }
            finally {
                response.close();
            }
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return StringUtils.EMPTY;
    }

}
