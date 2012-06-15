/**
 * Class that represents Maman16
 * 
 * @author Barak Levi
 * @version 1
 */

public class Ex16 {
  
  /**
  * Sort an array of integers by their modulo 4
  * @param arr The new artist
  */  
  public static void sortByFour(int[] arr) {
    int a = 0;
    int b = 0;
    int c = 0;

    for(int i = 0; i < arr.length; i++) {
      switch (arr[i] % 4) {
        case 0: swap(i,a++,arr);
        case 1: swap(i,b++,arr);
        case 2: swap(i,c++,arr);
      }
    }
  }
  
  // helper function that swap two index in array
  private static void swap(int x, int y, int[] arr) {
    int tmp = arr[x];
    arr[x] = arr[y];
    arr[y] = tmp;
  }
  
  /**
  * Finds how much sub strings included inside a given string that starts and ends with
  * a given char and also contain only one of the given char between.
  * @param s The string to look the (char,char,char) pattern.
  * @param c The input char to look by
  * @return the numner of sub strings found
  */
  public static int subStrC(String s, char c) {
    int counter = 0;
    for(int i = 0; i < s.length(); i++)
      if ( s.charAt(i) == c )
        counter++;
    
    if ( counter < 3 )
      counter  = 0;
    else
      counter -= 2;

    return counter;
  }
  
  /**
  * Find a how much a sequence of a given char in a the pattern of (c,{c * k},c) included
  * in the given string
  * @param s The string to look the (char,char,char) pattern.
  * @param c The input char to look by
  * @param k The amount of chars inside the pattern
  * @return the numner of sub strings found
  */
  public static int subStrMaxC(String s, char c, int k)  {
    int counter = 0;
        
    for(int i = 0; i < s.length(); i++)
      if ( s.charAt(i) == c )
        counter++;
    return subStrMaxCRecursively(s,c,k,counter);
  }
  
  // recursive helper function that calc number of instances of our pattern given the 'c' sub
  private static int subStrMaxCRecursively(String s, char c, int k, int counter) {
    if (k < 0) return 0; // our exit strategy
    if ( counter < k + 2 )
      counter  = 0;
    else
      counter -= k + 1;

    return counter + subStrMaxCRecursively(s,c,k - 1,counter);
    
  }
  
  /**
  * Find if a given array of chars included inside a second array of chars
  * @param findIn The array of chars to look in
  * @param toFind The array of chars to look for
  * @return boolean included or not
  */
  public static boolean findSequence(char [] findIn, char [] toFind) {
    return findSequenceRecursively(findIn,0,toFind,0);
  }
  
  // recursive helper function that find out if the sub array included
  private static boolean findSequenceRecursively(char [] array,int arrayIndex, char [] sub, int subIndex) {
    // stop if we got to the end
    if (subIndex == sub.length)
      return true;
    else if (arrayIndex == array.length - 1)
      return false;
    
    // matching both arrays
    if (array[arrayIndex] == sub[subIndex])
      subIndex +=1;
    else
      subIndex = 0;
      
    return findSequenceRecursively(array,++arrayIndex,sub,subIndex);
  }
  
  /**
  * Moshe-Lighting inc custom light bulb disco switch activator
  * help Moshe-Lighing inc employees with the setup of a new lighting bulbs disco sequence
  * Tell of a possible, or not, to program a given set of bulbs to an expected set of lights
  * @param init The current set of light bulbs.
  * @param target The expected set of light bulbs.
  * @return possible or not.
  **/
  public static boolean disco(boolean[] init, boolean[] target)
  {
    if (compareArrays(init, target,0))
      return true;
    else 
      return discoRecursively(init, target, 0);
  }

  // recursive helper function that helps the user to the discover if a given set of Moshe-Lighing
  // set of bulbs can be program to a given set of bulbs.
  private static boolean discoRecursively(boolean[] init, boolean[] target, int index)
  {
    if (index == init.length) // exit if we got to the end
      return false;
    
    discoRecursively(init, target, index + 1);

    if (init[index] != target[index]) // trying to find the right combination
      turnBulbs(init,target,index);

    return compareArrays(init, target,0); 
  }
  
  // recursive helper function that compare two arrays with the same size returns true if matched
  private static boolean compareArrays(boolean[] init,boolean[] target,int index) {
    if ((init[index] != target[index]) || (index == init.length))
      return false;
    
    if ((index == init.length - 1) && (init[index] == target[index]))
      return true;
    
    return compareArrays(init,target,index + 1);
  }
  
  // helper function that know which sequence of lights to turn on/off by the current index
  private static void turnBulbs(boolean[] init,boolean[] target, int index) {
    // turning on the current index
    turnOn(init,index);
    
    // case we got two opposites before of us
    if ( (index > 1) && (init[index - 1] != target[ index- 1]) && (init[index - 2] != target[index - 2]) ) {
      turnOn(init,index - 1);
      turnOn(init,index - 2);
    // one || two bulbs near of us that are opposite to us
    } else if (index > 0 && index < init.length - 1) {  
      // first two are the opposites
      if (index == 1 && (init[0] != target[0]) ) {
        turnOn(init,index - 1);
      } else if ( (index == init.length - 2) && (init[index + 1] != target[index + 1]) ) {
        turnOn(init,index + 1);
      } else {
        turnOn(init,index - 1);
        turnOn(init,index + 1);
      }
    } else { // first or last bulb
      if (index == 0) 
        turnOn(init,index + 1);
      else 
        turnOn(init,index - 1);
    }
  }
  
  // helper function that change cell to his opposite
  private static void turnOn(boolean[] init,int index) {
    init[index] = !init[index];
  }
  
  
}