
public class Tester {

    public static void printFraction(long numer, long denom)
    {
        Fraction f = new Fraction(numer, denom);
        System.out.println(f.toString());
    }
    public static void main(String[] args) {
        // Fraction f1 = new Fraction(1);
        // Fraction f2 = new Fraction(2);
        // Fraction f3 = new Fraction(3);
        // Fraction f4 = new Fraction(4);
        // Fraction f5 = new Fraction(5);
        // Fraction f6 = new Fraction(6);
        // CMatrix m = new CMatrix(new Fraction[][] {{f1, f2, f3}, {f4, f5, f6}});
        // System.out.println(m.toString());
        // m.addRow(1, 2, 2);
        // System.out.println(m.toString());
        System.out.println(Matrix.cToH(0));
    }
    

}
