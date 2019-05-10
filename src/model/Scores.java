package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.scene.control.Label;

public class Scores {

	public static void fulfillScores(Label board, Label board2) {
		File text = new File("scores.txt");
		Scanner scnr = null;
		try {
			scnr = new Scanner(text);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
       	int number = 0;

       	String temp;
       	int score = 0;

       	board.setText("\n");
       	board2.setText("\n");
       	
       	while(number < 10) {
        	temp = scnr.next();
        	score = scnr.nextInt();
           	board.setText(board.getText() + (number+1) + ": \t" + temp + "\n");
        	board2.setText(board2.getText() + score + "\n");
  
   
        	
	        number++;
        }
       	scnr.close();
       	
	}
	
	
	static public int checkIfHigher(int newScore) {
			File text = new File("scores.txt");
			Scanner scnr = null;
			try {
				scnr = new Scanner(text);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
	       	int number = 0;
	       	
	       	String temp;
	       	int score = 0;
	       
	       	while(number < 10) {
	        	temp = scnr.next();
	        	score = scnr.nextInt();
		        if(newScore >= score) {
		        	scnr.close();
		        	return number;
		        }    
		        number++;
	        }
	       	scnr.close();
	       	return -1;
	}
	
	static public void addScore(int newScore, int pos, String newName) {
		File text = new File("scores.txt");
		Scanner scnr = null;
		try {
			scnr = new Scanner(text);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> names = new ArrayList<>();
       	List<Integer> scores = new ArrayList<>();
       	int number = 0;
       	
       	while(number < 10) {
       		if(pos == number) {
	        	names.add(newName);
	        	scores.add(newScore);
	        } 
        	names.add(scnr.next());
        	scores.add(scnr.nextInt());
	        
	        number++;
        }
       	number = 0;
       	scnr.close();
       	
        BufferedWriter writer;
     		try {
     			writer = new BufferedWriter(new FileWriter("scores.txt", false));
     			while(number < 10) {
     	        	writer.write(names.get(number) + " " + scores.get(number));
     	        	writer.newLine();
     		        number++;
     	        }
     			
          	    writer.close();
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
         	
       	
	}
}
