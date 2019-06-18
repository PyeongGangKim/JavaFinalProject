package edu.handong.csee;

public class Main {
	public static void main(String[] args) {
		JavaFinalProject projector1 = new JavaFinalProject(1,args);
		JavaFinalProject projector2 = new JavaFinalProject(2,args);
		projector1.start();
		projector2.start();
	}
}
