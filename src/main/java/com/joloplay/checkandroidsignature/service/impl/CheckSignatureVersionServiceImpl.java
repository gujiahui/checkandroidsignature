package com.joloplay.checkandroidsignature.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.apksigner.ApkSignerTool;
import com.joloplay.checkandroidsignature.bean.SignatureVersionEntity;
import com.joloplay.checkandroidsignature.common.ConstanUtils;
import com.joloplay.checkandroidsignature.service.CheckSignatureVersionService;
import com.joloplay.checkandroidsignature.util.DownloadFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author gjh
 */
@Slf4j
@Service
public class CheckSignatureVersionServiceImpl implements CheckSignatureVersionService {

    @Value("${checkSignature_file_path}")
    private String checkSignatureFilePath ;

    @Override
    public synchronized String getSignatureVersion(String path, Boolean isLocal, Boolean showException) {
        //区分本地路径apk和远程下载Apk,远程链接则先下载
        if(!isLocal){
            String downloadApk = DownloadFileUtils.downloadApk(path, checkSignatureFilePath);
            if(downloadApk!=null){
                 path = downloadApk;
            }else {
                return ConstanUtils.CHECKANDROIDSIGNATURE_ERROR_TEXT;
            }
        }
        if(showException==null){
            showException =false;
        }
        return ApkSignerTool.verify(path,showException);
    }

}
