import java.util.InputMismatchException;
import java.util.regex.Pattern;

/**
 * Fraction class which allows for computations of rational numbers without having to worry about floating point rounding errors.
 * Keeps infinite precision as long as the numerator and denominator fall within the 64-bit {@code long} limit.
 * @author Matthew Park
 * @version v1 
 */
public class Fraction {
    protected long numer;
    private long denom;
    private boolean isInt;
    protected int length;

    /**
     * Create a {@code Fraction} with a specified numerator and denominator. Fraction will be simplified upon creation.
     * @param numer the numerator
     * @param denom the denominator
     * @throws ArithmeticException when the denominator is 0
     */
    public Fraction(long numer, long denom) {
        if (denom == 0) {
            throw new ArithmeticException("Denominator can't be 0");
        }
        this.numer = numer;
        this.denom = denom;
        simplify();

    }

    /**
     * Create a {@code Fraction} with a specified numerator. The denominator will be 0, so the {@code Fraction} will be a whole number.
     * @param num
     */
    public Fraction(long num) {
        this(num, 1);
        correctState();
    }

    /**
     * Makes a fraction based off a string. Fraction may include parenthesis. Defaults to 1 when the string is empty. (Also defaults to -1 when the input string is just a negative sign)
     * @param input the input string to parse
     * @throws NumberFormatException when the numerator/denominator is not a number
     * @throws InputMismatchException when there is more than 1 fraction bar
     */
    public Fraction(String input) {
        String cleanedInput = input.replaceAll("[\\(\\)]", "");//remove extra parenthesis
        long numer;
        long denom;
        if (input.equals(""))
        {
            numer = 1;
            denom = 1;
        } else if (input.equals("-")) {
            numer = -1;
            denom = 1;
        } else {
            Pattern fracBarPat = Pattern.compile("/");
            String[] nums = fracBarPat.split(cleanedInput);//index 0 holds the numerator, index 1 holds the denominator
            numer = Long.parseLong(nums[0]);
            switch (nums.length)
            {
                case 1:
                    denom = 1;
                    break;
                case 2:
                    denom = Long.parseLong(nums[1]);
                    break;
                default:
                    throw new InputMismatchException("Multiple fraction bars detected");
            }
        }
        this(numer, denom);
    }

    /**
     * Get the numerator of the fraction.
     * @return
     */
    public long getNumer() {
        return numer;
    }

    public long getDenom() {
        return denom;
    }

    public boolean isInt() {
        return isInt;
    }
    /**
     * Self-explanatory. Also works if the fraction is not simplified.
     * @return {@code true} if the fraction is negative, or {@code false} if the fraction is 0 or positive
     */
    public final boolean isNegative() {
        boolean numIsNegative = (numer < 0) ? true : false;
        boolean denomIsNegative = (denom < 0) ? true : false;
        return numIsNegative ^ denomIsNegative;
    }

    /**
     * Converts a fraction to a {@code long}
     * @return
     * @throws IllegalStateException when {@code this} ∉ ℤ
     */
    public long toLong() {
        if (!isInt()) {
            throw new IllegalStateException("Tried to convert a non-whole fraction into an long");
        }
        return numer;
    }


    public double toDouble() {
        return numer / denom;
    }

    @Override
    public final String toString() {
        if (isInt)
        {
            return "" + numer;
        } else {
            return numer + "/" + denom;
        }
        
    }
    /**
     * Like the {@code toString}, but
     * if the fraction is exactly 1 or -1, omit the 1 part
     * @return
     */
    public String toStringOmit1() {
        if (isInt) {
            if (toLong() == 1) {return "";}
            else if (toLong() == -1) {return "-";}
            else {return toString();}
        } else {
            return toString();
        }
    }
    /**
     * Like the {@code toString}, but
     * enclose the fraction in parenthesis if it's not an int
     * @return
     */
    public String toStringParenthesis() {
        if (isInt) {
            return toString();
        } else {
            return '(' + toString() + ')';
        }
    }

    /**
     * Like toString, but aligns it in the center using exactly the specified width.
     * If the padding is uneven, the string will be slightly to the left.
     * @param width the length of the returned string
     * @return
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
        fixSign();
        correctState();
    }
    /**
     * Correct the state. Fractions that aren't whole numbers need to use the simplify() method instead.
     */
    private void correctState() {
        isInt = (denom == 1) ? true : false;
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

    /**
     * Set {@code this} to be {@code this} + {@code num}.
     * In other words, {@code this} += {@code num}
     * @param num
     */
    public void peq(long num) {
        numer += num * denom;
        simplify();
    }

    /**
     * Set {@code this} to be {@code this} + {@code otherFrac}.
     * In other words, {@code this} += {@code otherFrac}
     * @param otherFrac
     */
    public void peq(Fraction otherFrac) {
        numer = (numer)*(otherFrac.denom) + (denom)*(otherFrac.numer);
        denom *= otherFrac.denom;
        simplify();
    }

    /**
     * Set {@code this} to be {@code this} - {@code num}.
     * In other words, {@code this} -= {@code num}
     * @param num
     */
    public void meq(long num) {
        numer -= num * denom;
        simplify();
    }
    /**
     * 
     * @param otherFrac
     * @see meq
     */
    public void meq(Fraction otherFrac) {
        numer = (numer)*(otherFrac.denom) - (denom)*(otherFrac.numer);
        denom *= otherFrac.denom;
        simplify();
    }

    /**
     * *=
     * @param num
     * @see peq
     */
    public void teq(long num) {
        numer *= num;
        simplify();
    }

    /**
     * *=
     * @param otherFrac
     * @see peq
     */
    public void teq(Fraction otherFrac) {
        numer *= otherFrac.numer;
        denom *= otherFrac.denom;
        simplify();
    }

    /**
     * /=
     * @param num
     * @see peq
     */
    public void deq(long num) {
        denom *= num;
        simplify();
    }

    /**
     * /=
     * @param otherFrac
     * @see peq
     */
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

    /**
     * Another way of constructing a {@code Fraction}
     * @param num
     * @return
     */
    public static Fraction toFraction(long num) {
        return new Fraction(num, 1);
    }
    /**
     * Return a {@code Fraction} that represents the swapped sign of this fraction (i.e. -5/3 becomes 5/3).
     * Doesn't change the value of {@code this}.
     */
    public Fraction swapedSign()
    {
        return new Fraction(-numer, denom);
    }
    /**
     * @return A new fraction which is the absolute value of this fraction. Doesn't change the value of this fraction.
     */
    public Fraction abs()
    {
        return new Fraction(Math.abs(numer), Math.abs(denom));
    }
}
