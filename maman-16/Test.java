public class Test {
  public static void main(String[] args) {
    int[] myArray = {90,3,11,13,4,12};
    Test.sortByFour(myArray);
    
    for (int i=0 ; i < myArray.length; i++) {
      //System.out.println(myArray[i]);
    }
    
    //System.out.println(subStrMaxC("abc",'c',2));
   //char[] array = {'e', 'r','e', 'q', 'e', 'e'} ;
   //char[] sub   = {'e','q', 'e'};
   //System.out.println(findSequence(array,sub));
   boolean[] a = {true,true,true,false,false,true};
   boolean[] b = {false,false,true,false,false,true};
   boolean[] c = {true, false, true, false, true, false};
   boolean[] d = {false, true, false, true, false, true};
   boolean[] e = {true, false, true, false, true };
   boolean[] f = { false, false, true, false, true };
   System.out.println(disco(c,d));
   System.out.println(disco(a,b));
   System.out.println(disco(e,f));
  }
  
  private static boolean compareArrays(boolean[] init,boolean[] target,int index) {
    if ((init[index] != target[index]) || (index == init.length))
      return false;
    
    if ((index == init.length - 1) && (init[index] == target[index]))
      return true;
    
    return compareArrays(init,target,index + 1);
  }
  
  
  public static boolean disco(boolean[] init, boolean[] target)
  {
    if (compareArrays(init, target,0))
      return true;
    else 
      return discoRecursively(init, target, 0);
  }

  private static boolean discoRecursively(boolean[] init, boolean[] target, int index)
  {
    if (index == init.length)
      return false;
    
    discoRecursively(init, target, index + 1);

    if (init[index] != target[index])
      //switchBulb(init, target, index);
      turnOn(init,target,index);

    return compareArrays(init, target,0);
  }
  
  private static void turnOn(boolean[] init,boolean[] target, int index) {
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
  
  private static void turnOn(boolean[] init,int index) {
    init[index] = !init[index];
  }
  
  private static void switchBulb(boolean[] init, boolean[] target, int i)
  {
      if ( (i > 1) && (init[i-1] != target[i-1]) && (init[i-2] != target[i-2]) ) {
          // If there's a series of 3 opposite-to-target bulbs, switch the middle one & from both sides
          init[i-1] = !init[i-1];
          init[i] = !init[i];
          init[i-2] = !init[i-2];
      } else if (i > 0 && i < init.length-1) {
          // There's only 1 bulb or 2 adjacent bulbs that are opposite of target.
          if (i == 1 && (init[0] != target[0]) ) {
              // First 2 bulbs are opposite of target's, so switch the 1st one.
              init[i-1] = !init[i-1];
              init[i] = !init[i];
          } else if ( (i == init.length-2) && (init[i+1] != target[i+1]) ) {
              init[i+1] = !init[i+1];
              init[i] = !init[i];
          } else {
              init[i] = !init[i];
              init[i-1] = !init[i-1];
              init[i+1] = !init[i+1];
          }
      } else {
          // First bulb or last bulb.
          init[i] = !init[i];
          if (i == 0) {
              init[i+1] = !init[i+1];
          } else {
              init[i-1] = !init[i-1];
          }
      }
  }
  
  public static boolean findSequence(char [] findIn, char [] toFind) {
    return findSequenceRecursively(findIn,0,toFind,0);
  }
  
  private static boolean findSequenceRecursively(char [] array,int arrayIndex, char [] sub, int subIndex) {
    if (subIndex == sub.length)
      return true;
    else if (arrayIndex == array.length - 1)
      return false;
    
    if (array[arrayIndex] == sub[subIndex])
      subIndex +=1;
    else
      subIndex = 0;
      
    return findSequenceRecursively(array,++arrayIndex,sub,subIndex);
  }
  
  
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

  private static void swap(int x, int y, int[] arr) {
    int tmp = arr[x];
    arr[x] = arr[y];
    arr[y] = tmp;
  }
  
    
  // O(n)
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
  
  public static int subStrMaxC(String s, char c, int k)  {
    if (k < 0)
      return 0;
    
    int counter = 0;
        
    for(int i = 0; i < s.length(); i++)
      if ( s.charAt(i) == c )
        counter++;
    
    if ( counter < k + 2 )
      counter  = 0;
    else
      counter -= k + 1;

    return counter + subStrMaxC(s,c,k - 1);
  }
  
  public static int subStrCBadImpl(String s, char c) {
    int counter = 0;
    
    int currentIndex;
    int cCounter;
    
    for(int i = 0; i < s.length(); i++) {
      if ( s.charAt(i) == c ) {
        currentIndex = i;
        cCounter = 1;
        for(int z=i + 1;z < s.length(); z++) {
          if (s.charAt(z) == c && ++cCounter == 3) {
            counter++;
          }
        }
      }
    }
    return counter;
  }
}

/*
  a = 0, b = 0 , c = 0
  [*c, b, a, d, d, b, b, b, d, c] do
    swap(0,0)
    c ++
  end
  
  a = 0, b = 0, c = 1
  [c, *b, a, d, d, b, b, b, d, c] do
    swap(1,0)
    c ++
  end
  
  
*/

