package edu.handong.csee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.*;



import org.apache.poi.ss.usermodel.CellType.*;
import org.apache.poi.ss.formula.FormulaType;

public class Utils {
	public static void writeAFile(ArrayList<ArrayList<String>> lines, String targetFileName) {
		
		String fPath=targetFileName.replace("/",File.separator);
		int num=fPath.lastIndexOf(File.separator);
		String filePath=fPath.substring(0,num);
		File path = new File(filePath);
		if (!path.exists()) {
			path.mkdirs();
		}
		
	
		HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        int rowNum = 0;
        for(ArrayList<String> line: lines) {
        	Row row = sheet.createRow(rowNum++);
        	int colNum = 0;
        	for(String data:line) {
        		Cell cell = row.createCell(colNum++);
        		cell.setCellValue(data);
        	}
        }try {
        	FileOutputStream out = new FileOutputStream(new File(targetFileName)); 
            workbook.write(out); 
            out.close(); 
        }catch(Exception e) {
			e.getMessage();
		}
	}


	/*public void run() {
		
		
	}*/

	
	public static LinkedList<ArrayList<String>> getSummaryData(HashMap<String,InputStream> zipFiles)  {
		int count=0;
		Map<String, InputStream> sortedFile = new TreeMap<String,InputStream>(zipFiles); 
		Iterator<String> iteratorKey = sortedFile.keySet().iterator();
		ArrayList<String> checkHead= new ArrayList<String>();
		LinkedList<ArrayList<String>> dataList = new LinkedList<ArrayList<String>>();
		while(iteratorKey.hasNext()) {
			int num=0;
			String key = iteratorKey.next();
			String temp = null;
			InputStream is=zipFiles.get(key);
		try {
		    if(is!=null) {
		    	Workbook wb = WorkbookFactory.create(is);
		    	Sheet sheet = wb.getSheetAt(0);
		        int rows = sheet.getPhysicalNumberOfRows();
		        for(int r=0;r<rows;r++) {//row를 하나씩
		        	ArrayList<String> data= new ArrayList<String>();
		        	data.add(key);
		        	Row row=sheet.getRow(r);
		        	int cells=row.getPhysicalNumberOfCells();
		        	for(int c=0;c<cells;c++) {//한 행씩 저장하기
		        		Cell cell = row.getCell(c,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		        		String cellData= new String();
		        	if(cell==null) {
		        		continue;
		        	}
		        		switch(cell.getCellType()) {
		        		case FORMULA:
		        			cellData = cell.getCellFormula();
		        			break;
		        		case NUMERIC:
		        			cellData = cell.getNumericCellValue()+"";
		        			break;
		        		case STRING:
		        			cellData = cell.getStringCellValue()+"";
		        			break;
		        		/*case BLANK:
		        			cellData =" ";
		        			break;
		        		/*case ERROR:
		        			cellData = " ";
		        			break;*/
		        			default:
		       
		        		}
		        		if(count==0&&num==0) {//첫번째꺼 헤더가져오기
		        			checkHead.add(cellData);
		        		}
		        		if(cell!=null) {
		        			data.add(cellData);
		        		}
		        		
		        	}
		        	if(count!=0&&num==0) {//헤더가 동일한지 체크하기
		        		data.set(0,temp);
		        		for(int j=0;j<checkHead.size();j++) {
		        			if(checkHead.get(j).equals(data.get(j))==false) {
		        				//throw new WrongDataForm(key);
		        			}
		        		}
		        		
		        	}else {
		        	dataList.addANodeToTail(data);
		        	}	
		        	num++;
		        }
		        temp=key;
		        count++;
		    }
			 }catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }/*catch (WrongDataForm e) {
				 e.makeErrorFile();
				 
			 }*/
			
		}
		return dataList;
	}
	public static LinkedList<ArrayList<String>> getImageData(HashMap<String,InputStream> zipFiles){
		int count=0;//첫번째 문자 세기
		Map<String, InputStream> sortedFile = new TreeMap<String,InputStream>(zipFiles); 
		Iterator<String> iteratorKey = sortedFile.keySet().iterator();
		ArrayList<String> checkFirstHeader= new ArrayList<String>();
		ArrayList<String> checkSecondHeader= new ArrayList<String>();
		LinkedList<ArrayList<String>> dataList = new LinkedList<ArrayList<String>>();
		while(iteratorKey.hasNext()) {
			int num=0;//헤더세기
			String key = iteratorKey.next();
			String temp = null;
			InputStream is=zipFiles.get(key);
		try {
		    if(is!=null) {
		    	Workbook wb = WorkbookFactory.create(is);
		    	Sheet sheet = wb.getSheetAt(0);
		        int rows = sheet.getPhysicalNumberOfRows();
		        for(int r=0;r<rows;r++) {//row를 하나씩
		        	
		        	ArrayList<String> data= new ArrayList<String>();
		        	data.add(key);
		        	Row row=sheet.getRow(r);
		        	int cells=row.getPhysicalNumberOfCells();
		        	for(int c=0;c<cells;c++) {//한 행씩 저장하기
		        		Cell cell = row.getCell(c,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		        		String cellData= new String();
		        		if(cell==null) {
			        		continue;
			        	}
		        		switch(cell.getCellType()) {
		        		case FORMULA:
		        			cellData = cell.getCellFormula()+"";
		        			break;
		        		case NUMERIC:
		        			cellData = cell.getNumericCellValue()+"";
		        			break;
		        		case STRING:
		        			cellData = cell.getStringCellValue()+"";
		        			break;
		        		case BLANK:
		        			cellData = "";
		        			break;
		        		case ERROR:
		        			cellData = "";
		        			break;
		        			default:
		       
		        		}
		        		if(count==0 && num==0) {//첫번째꺼 헤더가져오기
		        			checkFirstHeader.add(cellData);
		        		}
		        		if(count==0 && num==1) {//첫번째꺼 헤더가져오기
		        			checkSecondHeader.add(cellData);
		        		}
		        		if(cell!=null) {
		        			data.add(cellData);
		        		}
		        		
		        	}
		        	if(count!=0 && num==0) {//헤더가 동일한지 체크하기
		        		data.set(0,temp);
		        		for(int j=0;j<checkFirstHeader.size();j++) {
		        			if(checkFirstHeader.get(j).equals(data.get(j))==false) {
		        				//throw new WrongDataForm(key);
		        			}
		        		}
		        		
		        	}
		        	else if(count!=0&&num==1) {//헤더가 동일한지 체크하기
		        		data.set(0,temp);
		        		for(int j=0;j<checkSecondHeader.size();j++) {
		        			if(checkSecondHeader.get(j).equals(data.get(j))==false) {
		        				
		        				//throw new WrongDataForm(key);
		        			}
		        		}
		        		
		        	}
		        	else {
			        	dataList.addANodeToTail(data);
		        	}
			        	num++;
		        }
		        temp=key;
		        count++;
		    }
			 }catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }/*catch(WrongDataForm e) {
				 
				  e.makeErrorFile();
			 }*/
			
		}
		return dataList;
	}
}


