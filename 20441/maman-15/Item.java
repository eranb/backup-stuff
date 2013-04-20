/**
 * Abstract class that represents an Item
 * 
 * @author Barak Levi
 * @version 1
 */

public abstract class Item {
  protected String _title;
  protected int _publishYear;
  
  /**
  * Create new Item
  * @param title item title
  * @param item publish year
  */ 
  public Item(String title, int year) {
    _title = title;
    _publishYear = year;
  }
  
  /**
  * Copy constructor for Item. Construct a Item with the same attributes as another Item
  * @param other The Item object from which to construct the new Item
  */
  public Item(Item other) {
    _title = other.getTitle();
    _publishYear = other.getPublishYear();
  }
  
  /**
  * returns the item title
  * @return the title of the item
  */   
  public String getTitle() {
    return _title;
  }

  /**
  * returns the item publish year
  * @return the publish year of the item
  */
  public int getPublishYear() {
    return _publishYear;
  }
  
  /**
  * Change item title
  * @param newTitle The new title
  */
  public void setTitle(String newTitle) {
    _title = newTitle;
  }

  /**
  * Change item publish year
  * @param newYear The new publish year
  */  
  public void setPublishYear(int newYear) {
    _publishYear = newYear;
  }
  
  /**
  * String that represent the obj
  * @return string in format [title] \t Published at: [publishYear]
  */
  public String toString() {
    return _title + " \t Published at: " + _publishYear;
  }
  
  /**
  * Play the item
  */
  public abstract void play(); 
  
}