public class Heap {
  int[] _heap;
  int _size;
  
  public static void main( String args[] ) {
    int[] array = {1,2,3,4,7,8,9,10,14,16};
    //int[] array = {16,14,10,9,8,7,4,3,2,1};
    Heap heap = new Heap(array);
  }
  
  public Heap(int[] array) {
    this._heap = array;
    this._size = array.length;
    
    for (int i = size() / 2; i > -1; i--)
      maxHeapify(i);
    
    p(_heap);
    p(findInHeap(_heap,8,0));
  }
    
  public Integer findInHeap(int[] A, int z, int i) {
    if ( i >= A.length )
      return null;
    if ( A[i] == z )
      return i;
    if ( A[i] < z )
      return null;

    Integer left  = findInHeap(A,z, left(i));
    Integer right = findInHeap(A,z, right(i));

    if ( left != null )
      return left;
    else
      return right;
    
  }
  
  public void maxHeapify( int i ) {
    int largest = i;
    int l = left(i);
    int r = right(i);
    if ( l < size() && _heap[l] > _heap[largest])
      largest = l;
    if ( r < size() && _heap[r] > _heap[largest])
      largest = r;

    if ( largest != i ) {
      exch(i,largest);
      maxHeapify(largest);
    }
  }
  
  public void exch(int x, int y) {
    int temp = _heap[x];
    _heap[x] = _heap[y];
    _heap[y] = temp;
  }
  
  public int size() {
    return this._size;
  }
  public int left(int i) {
    if ( i == 0 )
      return 2;
    else
      return 2 * i;
  }
  
  public int right(int i) {
    return left(i) + 1;
  }
  
  public int parent( int i ) {
    return i/2;
  }
  
  public static void p(int[] a) {
     String out = "[ ";
     for (int i = 0; i < a.length ; i++) out = out + a[i] + " ";
     out = out + "]";
     p(out);
   }

   public static void p(Object o) {
     System.out.println(o);
   }
}