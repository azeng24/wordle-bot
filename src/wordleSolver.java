import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class wordleSolver {
  public static void main(String[] args) throws FileNotFoundException {
    
    //declare array for frequency
    double[] freqArr = new double[26];
    
    //declare ArrayList for words
    ArrayList<String> words = new ArrayList<>();
    
    //read in WordleList text file

    File myObj = new File("WordList.txt");
    Scanner readerWords = new Scanner(myObj);
    while(readerWords.hasNextLine()) {
      String w = readerWords.nextLine();
      words.add(w);  
    }
    readerWords.close();
      
    //read in frequency text file
    File myObj2 = new File("FreqByLetter.txt");
    Scanner readFreq = new Scanner(myObj2);
    int i = 0;
    while (readFreq.hasNextLine()) {
      freqArr[i] = Double.parseDouble(readFreq.nextLine());
      i++;
    }
    
  }
}

