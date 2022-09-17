import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class wordleSolver {
  //declare array for frequency
  static double[] freqArr = new double[26];

  //declare ArrayList for words
  static ArrayList<String> words = new ArrayList<>();
  public static void main(String[] args) throws FileNotFoundException {

    
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
 doSelectionSort();
    //System.out.println(probability(words.get(0)));
    //for (int h=0; h<5; h++)
    //{
     // System.out.println(words.get(h));
    //}
  }

  static String avoidRepeats(ArrayList<String> words) {
    boolean repeat = true;
    int index = 0;
    while (repeat){
      char[] word = words.get(index).toCharArray();
      int counter = 0;
      for (int i = 0; i < 5; i++) {
        for (int j = i + 1; j < 5; j++) {
          if (word[i] == word[j]) {
            index++;
            counter++;
          }
        }
      }
      if (counter == 0) {
        repeat = false;
      }
    }
    return words.get(index);
  }
  static double probability (String word)
  {
    double sum=0;
    int pos=0;

    for (int i=0; i<5 ; i++)
    {
      pos=word.toLowerCase().charAt(i) - 'a';

      sum+=freqArr[pos];
    }

    return sum;
  }

  // Create doSelectionSort method
  public static void doSelectionSort() {
    for (int i = 0; i < words.size(); i++) {
      int max = i;
      // find position of greatest probability between (i + 1)th element and last element
      for (int j = i + 1; j < words.size(); j++) {
        if (probability(words.get(j)) > probability(words.get(max)))
          max = j;}

      // Swap min (smallest num) to current position on array
      String swap = words.get(i);
      words.set(i, words.get(max));
      words.set(max, swap);
    }
  }

}

