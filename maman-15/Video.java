/**
 * Represents Video item
 * 
 * @author Barak Levi
 * @version 1
 */

public class Video extends Item {
  private String _director;
  
  /**
  * Create new video
  * @param title video title name
  * @param year video release year
  * @param director video director
  */ 
  public Video(String title, int year, String director) {
    super(title,year);
    _director = director;
  }

  /**
  * Copy constructor for Video. Construct a video with the same attributes as another video
  * @param other The Video object from which to construct the new video
  */
  public Video(Video other) {
    super( other.getTitle(), other.getPublishYear() );
    _director = other.getDirector();
  }
  
  /**
  * returns the video's director
  * @return the video director name
  */ 
  public String getDirector() {
    return _director;
  }
  
  /**
  * Change video's director
  * @param director The new director
  */
  public void setDirector(String director) {
    _director = director;
  }
  
  /**
  * String that represent the obj
  * @return string in format Video - [title] \t Published at: [publishYear] \t directed by: [director] 
  */
  public String toString() {
    return "Video - " + super.toString() + "  \t directed by: " + _director;
  }
  
  /**
  * Play the video, prints Now playing [Video title] directed by [director], enjoy watching...
  */  
  public void play() {
    System.out.println("Now playing " + _title + " directed by " + _director + ", enjoy watching... ");
  }
  
  /**
  * Checks this video was released before a given year
  * @param year The year to compare
  * @return True if this older otherwise false
  */
  public boolean isOlder(int year) {
    return _publishYear < year;
  }
  
}