# wordle-bot
<h1> Welcome to the Wordle Bot! </h1>

<p> Here, we created a program that helps the user solve the daily wordle. </p>
<p> The program first starts by gently informing the user how it works and while it does not provide the actual answers, it will make suggestions based off of researched letter frequencies in 5 letter words and the results that they (as the user) input. </p>

<p> The program starts by storing all the 2,300 wordle solutions in an array list. It then calls a sorting method which will sort all the solutions with words that have the most amount of letter frequencies at the top. Then, the array list is run through another method which checks to see if the word at the top has any repeats (ex. apple: p is repeated twice). If the top word does have a repeated letter, it goes to the second word and checks that. Once the method finds a word that doesn't have any repeated words, it will return ONLY that word. This word is then suggested to the user as their first word. </p>

<p> The user then has the ability to input the suggested word into their wordle. They then feed their results back into the program. </p>

<p> The program then enters all this data into another method that is based around the color that the word recieved when submitting. </p>

<h5> If the letter recieved grey... </h5>
<p> The program then removes any words that have that letter in them at all from the array list </p>

<h5> If the letter recieved green... </h5>
<p> The program then removes any words that don't have that letter in them at that very position from the array list. </p>

<h5> If the letter recieved green... </h5>
<p> The program then removes any words that don't have that letter in them at that very position from the array list. </p>

<h5> If the letter recieved yellow... </h5>
<p> The program then removes any words that have that letter in them at that very position from the array list and makes sure that next word suggested has that same letter in a different position. </p>

<h5> In case of duplicated/repeated letters... </h5>
<p> If the user gets a result that implies that there are 2 of the same letter in the word, they will inform the program by inputting it at the appropriate time and the method will accommodate for that.  </p>
