package com.joloplay.checkandroidsignature.controller;

import com.joloplay.checkandroidsignature.service.CheckSignatureVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gjh
 */
@RestController
public class CheckSignatureVersionController {

    @Autowired
    private CheckSignatureVersionService checkSignatureVersionService;

    /**
     * 0正常 msg异常信息
     *{"ret":0,"msg":"","isV1OK":true,"isV2":false,"isV2OK":false,"isV3":false,"isV3OK":false,"keystoreMd5":"24f8fdd198faa68ac77f9e910a9698d7"}
     * @param path apk地址
     * @param isLocal 是否本地文件
     * @param showException 是否显示异常信息
     * @param request
     * @return
     */
    @RequestMapping("/checkSignature")
    public String getSignatureVersion(String path, Boolean isLocal, Boolean showException, HttpServletRequest request){
        return checkSignatureVersionService.getSignatureVersion(path,isLocal,showException);
    }
}
