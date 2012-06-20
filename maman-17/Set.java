/**
 * This class represents a "Set" of a non-negative numbers.
 * @author Barak Levi
 * @version 1
 */

public class Set {
  IntNode _head;
  IntNode _tail;
  int _numElements; // elements counter
  
  // NOTE: I implemented this class, so that the set is always sorted ( ascending )
  // the logic of each function depends on it.
    
  /**
   * Determines whether or not the set is empty
   * Complexity: none
   * @return empty or not
   */
  public boolean isEmpty() {
    return _head == null;
  }
  
  /**
   * Determines whether or not a given integer is member of the set or not
   * Runtime Complexity: O(n) ( thorugh the find function )
   * Memory Complexity: none
   * @param int - the number to look for
   * @return member or not
   */
  public boolean isMember( int num ) {
    return find( num ) != null;
  }

  /**
   * Determines whether or not a given set equals or not
   * Runtime Complexity: O(n)
   * Memory Complexity: none
   * @param other - set to inspect
   * @return equals or not
   */  
  public boolean equals( Set other ) {
    if ( other == null || numOfElements() != other.numOfElements() )
      return false; // some basic filtering 
    
    IntNode cursor1 = _head;
    IntNode cursor2 = other._head;
    
    // loop through each node on both lists, until the end
    // if the nodes are not equal the loop will break
    for (int i = 0; i < numOfElements() ; i++) {
      if ( !nodesEqual(cursor1, cursor2) )
        break;

      cursor1 = cursor1.getNext();
      cursor2 = cursor2.getNext();
    }
    
    return lastElementInList( cursor1 ); // equals if we got to the end
  }
  
  /**
   * Get the current number of elements in Set
   * Complexity: none
   * @return the number or elements
   */  
  public int numOfElements() {
    return _numElements;
  }
  
  /**
   * Determines whether or not a given set is subset of this Set
   * Runtime Complexity: O(n)
   * Memory Complexity: none
   * @param other - the set to inspect
   * @return subset or not
   */  
  public boolean subSet(Set other) {
    // basic filtering
    if (other == null)
      return false;
    
    if ( other.isEmpty() )
      return true;
    else if ( isEmpty() )
      return false;
    
    // Initialize cursors    
    IntNode cursor1 = find(other._head.getValue()); // O(n)
    IntNode cursor2 = other._head;

    // we didn't find any element in our list that match the first
    // element of the given set...
    if (cursor1 == null) 
      return false;
    
    // Loop thorugh each node on both lists and try to find unmatched values O(n).
    while( cursor2 != null ) {
      if (cursor1 == null || cursor1.getValue() != cursor2.getValue())
        return false;
        
      cursor1 = cursor1.getNext();
      cursor2 = cursor2.getNext();
    }
    
    return true;
  }
  
  /**
   * Adds a new number to the Set, invalid values will be ignored
   * after the new value is added the list should be sorted.
   * Runtime Complexity: O(n)
   * Memory Complexity: none
   * @param x - the number to add
   */  
  public void addToSet(int x) {
    // Verifying candidate
    if ( invalidCandidate( x ) || isMember( x ) ) return; 
    
    // Initializing new member 
    IntNode myCandidate = new IntNode(x,null);

    if ( isEmpty() ) { // if the set is empty
      _head = myCandidate;
    // smaller than head
    } else if ( myCandidate.getValue() < _head.getValue() ) {
      myCandidate.setNext(_head);
      _head = myCandidate; // new head!
    } else { 
      // candidate is bigger than head, going to find him place on the list
      if ( lastElementInList( _head ) ) { // case the head is the last element
        _head.setNext( myCandidate ); 
      } else {
        // finding the right spot... O(n)
        IntNode parent = _head;
        IntNode target = _head.getNext();
        
        while( target != null && myCandidate.getValue() > target.getValue() ) {
          parent = target;
          target = target.getNext();
        }
        
        if ( target == null ) { 
          parent.setNext(myCandidate); // we got to the end of the list
        } else {
          myCandidate.setNext(target); // patching it right in...
          parent.setNext(myCandidate);
        }
      }
    }
    
    if (lastElementInList(myCandidate))
      _tail = myCandidate;
      
    _numElements++;
  }  
  
