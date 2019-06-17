package edu.handong.csee;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.cli.*;
public class JavaFinalProject extends Thread {
	private int selectNumber=0;
	private String dataPath;
	private String resultPath;
	private boolean help;
	private String[] args;
	private LinkedList<ArrayList<String>> summaryList;
	private LinkedList<ArrayList<String>> imageList;
	ArrayList<ArrayList<String>> SumaarydataCollect;
	ArrayList<ArrayList<String>> ImagedataCollect;
	HashMap<String,InputStream> summary;
	HashMap<String,InputStream> image;
	public static void main(String[] args) {
		JavaFinalProject projector1 = new JavaFinalProject(1,args);
		JavaFinalProject projector2 = new JavaFinalProject(2,args);
		projector1.start();
		projector2.start();
	}

	public void run() {
	 Options options = new Options();
	 createOptions(options);
	 if(parseOptions(options, args)) {
			if (help){
				printHelp(options);
				return;
			}
	
	 ZipFileUtils zipFile=new ZipFileUtils();
	 zipFile.readFileInZip(dataPath);
	 int num1=resultPath.lastIndexOf(".");
	 String extension=resultPath.substring(num1);
	 String result= resultPath.substring(0,num1);
	 if(selectNumber==1) {
	 summary=zipFile.getCollectTheInputStreamAboutSummary();
	 summaryList=Utils.getSummaryData(summary);
	 SumaarydataCollect=summaryList.toArrayList();
	 String result2=result+2+extension;
	 Utils.writeAFile(SumaarydataCollect, result2);
	 }else {
	 image=zipFile.getCollectTheInputStreamAboutImage();
	 imageList=Utils.getImageData(image);
	 ImagedataCollect=imageList.toArrayList();
	 String result1=result+1+extension;
	 Utils.writeAFile(ImagedataCollect, result1);
	 }
	 }
}
 private void createOptions(Options options) {

		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Input path")
				.required()
				.build());
		
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("Output path")
				.required()
				.build());
	
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				//.hasArg()
				.argName("Help")
				//.required()
				.build());
	}
 public JavaFinalProject(int num,String[] arg) {
	 selectNumber=num;
	 args=arg;
 }
 private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			dataPath = cmd.getOptionValue("i");
			resultPath = cmd.getOptionValue("o");
			help = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
		
	}
private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "DataCollector";
		String footer ="";
		formatter.printHelp("DataCollector", header, options, footer, true);
		
	}


}
