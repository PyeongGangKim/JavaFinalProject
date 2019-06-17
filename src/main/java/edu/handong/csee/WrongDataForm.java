package edu.handong.csee;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WrongDataForm extends Exception {
 private String key;
		public WrongDataForm(){
			super("Some file have wrong data form.");
		}
		public WrongDataForm(String key) {
			super("Some file have wrong data form.");
			this.key=key;
		}
		public void makeErrorFile(){
			File error =new File("error.csv");
			try {
				FileWriter fw = new FileWriter(error, true);
	            fw.write(key);
	            fw.close();
			}catch(IOException e){
				e.printStackTrace();
				}
		}


}