  /**
   * Remove item from the set
   * Runtime Complexity: O(n)
   * Memory Complexity: none
   * @param x - the item to remove
   */  
  public void removeFromSet(int x) {
    if ( isEmpty() || invalidCandidate( x ) )
      return; // basic filtering

    if ( isHead( x ) ) { 
      _head = _head.getNext( ); // removing the head
      
      if (_head == null ) 
        _tail = null;
      
      _numElements--;
    } else {
      // finding the parent of the node that holds are value (if got one)
      IntNode parent = parentOf( x ); // O(n)
      
      if ( parent != null ) {
        parent.setNext(parent.getNext().getNext()); // "cutting" it
        
        if ( lastElementInList( parent.getNext() ) )
          _tail = parent.getNext(); // fixing tail
        
        _numElements--;
      }
    } 
  }
  
  /**
  * Returns a string representation of this Set
  * Runtime Complexity: O(n)
  * Memory Complexity: none
  * @return a string representation of this Set
  */
  public String toString() {
    return "{" + toString( _head ) + "}";
  }
  
  /**
  * Returns the intersection set, of this Set with a given one
  * Runtime Complexity: O(n)
  * Memory Complexity: none
  * @param other - the set to inspect
  * @return the intersection set
  */
  public Set intersection(Set other) {
    Set mySet = new Set(); // initializing new Set
    
    if (other == null || other._head == null || _head == null)
      return mySet; // some basic filtering
    
    // initializing cursors
    IntNode cursor1 = _head;
    IntNode cursor2 = other._head;
    
    // Starting looping on both lists
    while( cursor1 != null && cursor2 != null ) {
      if ( cursor1.getValue() == cursor2.getValue() ) {
        // if we find two matched nodes, we wanna add it to the list
        mySet.addToTail(new IntNode(cursor1.getValue(), null));        
        
        // incrementing both cursors
        cursor1 = cursor1.getNext(); 
        cursor2 = cursor2.getNext();
        
        // if one of the nodes are smaller than the other, we want to increment it
      } else if ( cursor1.getValue() < cursor2.getValue() )
        cursor1 = cursor1.getNext();
      else
        cursor2 = cursor2.getNext();
    }
    
    return mySet;    
  }

  /**
  * Returns a new Set, that is results of unioning this Set with a given one
  * Runtime Complexity: O(n)
  * Memory Complexity: none
  * @param other - the set to inspect
  * @return the unioned set
  */
  public Set union(Set other) {
    if (other == null || other._head == null) 
      return this; // some basic filtering...
    
    Set mySet = new Set(); // initializing new Set
    
    // Initializing cursors
    IntNode cursor1 = _head;
    IntNode cursor2 = other._head;
    
    // looping on both list until both are null
    while (( cursor1 != null) || (cursor2 != null )) {
      int value;
      
      if (cursor1 == null) { // we got to the end of the first list, continue with the second
        value = cursor2.getValue(); 
        cursor2 = cursor2.getNext();
      } else if (cursor2 == null) { // the given list reach the end
        value = cursor1.getValue();
        cursor1 = cursor1.getNext();
      } else if (cursor1.getValue() <= cursor2.getValue() ) { // taking only one of them
        value = cursor1.getValue();
        cursor1 = cursor1.getNext();
        if (nodesEqual(cursor1,cursor2))
          cursor2 = cursor2.getNext(); // increment second as well (both were matched)
      } else { // taking value 2 and incrementing the cursor2
        value = cursor2.getValue();
        cursor2 = cursor2.getNext();
      }
      
      // adding the new member to the tail of the list
      mySet.addToTail(new IntNode(value, null));
    }
    
    return mySet;
  }

