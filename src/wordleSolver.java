import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class wordleSolver {
  //declare array for frequency
  static double[] freqArr = new double[26];
  //declare array for storing "green" letters
  static final char[] ANSWER = new char[5];
  //bool array that marks yellow letters
  static boolean[] isYellow = new boolean[26];
  //bool that is true only for the start word
  static boolean isStartWord = true;
  //char array of the current string in words
  static char[] word;
  //declare ArrayList for words
  static ArrayList<String> words = new ArrayList<>();
  public static void main(String[] args) throws FileNotFoundException {
    // Quick introduction to the program!
    System.out.println("Hello! Welcome to the Wordle Bot:)");
    System.out.println("Instructions are pretty simple: Follow along to help get suggestions " +
            "on different words you can use to try to get the \"Wordle\" word!");
    System.out.println("DISCLAIMER: this is NOT a \"Wordle\" solver. You are not guaranteed to get " +
            "the right word suggested in the limited 6 tries! \n");

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

    //declare input scanner
    Scanner scan = new Scanner(System.in);
    int counter = 0; //count for current iteration
    String gotIt; //string for tracking a win
    doSelectionSort(); //sorts the array of words based on probability
    String a = avoidRepeats(words); //suggests the first word as the first in the probability list without repeats
    System.out.printf("Your recommended first word is: %s \n", a);
    System.out.println("Did you get the wordle? (Enter \"yes\" for yes, enter anything else for no)");
    gotIt = scan.nextLine();
    //loop through until a win or loss
    while (!(gotIt.equalsIgnoreCase("yes")) && counter < 6) {
      counter++;
      if(isStartWord){
        word = a.toCharArray();
      } else {
        word = words.get(0).toCharArray();
      }
      isYellow = new boolean[26]; //resets the bool array
      for (int pos = 0; pos < 5; pos++) {
        System.out.printf("What color was tile #%d? (green, yellow, or grey) \n", pos+1);
        String color = scan.nextLine();
        System.out.println(word[pos]);
        removeWords(words, color, word[pos], pos); //removes words based on the color of the tile
        System.out.println(words);
      }
      isStartWord = false;
      System.out.printf("Your next recommended word is: %s \n", words.get(0)); //recommends the next word based on the highest probability
      System.out.println(words);
      System.out.println("Did you get the wordle? (Enter yes for yes, anything else for no)");
      gotIt = scan.nextLine();
    }
  }

// returns the first word in words without any repeated numbers
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

  //returns the sum of the percentage of occurrence of each letter in word
  public static double probability(String word) {
    double sum=0;
    int pos=0;

    for (int i=0; i<5 ; i++)
    {
      pos=word.toLowerCase().charAt(i) - 'a'; //use char math to compare to value in the frequency array

      sum+=freqArr[pos];
    }

    return sum;
  }

  // sorts the array (words) based on probability of the letters within the word. uses a selection sort
  //highest to lowest
  public static void doSelectionSort() {
    for (int i = 0; i < words.size(); i++) {
      int max = i;
      // find position of greatest probability between (i + 1)th element and last element
      for (int j = i + 1; j < words.size(); j++) {
        if (probability(words.get(j)) > probability(words.get(max)))
          max = j;
      }

      // Swap max (biggest num) to current position on array
      String swap = words.get(i);
      words.set(i, words.get(max));
      words.set(max, swap);
    }
  }

  //based on color tile, removes words from the arraylist "words"
  public static ArrayList<String> removeWords(ArrayList<String> words, String color, char letter, int pos) {
    //case for green
    if (color.equalsIgnoreCase("green")) {
      ANSWER[pos] = letter; //updates the answer array pos to the letter passed into the method
      //removes any words without the green letter
      for (int i = words.size() - 1; i >= 0; i--) {
        if (words.get(i).charAt(pos) != letter) {
          words.remove(i);
        }
      }
      //case for yellow
    } else if (color.equalsIgnoreCase("yellow")) {
      for (int i = words.size() - 1; i >= 0; i--) {
        char[] word = words.get(i).toLowerCase().toCharArray(); //converts the current word to a char array
        int counter = 0; //tracks if a word contains the yellow letter
        for (int j = 0; j < 5; j++) {
          if (j == pos) {
            if (word[j] == letter) { //removes words that have the letter in the same position as pos
              counter++;
              words.remove(i);
              break;
            }
          } else if (word[j] == letter) { //if counter is greater than 0, then the yellow letter is in the word and can stay in the running
            counter++;
          }
        }
        if (counter == 0) { //removes the word if it does not have the yellow letter
          words.remove(i);
        }
        isYellow[letter-'a'] = true; //updates isYellow array
      }

    } else if(!isYellow[letter-'a'] && color.equalsIgnoreCase("grey")){ //case for grey
      boolean isGreen=false; //makes sure that if there is a yellow or green duplicate of the grey letter, there is no removal
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
      if (words.get(0).equals("FALSE")) { //accounts for random word "false" in the words array
        words.remove(0);
      }
    }
    return words;
  }
}
