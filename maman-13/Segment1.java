/**
 * A segment1 represent a line (parallel to the x-axis) using two Points
 *
 * @author Barak Levi
 * @version 1
 */

public class Segment1 {
	private Point _poLeft;
	private Point _poRight;
	
	public static void main(String[] args) {
		System.out.println( new Segment1(-2,0,10,0).overlap(new Segment1(-3,0,4,0)) );
	}
	
    /**
     * Constructs a segment. Constructs a new segment using two Points
     * @param left the left point of the segment
     * @param right the right point of the segment
     */
	public Segment1(Point left, Point right) {
		registerNewPoints(left.getX(),left.getY(),right.getX(),right.getY());
	}
	
    /**
     * Constructs a segment. Constructs a new segment using 4 specified x y coordinates:
     * two coordinates for the left point and two coordinates for the right point.
     * @param leftX  X value of left point
     * @param leftY  Y value of left point
     * @param rightX X value of right point
     * @param rightY Y value of right point
     */
	public Segment1(double leftX ,double leftY,  double rightX ,double rightY) {
		registerNewPoints(leftX ,leftY, rightX ,rightY);
	}
	
    /**
     * Copy Constructor. Construct a segment using a reference segment
     * @param other  the reference segment
     */
	public Segment1(Segment1 other) {
		registerNewPoints(
			other.getPoLeft().getX(),
			other.getPoLeft().getY(),
			other.getPoRight().getX(),
			other.getPoRight().getY()
		);
	}
	
    /*
     * Gateway for creating new objects.
     * By pushing all new objects into this function we get the benefits of:
     *  - No aliasing to input points.
     *  - Making sure both Y points are equal.
     *  - No code duplication.
     */
	private void registerNewPoints(double lx ,double ly,  double rx ,double ry) {
		if ( ly != ry )
			ry = ly;

		_poLeft  = new Point(lx, ly);
		_poRight = new Point(rx, ry);
	}

    /**
     * Returns the left point of the segment
     * @return The left point of the segment
     */	
	public Point getPoLeft() {
		return _poLeft;
	}

    /**
     * Returns the right point of the segment
     * @return  The right point of the segment
     */
	public Point getPoRight() {
		return _poRight;
	}
	
    /**
     * Returns the segment length
     * @return The segment length
     */
	public double getLength() {
		return _poRight.distance(_poLeft);
	}
	
    /**
     * Return a string representation of this segment. i.e. (3,4)---(3,6)
     * @Overrides toString in class java.lang.Object
     * @return  String representation of this segment
     */
	public String toString() {
		return _poLeft + "---" + _poRight;
	}

    /**
     * Check if the reference segment is equal to this segment
     * @param other  the reference segment
     * @return True if the reference segment is equal to this segment
     */
	public boolean equals(Segment1 other) {
		return _poLeft.equals( other.getPoLeft() ) &&
		 	   _poRight.equals( other.getPoRight() );
	}

    /**
     * Check if this segment is above a reference segment
     * @param other  the reference segment
     * @return  True if this segment is above the reference segment
     */
	public boolean isAbove(Segment1 other) {
		return _poLeft.isAbove( other.getPoLeft() );
	}
	
    /**
     * Check if this segment is under a reference segment.
     * @param  other  the reference segment
     * @return True if this segment is under the reference segment
     */
	public boolean isUnder(Segment1 other) {
		return other.isAbove(this);
	}
	
    /**
     * Check if this segment is left of a received segment
     * @param  other  the reference segment
     * @return True if this segment is left to the reference segment
     */
	public boolean isLeft(Segment1 other) {
		return _poRight.getX() < other.getPoLeft().getX();
	}
	
    /**
     * Check if this segment is right of a received segment.
     * @param  other the reference segment
     * @return True if this segment is right to the reference segment
     */
	public boolean isRight(Segment1 other) {
		return _poLeft.getX() > other.getPoRight().getX();
	}
	
    /**
     * Move the segment horizontally by delta
     * @param  delta  the displacement size
     */
	public void moveHorisontal(double delta) {
		_poLeft.setX(_poLeft.getX()  + delta);
		_poRight.setX(_poRight.getX() + delta);
	}
	
    /**
     * Move the segment vertically by delta
     * @param delta  the displacement size
     */
	public void moveVertical(double delta) {
		_poLeft.setY(_poLeft.getY()  + delta);
		_poRight.setY(_poRight.getY() + delta);
	}
	
    /**
     * Change the segment size by moving the right point by delta 
     * Will be done only for valid delta: only if the new right point remains the right point
     * @param  delta  the change size
     */	
	public void changeSize(double delta) {
		if (!(delta < 0 && getLength() < Math.abs(delta)))
			_poRight.setX(_poRight.getX() + delta);			
	}

    /**
     * Check if a point is located on the segment  
     * @param  p  a point to be checked
     * @return True if p is on this segment
     */	
	public boolean pointOnSegment(Point p) {
		return _poLeft.getY()  == p.getY() && 
               _poLeft.getX()  <= p.getX() &&
               _poRight.getX() >= p.getX();
	}
	
    /**
     * Check if this segment is bigger then a reference segment
     * @param  other  the reference segment
     * @return  True if this segment is bigger than the reference segment
     */
	public boolean isBigger(Segment1 other) {
		return getLength() > other.getLength();
	}
		
    /* helper function that manage to determine if a given segment
     * overlaps this segment (in at least one point).
	*/
	private boolean overlapsWithEach(Segment1 other) {
		return pointOnSegment(other.getPoLeft()) || pointOnSegment(other.getPoRight());
	}
	
    /**
     * Returns the overlap size of this segment and a reference segment
     * @param  other the reference segment
     * @return  The overlap size
     */
	public double overlap(Segment1 other) {
		double value = 0.0;
		
		// both segments overlap for sure
		if ( overlapsWithEach(other) ) 
		{	
           /* 
           * After we figured out that both segments overlap in at aleast one point
           * We need to "cut" the overlapping segment.
           * for this we need to find two point, start and stop points.
		   * the starting point is the righter left point.
		   * the stoping point is the lefter right point.
           */

			// Defalts
			Point startPoint = _poLeft; 
			Point stopPoint  = _poRight;
			
			// finding points
			if (other.getPoLeft().isRight(startPoint))
				startPoint = other.getPoLeft();
			if (other.getPoRight().isLeft(stopPoint))
				stopPoint = other.getPoRight();
			
			value = startPoint.distance(stopPoint);
		}

		return value;
	}
	
    /**
     * Compute the trapez perimeter, which constructed by this segment and a reference segment
     * @param other  the reference segment
     * @return The trapez perimeter
     */
	public double trapezePerimeter(Segment1 other) {
         return _poLeft.distance(other.getPoLeft()) +
                _poRight.distance(other.getPoRight()) +
                getLength() + 
                other.getLength();
	}
}