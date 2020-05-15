package com.joloplay.checkandroidsignature.service;

/**
 * @author gjh
 */
public interface CheckSignatureVersionService {
    /**
     * 获取APK打包方式
     * @param path  路径或者URL地址
     * @param isLocal 是否为本地路径
     * @return
     */
    String getSignatureVersion(String path, Boolean isLocal, Boolean showException);
}
