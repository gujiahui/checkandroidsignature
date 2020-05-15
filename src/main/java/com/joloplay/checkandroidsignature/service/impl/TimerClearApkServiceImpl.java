package com.joloplay.checkandroidsignature.service.impl;

import com.joloplay.checkandroidsignature.service.TimerClearApkService;
import com.joloplay.checkandroidsignature.util.FileHelpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author gjh
 */
@Slf4j
@Service
public class TimerClearApkServiceImpl implements TimerClearApkService {


    @Override
    public void clearApkFile(String checkSignatureFilePath) {
        try {
            boolean clearResoult=false;
            int apkCount = FileHelpUtils.directoryIsEmpty(checkSignatureFilePath);
            if(apkCount>0){
                clearResoult  = FileHelpUtils.delAllFile(checkSignatureFilePath);
            }
            log.info(apkCount+"...apk---exit,,ifclearFile:"+clearResoult);
        }catch (Exception e){
            log.error("FILE----CLEAR----ERROR::MSG:::"+e.getMessage());
        }

    }


}
