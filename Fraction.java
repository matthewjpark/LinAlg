public class Fraction {
    protected long numer;
    private long denom;
    private boolean isWhole;
    protected int length;

    public Fraction(long numer, long denom) {
        if (denom == 0) {
            throw new ArithmeticException("Denominator can't be 0");
        }
        this.numer = numer;
        this.denom = denom;
        simplify();

    }

    public Fraction(long num) {
        this(num, 1);
    }

    public long getNumer() {
        return numer;
    }

    public long getDenom() {
        return denom;
    }

    public boolean isWhole() {
        return isWhole;
    }
    /**
     * See return. Also handles the case where the fraction is not simplified.
     * @return {@code true} if the fraction is negative, or {@code false} if the fraction is 0 or positive
     */
    public final boolean isNegative() {
        boolean numIsNegative = (numer < 0) ? true : false;
        boolean denomIsNegative = (denom < 0) ? true : false;
        return numIsNegative ^ denomIsNegative;
    }

    public long toLong() {
        if (!isWhole()) {
            throw new IllegalStateException("Tried to convert a non-whole fraction into an int");
        }
        return numer;
    }

    public double toDouble() {
        return numer / denom;
    }

    @Override
    public final String toString() {
        if (isWhole)
        {
            return "" + numer;
        } else {
            return numer + "/" + denom;
        }
        
    }

    /**
     * Like toString, but aligns it in the center using exactly the specified width.
     * If the padding is uneven, the string will be slightly to the left.
     * @param width the length of the returned string
     * @return the string
     */
    public String toCenterString(int width)
    {
        final int extraSpaces = width - length;
        assert(extraSpaces >= 0);
        int eSL = extraSpaces / 2;//extra spaces left
        int eSR = extraSpaces / 2;
        if (extraSpaces % 2 == 1)
        {
            eSR++;
        }
        assert(eSL + eSR == extraSpaces);
        StringBuilder centerStr = new StringBuilder(width);
        centerStr.append(" ".repeat(eSL));
        centerStr.append(this.toString());
        centerStr.append(" ".repeat(eSR));
        //System.out.println("center string length: " + centerStr.length() +". Expected: " + width);
        assert(centerStr.length() == width);
        return centerStr.toString();
    }
    /**
     * Get the length of the string representation of the fraction
     * @return
     */
    public int getLength() {
        return length;
    }

    //time complexity: O(log(min(a, b))) where a and b are the numbers
    private void simplify() {
        long gcd = Utils.gcd(numer, denom);
        assert(numer % gcd == 0);
        assert(denom % gcd == 0);
        numer /= gcd;
        denom /= gcd;
        assert(Utils.gcd(numer, denom) == 1);
        isWhole = (denom == 1) ? true : false;
        fixSign();
        setLength();
    }
    //Set the length based on its actual length
    protected final void setLength()
    {
        length = this.toString().length();
    }

    /**
     * If the fraction is negative, makes sure the negative sign is on the numerator
     */
    private void fixSign()
    {
        if (isNegative() && (denom < 0))
        {
            numer *= -1;
            denom *= -1;
        }
    }

    public void peq(long num) {
        numer += num * denom;
        simplify();
    }

    public void peq(Fraction otherFrac) {
        numer = (numer)*(otherFrac.denom) + (denom)*(otherFrac.numer);
        denom *= otherFrac.denom;
        simplify();
    }

    public void meq(long num) {
        numer -= num * denom;
        simplify();
    }

    public void meq(Fraction otherFrac) {
        numer = (numer)*(otherFrac.denom) - (denom)*(otherFrac.numer);
        denom *= otherFrac.denom;
        simplify();
    }

    public void teq(long num) {
        numer *= num;
        simplify();
    }

    public void teq(Fraction otherFrac) {
        numer *= otherFrac.numer;
        denom *= otherFrac.denom;
        simplify();
    }

    public void deq(long num) {
        denom *= num;
        simplify();
    }

    public void deq(Fraction otherFrac) {
        numer *= otherFrac.denom;
        denom *= otherFrac.numer;
        simplify();
    }

    /**
     * Multiply two fractions and return the result. The two fractions don't change as a result of this.
     * @param f1
     * @param f2
     * @return
     */
    public static Fraction multiply(Fraction f1, Fraction f2)
    {
        long newNumer = f1.numer * f2.numer;
        long newDenom = f1.denom * f2.denom;
        return new Fraction(newNumer, newDenom);
    }

    /**
     * Multiply a fraction and a long and return the result. The orignal fraction doesn't change as a result of this.
     * @param f1
     * @param l2
     * @return
     */
    public static Fraction multiply(Fraction f1, long l2)
    {
        long newNumer = f1.numer * l2;
        return new Fraction(newNumer, f1.denom);
    }

    public static Fraction toFraction(long num) {
        return new Fraction(num, 1);
    }
    /**
     * Return a {@code Fraction} that represents the swapped sign of this fraction (i.e. -5/3 becomes 5/3)
     */
    public Fraction swapedSign()
    {
        return new Fraction(-numer, denom);
    }
    /**
     * @return A new fraction which is the absolute value of this fraction. Doesn't change the value of this fraction.
     */
    public Fraction absOf()
    {
        return new Fraction(Math.abs(numer), Math.abs(denom));
    }
}