  /**
  * Returns a new Set, that is results of "differencing" this Set with a given Set
  * Runtime Complexity: O(n)
  * Memory Complexity: none
  * @param other - the set to inspect
  * @return the differenced set
  */
  public Set difference( Set other ) {
    if (other == null || other._head == null) 
      return this; // basic filtering
    
    Set mySet = new Set();
    
    // Initializing cursors
    IntNode cursor1 = _head;
    IntNode cursor2 = other._head;
    
    while ( cursor1 != null ) {
      int value = -1; // default value
      
      if (cursor2 == null) {
        // reached to the end, incrementing and taking the value of this
        value = cursor1.getValue();
        cursor1 = cursor1.getNext();
        // taking & incrementing the first cursor if is smaller
      } else if ( cursor1.getValue() < cursor2.getValue() ) { 
        value = cursor1.getValue();
        cursor1 = cursor1.getNext();
        // taking the first cursor, but incremente second if is bigger
      } else if ( cursor1.getValue() > cursor2.getValue() ) { 
        value = cursor1.getValue();
        cursor2 = cursor2.getNext();
      } else { // incrementing both
        cursor1 = cursor1.getNext();
        cursor2 = cursor2.getNext();
      }
      
      if (value > 0) // if we entered to one of the IFs
        mySet.addToTail(new IntNode(value, null));
      
    }
    return mySet;
  }
  
  /* * * * * * * * * * * ** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * /
  /                                  Private Area                                       /
  / * * * * * * * * * * * * * * * * * * * * * * * * * * * * ** * * * * * * * * * * * * */  

  // method to findout if a given int is valid or not
  private boolean validCandidate( int candidate ) {
    return candidate % 2 == 1 && candidate > 0 ;
  }

  // method to filter invalid candidates  
  private boolean invalidCandidate( int candidate ) {
    return !validCandidate( candidate ) ;
  }
  
  // check if node value is equal to a given value in a safe way
  private boolean safeMatch( IntNode node, int num ) {
    return ( node != null ) && ( node.getValue() == num );
  }
  
  // check if two nodes are queal or not (got the same values)
  private boolean nodesEqual( IntNode node1, IntNode node2 ) {
    return ( node1 != null && node2 != null) &&
     ( node1.getValue() == node2.getValue() );
  }
  
  // the node of this num, is the head?
  private boolean isHead(int num) {
    return safeMatch( _head, num );
  }
  
  // does this node is the last element in list
  private boolean lastElementInList(IntNode node) {
    return !hasNext(node);
  }
  
  // get the next node of a given node in a safer way
  private IntNode safeGetNext( IntNode node ) {
    IntNode cursor = null;

    if (node != null)
      cursor = node.getNext();
    
    return cursor;
  }
  
  // find the parent of the node that holds this value
  // this one got complexity of O(n)
  private IntNode parentOf(int value) {
    IntNode cursor = _head;
    
    while (cursor != null && !safeMatch(cursor.getNext(),value))
      cursor = cursor.getNext();
    
    
    return cursor;
  }
  
  // return the child of parentOf( num ) O(n)
  private IntNode find(int num) {
    IntNode cursor;
    
    if ( isEmpty() || invalidCandidate( num ) )
      cursor = null;
    else if ( isHead( num ) )
      cursor = _head;
    else
      cursor = safeGetNext( parentOf( num ) );
      
    return cursor;
  }
  
  // recusive function that return a string representation of each element on list
  // this one is also O(n)
  private String toString(IntNode node) {
    String myString =  "";
    
    if (isEmpty())
      return myString;
      
     myString += node.getValue();
    
    if ( hasNext(node) )
      myString += "," + toString(node.getNext());
      
    return myString;
  }
  
  // node has next ?
  private boolean hasNext( IntNode node ) {
    return node != null && node.getNext() != null;
  }
  
  // add node to the tail, usful when adding sorted values
  private void addToTail(IntNode node) {
    if (_head == null)
      _head = node;
    else
      _tail.setNext(node);
    
    _tail = node;
  }
  
}