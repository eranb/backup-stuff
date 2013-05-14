/**
 * The "Heap" class partially implements the Max-Heap data structure
 * as widely described @ http://en.wikipedia.org/wiki/Binary_heap
 * @version 1
 */
public class Heap {
  private HeapObject[] _heap; // array that represent the heap
  private int size;           // heap size
  
  // struct for representing heap objects ( internal use )
  private class HeapObject {
    int priority;
    Object payload;

    public HeapObject(int priority, Object payload) {
      this.priority = priority;
      this.payload = payload;
    }
  }

  /**
  * Create new Max Heap.
  */
  public Heap( int maxSize ) {
    this._heap = new HeapObject[maxSize];
    this.size = 0;
  }
    
  /**
  * Add object to the heap.
  * Complexity: O(lg(heap-size)) - increaseKey function call
  * @param priority object priority in the heap.
  * @param object the actual object to insert to the heap.
  */
  public void insert( int priority, Object object ) {
    increaseKey(this.size++,new HeapObject(priority,object));
  }
  
  /**
  * Extract the object with the highest priority form the heap.
  * @return Object with the highest priority.
  * Complexity: O(lg(heap-size)) - heapify function call
  */
  public Object extractMax() {
    if (isEmpty()) return null;
    HeapObject max = _heap[0];
    _heap[0] = _heap[--this.size];
    heapify(0);
    return max.payload;
  }
  
  /**
  * Check if the heap is empty or not.
  * Complexity: O(1)
  * @return True if the heap has no elements.
  */
  public boolean isEmpty() {
    return (size == 0);
  }
  

  /* * * * * * * * * * * * * * * * /
  /       Private Functions        /
  / * * * * * * * * * * * * * * * */

  /*
  * Add the element to the bottom level of the heap then
  * fix the heap by cascading-up the largest value
  * complexity: O(lg(heap-size))
  */ 
  private void increaseKey(int index, HeapObject obj) {
    _heap[index] = obj;
    while ( index > 0 && _heap[parent(index)].priority < obj.priority) {
      exchange(index, parent(index));
      index = parent(index);
    }
  }
  
  /* 
  * recursive funcation that starts from index x and then
  * checks whois child with the largest priority.
  * if the largest child is highesr than the priority of the
  * current node it will make the necessary adjustments
  * complexity: O(lg(heap-size))
  */ 
  private void heapify( int i ) {
    int largest = i;
    int l = left(i);
    int r = right(i);
    if ( l < this.size && _heap[l].priority > _heap[largest].priority)
      largest = l;
    if ( r < this.size && _heap[r].priority > _heap[largest].priority)
      largest = r;

    if ( largest != i ) {
      exchange(i,largest);
      heapify(largest);
    }
  }
  
  // return the left child of a given index
  private int left( int i) {
    return ( i == 0 ) ? 1 : 2*i;
  }
  
  // return the right child of a given index
  private int right( int i ) {
    return left(i) + 1;
  }

  // return the parent of a given index
  private int parent( int i ) {
    return i/2;
  }
  
  // exchange two objects in the heap array
  private void exchange(int x, int y) {
    HeapObject temp = _heap[x];
    _heap[x] = _heap[y];
    _heap[y] = temp;
  }

}