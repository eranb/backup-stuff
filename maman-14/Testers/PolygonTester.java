public class PolygonTester {
    public static void main(String[] args) {
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
        if (Math.abs(actualPerimeter - perimeter) > EPSILON) {
            System.out.println("Failed the test of The method \"calcPerimeter()\" of class \"Polygon\".");
            success = false;
        }

        double actualArea = 22.499999999999996;
        double area = polygon.calcArea();
        if (Math.abs(actualArea - area) > EPSILON) {
            System.out.println("Failed the test of The method \"calcArea()\" of class \"Polygon\".");
            success = false;
        }

        Polygon biggerPolygon = new Polygon();
        biggerPolygon.addVertex(2, 1);
        biggerPolygon.addVertex(5, 0);
        biggerPolygon.addVertex(7, 5);
        biggerPolygon.addVertex(4, 7);
        biggerPolygon.addVertex(1, 4);

        if (polygon.isBigger(biggerPolygon)) {
            System.out.println("Failed the test of The method \"isBigger(Polygon)\" of class \"Polygon\".");
            success = false;
        }

        int actualIndex = 3;
        Point point = new Point(4, 6);
        int index = polygon.findVertex(point);
        if (actualIndex != index) {
            System.out.println("Failed the test of The method \"findVertex(Point)\" of class \"Polygon\".");
            success = false;
        }

        Point actualNextVertex = new Point(1, 4);
        Point nextVertex = polygon.getNextVertex(point);
        if (!actualNextVertex.equals(nextVertex)) {
            System.out.println("Failed the test of The method \"getNextVertex(Point)\" of class \"Polygon\".");
            success = false;
        }

  
        if (success) {
            System.out.println("Success :-)\nNote that this is only a basic test. Make sure you test all cases!");
        }

          System.out.println("end");
  }
}
