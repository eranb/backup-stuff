public class ArraySearches {
  public static void main(String[] args) {
    int[] myArray = {5,9,1,3,10,22,12,11,91};
    System.out.println(ArraySearches.simpleSearch(myArray, 22));
  }

  public static int simpleSearch(int[] array, int num) { 
  // Takes unsorted array with complexity O(n)
    for (int i = 0; i < array.length; i++)
      if (array[i] == num)
        return i; 
    return -1;
  }

  public static int linearSearch(int[] array, int num) { 
  // Takes sorted array with complexity O(n)
    int pos = 0;
    while ( (array[pos]<num) && (pos<array.length - 1) ) 
      pos++;

    if (array[pos] == num)
      return pos;
    else 
      return -1;
  }

  public static int binarySearch(int[] array, int num) {
    // Takes sorted array with complexity O(log n)
    int middle;
    int lower = 0;
    int upper = array.length - 1;

    do {
      middle = (lower + upper) / 2;
      if (num < array[middle])
        upper = middle - 1;
      else
        lower = middle + 1;
    } while(array[middle] != num && lower <= upper);

    if ( array[middle] == num )
      return middle;
    else 
      return -1;
  }
}
