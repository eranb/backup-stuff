public class MatrixTester {
    public static void main(String[] args) {
        final double EPSILON = 0.00001;
        boolean success = true;

        System.out.println("start");
        int[][] array = {
                {3, 8, 72},
                {4, 6, 60},
                {253, 2, 1}};
        Matrix matrix = new Matrix(array);

        String actualStringPresentation = "3\t8\t72\n4\t6\t60\n253\t2\t1\n";
        String stringPresentation = matrix.toString();
        if (!actualStringPresentation.equals(stringPresentation)) {
            System.out.println("Failed the test of The method \"toString()\" of class \"Matrix\".");
            success = false;
        }

        String actualNegativeMatrixString = "252\t247\t183\n251\t249\t195\n2\t253\t254\n";
        Matrix negativeMatrix = matrix.makeNegative();
        String negativeMatrixString = negativeMatrix.toString();
        if (!actualNegativeMatrixString.equals(negativeMatrixString)) {
            System.out.println("Failed the test of The method \"makeNegative()\" of class \"Matrix\".");
            success = false;
        }

        String actualFilterAverageMatrixString = "5\t25\t36\n46\t45\t24\n66\t54\t17\n";
        Matrix filterAverageMatrix = matrix.imageFilterAverage();
        String filterAverageMatrixString = filterAverageMatrix.toString();
        if (!actualFilterAverageMatrixString.equals(filterAverageMatrixString)) {
            System.out.println("Failed the test of The method \"imageFilterAverage()\" of class \"Matrix\".");
            success = false;
        }
        if (success) {
            System.out.println("Success :-)\nNote that this is only a basic test. Make sure you test all cases!");
        }

          System.out.println("end");
  }
}
