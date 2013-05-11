public class Heap {
  private HeapObject[] _heap;
  private int size;
  
  private class HeapObject {
    int key;
    Object payload;

    public HeapObject(int key, Object payload) {
      this.key = key;
      this.payload = payload;
    }
  }
    
  public Heap( int maxSize ) {
    this._heap = new HeapObject[maxSize];
    this.size = 0;
  }
  
  public void insert( int key, Object obj ) {
    increaseKey(this.size++,new HeapObject(key,obj));
  }
  
  public Object extractMax() {
    if (isEmpty()) return null;
    HeapObject max = _heap[0];
    _heap[0] = _heap[--this.size];
    heapify(0);
    return max.payload;
  }
  
  public boolean isEmpty() {
    return (size == 0);
  }
  
  // Private Area
  private void increaseKey(int index, HeapObject obj) {
    _heap[index] = obj;
    while ( index > 0 && _heap[parent(index)].key < obj.key) {
      exchange(index, parent(index));
      index = parent(index);
    }
  }
  
  private void heapify( int i ) {
    int largest = i;
    int l = left(i);
    int r = right(i);
    if ( l < this.size && _heap[l].key > _heap[largest].key)
      largest = l;
    if ( r < this.size && _heap[r].key > _heap[largest].key)
      largest = r;

    if ( largest != i ) {
      exchange(i,largest);
      heapify(largest);
    }
  }
  
  private int left( int i) {
    return ( i == 0 ) ? 1 : 2*i;
  }

  private int right( int i ) {
    return left(i) + 1;
  }

  private int parent( int i ) {
    return i/2;
  }
  
  private void exchange(int x, int y) {
    HeapObject temp = _heap[x];
    _heap[x] = _heap[y];
    _heap[y] = temp;
  }

}