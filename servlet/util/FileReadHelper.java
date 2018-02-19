package com.wethink.servlet.util;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

public class FileReadHelper
{
	public static File getFile(String fileName)
	{
		File file = new File(fileName);
		return file;
	}
	
	public static Properties getProperties(File file) throws Exception
	{
		if(file.exists())
		{
			Properties props = new Properties();
			props.load(new FileInputStream(file));
			return props;
		}
		return null;
	}
	
	public static JSONObject getJSONObject(File file) throws Exception
	{
		String contents = new String(Files.readAllBytes(file.toPath()));
		return new JSONObject(contents);
	}
	
	public static JSONArray getJSONArray(File file) throws Exception
	{
		String contents = new String(Files.readAllBytes(file.toPath()));
		return new JSONArray(contents);
	}
	
//	public static JSONArray getCSVToJSONArray(File file) throws Exception
//	{
//		JSONArray result = new JSONArray();
//		BufferedReader br = new BufferedReader(new FileReader(file));
//		List<String> words = new ArrayList<>();
//        String lineJustFetched = null;
//        String[] wordsArray;
//        br.readLine();
//		while(true){
//            lineJustFetched = br.readLine();
//            if(lineJustFetched == null){  
//                break; 
//            }else{
//            	JSONObject job = new JSONObject();
//                wordsArray = lineJustFetched.split("\t");
//                for(String each : wordsArray){
//                    if(!"".equals(each)){
//                    	job.put("primaryCode", each);
//                    }
//                }
//            }
//        }
//		return null;
//	}

}

