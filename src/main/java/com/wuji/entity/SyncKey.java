package main.java.com.wuji.entity;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class SyncKey {

    @SerializedName("Count")
    private int count;

    @SerializedName("List")
    private List<KeyVal> keyList = new ArrayList<>();

    public int getCount() {

        return count;
    }

    public void setCount(int count) {

        this.count = count;
    }

    public List<KeyVal> getKeyList() {

        return keyList;
    }

    public void setKeyList(List<KeyVal> keyList) {

        this.keyList = keyList;
    }

    @Override
    public String toString() {

        return "SyncKey{" + "count=" + count + ", keyList=" + keyList + '}';
    }

    /**
     * 由于在url中的SyncKey都是通过key_value|key_value...等形式进行连接的，所以，对参数进行编码
     * 
     * @param sk
     * @return 编码后的字符串
     */
    public String getSyncKeyStr() {

        StringBuilder sb = new StringBuilder();
        List<KeyVal> list = this.getKeyList();
        int size = list.size();
        if (size == 0) {
            return "";
        }
        for (int index = 0; index < size; index++) {
            KeyVal kv = list.get(index);
            sb.append(kv.getKey()).append("_").append(kv.getVal());
            if (index != size - 1) {
                sb.append("|");
            }
        }
        return sb.toString();
    }
}
