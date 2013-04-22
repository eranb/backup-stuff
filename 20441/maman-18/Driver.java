public class Driver {
  public static void main(String[] args) {
    A y1 = new B(10);
    B y2 = new B(10);
    A z1 = new C(10);
    C z2 = new C(10);
    
    A a1 = new A(10);
    
    //System.out.println((A)a1);
//    System.out.println(y1.num == y2.num);
//    System.out.println(y1.getNum() == ((B)z1).getNum());
    System.out.println(y1.f(y2));
  }
}

class A {
  protected int num;
  
  public A(int n) { 
    num = n;
  }
  
  public int getNum() {
    return num;
  }
  
  public boolean f(A a) {
    return num == a.num * 2;
  }
}

class B extends A {
  public B(int n) {
    super(n);
  }
  
  public boolean f(B b) {
    return num == b.num;
  }
}

class C extends A {
  public C(int n) {
    super(n);
  }
  
  public boolean f(A a) {
    return a instanceof C && num == a.num;
  }
}