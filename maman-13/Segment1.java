public class Segment1 {
	private Point _poLeft;
	private Point _poRight;
	
	public static void main(String[] args) {
		Segment1 obj1  = new Segment1(2,4,6,4);
		Segment1 obj2 = new Segment1(obj1);
		Segment1 obj3 = new Segment1(1,9,2,15);
//		System.out.println(obj.getLength());
//		System.out.println(new Segment1(2,9,6,15));
//		System.out.println(obj);
//		System.out.println(obj2.isUnder(obj1));
//		System.out.println( obj1.isUnder(new Segment1(2,5,6,4)) );
//		System.out.println(obj1);
//		obj1.changeSize(-5);
//		System.out.println( obj1.pointOnSegment( new Point(6,4) ) );
		System.out.println( obj1.isBigger(obj3));
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
		if ( size() >= Math.abs(delta))
			_poRight.setX(_poRight.getX() + delta);
	}
	
	private double size() {
		return _poRight.distance(_poLeft);
	}
	
	public boolean pointOnSegment(Point p) {
		return _poLeft.getY()  == p.getY() && 
			   _poLeft.getX()  <= p.getX() &&
			   _poRight.getX() >= p.getX();
	}
	
	public boolean isBigger(Segment1 other) {
		return size() > other.size();
	}
	
	public double overlap(Segment1 other) {
		
	}
	
}