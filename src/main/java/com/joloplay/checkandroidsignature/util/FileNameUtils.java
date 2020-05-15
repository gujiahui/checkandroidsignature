package com.joloplay.checkandroidsignature.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.Adler32;

/**
 * @author gjh
 */
@Slf4j
public class FileNameUtils {


	/**文件重命名 
	 * @param path 文件目录 
	 * @param oldname  原来的文件名 
	 * @param newname 新文件名 
     */ 
    public static void renameFile(String path,String oldname,String newname){
		//新的文件名和以前文件名不同时,才有必要进行重命名
        if(!oldname.equals(newname)){
            File oldfile=new File(path+oldname); 
            File newfile=new File(path+newname); 
            if(!oldfile.exists()){
            	log.info(path+oldname+"文件不存在");
				//重命名文件不存在
                return;
            }
			//若在该目录下已经有一个文件和新文件名相同，则不允许重命名 
            if(newfile.exists()) {
				log.info(newname+"文件已经存在！");
			} else{
                oldfile.renameTo(newfile); 
            } 
        }else{
            log.info("新文件名和旧文件名相同...");
        }
    }
	
	/**
	 * 判断APP文件名是否存在特殊字符“!”，有的话替换成“_”
	 * @param sourceFile
	 * @return
	 * @throws Exception
	 */
	public static String replaceGTH(String sourceFile) throws Exception {
		if (StringUtils.isEmpty(sourceFile)) {
			return "";
		}
		if (sourceFile.indexOf("!")>=0) {
			sourceFile = sourceFile.replaceAll("!", "_");
		}
		return sourceFile;
	}
	
	/**
	 * 把文件名的后缀“.apk”改成七牛的样式“!bjjl”
	 * @param sourceFile
	 * @return
	 * @throws Exception
	 */
	public static String reNameApk2Bjjl(String sourceFile) throws Exception {
		if (StringUtils.isEmpty(sourceFile) || sourceFile.indexOf(".apk")<0) {
			return "";
		}
		return sourceFile.replace(".apk", "!bjjl");
	}
	
	
	/**
	 * 根据文件的文件名或文件路径获取文件扩展名
	 * @param filename 文件名或文件路径
	 */
	public static String getExtensionName(String filename) {   
		if ((filename != null) && (filename.length() > 0)) {   
		    int dot = filename.lastIndexOf('.');   
		    if ((dot >-1) && (dot < (filename.length() - 1))) {   
		        return filename.substring(dot + 1);   
		    }   
		}   
		return filename;   
	}

	/**
	 * 获取不带后缀的文件名
	 * @param filename 只能是文件名，不能是文件路径
	 * @return eg. abc.txt  返回为 abc
	 */ 
	public static String getFileNameNoEx(String filename) {  
		if ((filename != null) && (filename.length() > 0)) {   
		    int dot = filename.lastIndexOf('.');   
		    if ((dot >-1) && (dot < (filename.length()))) {   
		        return filename.substring(0, dot);   
		    }   
		}   
		return filename;   
	}
	
	/**
     * 根据URL地址获取文件名称  eg.   test.apk
     * @param url
     * @return
     */
    public static String getFileNameFromUrl(String url) {
        String filename = null;
        boolean isok = false;
        // 从UrlConnection中获取文件名称
        try {
            URL myURL = new URL(url);
            URLConnection conn = myURL.openConnection();
            if (conn == null) {
                return null;
            }
            Map<String, List<String>> hf = conn.getHeaderFields();
            if (hf == null) {
                return null;
            }
            Set<String> key = hf.keySet();
            if (key == null) {
                return null;
            }
            for (String skey : key) {
                List<String> values = hf.get(skey);
                for (String value : values) {
                    String result;
                    try {
                        result = new String(value.getBytes("ISO-8859-1"), "GBK");
                        int location = result.indexOf("filename");
                        if (location >= 0) {
                            result = result.substring(location
                                    + "filename".length());
                            filename = result
                                    .substring(result.indexOf("=") + 1);
                            if (filename.indexOf(";") > -1) {
                            	filename = filename.substring(0, filename.indexOf(";"));
                            }
                            isok = true;
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }// ISO-8859-1 UTF-8 gb2312
                }
                if (isok) {
                    break;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 从路径中获取
        if (filename == null || "".equals(filename)) {
            filename = url.substring(url.lastIndexOf("/") + 1);
            if (filename.indexOf(";") > -1) {
            	filename = filename.substring(0, filename.indexOf(";"));
            }
        }
        return filename.replaceAll("\"", "");
    }
    
    /**
     * 根据本地路径获取文件全名 (cn.geekgame.dota.htc_signer.apk)
     * @param filePath
     * @return
     */
    public static String getFileNameFromLocalPath(String filePath){
    	File tempFile =new File(filePath.trim());
    	String fileName = null;
    	if(tempFile == null){
    		return null;
    	}else{
    		fileName = tempFile.getName(); 
    		return fileName;
    	}
    }
    
    /**
	 * 计算URL路径的Adler32的值
	 * @param filepath
	 * @return
	 */
	public static String getAdler32CRC(String filepath) throws Exception {
		File f = new File(filepath);
		if (!f.exists()) {
			return "-1";
		}
		BufferedInputStream bis = null;
		FileInputStream fis = null;
		long fcrc = 0;
		Adler32 adler = new Adler32();
		try {
			fis = new FileInputStream(f);
			bis = new BufferedInputStream(fis);
			byte[] b = new byte[8192];
			int len = bis.read(b, 0, b.length);
			while (len != -1) {
				adler.update(b, 0, len);
				len = bis.read(b, 0, b.length);
			}
			bis.close();
			bis = null;
			fis.close();
			fis = null;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (Exception e) {
					throw new Exception(e);
				}
				bis = null;
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					throw new Exception(e);
				}
				bis = null;
			}
		}
		fcrc = adler.getValue();
		return String.valueOf(fcrc);
	}
	
	public static void main(String[] args){
		//根据文件的文件名或文件路径获取文件扩展名
		System.out.println(getExtensionName("abc.txt"));
		//获取不带扩展名的文件名 
		System.out.println(getFileNameNoEx("abc.txt"));
		//根据文件本地路径获取文件名
		System.out.println(getFileNameFromLocalPath("d://abc/edd/123//"));
		
//		System.out.println(getFileNameFromUrl("http://cdn4.joloplay.cn/apk/TCYFXQ-1.6.20170316_20170401_jolo20170401153153364!bjjl"));
		System.out.println(getFileNameFromUrl("http://cdn4.joloplay.cn/apk/mgde_an_176146901_jolo20170406163626916.apk; filename*=utf-8' 'mgde_an_176146901_jolo20170406163626916.apk"));
	}
}
  
