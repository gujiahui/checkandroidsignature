package com.joloplay.checkandroidsignature.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;


/**
 * @author gjh
 */
@Slf4j
public class DownloadFileUtils {
	
	
	/**
	 * 根据url地址下载APK文件
	 * @param urlPath 文件的url地址
	 * @param toFilePath 文件存储在本地的地址
	 * @return
	 */
	public static String downloadApk(String urlPath, String toFilePath){
		try{  
			String fileName = FileNameUtils.getFileNameFromUrl(urlPath.replace("!bjjl", ".apk"));
        	log.info("----------------"+fileName+"开始下载中----------------");
        	long time1 = System.currentTimeMillis();
        	FileHelpUtils.isExistFile(toFilePath);
        	if(FileHelpUtils.isExistFile(toFilePath+fileName)){
        		log.info(fileName+"文件已存在");
        		return toFilePath+fileName;
        	}
            downLoadApk(urlPath, fileName, toFilePath);  
            long time2=System.currentTimeMillis();
            log.info(toFilePath+"----------------"+fileName+"下载完毕,耗时:"+(time2-time1)+"ms----------------");
            if(FileHelpUtils.isExistFile(toFilePath+fileName)){
            	return toFilePath+fileName;
            }else{
            	return null;
            }
        }catch (Exception e) {  
            log.error("文件下载失败", e);
            return null;
        }  
	}
	
	/**
	 * 根据url地址下载素材文件(ICON+截图+游戏介绍)
	 * @param urlPath
	 * @return
	 */
	public static boolean downloadPic(String urlPath, String toFilePath){
		try{  
			String fileName = FileNameUtils.getFileNameFromUrl(urlPath);
        	log.info("----------------"+fileName+"开始下载中----------------");
        	long time1=System.currentTimeMillis();
        	FileHelpUtils.isExistFile(toFilePath);
            downLoadFromUrl(urlPath, fileName, toFilePath);  
            long time2=System.currentTimeMillis();
            log.info("----------------"+fileName+"下载完毕,耗时:"+(time2-time1)+"ms----------------");
            if(FileHelpUtils.isExistFile(toFilePath+fileName)){
            	return true;
            }else{
            	return false;
            }
        }catch (Exception e) {  
            e.printStackTrace();
            return false;
        }  
	}

	/** 
     * 从网络Url中下载文件 
     * @param urlStr 
     * @param fileName 
     * @param savePath 
     * @throws IOException 
     */  
    public static void downLoadFromUrl(String urlStr,String fileName,String savePath) {  
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
             
	        conn.setConnectTimeout(3*1000); //设置超时间为3秒   
	        //防止屏蔽程序抓取而返回403错误  
	        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
	        //得到输入流  
	        InputStream inputStream = conn.getInputStream();    
	        //获取自己数组  
	        byte[] getData = readInputStream(inputStream);      
	        //文件保存位置  
	        File saveDir = new File(savePath);  
	        if(!saveDir.exists()){  
	            saveDir.mkdir();  
	        }  
	        File file = new File(saveDir+File.separator+fileName);      
	        FileOutputStream fos = new FileOutputStream(file);   
	        
	        fos.write(getData);   
	        if(fos!=null){  
	            fos.close();    
	        }  
	        if(inputStream!=null){  
	            inputStream.close();  
	        }  
	        log.info("info:"+url+" download success");   
		} catch (IOException e) {
			e.printStackTrace();
			log.error("下载失败", e);
		}    
  
    }  
    
    /**
     * apk下载因为文件太大，不能一次全部写入内存
     * @param urlStr
     * @param fileName
     * @param savePath
     */
    public static void downLoadApk(String urlStr,String fileName,String savePath) {  
    	long time1 = System.currentTimeMillis();
		InputStream inputStream=null;
		FileOutputStream out=null;
		try {
			log.info("start to download");
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			//设置超时间为3秒
	        conn.setConnectTimeout(3*1000);
	        //防止屏蔽程序抓取而返回403错误  
	        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
	        //得到输入流  
	        int contentLength=conn.getContentLength();
	        log.info("contentLength>>>"+contentLength);
	        inputStream= conn.getInputStream();  
	        
	        File saveDir = new File(savePath);  
	        if(!saveDir.exists()){  
	            saveDir.mkdir();  
	        } 
	        File file=new File(savePath+"/"+fileName);
	        out =new FileOutputStream(file,true);
	        byte[] tempbytes = new byte[4096];
            int byteread = 0;
            long totalSize=0;
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = inputStream.read(tempbytes)) != -1) {	 
            	out.write(tempbytes, 0, byteread);             
                totalSize=totalSize+byteread;
                log.debug("wen>>>"+(totalSize/1024)+"k");
            }
            log.info("end download Ok,totoal size="+totalSize);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("下载失败", e);
		} finally{
			if(out !=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inputStream !=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			long time2 = System.currentTimeMillis();
			System.out.println("下载耗时:"+(time2-time1)/1000+"s");
		}
	  
	}  
    
  
    /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */   
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }  
}
  
