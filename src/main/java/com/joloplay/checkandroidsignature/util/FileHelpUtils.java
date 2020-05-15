package com.joloplay.checkandroidsignature.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.apache.commons.io.FileUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gjh
 */
@Slf4j
public class FileHelpUtils {

	 /** 
     * 获取待复制文件夹的文件夹名 
     * @param dir 
     * @return String 
     */  
    private static String getDirName(String dir) {
		// 如果文件夹路径以"//"结尾，则先去除末尾的"//"  
        if (dir.endsWith(File.separator)) { 
            dir = dir.substring(0, dir.lastIndexOf(File.separator));  
        }  
        return dir.substring(dir.lastIndexOf(File.separator) + 1);  
    }  
	
	/**
	 * 移动文件夹及子文件到指定的文件夹下
	 * @param srcDirPath 源文件夹路径
	 * @param destDirPath 目标文件夹路径
	 * @return
	 */
	public static boolean moveToDirectory(String srcDirPath, String destDirPath){
		File fileSrc = new File(srcDirPath);  
		File fileDesc = new File(destDirPath);  
		if(!fileSrc.exists()){
			log.info("源文件夹路径不存在");
			return false;
		}
		log.info("源文件夹路径存在");
		if(!fileDesc.exists()){
			fileDesc.mkdirs();
			log.info("目标文件夹不存在,创建成功");
		}
		// 源文件路径和目标文件路径重复
        if(srcDirPath.equals(destDirPath)) {
        	log.info("源文件路径和目标文件路径重复,请核对文件夹路径参数");  
            return false;  
        }  
        String fileName = getDirName(srcDirPath);  
        String destPath = destDirPath + fileName;   
        File destFile = new File(destPath);
		// 该路径下已经有一个同名文件
        if (destFile.exists() && destFile.isFile()) {
        	log.info("目标目录下已有同名文件,文件名是："+fileName);  
            return false;  
        } 
        log.info("目标文件夹存在,开始移动");
		try {
			//common.io架包中提供的文件操作方法，移动指定路径到指定文件夹路径下
			FileUtils.moveToDirectory(fileSrc, fileDesc, false);
			log.info("移动文件夹:"+srcDirPath+"  内容到文件夹"+destDirPath+"  下,成功");
			return true;
		} catch (IOException e) {
			log.error("移动文件夹:"+srcDirPath+"  内容到文件夹"+destDirPath+"  下,失败", e);
			return false;
		}
	}
	
	/** 
     * 文件剪切：复制+删除 
     *  
     * @param destDirPath 同上
     */  
    public static boolean cutGeneralFile(String srcDirPath, String destDirPath) {  
    	File fileSrc = new File(srcDirPath);  
		File fileDesc = new File(destDirPath);  
		if(!fileSrc.exists()){
			log.info("源文件夹路径不存在");
			return false;
		}
		log.info("源文件夹路径存在");
		if(!fileDesc.exists()){
			fileDesc.mkdirs();
			log.info("目标文件夹不存在,创建成功");
		}
		// 源文件路径和目标文件路径重复
        if(srcDirPath.equals(destDirPath)) {
        	log.info("源文件路径和目标文件路径重复,请核对文件夹路径参数");  
            return false;  
        }  
        String fileName = getDirName(srcDirPath);  
        String destPath = destDirPath + fileName;   
        File destFile = new File(destPath);
		// 该路径下已经有一个同名文件
        if (destFile.exists() && destFile.isFile()) {
        	log.info("目标目录下已有同名文件,文件名是："+fileName);  
            return false;  
        } 
        log.info("目标文件夹存在,开始移动");
    	//判断操作系统是windows还是linux下
        if (!copyFileOrDirToDirectory(srcDirPath, destDirPath)) {  
        	log.info("复制失败导致剪切失败!");  
            return false;  
        }  
        if (!deleteFile(srcDirPath)) {  
        	log.info("删除源文件(文件夹)失败导致剪切失败!");  
            return false;  
        }  
        log.info("剪切成功!");  
        return true;  
    }  
	
