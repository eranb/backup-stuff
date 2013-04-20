/**
 * This class represents a two dimensional Matrix
 * @author Barak Levi
 * @version 1
 */

public class Matrix {
  int[][] _matrix;
  private final int MAX_INT = 255; // max int for each cell
    
  /**
  * Constructs a Matrix from a two-dimensional array; the dimensions as well as the values of this Matrix will be the same as the dimensions and values of the two-dimensional array.
  * @param array A two dimensional array. It is the source of the initial values for Matrix
  */
  public Matrix(int[][] array) {
    _matrix = new int[array.length][array[0].length]; // initializing the matrix
    
    // copying cells
    for (int x = 0; x < array.length; x++)
      for (int y = 0; y < array[0].length; y++)
        _matrix[x][y] = array[x][y];

  }

  /**
  * Constructs a size1 by size2 Matrix of zeroes.
  * @param size1 Number of rows in the matrix.
  * @param size2 Number of columns in the matrix.
  */
  public Matrix(int size1, int size2) {
    _matrix = new int[size1][size2];
  }
  
  /**
  * Returns a string representation of this Matrix. All numbers appearing in the same row are separated by the tab key; all rows are separated by the '\n' char.
  * @returns A string representation of this Matrix.
  */
  public String toString() {
    String str = "";

    // looping through the matrix ( until max-index - 1 )
    for (int x = 0; x < size(); x++) {
      for (int y = 0; y < innerSize() - 1; y++) 
        str += _matrix[x][y] + "\t";

      //last one
      str += _matrix[x][innerSize() - 1] + "\n";
    }
    
    return str;
  }
  
  /**
  * Calculates and returns a negative copy of this Matrix. All pixels are represented by a number 0-255 inclusive.
  * @return A negative copy of this Matrix.
  */
  public Matrix makeNegative() {
    int[][] myArray = new int[size()][innerSize()];
    
    // fill the array with negative values
    for (int x = 0; x < size(); x++)
      for (int y = 0; y < innerSize(); y++)
        myArray[x][y] = negativeOf( _matrix[x][y] );
    
    return new Matrix(myArray);
  }
  
  /**
  * Calculates and returns a copy of this Matrix after it has been filtered from noise. All pixels are represented by a number 0-255 inclusive.
  * @return A copy of this Matrix after it has been filtered from noise
  */
  public Matrix imageFilterAverage() {
    int[][] myArray = new int[size()][innerSize()];
    
    // calc the neighbors average for each (x,y)
    for (int x = 0; x < size(); x++)
      for (int y = 0; y < innerSize(); y++)
        myArray[x][y] = averageOf( neighborsOf(x,y) );
    
    return new Matrix(myArray);
  }
  
  /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * /
  /                       Private Area                           /
  / * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
  
  // returns the outer parameter length
  private int size() {
    return _matrix.length;
  }
  
  // returns the inner parameter length
  private int innerSize() {
    return _matrix[0].length;
  }
  
  // returns the negative value of a cell
  private int negativeOf(int c) {
    return MAX_INT - c;
  }
  
  /* Fetch the neighbros of a given (x,y)
  * returns array of neighbors for a given index using the 'Moore-Neighborhood' algo
  * based on http://en.wikipedia.org/wiki/Moore_neighborhood
  * actual formula described @ http://www.wolframalpha.com/entities/mathworld/moore_neighborhood/so/hp/b1/
  */
  private int[] neighborsOf(int xIndex, int yIndex) {
    int[] tmpArray = new int[9];
    int curIndex = 0;
    
    for (int x = 0; x < size(); x++)                                // getting the Xs`
      for (int y = 0; y < innerSize(); y++)                         // getting the Ys`
        if (Math.abs(xIndex - x) <= 1 && 1 >= Math.abs(yIndex - y)) // checking distance for each (x,y)
          tmpArray[curIndex++] = _matrix[x][y];
            
    // building results
    int[] myArray = new int[curIndex];
    for (int i = 0; i < curIndex; i++)
      myArray[i] = tmpArray[i];
      

    return myArray;
  }
  
  // loops through array of integers and summs them
  private int sum(int[] array) {
    int sum = 0;
    
    for (int i = 0; i < array.length; i++)
      sum += array[i];
      
    return sum;  
  }
  
  // returns the average value of a given integers array
  private int averageOf(int[] array) {
    return sum(array) / array.length;
  }
  
}