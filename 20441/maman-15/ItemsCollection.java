/**
 * class that represents Item Collection
 * 
 * @author Barak Levi
 * @version 1
 */

public class ItemsCollection {
  private Item[] _itemList;
  private int _numItems;

  private final int MAX_ITEMS = 20;

  /**
  * Create new Item Collection
  */ 
  public ItemsCollection() {
    _itemList = new Item[MAX_ITEMS]; 
  }
  
  /**
  * Copy constructor for ItemsCollection. Construct a ItemsCollection with the same attributes as another ItemsCollection
  * @param newItem The ItemsCollection object from which to construct the new ItemsCollection
  */
  public boolean addItem(Item newItem) {
    boolean added = false; // default return value
    
    // verifying constraints
    if (_numItems < MAX_ITEMS && newItem != null) {
      Item tmp = null; // initializing tmp for copying
      
      // Copying the instance based on his type
      if (newItem instanceof Video)
        tmp = new Video((Video) newItem);
      else if (newItem instanceof CD)
        tmp = new CD((CD) newItem);
      
      // Adding the new instace to collection
      _itemList[_numItems++] = tmp;
      added = true; // success
    }
    
    return added;
  }
  
  /**
  * Play item from the list
  * @param itemNumber the item position
  */  
  public void playItem(int itemNumber) {
    if ( itemNumber >= 0 && itemNumber < _numItems  ) // verifying constraints
      _itemList[itemNumber].play();
    else
      System.out.println("Item no. [itemNumber] doesn’t exist in the collection.");
      
  }

  /**
  * returns the number of CD items on the list
  * @return the number of CD items on the list
  */    
  public int getNumberOfCDs() {
    int count = 0;
    // loops though the collection and counting the number of CD instances
    for (int i = 0 ; i < _numItems; i++) 
      if (_itemList[i] instanceof CD) 
        count++;
        
    return count;
  }

  /**
  * List videos from the collection that were published before a given year
  * @param year the year to compare
  */
  public void oldiesButGoldies(int year) {
    // loops though the collection and printing the number of Videos
    // that were published before the given year
    for (int i = 0 ; i < _numItems; i++)
      if (_itemList[i] instanceof Video && _itemList[i].getPublishYear() < year) 
        System.out.println(_itemList[i]);
    
  }
  
  /**
  * String that represent the obj
  * @return string in format:
  * The items in the collection are: 
  * CD - [title] \t Published at: [publishYear] \t by: [artist] \t Number of tracks: [numberOfTracks] 
  * Video - [title] \t Published at: [publishYear] \t directed by: [director] 
  * ...
  */
  public String toString() {
    String str = "The items in the collection are:";
    for (int i = 0 ; i < _numItems; i++)
      str += "\n" + _itemList[i];
      
    return str;
  }
  
}