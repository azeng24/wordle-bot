public wordleSolver {
  public static void main(String[] args) {
    
    //declare array for frequency
    double freqArr = [];
    
    //declare ArrayList for words
    ArrayList words = new ArrayList();
    
    //read in WordleList text file
    File myObj = new File("WordList.txt");
    Scanner readerWords = new Scanner(myObj);
    while(readerWords.hasNextLine()) {
      String w = readerWords.nextLine();
      words.add(w);  
    }
    readerWords.close()
      
    //read in frequency text file
    File myObj2 = new File("
    
  }
}

