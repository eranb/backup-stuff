/**
 * This class represents a "Convex Polygon" in the plane .
 * @author Barak Levi
 * @version 1
 */

public class Polygon {
	private Point[] _vertices;
	private int _noOfVertices; // set to 0 by default
	private final int VERTICS_MAX_SIZE = 10;
	
	public static void main(String[] args) {
/*		
		Polygon shuki = new Polygon();
		System.out.println(shuki);
		System.out.println(shuki.addVertex(1,1));
		System.out.println(shuki.addVertex(2,2));
		System.out.println(shuki.addVertex(3,3));
		System.out.println(shuki.addVertex(4,4));
		System.out.println(shuki.addVertex(5,5));
		System.out.println(shuki.addVertex(6,6));
		System.out.println(shuki.addVertex(7,7));
		System.out.println(shuki.addVertex(8,8));
		System.out.println(shuki.addVertex(9,9));
//		System.out.println(shuki.addVertex(10,10));
		System.out.println(shuki.addVertex(11,11));
		System.out.println(shuki.highestVertex());
		System.out.println(shuki);
		System.out.println(shuki.calcPerimeter());
*/		
		final double EPSILON = 0.00001;
        boolean success = true;
          System.out.println("start");
        Polygon polygon = new Polygon();
        polygon.addVertex(2, 1);
        polygon.addVertex(5, 0);
        polygon.addVertex(7, 5);
        polygon.addVertex(4, 6);
        polygon.addVertex(1, 4);

        Point actualHighest = new Point(4, 6);
        Point highest = polygon.highestVertex();
      
        if (!actualHighest.equals(highest)) {
            System.out.println("Failed the test of The method \"highestVertex()\" of class \"Polygon\".");
            success = false;
        }

        String actualStringPresentation = "The polygon has 5 vertices:\n((2,1),(5,0),(7,5),(4,6),(1,4))";
        String stringPresentation = polygon.toString();
        if (!actualStringPresentation.equals(stringPresentation)) {
            System.out.println("Failed the test of The method \"toString()\" of class \"Polygon\".");
            success = false;
        }

        double actualPerimeter = 18.47754906310363;
        double perimeter = polygon.calcPerimeter();
		System.out.println("perimeter is " + perimeter + " instead of " + actualPerimeter);
        if (Math.abs(actualPerimeter - perimeter) > EPSILON) {
            System.out.println("Failed the test of The method \"calcPerimeter()\" of class \"Polygon\".");
            success = false;
        }
        
	}
	
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
		String str = "The polygon has 0 vertices.";
		if ( isNotEmpty() ) {
			str = "The polygon has " + _noOfVertices + " vertices:\n(";
			for ( int i = 0; i < _noOfVertices; i++ ) {
				if (i !=0)
					str += ",";
					
				str += _vertices[i];
			}
			str += ")";
		}
		return str;
		
	}
	

  /**
   * Calculates and returns the perimeter of the Polygon. In the event that there are only 2 verticies, it calculates the distance of the line represented by the two verticies. In the event that there are less than 2 verticies it returns 0.
   * @return the perimeter of the Polygon if the Polygon has 3 or more verticies; otherwise if it has 2 verticies returns the distance between the verticies; otherwise 0
   */	
	public double calcPerimeter2() {
		double perimeter = 0;
		
		if ( _noOfVertices > 1 ) {
			int targetVerticIndex;
			
			for ( int i = 0; i < _noOfVertices; i++ ) {
				targetVerticIndex = i + 1;
				
				// if it's the last vertic - calc the distance to the first
				if (targetVerticIndex == _noOfVertices)
					targetVerticIndex = 0;
				
				perimeter += _vertices[i].distance(_vertices[targetVerticIndex]);	
			}
		}
		
		return perimeter;
	}
	
	public double calcPerimeter() {
		double perimeterSize = 0;
		Point[] myPerimeterArray = perimeterArray();
		for ( int i = 0; i < (myPerimeterArray.length - 1); i++ )
			perimeterSize += myPerimeterArray[i].distance(myPerimeterArray[i+1]);
	
		return perimeterSize;
	}
	
	
  /**
   * Calculates and returns the area of the Polygon. In the event that the Polygon has less than 3 verticies, it returns 0.
   * @return  the area of the Polygon if the Polygon has 3 or more verticies; otherwise 0.
   */
	public double calcArea() {
		return 0.0;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * /
	/                      Private methods	                       /
	/ * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* Creates an array of points that represent the perimeter.
	* For example:
	* Polygon[(1,3), (2,3), (3,4)].perimeterArray() => [(1,3), (2,3), (3,4), (1,3)]
	* Also will return empty array if the number of vertics is smaller than one.
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
		if ( isNotFull() ) {
			_vertices[_noOfVertices++] = point;
			return true;
		} else
			return false;
	}
	
	// vertics array full?
	private boolean isFull() {
		return _noOfVertices == _vertices.length;
	}
	
	// vertics array is not full?
	private boolean isNotFull() {
		return !isFull();
	}
	
	// vertics array empty?
	private boolean isEmpty() {
		return _noOfVertices == 0;
	}
	
	// vertics array is not empty?
	private boolean isNotEmpty() {
		return !isEmpty();
	}
	
}