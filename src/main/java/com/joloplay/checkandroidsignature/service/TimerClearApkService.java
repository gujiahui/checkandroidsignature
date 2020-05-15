package com.joloplay.checkandroidsignature.service;

/**
 * @author gjh
 */
public interface TimerClearApkService {
    /**
     * 清理存放Apk的文件夹
     * @param checkSignatureFilePath 文件夹路径
     */
    void clearApkFile(String checkSignatureFilePath);
}
