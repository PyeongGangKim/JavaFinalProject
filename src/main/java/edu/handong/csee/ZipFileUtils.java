package edu.handong.csee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

public class ZipFileUtils {
	public static HashMap<String,ArrayList<InputStream>> readFileInZip(String path) {
		ZipFile zipFile;
		HashMap<String,ArrayList<InputStream>> collectTheInputStreamAndFileName=new HashMap<String,ArrayList<InputStream>>();
		File dir = new File(path);
		File[] allTheFileInDirectory= dir.listFiles();
		try {
			for(File file: allTheFileInDirectory) {
			ArrayList<InputStream> collectTheInputStream=new ArrayList<InputStream>();
			zipFile = new ZipFile(file.getAbsoluteFile());
			int num=file.getName().indexOf(".");
			String zipFileName=file.getName().substring(0,num);
			Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();
		    while(entries.hasMoreElements()){
		    	ZipArchiveEntry entry = entries.nextElement();
		        InputStream stream = zipFile.getInputStream(entry);
		        collectTheInputStream.add(stream);
		    }
		    collectTheInputStreamAndFileName.put(zipFileName,collectTheInputStream);
		    }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			return collectTheInputStreamAndFileName;
		
	}

}
