/**
 * Represents 2 dimensional integral points on a map.
 * 
 * @author Barak Levi
 * @version 1
 */
public class Point {
  private double _x;
  private double _y; 

  /**
   * Constructs a point.
   * @param x The x coordinate
   * @param y The y coordinate
   */
  public Point(double x, double y) {
    _x = x;
    _y = y;
  }

  /**
   * Copy constructor for Points. Construct a point with the same coordinates as another point
   * @param other The point object from which to construct the new point
   */
  public Point(Point other) {
    _x = other._x;
    _y = other._y;
  }

  /**
   * Returns the x coordinate of the point.
   * @return The x coordiante of the point
   */
  public double getX() {
    return _x;
  }
  
  /**
   * Returns the y coordinate of the point.
   * @return The y coordinate of the point
   */
  public double getY() {
    return _y;
  }

  /**
   * Changes the x coordinate of the point.
   * @param num The new x coordinate
   */
  public void setX(double num) {
    _x = num;
  }

  /**
   * Changes the y coordinate of the point.
   * @param num The new y coordinate
   */
  public void setY(double num) {
    _y = num;
  }

  /**
   * String that represent the obj
   * @return string in format (X,Y)
   */
  public String toString() {
    return "(" + _x + "," + _y + ")";
  }

  /**
   * Check if the received point is equal to this point.
   * @param other The point to be compared with this point
   * @return True if the received point is equal to this point
   */
  public boolean equals(Point other) {
    return other._x == _x && other._y == _y;
  }

  /**
   * Check if this point is above a received point.
   * @param other The point to check if this point is above
   * @return True if this point is above other point
   */
  public boolean isAbove(Point other) {
    return _y > other._y;
  }

  /**
   * Check if this point is below a received point.
   * @param other The point to check if this point is below
   * @return True if this point is below other point
   */  
  public boolean isUnder(Point other) {
    return other.isAbove(this);
  }

  /**
   * Check if this point is left of a received point.
   * @param other The point to check if this point is left of
   * @return True if this point is left of other point
   */  
  public boolean isLeft (Point other) {
    return _x < other._x;
  }
   
  /**
   * Check if this point is right of a received point.
   * @param other The point to check if this point is right of
   * @return True if this point is right of other point
   */  
  public boolean isRight(Point other) {
    return other.isLeft(this);
  }

  /**
   * Check the distance between this point and a received point.
   * @param p The point to check distance from
   * @return double representing the distance
   */
  public double distance(Point p) {
//	System.out.println("Math.abs(" + p._x + "-" + _x + ") + " + "Math.abs(" + p._y + " - " + _y + ") = " + (Math.abs(p._x - _x) + Math.abs(p._y - _y)));
//	System.out.println("Math.abs(" + p._x + "-" + _x + ") = " + Math.abs(p._x - _x));
//	System.out.println("Math.abs(" + p._y + "-" + _y + ") = " + Math.abs(p._y - _y));
    return Math.abs(p._x - _x) + Math.abs(p._y - _y);
  }

  /**
   * Move the x coordinate by dX and the y coordinate by dY.
   * @param dX The amount to move x
   * @param dy The amount to move y
   */
  public void move(double dx, double dy) {
    _x += dx;
    _y += dy;
  }

}