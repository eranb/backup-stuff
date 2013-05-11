public class Shuki {
  public static void main( String[] args ) {
    int[] a = {1,3,5,2,4,6};
    //merge(a,0,((a.length-1)/2),(a.length - 1));
    mergeSort(a,0,a.length-1);
    p(a);
  }
  
  public static void merge( int[] a, int left, int middle, int right ) {
    int n1= middle - left + 1;
    int n2= right - middle;

    int[] l = new int[n1+1];
    int[] r = new int[n2+1];

    l[n1] = Integer.MAX_VALUE;
    r[n2] = Integer.MAX_VALUE;
    
    for (int i = 0; i < n1; i++) l[i] = a[left+i];
    for (int i = 0; i < n2; i++) r[i] = a[middle+1+i];

    int li = 0;
    int ri = 0;

    for ( int i = left; i <= right;i++) {
      if ( l[li] < r[ri] ) {
        a[i] = l[li];
        li++;
      } else {
        a[i] = r[ri];
        ri++;
      }
    }
  }
  
  public static void mergeSort(int[] a, int p, int r) {
    if ( p < r) {
      int q = ( p + r ) / 2;
      mergeSort(a,p,q);
      mergeSort(a,q+1,r);
      merge(a,p,q,r);
    }
  }
  
  public static void p(int[] a) {
    String out = "[ ";
    for (int i = 0; i < a.length ; i++) out = out + a[i] + " ";
    out = out + "]";
    p(out);
  }
  
  public static void p(Object o) {
    System.out.println(o);
  }
}