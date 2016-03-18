package main.java.com.wuji.entity.baidu;

import java.util.List;
import java.util.Map;

public class BaiduImg {

    private String listNum;

    private List<Map<String, String>> imgs;

    public String getListNum() {

        return listNum;
    }

    public void setListNum(String listNum) {

        this.listNum = listNum;
    }

    public List<Map<String, String>> getImgs() {

        return imgs;
    }

    public void setImgs(List<Map<String, String>> imgs) {

        this.imgs = imgs;
    }

}
