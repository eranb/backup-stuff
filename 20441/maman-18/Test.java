public class Test {
  
  public static void main(String[] args) {
//    System.out.println(16 / 3.0);
    int[] array = {1,2,2,5,6};
    System.out.println(counter(array, 2));
/*
    int[][] array = {
      {8 ,9 ,5 ,1 ,7 ,8 },
      {9 ,5 ,5 ,16,17,18},
      {10,11,6 ,15,2 ,19},
      {7 ,12,13,14,4 ,20},
      {9 ,13,4 ,15,22,21},
      {10,11,12,12,23,22}
    };
    System.out.println(maze(array,5, 5,-1));
*/
//  int[] myArray = {1,9,2,8,4,7,7,4,12};
//  crossSort(myArray);
  
//  int[] myArray2 = {1,4,3,3,5,2,7,1};
//  crossSort(myArray2);
  }
  
  public static void printArray(int[] myArray) {
    System.out.print("[" + myArray[0]);
    for (int i =1; i < myArray.length; i++)
      System.out.print("," + myArray[i]);
    System.out.print("]");
    System.out.println("");
  }

  public static void crossSort(int[] arr) {
    int firstIndex = 0;
    int secondIndex = arr.length - 1;
    int index = 0;
    
    if ((secondIndex % 2) == 0)
      secondIndex -= 1;
      
    while( (secondIndex > index ) && (index < (arr.length - 1)) ) {
      
      System.out.println("INDEX " + index + " FIRST " + firstIndex + " SECOND " + secondIndex);
      printArray(arr);
     if (arr[firstIndex] <= arr[index] || arr[secondIndex] <= arr[index] )
     {
       if (arr[firstIndex] < arr[secondIndex] || firstIndex >= secondIndex )
       {
         swap(arr,index,firstIndex);
         firstIndex +=2;
       } else {
         swap(arr,index,secondIndex);
         secondIndex -=2;
       }
     } 
     index +=1;
     printArray(arr);
     System.out.println();
    }
    System.out.println("END => INDEX " + index + " FIRST " + firstIndex + " SECOND " + secondIndex);
  }
  
  public static void swap(int[] arr, int x, int y) {
    int temp = arr[x];
    arr[x] = arr[y];
    arr[y] = temp;
  }
  
  public static boolean maze(int[][] array, int x, int y, int previous) {     
    if ( outOfBound(array, x, y))
      return false;    
    if ( (x == 0) && (y == 0) )
      return true; // finish the maze
    if (array[x][y] == -1)
      return false;
      
    System.out.println("############## IN (" + x +"," + y +") ##############");    
    if ( (previous != -1) && (previous - 1 != array[x][y]) )
      return false;
    

    int temp = array[x][y];    
    array[x][y] = -1;
    
    if ( maze(array,x + 1,y, temp) ||maze(array,x - 1,y, temp) ||maze(array,x,y + 1, temp) ||maze(array,x,y - 1, temp))
     return true;
    else {
     array[x][y] = temp; 
     return false;
    }
  }
  
  public static boolean outOfBound(int[][] array, int x, int y) {
    int maxIndex = array.length - 1;
    return x > maxIndex || x < 0 || y < 0 || y > maxIndex;
  }
  
  
  public static boolean hasRight(int[][] array, int x, int y) {
    return (x + 1) < array.length;
  }
  
  public static boolean hasLeft(int[][] array, int x, int y) {
    return (x - 1) >= 0;
  }

  public static boolean hasUp(int[][] array, int x, int y) {
    return (y + 1) < array.length;
  }
  
  public static boolean hasDown(int[][] array, int x, int y) {
    return (y - 1) >= 0;
  }
  
  
  public static int counter(int[] array, int target) {
    int low  = 0;
    int high = array.length - 1;
    int mid  = (low + high) / 2;
    int beginIndex = -1;
    int endIndex = -1;
    
    while (! (low == mid && high == mid) ) {
      if ( array[mid] < target )
        low = mid + 1;
      else
        high = mid;
              
      mid = (low + high) / 2;
    }
    
    if (array[mid] == target)
      beginIndex = mid;
    
    if (beginIndex == -1)
      return -1;
    
    low  = beginIndex;
    high = array.length - 1;
    mid  = (low + high) / 2;
    
    while (! (low == mid && ( high == mid || (high -1) == mid ) ) ) {
      if (array[mid] > target)
        high = mid -1;
      else
        low = mid;
      mid = (low + high) / 2;
    }
    
    if (array[high] == target)
      endIndex = high;
    else
      endIndex = mid;
    
    return ( endIndex - beginIndex ) + 1;
  }
  
  //  int[] array = {5,4};
  //  System.out.println(isSum(array, 14, 0, 0));
  public static boolean isSum(int[] array,int target, int sum, int index) {
    if ( sum == target) {
      return true;
    }
    else if ( sum > target || index > array.length - 1 )
      return false;

    if ( isSum(array, target, sum + array[index], index) || isSum(array, target, sum + array[index], index + 1)) {
      return true;
    } else {
      return false;
    }
      
    
    
  }
  
  
  public static boolean splitEqualSum(int[] array, int sum1, int sum2, int index) {
    if ( index > array.length - 1 )
      return sum1 == sum2;
      
    return splitEqualSum(array, sum1 + array[index], sum2, index + 1) ||
           splitEqualSum(array, sum1, sum2 + array[index], index + 1);
  }
  
  public static int findBC(String s) {
    int i = 0;
    while (s.charAt(i) == 'a')
      i = (i + 1) * 2;
    
    return i;
  }
  
  public static int firstB(String s) {
    int lower = 0;
    int upper = findBC(s);
    int middle = (upper + lower) / 2;
    
    while (!(lower == middle && upper == middle)) {
      if ( s.charAt(middle) == 'c' || s.charAt(middle) == 'b' ) {
        upper = middle;
      } else {
        lower = middle + 1;
      }
      middle = (upper + lower) / 2;
    }
    
    if (s.charAt(middle) != 'b')
      return -1;
    else
      return middle;
  }
  
}
