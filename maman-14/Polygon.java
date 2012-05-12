/**
 * This class represents a "Convex Polygon" in the plane .
 * @author Barak Levi
 * @version 1
 */

public class Polygon {
	private Point[] _vertices;
	private int _noOfVertices; // initialized to 0 by default
	private final int VERTICS_MAX_SIZE = 10;
	
  /**
  * Constructor for objects of class Polygon
  * Creates an empty polygon.
  */
  public Polygon() {
    _vertices = new Point[VERTICS_MAX_SIZE];
  }
	
  /**
  * Adds a new Point to the Polygon with the given x and y values
  * @param x - the specified x coordinate
  * @param y - the specified y coordinate
  * @return true if the point was added successfully; false otherwise.
  */
  public boolean addVertex(int x,int y) {
    return push( new Point(x,y) );
  }	

  /**
  *  Finds and returns a copy of the highest Point on the Polygon if such a point exists. If it does not exist, returns null. In the event that more than one such point exists, it returns the first point it finds.
  *  @return a Point which represents the highest vertex on the polygon if such a point exists; false otherwise.
  */
  public Point highestVertex() {
    Point highestPoint = null;
    
    if ( isNotEmpty() ) {
      highestPoint = _vertices[0]; // first one as default
			
    // iterating and finding the highest vertic
    for ( int i = 1; i < _noOfVertices; i++ )
      if ( _vertices[i].isAbove(highestPoint) )
        highestPoint = _vertices[i];
			
      highestPoint = new Point(highestPoint);
    }
		
    return highestPoint;
  }
	
  /**
  * Returns a string representation of this Polygon
  * @return a string representation of this Polygon
  */
  public String toString() {
    String str = "";
    if ( isNotEmpty() ) {
      str = "The polygon has " + _noOfVertices + " vertices:\n(" + _vertices[0];
      for ( int i = 1; i < _noOfVertices; i++ )
        str += "," + _vertices[i];
        
      str += ")";
    } else {
      str = "The polygon has 0 vertices.";
    }
    return str;		
  }
	

  /**
  * Calculates and returns the perimeter of the Polygon. In the event that there are only 2 verticies, it calculates the distance of the line represented by the two verticies. In the event that there are less than 2 verticies it returns 0.
  * @return the perimeter of the Polygon if the Polygon has 3 or more verticies; otherwise if it has 2 verticies returns the distance between the verticies; otherwise 0
  */	
  public double calcPerimeter() {
    double perimeterSize = 0;
    Point[] myPerimeterArray = perimeterArray();
    for ( int i = 0; i < (myPerimeterArray.length - 1); i++ )
      perimeterSize += myPerimeterArray[i].distance(myPerimeterArray[i+1]);
    
    return perimeterSize;
  }
	
	
  /**
  * Calculates and returns the area of the Polygon. In the event that the Polygon has less than 3 verticies, it returns 0.
  * @return the area of the Polygon if the Polygon has 3 or more verticies; otherwise 0.
  */
  public double calcArea() {
    // based on http://en.wikipedia.org/wiki/Shoelace_formula
    Point[] myPerimeterArray = perimeterArray();
    double area = 0.0;

    // Iterating over the perimeter and summing (x * y - y * x)
    if (_noOfVertices > 2) {
      for ( int i = 0; i < (myPerimeterArray.length - 1); i++ ) {
        area += myPerimeterArray[i].getX() * myPerimeterArray[i+1].getY();
        area -= myPerimeterArray[i].getY() * myPerimeterArray[i+1].getX();
      }
      area = Math.abs( area / 2);
    }
    
    return area;
  }
	
	
  /**
  * Determines whether or not the area of Polygon is bigger than the area of the instance of Polygon it is to be compared with.
  * @param other an object to be compared with this Polygon
  * @return true if this Polygon has an area larger than the object to be compared with; false otherwise
  */
  public boolean isBigger(Polygon other) {
    return this.calcArea() > other.calcArea();
  }
  
  /**
  * Finds and returns the index of a vertex.
  * @param p the Point whose index will be returned
  * @return the index of p if the Point exists in the Polygon; otherwise-1
  */
  public int findVertex(Point p) {
    int index = -1; // default
    for ( int i = 0; i < _noOfVertices; i++ ) {
      if (_vertices[i].equals(p))
        index = i;
    }
    return index;
  }
  
  /**
  * Finds and returns a copy of the successor of a Point in the Polygon.
  * @param p the Point whose successor will be returned
  * @return the successor of p if Point exists in the Polygon; otherwise null
  */
   public Point getNextVertex(Point p) {
     Point successor = null;
     
     int index = this.findVertex(p);
     if (index > 0) { // point exists 
       int targetIndex = index + 1;
       if (targetIndex == _noOfVertices) {
         successor = _vertices[0]; // taking the first point on last index
       } else {
         successor = _vertices[targetIndex];
       }
       
       successor = new Point(successor); // copying...
     }
     return successor;
   }
   
  /**
  * Calculates and returns a Polygon which represents the bounding box of this Polygon. The bounding box is the smallest Rectangle whose sides are parallel to the x and y axes of the coordinate space, and can completely contain the Polygon.
  * @return a Polygon (in the shape of a rectangle) that defines the bounds of this Polygon
  */
   public Polygon getBoundingBox() {
     Polygon boundingBox = null;
     
     if (_noOfVertices < 3)
       return boundingBox;
     
     // Setting up defaults for the outer points
     Point maxY = _vertices[0];
     Point maxX = _vertices[0];
     Point minY = maxY;
     Point minX = maxX;
     
     // finding the outer points
     for ( int i = 1; i < _noOfVertices; i++ ) {
       Point myPoint = _vertices[i];
       if (myPoint.isAbove(maxY))
         maxY = myPoint;
       if (myPoint.isUnder(minY))
         minY = myPoint;
       if (myPoint.isLeft(minX))
         minX = myPoint;
       if (myPoint.isRight(maxX))
         maxX = myPoint;
     }
     
     // Building the new polygon based on the points we found
     boundingBox = new Polygon();
     boundingBox.addVertex( minX.getX(), minY.getY() );
     boundingBox.addVertex( minX.getX(), maxY.getY() );
     boundingBox.addVertex( maxX.getX(), maxY.getY() );
     boundingBox.addVertex( maxX.getX(), minY.getY() );
     return boundingBox;
   }
   
  /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * /
  /                       Private Area                           /
  / * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

  /* Creates an array of points that represent the perimeter.
  * For example:
  * Polygon[(1,3), (2,3), (3,4)].perimeterArray() => [(1,3), (2,3), (3,4), (1,3)]
  * Also will return empty array if the number of vertices is smaller than one.
  */
  private Point[] perimeterArray() {
    Point[] myArray;
		
    if ( _noOfVertices > 1 ) {
      myArray = new Point[_noOfVertices + 1];
      for ( int i = 0; i < _noOfVertices; i++ )
        myArray[i] = _vertices[i];
      
      myArray[_noOfVertices] = _vertices[0];
    } else
      myArray = new Point[0];

    return myArray;
  }
	
  // helper method to add new points to the _vertices array
  private boolean push(Point point) {
    if (_noOfVertices == _vertices.length) {
        return false;
    } else {
      _vertices[_noOfVertices++] = point;
      return true;
    }
  }
	
  // vertics array is not empty?
  private boolean isNotEmpty() {
    return _noOfVertices > 0;
  }
  
	
}