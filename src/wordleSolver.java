import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class wordleSolver {
  //declare array for frequency
  static double[] freqArr = new double[26];
  static final char[] ANSWER = new char[5];
  static boolean[] isYellow = new boolean[26];
  static boolean isStartWord = true;
  static char[] word;

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

    Scanner scan = new Scanner(System.in);
    int counter = 0;
    String gotIt;
    doSelectionSort();
    String a = avoidRepeats(words);
    System.out.printf("Your recommended first word is %s \n", a);
    System.out.println("Did you get the wordle? (Enter yes for yes, anything else for no)");
    gotIt = scan.nextLine();
    while (!(gotIt.equalsIgnoreCase("yes")) && counter < 6) {
      counter++;
      if(isStartWord){
        word = a.toCharArray();
      } else {
        word = words.get(0).toCharArray();
      }
      isYellow = new boolean[26];
      for (int pos = 0; pos < 5; pos++) {
        System.out.println("What color was the tile? (green, yellow, grey)");
        String color = scan.nextLine();
        System.out.println(word[pos]);
        removeWords(words, color, word[pos], pos);
        System.out.println(words);
      }
      isStartWord = false;
      System.out.printf("Your next recommended word is: %s \n", words.get(0));
      System.out.println(words);
      System.out.println("Did you get the wordle? (Enter yes for yes, anything else for no)");
      gotIt = scan.nextLine();
    }
  }

  public static String avoidRepeats(ArrayList<String> words) {
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

  public static double probability(String word) {
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
          max = j;
      }

      // Swap min (smallest num) to current position on array
      String swap = words.get(i);
      words.set(i, words.get(max));
      words.set(max, swap);
    }
  }

  public static ArrayList<String> removeWords(ArrayList<String> words, String color, char letter, int pos) {
    if (color.equalsIgnoreCase("green")) {
      ANSWER[pos] = letter;
      for (int i = words.size() - 1; i >= 0; i--) {
        if (words.get(i).charAt(pos) != letter) {
          words.remove(i);
        }
      }
    } else if (color.equalsIgnoreCase("yellow")) {
      for (int i = words.size() - 1; i >= 0; i--) {
        char[] word = words.get(i).toLowerCase().toCharArray();
        int counter = 0;
        for (int j = 0; j < 5; j++) {
          if (j == pos) {
            if (word[j] == letter) {
              counter++;
              words.remove(i);
              break;
            }
          } else if (word[j] == letter) {
            counter++;
          }
        }
        if (counter == 0) {
          words.remove(i);
        }
        isYellow[letter-'a'] = true;
      }

    } else if(!isYellow[letter-'a'] && color.equalsIgnoreCase("grey")){ //added isGreen -> looks for greens before removing gray
        boolean isGreen=false;
        for (int i = 0; i < 5; i++) {
            if (word[i] == word[pos] && i != pos && !isStartWord) {
              isGreen = true;
              for (int j = words.size() - 1; j >= 0; j--) {
                if (words.get(j).charAt(pos) == letter) {
                  words.remove(j);
                }
              }
              break;
            }
        }

        if(!isGreen) {
          for (int i = words.size() - 1; i >= 0; i--) {
            char[] word = words.get(i).toCharArray();
            for(int j = 0; j < 5; j++) {
              if (word[j] == letter) {
                words.remove(i);
                break;
              }
            }
          }
        }
        if (words.get(0).equals("FALSE")) {
          words.remove(0);
        }
      }
    return words;
  }
}
