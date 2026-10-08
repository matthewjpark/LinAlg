import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Utils {
    public static Collection<Long> getFactors(long num) {
        Collection<Long> factors = getFactorsExcept1(num);
        factors.add((long) 1);
        factors.add(num);
        return factors;
    }


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
    
    public static <T> HashSet<T> getIntersection(Set<T> s1, Set<T> s2)
    {
        HashSet<T> s3 = new HashSet<T>(s1);
        s3.retainAll(s2);
        return s3;
    }

    // Source - https://stackoverflow.com/a/4009247
    // Posted by Matt, modified by community. See post 'Timeline' for change history
    // Retrieved 2026-10-06, License - CC BY-SA 4.0
    //time complexity: O(log(min(a, b)))
    public static long gcd(long a, long b) { return b==0 ? a : gcd(b, a%b); }
    //from https://stackoverflow.com/questions/14586131/lcm-lowest-common-multiple-in-java
    public static long lcm(long a, long b) { return (a * b) / gcd(a, b); }
}
