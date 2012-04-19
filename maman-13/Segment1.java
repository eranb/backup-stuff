public class Segment1 {
	private Point _poLeft;
	private Point _poRight;
	
	public static void main(String[] args) {
//		Segment1 obj1  = new Segment1(2,4,6,4);
//		Segment1 obj2 = new Segment1(obj1);
//		Segment1 obj3 = new Segment1(1,9,2,15);
//		Segment1 obj4 = new Segment1(-1,-10,2,15);
//		System.out.println(obj.getLength());
//		System.out.println(new Segment1(2,9,6,15));
//		System.out.println(obj);
//		System.out.println(obj2.isUnder(obj1));
//		System.out.println( obj1.isUnder(new Segment1(2,5,6,4)) );
//		System.out.println(obj1);
//		obj1.changeSize(-5);
//		System.out.println( obj1.pointOnSegment( new Point(6,4) ) );
//		System.out.println( obj1.isBigger(obj3));
//        System.out.println( new Segment1(-1,1,2,1).overlap(new Segment1(-1,1,-1,1)) );
//		System.out.println( new Segment1(1,1,3,1).overlap(new Segment1(2,1,4,1)) );
//		System.out.println( new Segment1(2,2,4,2).overlap(new Segment1(1,2,3,2)) );
//		System.out.println( new Segment1(-3,1,-1,1).overlap(new Segment1(-4,1,-2,1)) );
//		System.out.println( new Segment1(-4,1,-2,1).overlap(new Segment1(-3,1,-1,1)) );
//		System.out.println( new Segment1(-2,-2,1,-2).overlap(new Segment1(-1,-2,2,-2)) );
//		System.out.println( new Segment1(-2,-2,1,-2).overlap(new Segment1(-1,-2,1,-2)) );
//		System.out.println( new Segment1(-2,-2,1,-2).overlap(new Segment1(-1,-2,0.5,-2)) );
//		System.out.println( new Segment1(1,1,1,1).overlap(new Segment1(1,1,1,1)) );
		System.out.println(getSeg(-1,10).overlap(getSeg(-1,11)));
		System.out.println("********** Test Q2 Segment1 - Start **********");
		Segment1 seg0 = null; // (0.0,0.0)---(2.0,0.0)
		Segment1 seg2 = null;
		Segment1 seg3 = null; // seg2 = seg3 = (1.0,4.0)---(4.0,4.0)
		// constructor and toString
		seg0 = new Segment1(0.0, 0.0, 2.0, 0.0);
		if (!seg0.getPoLeft().equals(new Point(0.0, 0.0))) {
			System.out.println("\t ERROR - expected seg0.getPoLeft()=(0.0,0.0) ; actual="
							+ seg0.getPoLeft());
		} else
			System.out.println("\t OK - expected seg0.getPoLeft()=(0.0,0.0) ; actual="
							+ seg0.getPoLeft());

		// second constructor Segment1 (Point left, Point right)
		Point pLeft = new Point(1.0, 4.0);
		Point pRight = new Point(4.0, 8.0);
		seg2 = new Segment1(pLeft, pRight);
		if (!seg2.getPoLeft().equals(pLeft) || !seg2.getPoRight().equals(new Point(4.0, 4.0))) {
			System.out.println("\t ERROR - second Constructor - expected (1.0,4.0)---(4.0,4.0) ; actual="
							+ seg2);
		} else
			System.out.println("\t OK - second Constructor - expected (1.0,4.0)---(4.0,4.0) ; actual="
							+ seg2);

		// copy constructor
		seg3 = new Segment1(seg2);
		if (!seg3.getPoLeft().equals(new Point(1.0, 4.0))|| !seg3.getPoRight().equals(new Point(4.0, 4.0))) {
			System.out.println("\t ERROR - 3rd Constructor - expected (1.0,4.0)---(4.0,4.0) ; actual="
							+ seg3);
		} else
			System.out.println("\t OK - 3rd Constructor - expected (1.0,4.0)---(4.0,4.0) ; actual="
							+ seg3);

		// equals

		if (!seg2.equals(seg3)) {
			System.out.println("\t ERROR - equals - seg2.equals(seg3)? - expected true ; actual="
							+ seg2.equals(seg3));
		} else
			System.out.println("\t OK - equals - seg2.equals(seg3)? - expected true ; actual="
							+ seg2.equals(seg3));

		// sizes
		if (seg0.getLength() != 2.0) {
			System.out.println("\t ERROR - seg0.getLength() - expected 2.0 ; actual="
							+ seg0.getLength());
		} else
			System.out.println("\t OK - seg0.getLength() - expected 2.0 ; actual="
							+ seg0.getLength());

		Segment1 s1 = new Segment1(0.0, 0.0, 2.0, 0.0);
		s1.changeSize(3.0);
		if (s1.getLength() != 5.0) {
			System.out.println("\t ERROR - s1.changeSize() - expected length 5.0 ; actual="
							+ s1.getLength());
		} else
			System.out.println("\t OK - s1.getLength() - expected length 5.0 ; actual="
							+ s1.getLength());

		s1 = new Segment1(0.0, 0.0, 2.0, 0.0);
		Segment1 s2 = new Segment1(0.0, 2.0, 4.0, 2.0); // bigger than s1
		if (s1.isBigger(s2)) {
			System.out.println("\t ERROR - s1.isBigger(s2) - expected false ; actual="
							+ s1.isBigger(s2));
		} else
			System.out.println("\t OK - s1.isBigger(s2) - expected false ; actual="
							+ s1.isBigger(s2));

		// under, above, right & left
		s1 = new Segment1(5.0, 0.0, 10.0, 0.0);
		Segment1 s3 = new Segment1(5.0, 3.0, 10.0, 3.0);
		Segment1 s4 = new Segment1(5.0, -3.0, 10.0, -3.0);
		// under
		if (!s1.isUnder(s3)) {
			System.out.println("\t ERROR - s1.isUnder(s3) - expected true ; actual="
							+ s1.isUnder(s3) + " s1=" + s1 + " s3=" + s3);
		} else
			System.out.println("\t OK - s1.isUnder(s3) - expected true ; actual="
							+ s1.isUnder(s3) + " s1=" + s1 + " s3=" + s3);
		// above
		if (!s1.isAbove(s4)) {
			System.out.println("\t ERROR - s1.isAbove(s4) - expected true ; actual="
							+ s1.isAbove(s4) + " s1=" + s1 + " s4=" + s4);
		} else
			System.out.println("\t OK - s1.isAbove(s4) - expected true ; actual="
							+ s1.isAbove(s4) + " s1=" + s1 + " s4=" + s4);

		Segment1 sMid = new Segment1(5.0, 0.0, 10.0, 0.0);
		Segment1 sLeft = new Segment1(0.0, 3.0, 4.0, 3.0);
		Segment1 sRight = new Segment1(11.0, -3.0, 15.0, -3.0);

		// left
		if (!sMid.isRight(sLeft)) {
			System.out.println("\t ERROR - sMid.isRight(sLeft) - expected true ; actual="
							+ sMid.isRight(sLeft)+ " sMid="+ sMid+ " sLeft="+ sLeft);
		} else
			System.out.println("\t OK - sMid.isRight(sLeft) - expected true ; actual="
							+ sMid.isRight(sLeft)+ " sMid="+ sMid+ " sLeft="+ sLeft);
		// right
		if (!sMid.isLeft(sRight)) {
			System.out.println("\t ERROR - sMid.isLeft(sRight) - expected true ; actual="
							+ sMid.isLeft(sRight)+ " sMid="+ sMid+ " sRight=" + sRight);
		} else
			System.out.println("\t OK - sMid.isLeft(sRight) - expected true ; actual="
							+ sMid.isLeft(sRight)+ " sMid="+ sMid+ " sRight=" + sRight);

		// overlap
		if (sMid.overlap(sMid) != 5.0) {
			System.out.println("\t ERROR - sMid.overlap(sMid) - expected 5.0 ; actual="
							+ sMid.overlap(sMid)+ " sMid="+ sMid+ " sMid="+ sMid);
		} else
			System.out.println("\t OK - sMid.overlap(sMid) - expected 5.0 ; actual="
							+ sMid.overlap(sMid)+ " sMid="+ sMid+ " sMid="+ sMid);


		System.out.println("********** Test Q2 Segment1 - Finish **********\n");
		
	}
	
	public static Segment1 getSeg(int x1,int x2) {
		return new Segment1(x1,1,x2,1);
	}
	
	public Segment1(Point left, Point right) {
		registerNewPoints(left.getX(),left.getY(),right.getX(),right.getY());
	}
	
	public Segment1(double leftX ,double leftY,  double rightX ,double rightY) {
		registerNewPoints(leftX ,leftY, rightX ,rightY);
	}
	
	public Segment1(Segment1 other) {
		registerNewPoints(
			other.getPoLeft().getX(),
			other.getPoLeft().getY(),
			other.getPoRight().getX(),
			other.getPoRight().getY()
		);
	}
	
	private void registerNewPoints(double leftX ,double leftY,  double rightX ,double rightY) {
		if ( leftY != rightY )
			rightY = leftY;

		System.out.println("INIT left point " + new Point(leftX, leftY));
		System.out.println("INIT right point " + new Point(rightX, rightY));
		_poLeft  = new Point(leftX, leftY);
		_poRight = new Point(rightX, rightY);
	}
	
	public Point getPoLeft() {
		return _poLeft;
	}
	
	public Point getPoRight() {
		return _poRight;
	}
	
	public double getLength() {
		return _poRight.getX() - _poLeft.getX();
	}
	
	public String toString() {
		return _poLeft + "---" + _poRight;
	}
	
	public boolean equals(Segment1 other) {
		return _poLeft.equals( other.getPoLeft() ) &&
		 	   _poRight.equals( other.getPoRight() );
	}
	
	public boolean isAbove(Segment1 other) {
		return _poLeft.isAbove( other.getPoLeft() );
	}
	
	public boolean isUnder(Segment1 other) {
		return other.isAbove(this);
	}
	
	public boolean isLeft(Segment1 other) {
		return _poRight.getX() < other.getPoLeft().getX();
	}
	
	public boolean isRight(Segment1 other) {
		return _poLeft.getX() > other.getPoRight().getX();
	}
	
	public void moveHorisontal(double delta) {
		_poLeft.setX(_poLeft.getX()  + delta);
		_poRight.setX(_poRight.getX() + delta);
	}
	
	public void moveVertical(double delta) {
		_poLeft.setY(_poLeft.getY()  + delta);
		_poRight.setY(_poRight.getY() + delta);
	}
	
	public void changeSize(double delta) {
		if ( segmentSize() >= Math.abs(delta))
			_poRight.setX(_poRight.getX() + delta);
	}
	
	private double segmentSize() {
		return _poRight.distance(_poLeft);
	}
	
	public boolean pointOnSegment(Point p) {
		return _poLeft.getY()  == p.getY() && 
			   _poLeft.getX()  <= p.getX() &&
			   _poRight.getX() >= p.getX();
	}
	
	public boolean isBigger(Segment1 other) {
		return segmentSize() > other.segmentSize();
	}
	
	private boolean notTheSameHeightAs(Segment1 other) {
		return isAbove(other) || isUnder(other);
	}
	
	// helper function that manage to determine if other segment
	// got at least one point that overlap with my segment points.
	private boolean overlapsWithEach(Segment1 other) {
		return !(notTheSameHeightAs(other) || 
		other.getPoLeft().isRight(_poRight) ||
		other.getPoRight().isLeft(_poLeft));
	}
	
	public double overlap(Segment1 other) {
		double value = 0.0;
		
		// both segments overlap for sure
		if ( overlapsWithEach(other) ) 
		{	
			// other segment is bigger or with the same size
			if ( (other.getPoRight().isRight(_poRight) && other.getPoLeft().isLeft(_poLeft)) || other.equals(this) ) { 
				value = segmentSize();
			
			// other segment is smaller than me (by it's size)
			} else if ( other.getPoRight().isLeft( _poRight ) && other.getPoLeft().isRight( _poLeft )) {
				value = other.segmentSize();
			
			// other segment right point is on my right
			} else if ( other.getPoRight().isRight(_poRight) || _poRight.equals(other.getPoRight())) {
				value = other.getPoLeft().distance(_poRight);

			// other segment left point is on my left
			} else if ( other.getPoLeft().isLeft(_poLeft)    || _poLeft.equals(other.getPoLeft())) {
				value = other.getPoRight().distance(_poLeft);
			}
		}

		return value;
	}
	
}