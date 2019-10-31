package com.util;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {

	private static Logger log = Logger.getLogger(UploadUtil.class);
	
	public static String uploadFile(MultipartFile file, String path) throws IOException{
		String name = file.getOriginalFilename();//上传文件的真实名称
		String suffixName = name.substring(name.lastIndexOf("."));//获取后缀名
		String hash = Integer.toHexString(new Random().nextInt());
		String fileName = hash + suffixName;
		File tempFile = new File(path, fileName);
		if(!tempFile.getParentFile().exists()){
			tempFile.getParentFile().mkdir();
		}
		if(tempFile.exists()){
			tempFile.delete();
		}
		tempFile.createNewFile();
		file.transferTo(tempFile);
		
		return tempFile.getName();
		
	}
}
