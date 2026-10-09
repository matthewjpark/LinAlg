import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Takes strings that represent row operations, parses them, and performs the operation on the matrix.
 */
public class RowOpInterpreter {
    private Matrix m;
    public RowOpInterpreter(Matrix m)
    {
        this.m = m;
    }
    /**
     * Examples: "p1*=2"
     * "p10/=3"
     * "p2*=-2/3"
     * "p2*=(-2/3)"
     * "p2+=3p1"
     * "p3+=2/3p1"
     * "p3+=(2/3)p1"
     * "p3-=3p1"
     * "p3-=3/2p1"
     * "p3-=(2/3)p1"
     * "p3sp2"
     * "p3 p2"
     * @param s a string that represents the row operation
     */
    public void run(String s)
    {

    }

    //change to private later
    /**
     * 
     * @param s
     * @return
     * //@throws ArrayOutOfBoundsException when the regex couldn't be matched
     * @throws IllegalStateException when you don't specify a destination row
     */
    private static String[] parse(String s)
    {
        enum RowOps {
            ADD, SUBTR, MULT, DIV, SWAP;
        }
        //finds the operation
        //looks for one +, -, *, /, s, or ' ', followed by one or more =
        Pattern opPattern = Pattern.compile("(\\+|-|\\*|/|s|\\s)=*", Pattern.CASE_INSENSITIVE);
        Pattern digit = Pattern.compile("\\d+");//does not include negatives!

        //index 1: row
        //index 2: op
        //index 3: the rest
        String[] splitByOp = opPattern.splitWithDelimiters(s, 2);
        //find dest row
        Matcher dRowMatcher = digit.matcher(splitByOp[0]);
        dRowMatcher.find();
        int destRow = Integer.parseInt(dRowMatcher.group());//if there is more than one match, it takes the first one
        char //TODO interpret the op

        splitByOp[0] = Integer.toString(destRow);
        return splitByOp;
    }
    public static void main()
    {
        String s = "1+2";
        System.out.println(Arrays.deepToString(parse(s)));
        Scanner sc = new Scanner(System.in);
        while (true)
        {
            try {
                System.out.println(Arrays.deepToString(parse(sc.nextLine())));
            } catch (IllegalStateException e) {
                System.out.println(e);
            }
            
        }
    }
}
