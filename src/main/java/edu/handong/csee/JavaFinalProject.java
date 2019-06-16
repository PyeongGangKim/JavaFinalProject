package edu.handong.csee;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.cli.*;
public class JavaFinalProject {
	private String dataPath;
	private String resultPath;
	private boolean help;
	private LinkedList<ArrayList<String>> dataList;
	ArrayList<ArrayList<String>> dataCollect;
	HashMap<String,ArrayList<InputStream>> inputStreamCollector;
 public void run(String[] args) {
	 Options options = new Options();
	 createOptions(options);
	 if(parseOptions(options, args)) {
			if (help){
				printHelp(options);
				return;
			}
	 inputStreamCollector=ZipFileUtils.readFileInZip(dataPath);
	 dataList=Utils.getData(inputStreamCollector);
	 dataCollect=dataList.toArrayList();
	 Utils.writeAFile(dataCollect, resultPath);
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
