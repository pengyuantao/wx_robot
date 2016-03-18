package main.java.com.wuji.common;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class RegexUtils {

    public static final String IMG_REG = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png|.ICO|.ico)$";

    // 去掉表情的正则
    public static final String EMOJI_REG = "<span class=\"emoji.*?></span>";

    public static boolean isImageFile(String fileName) {

        if (fileName == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(IMG_REG);
        Matcher matcher = pattern.matcher(fileName.toLowerCase());
        return matcher.find();
    }

    public static List<String> findMatchers(String regex, String content) {

        if (StringUtils.isBlank(regex) || StringUtils.isBlank(content)) {
            return null;
        }
        List<String> list = new ArrayList<String>(0);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    /**
     * 用于删除str中的html微信表情标签
     * 
     * @param str
     * @return
     */
    public static String removeEmoji(String str) {

        Pattern pat = Pattern.compile(EMOJI_REG);
        Matcher mat = pat.matcher(str);

        return mat.replaceAll(StringUtils.EMPTY);
    }

    /**
     * 去掉非UTF-8编码的字节,用于删除微信表情emoji 已不需此方法
     * 
     * @param text
     * @return
     */
    public static String filterOffUtf8Mb4(String text) {

        byte[] bytes;
        try {
            bytes = text.getBytes("UTF-8");

            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            int i = 0;
            while (i < bytes.length) {
                short b = bytes[i];
                if (b > 0) {
                    buffer.put(bytes[i++]);
                    continue;
                }
                // 去掉符号位
                b += 256;
                // 2字节字符如字母数字符号等
                if (((b >> 5) ^ 0x6) == 0) {
                    buffer.put(bytes, i, 2);
                    i += 2;
                }
                // 3字节汉字
                else if (((b >> 4) ^ 0xE) == 0) {
                    buffer.put(bytes, i, 3);
                    i += 3;
                }
                // 4字节emiji表情符号
                else if (((b >> 3) ^ 0x1E) == 0) {
                    i += 4;
                }
                else if (((b >> 2) ^ 0x3E) == 0) {
                    i += 5;
                }
                else if (((b >> 1) ^ 0x7E) == 0) {
                    i += 6;
                }
                else {
                    buffer.put(bytes[i++]);
                }
            }
            buffer.flip();
            return new String(buffer.array(), "utf-8");
        }
        catch (UnsupportedEncodingException e) {

            return StringUtils.EMPTY;
        }
    }

    /**
     * 清除所有非标准格式
     * 
     * @param text
     * @return
     */
    public static String formatWechatText(String text) {

        String formatText = removeEmoji(text);
        formatText = formatText.replaceAll("&amp;", "&").trim();
        return formatText;
    }
    
}
