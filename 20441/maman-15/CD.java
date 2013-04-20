/**
 * Represents A CD item
 * 
 * @author Barak Levi
 * @version 1
 */

public class CD extends Item {
  private String _artist;
  private int _numberOfTracks;

  /**
  * Create new CD
  * @param title CD's title name
  * @param year CD's release year
  * @param artist CD's artist
  * @param tracks CD's number of tracks
  */ 
  public CD(String title, int year, String artist, int tracks) {
    super(title,year); // initialize parent
    _artist = artist;
    _numberOfTracks = tracks;
  }

  /**
  * Copy constructor for CD. Construct a CD with the same attributes as another CD
  * @param other The CD object from which to construct the new CD
  */  
  public CD(CD other) {
    super( other.getTitle(), other.getPublishYear() ); // initialize parent
    _artist = other.getArtist();
    _numberOfTracks = other.getNumberOfTracks();
  }
  
  /**
  * returns the CD's artist
  * @return the CD artist name
  */   
  public String getArtist() {
    return _artist;
  }

  /**
  * returns the number of tracks
  * @return the number of tracks in a CD
  */     
  public int getNumberOfTracks() {
    return _numberOfTracks;
  }
  
  /**
  * Change CD's artist
  * @param artist The new artist
  */  
  public void setArtist(String artist) {
    _artist = artist;
  }

  /**
  * Change number of tracks in a CD's
  * @param tracks The new number of tracks
  */    
  public void setNumberOfTracks(int tracks) {
    _numberOfTracks = tracks;
  }

  /**
  * String that represent the obj
  * @return string in format CD - [title] \t Published at: [publishYear] \t by: [artist] \t Number of tracks: [numberOfTracks]
  */
  public String toString() {
    return "CD - " + super.toString() + " \t by: " + _artist + " \t Number of tracks: " + _numberOfTracks;
  }

  /**
  * Play the CD, prints Now playing [CD title] by [artist], enjoy listening...
  */  
  public void play() {
    System.out.println("Now playing " + _title + " by " + _artist + ", enjoy listening... ");
  }

}