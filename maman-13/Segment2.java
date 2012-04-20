/**
 * A segment2 represent a line (parallel to the x-axis) using a center point and length
 *
 * @author Barak Levi
 * @version 1
 */

public class Segment2 {
	private Point _poCenter;
	private double _length;
		
   /**
    * Constructs a segment. Constructs a new segment using a center point and the segment length
    * @param poCenter the Center Point
    * @param length the segment length
    */
	public Segment2(Point poCenter, double length) {
		_poCenter = new Point(poCenter);
        _length = length;
	}
	
   /**
    * Constructs a segment. Constructs a new segment using two Points
    * @param left the left point of the segment
    * @param right the right point of the segment
    */
    public Segment2(Point left, Point right) {
		initializeVariables(left, right);
	}
	
   /**
    * Constructs a segment. Constructs a new segment using 4 specified x y coordinates: two coordinates for the left point and two coordinates for the right point
    * @param leftX X value of left point
    * @param leftY Y value of left point
    * @param rightX X value of right point
    * @param rightY Y value of right point
    */
    public Segment2(double leftX, double leftY, double rightX, double rightY) {
		initializeVariables(new Point(leftX,leftY), new Point(rightX,rightY));
	}
	
	/**
     * Copy Constructor. Construct a segment using a reference segment
     * @param other  the reference segment
     */
    Segment2(Segment2 other) {
  		_length = other._length;
		_poCenter = other._poCenter;
    }
	
	// helper method to initialize new Segmet2 using two points.
	private void initializeVariables(Point left, Point right) {
		// using the distance between the points to set the length.
		_length = left.distance(right);
		
		/* Creating the center point
		 * formula to get x => taking the left X and adding it the length divided by 2
		 * formula to get y => we are always taking the Y of the left point.
		 */ 
		_poCenter = new Point( (left.getX() + _length / 2), left.getY() );
	}
	
	/**
	* Returns the left point of the segment
	* @return The left point of the segment
	*/
	public Point getPoLeft() {
		return fetchOuterPoint(-halfLength());
	}
	
	/**
	* Returns the left point of the segment
	* @return The left point of the segment
	*/
	public Point getPoRight() {
		return fetchOuterPoint(halfLength());
	}
	
	// helper function to fetch the outer points
	// right or left by delta by negative / positive delta
	private Point fetchOuterPoint(double delta) {
		return new Point(_poCenter.getX() + delta, _poCenter.getY());
	}
	
	// divide the length by two, to prevent duplication in code
	private double halfLength() {
		return _length / 2;
	}
	
    /**
     * Returns the segment length
     * @return The segment length
     */
	public double getLength() {
		return getPoRight().distance(getPoLeft());
	}
	
	/**
     * Return a string representation of this segment. i.e. (3,4)---(3,6)
     * @return  String representation of this segment
     */
	public String toString() {
		return getPoLeft() + "---" + getPoRight();
	}
    
	/**
     * Check if the reference segment is equal to this segment
     * @param other  the reference segment
     * @return True if the reference segment is equal to this segment
     */
	public boolean equals(Segment2 other) {
		return getPoLeft().equals( other.getPoLeft() ) &&
		 	   getPoRight().equals( other.getPoRight() );
	}
    
    /**
     * Check if this segment is above a reference segment
     * @param other  the reference segment
     * @return  True if this segment is above the reference segment
     */
	public boolean isAbove(Segment2 other) {
		return getPoLeft().isAbove( other.getPoLeft() );
	}
	
	/**
     * Check if this segment is under a reference segment.
     * @param  other  the reference segment
     * @return True if this segment is under the reference segment
     */
	public boolean isUnder(Segment2 other) {
		return other.isAbove(this);
	}
    
	/**
     * Check if this segment is left of a received segment
     * @param  other  the reference segment
     * @return True if this segment is left to the reference segment
     */
	public boolean isLeft(Segment2 other) {
		return getPoRight().getX() < other.getPoLeft().getX();
	}
	
    /**
     * Check if this segment is right of a received segment.
     * @param  other the reference segment
     * @return True if this segment is right to the reference segment
     */
	public boolean isRight(Segment2 other) {
		return getPoLeft().getX() > other.getPoRight().getX();
	}
	
    /**
     * Move the segment horizontally by delta
     * @param  delta  the displacement size
     */
	public void moveHorisontal(double delta) {
		getPoLeft().setX(getPoLeft().getX()  + delta);
		getPoRight().setX(getPoRight().getX() + delta);
	}
	
    /**
     * Move the segment vertically by delta
     * @param delta  the displacement size
     */
	public void moveVertical(double delta) {
		getPoLeft().setY(getPoLeft().getY()  + delta);
		getPoRight().setY(getPoRight().getY() + delta);
	}
	
    /**
     * Change the segment size by moving the right point by delta 
     * Will be done only for valid delta: only if the new right point remains the right point
     * @param  delta  the change size
     */	
	public void changeSize(double delta) {
		// verifying input to prevent cases which the right point is no longer right
		if (!(delta < 0 && getLength() < Math.abs(delta)))
			getPoRight().setX(getPoRight().getX() + delta);			
	}

    /**
     * Check if a point is located on the segment  
     * @param  p  a point to be checked
     * @return True if p is on this segment
     */	
	public boolean pointOnSegment(Point p) {
		return getPoLeft().getY()  == p.getY() && 
               getPoLeft().getX()  <= p.getX() &&
               getPoRight().getX() >= p.getX();
	}
	
    /**
     * Check if this segment is bigger then a reference segment
     * @param  other  the reference segment
     * @return  True if this segment is bigger than the reference segment
     */
	public boolean isBigger(Segment2 other) {
		return getLength() > other.getLength();
	}
		
    /* helper function that manage to determine if a given segment
     * overlaps this segment (in at least one point).
     */
	private boolean overlapsWithEach(Segment2 other) {
		return pointOnSegment(other.getPoLeft()) || pointOnSegment(other.getPoRight());
	}
	
    /**
     * Returns the overlap size of this segment and a reference segment
     * @param  other the reference segment
     * @return  The overlap size
     */
	public double overlap(Segment2 other) {
		double value = 0.0;
		
		// both segments overlap for sure
		if ( overlapsWithEach(other) ) 
		{	
           /* 
           * After we figured out that both segments overlap in at aleast one point
           * We need to "cut" the overlapping segment, for this we need to find two point
           * start and stop (left and right) points of the overlapping segment.
           * the starting point is the righter left point.
           * the stoping point is the lefter right point.
           */

			// we got to start with something...
			Point startPoint = getPoLeft(); 
			Point stopPoint  = getPoRight();
			
			// finding points
			if (other.getPoLeft().isRight(startPoint))
				startPoint = other.getPoLeft();
			if (other.getPoRight().isLeft(stopPoint))
				stopPoint = other.getPoRight();
			
			// returning the size of the overlapiing segment
			value = startPoint.distance(stopPoint);
		}

		return value;
	}
	
    /**
     * Compute the trapez perimeter, which constructed by this segment and a reference segment
     * @param other  the reference segment
     * @return The trapez perimeter
     */
	public double trapezePerimeter(Segment2 other) {
		//  using this formula to calculate trapeze:
		// (my.x to is.x ) + (my.y to is.y ) + my size + is size
         return getPoLeft().distance(other.getPoLeft()) +
                getPoRight().distance(other.getPoRight()) +
                getLength() + 
                other.getLength();
	}

}