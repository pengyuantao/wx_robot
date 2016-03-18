package main.java.com.wuji.entity;

import com.google.gson.annotations.SerializedName;

public class KeyVal {

    @SerializedName("Key")
    private int key;

    @SerializedName("Val")
    private long val;

    public int getKey() {

        return key;
    }

    public void setKey(int key) {

        this.key = key;
    }

    public long getVal() {

        return val;
    }

    public void setVal(long val) {

        this.val = val;
    }

    @Override
    public String toString() {

        return "KeyVal{" + "key=" + key + ", val=" + val + '}';
    }

}
