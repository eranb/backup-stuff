public class IntList {
  private IntNode _head;
  
  public static void main(String[] args) {
    IntList myList = new IntList();
    myList.add(10);
    myList.add(12);
    myList.add(13);
    myList.add(14);
    myList.add(15);
    System.out.println( myList.length( ) );
  }
  
  public int length() {
    return length(_head);
  }
  
  private int length(IntNode head) {
    if (head == null)
      return 0;
    else
      return 1 + length(head.getNext());
  }
  
  public IntList() {
    _head = null;
  }

  public IntList(IntNode node) { 
    _head = node;
  }

  public boolean isEmpty() {
    return _head == null;
  }

  public void add(int value) {
    IntNode node = new IntNode(value, null);

    if ( isEmpty() )
      _head = node;
    else {
      IntNode currentNode= _head;
      
      while (currentNode.getNext() != null)
        currentNode = currentNode.getNext( );

      currentNode.setNext(node);
    }
  }

  public void removeNode(int value) {
    IntNode prev = null;
    IntNode current = _head;
    
    while ( current != null && current.getValue() != value ) {
      prev = current;
      current = current.getNext();
    }
    
    if ( current != null ) {
      if ( prev == null )
        _head = current.getNext();
      else
        prev.setNext( current.getNext());
    }
  }
}
  