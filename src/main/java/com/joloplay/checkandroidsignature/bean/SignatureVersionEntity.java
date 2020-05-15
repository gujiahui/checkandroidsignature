package com.joloplay.checkandroidsignature.bean;

public class SignatureVersionEntity {

    /**
     * ret : 0
     * msg :
     * isV1OK : true
     * isV2 : false
     * isV2OK : false
     * isV3 : false
     * isV3OK : false
     * keystoreMd5 : 230eed301394300c66e3a2fec5973227
     */

    private int ret;
    private String msg;
    private Boolean isV1OK;
    private Boolean isV2;
    private Boolean isV2OK;
    private Boolean isV3;
    private Boolean isV3OK;
    private String keystoreMd5;


    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isIsV1OK() {
        return isV1OK;
    }

    public void setIsV1OK(boolean isV1OK) {
        this.isV1OK = isV1OK;
    }

    public boolean isIsV2() {
        return isV2;
    }

    public void setIsV2(boolean isV2) {
        this.isV2 = isV2;
    }

    public boolean isIsV2OK() {
        return isV2OK;
    }

    public void setIsV2OK(boolean isV2OK) {
        this.isV2OK = isV2OK;
    }

    public boolean isIsV3() {
        return isV3;
    }

    public void setIsV3(boolean isV3) {
        this.isV3 = isV3;
    }

    public boolean isIsV3OK() {
        return isV3OK;
    }

    public void setIsV3OK(boolean isV3OK) {
        this.isV3OK = isV3OK;
    }

    public String getKeystoreMd5() {
        return keystoreMd5;
    }

    public void setKeystoreMd5(String keystoreMd5) {
        this.keystoreMd5 = keystoreMd5;
    }
}
