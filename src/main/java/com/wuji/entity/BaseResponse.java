package main.java.com.wuji.entity;

public class BaseResponse {

    private Integer Ret;

    private String ErrMsg = "";


    public Integer getRet() {

        return Ret;
    }

    public void setRet(Integer Ret) {

        this.Ret = Ret;
    }

    public String getErrMsg() {

        return ErrMsg;
    }

    public void setErrMsg(String ErrMsg) {

        this.ErrMsg = ErrMsg;
    }

    @Override
    public String toString() {

        return "BaseResponse{" + "Ret=" + Ret + ", ErrMsg=" + ErrMsg + '}';
    }

}
