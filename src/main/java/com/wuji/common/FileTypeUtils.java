package main.java.com.wuji.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public class FileTypeUtils {

    public static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

    static {

        FILE_TYPE_MAP.put("jpg", "FFD8FFE000104A464946");
        FILE_TYPE_MAP.put("png", "89504E470D0A1A0A0000");
        FILE_TYPE_MAP.put("gif", "47494638396126026F01");
        FILE_TYPE_MAP.put("ico", "00000100010020200000");
    }

    public static String getFileTypeByStream(byte[] b) {

        String filetypeHex = String.valueOf(getFileHexString(b));
        System.out.println(filetypeHex);
        Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP.entrySet().iterator();
        while (entryiterator.hasNext()) {
            Entry<String, String> entry = entryiterator.next();
            String fileTypeHexValue = entry.getValue().toUpperCase();
            if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
                return entry.getKey();
            }
        }
        return StringUtils.EMPTY;
    }

    public static String getFileHexString(byte[] b) {

        StringBuilder stringBuilder = new StringBuilder();
        if (b == null || b.length <= 0) {
            return null;
        }
        for (int i = 0; i < 10; i++) {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static void main(String args[]) throws Exception {

        File file = new File("D:\\static_home\\qrcode\\favicon.ico");
        InputStream input = new FileInputStream(file);
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        for (int i = 0; i < 10; i++) {
            int a = input.read();
            bytestream.write(a);
        }
        byte imgdata[] = bytestream.toByteArray();
        System.out.println(getFileTypeByStream(imgdata));
        input.close();
    }
}