	/**
	 * 复制文件或文件夹到指定路径下
	 * @param srcDirPath
	 * @param destDirPath
	 * @throws IOException
	 */
	public static boolean copyFileOrDirToDirectory(String srcDirPath, String destDirPath){  
		File srcFile = new File(srcDirPath);  
		File destDir = new File(destDirPath);  
		if(!srcFile.exists()){
			log.info("源文件路径不存在");
			return false;
		}
		log.info("源文件路径存在");
		if(!destDir.exists()){
			destDir.mkdirs();
			log.info("目标文件夹不存在,创建成功");
		}
		// 源文件路径和目标文件路径重复
        if (srcDirPath.equals(destDirPath)) {
        	log.info("源文件路径和目标文件路径重复,请核对文件夹路径参数");  
            return false;  
        }  
        String fileName = getDirName(srcDirPath); 
        String destPath = destDirPath + fileName; 
        File destFile = new File(destPath);
		// 该路径下已经有一个同名文件
        if (destFile.exists() && destFile.isFile()) {
        	log.info("目标目录下已有同名文件,文件名是："+fileName);  
            return false;  
        } 
		log.info("目标文件夹存在,开始移动");
		try {
			//common.io架包中提供的文件操作方法，复制文件到指定路径下// 源文件
			if (srcFile.isFile()) {
	            log.info("开始进行文件复制!");  
	            FileUtils.copyFileToDirectory(srcFile,destDir, false);
	        } else if (srcFile.isDirectory()) {  
	        	log.info("开始进行文件夹复制");  
	        	FileUtils.copyDirectoryToDirectory(srcFile,destDir);
	        }  
			log.info("复制文件:"+srcDirPath+"  内容到文件夹"+destDirPath+"  下,成功");
			return true;
		} catch (IOException e) {
			log.error("复制文件:"+srcDirPath+"  内容到文件夹"+destDirPath+"  下失败", e);
			return false;
		}  
    }  
	
	/**
	 * 递归的删除一个目录
	 * @param directoryPath 文件夹目录路径
	 */
	public static boolean deleteDirectory(String directoryPath){
		File directoryFile = new File(directoryPath);  
		if(!directoryFile.exists()){
			log.info("源文件夹路径不存在,删除失败");
			return false;
		}
		try {
			//common.io 递归删除一个目录
			FileUtils.deleteDirectory(directoryFile);
			log.info("删除文件夹及文件:"+directoryPath+"  成功");
			return true;
		} catch (IOException e) {
			log.error("删除文件夹及文件:"+directoryPath+"  失败");
			return false;
		}
	}
	
	/** 
     * 删除文件 
     * @param filePath
     * @return boolean 
     */ 
    public static boolean deleteFile(String filePath) { 
    	File file = new File(filePath);  
		if(!file.exists()){
			log.info("源文件不存在,删除失败");
			return false;
		}
		return file.delete();
    } 
    
    /**
     * 判断文件夹路径是否存在，不存在则创建
     * @param directoryPath 文件或文件夹全路径
     * @return
     */
    public static boolean isExistNoDirectory(String directoryPath){
    	File file = new File(directoryPath);  
		if(!file.exists()){
			log.info("源文件夹路径"+directoryPath+"不存在,创建");
			file.mkdirs();
			return false;
		}else{
			log.info("源文件夹路径"+directoryPath+"已存在");
			return true;
		}
    }
    
    /**
     * 判断文件路径是否存在
     * @param directoryPath 文件或文件夹全路径
     * @return
     */
    public static boolean isExistFile(String directoryPath){
    	File file = new File(directoryPath);  
		if(!file.exists()){
			return false;
		}else{
			return true;
		}
    }
    
    /**
     * 复制文件到指定的目录
     * @param srcFilePath 复制的文件路径
     * @param destFilePath 目标文件夹路径
     * @return
     */
    public static boolean copyFileToDirectory(String srcFilePath, String destFilePath){
    	File srcFile = new File(srcFilePath);
		File destFile = new File(destFilePath);
		try {
			FileUtils.copyFileToDirectory(srcFile, destFile, true);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }

    /**
     * 将内容写入文件中
     * @param context 文件内容
     * @param filename 文件名
     * @param filepath 文件路径(不带文件名)
     * @return
     */
	public static boolean writeFile(String context, String filename,
			String filepath) {
		try {
			File file = new File(filepath + File.separator + filename);
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter output = new BufferedWriter(new FileWriter(file,
					true));
			output.write(context);
			output.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 	//删除指定文件夹下所有文件
	 * @param path 文件夹完整绝对路径
	 * @return
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				//先删除文件夹里面的文件
				delAllFile(path + "/" + tempList[i]);
				//再删除空文件夹
				delFolder(path + "/" + tempList[i]);
			}
			flag = true;
		}
		return flag;
	}
	/**
	 * 删除文件夹
	 * @param folderPath
	 */
	public static void delFolder(String folderPath) {
		try {
			//删除完里面所有内容
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			//删除空文件夹
			myFilePath.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int directoryIsEmpty(String folderPath) {
		File file = new File(folderPath);
		if (!file.exists()) {
			return 0;
		}
		if (!file.isDirectory()) {
			return 0;
		}
		return  file.list().length;
	}

}
  
