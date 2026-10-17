import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Static utility methods. Most methods are for numbers.
 */
public class Utils {
    public static final boolean RUNTIME_WARNINGS_ENABLED = true;
    /**
     * Get {@code Collection} (HashSet) of factors for {@code} num
     * @param num
     * @return
     */
    public static Collection<Long> getFactors(long num) {
        Collection<Long> factors = getFactorsExcept1(num);
        factors.add((long) 1);
        factors.add(num);
        return factors;
    }

    /**
     * 
     * @param num
     * @return
     * @see getFactors
     */
    public static Collection<Long> getFactorsExcept1(long num) {
       Collection<Long> factors = new HashSet<Long>();
       long root = (long) Math.sqrt(num);
       for (long i = 2; i < root; i++) {
        if ((num % i) == 0) {
            factors.add(i);
            factors.add(num / i);
        }
       }
       return factors;
    }
    
    /**
     * Returns a set that contains the elements that s1 and s2 share in common.
     * I.e., returns s1 ∩ s2
     * @param <T>
     * @param s1
     * @param s2
     * @return
     */
    public static <T> HashSet<T> getIntersection(Set<T> s1, Set<T> s2)
    {
        HashSet<T> s3 = new HashSet<T>(s1);
        s3.retainAll(s2);
        return s3;
    }

    // 
    // 
    // 
    //
    /**
     * Returns the greatest common denominator of two numbers
     * time complexity: O(log(min(a, b)))
     * Source - https://stackoverflow.com/a/4009247
     * Posted by Matt, modified by community. See post 'Timeline' for change history
     * Retrieved 2026-10-06, License - CC BY-SA 4.0
     * @param a
     * @param b
     * @return
     */
    public static long gcd(long a, long b) { return b==0 ? a : gcd(b, a%b); }
    //Source - https://stackoverflow.com/a/45218296
    /**
     * Returns the least common multiple of two numbers
     * Source - https://stackoverflow.com/a/45218296
     * Retrieved 2026-10-16, License - CC BY-SA 3.0
     * Posted by sanjaykumar81
     * @param a
     * @param b
     * @return
     */
    public static long lcm(long a, long b) { return (a * b) / gcd(a, b); }

    public static void printRuntimeWarningIfFalse(boolean expression, String warning)
    {
        if (RUNTIME_WARNINGS_ENABLED && !expression) {System.out.println("Warning:" + warning);}
    }
}