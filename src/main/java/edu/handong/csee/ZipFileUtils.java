package edu.handong.csee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

public class ZipFileUtils {
	private HashMap<String,InputStream> collectTheInputStreamAboutSummary;
	private HashMap<String,InputStream> collectTheInputStreamAboutImage;
	public void readFileInZip(String path) {
		ZipFile zipFile;
		String check=null;
		int count=0;
		 collectTheInputStreamAboutSummary=new HashMap<String,InputStream>();
		 collectTheInputStreamAboutImage=new HashMap<String,InputStream>();
		File dir = new File(path);
		File[] allTheFileInDirectory= dir.listFiles();
		try {
			for(File file: allTheFileInDirectory) {
			//int number;
			//ArrayList<InputStream> collectSummary=new ArrayList<InputStream>();
			//ArrayList<InputStream> collectImage=new ArrayList<InputStream>();
			zipFile = new ZipFile(file.getAbsoluteFile());
			int num=file.getName().indexOf(".");
			String zipFileName=file.getName().substring(0,num);
			Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();
		    while(entries.hasMoreElements()){
		    	ZipArchiveEntry entry = entries.nextElement();
		    	if(count==0){
		    		int start=entry.toString().indexOf("(");
		    		int end=entry.toString().indexOf(")");
		    		check=entry.toString().substring(start+1, end);
		    		count++;
		    	}
		    	InputStream stream = zipFile.getInputStream(entry);
		    	if(entry.toString().indexOf(check) != -1) {
		    		collectTheInputStreamAboutSummary.put(zipFileName,stream);
		    	}else {
		    		collectTheInputStreamAboutImage.put(zipFileName,stream);
		    		//number=1;
		    	}
		    	}
		   /* if(num==1) {
		    	collectTheInputStreamAboutSummary.put(zipFileName,collectTheInputStream);
		    }else {
		    	collectTheInputStreamAboutImage.compute(key, remappingFunction)
		    }*/
		    }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	public HashMap<String, InputStream> getCollectTheInputStreamAboutSummary() {
		return collectTheInputStreamAboutSummary;
	}
	public HashMap<String, InputStream> getCollectTheInputStreamAboutImage() {
		return collectTheInputStreamAboutImage;
	}

}
