
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
    // /**
    //  * Examples: "p1*=2"
    //  * "p10/=3"
    //  * "p2*=-2/3"
    //  * "p2*=(-2/3)"
    //  * "p2+=3p1"
    //  * "p3+=2/3p1"
    //  * "p3+=(2/3)p1"
    //  * "p3-=3p1"
    //  * "p3-=3/2p1"
    //  * "p3-=(2/3)p1"
    //  * "p3sp2"
    //  * "p3 p2"
    //  * @param s a string that represents the row operation
    //  */
    // public void run(String s)
    // {
    //     parse(s);

    // }
    
    //why was this static and of return type String[]?
    /**
     * Parses the input string and performs the corresponding row operation
     * @param s the input string to parse, which uses one-indexed row numbers
     * @throws ArrayIndexOutOfBoundsException when the regex couldn't be matched
     * @throws IllegalStateException when you don't specify a destination row
     * @throws NumberFormatException when you use /= and the divisor isn't a number
     */
    public void parse(String s)
    {
        // enum RowOps {
        //     ADD, SUBTR, MULT, DIV, SWAP;
        // }
        //finds the operation
        //looks for one +, -, *, /, s, or ' ', followed by one or more =
        Pattern opPattern = Pattern.compile("(\\+|-|\\*|/|s|\\s)=*", Pattern.CASE_INSENSITIVE);
        Pattern digit = Pattern.compile("\\d+");//does not include negatives!
        Pattern rho = Pattern.compile("p", Pattern.CASE_INSENSITIVE);
        //index 1: row
        //index 2: op
        //index 3: the rest
        String[] splitByOp = opPattern.splitWithDelimiters(s, 2);
        if (splitByOp.length != 3) {throw new ArrayIndexOutOfBoundsException("Couldn't find the op code");}
        //find dest row
        Matcher dRowMatcher = digit.matcher(splitByOp[0]);
        dRowMatcher.find();
        int destRow = Integer.parseInt(dRowMatcher.group());//if there is more than one match, it takes the first one
        if (Matrix.ONE_INDEXED) {destRow--;}
        char opCode = splitByOp[1].charAt(0);
        switch (opCode) {
            case '+':
            case '-': {
                String[] rightSplit = rho.split(splitByOp[2], 2);//index 0 contains c, index 1 contains the row number
                String cStr = rightSplit[0];
                String srcRowStr = rightSplit[1];
                Fraction c = new Fraction(cStr);
                int srcRow = Integer.parseInt(srcRowStr);
                if (Matrix.ONE_INDEXED) {srcRow--;}
                if (opCode == '-') {c.teq(-1);}
                m.addRow(destRow, c, srcRow);
                break;
            }
            case '*': {
                Fraction c = new Fraction(splitByOp[2]);
                m.multiplyRow(c, destRow);
                break;
            }
            case '/': {
                String cNoPar = splitByOp[2].replaceAll("[\\(\\)]", "");//remove extra parenthesis
                long c = Long.parseLong(cNoPar);
                m.multiplyRow(new Fraction(1, c), destRow);
                break;
                //fractions not supported when using "/="
            }
            case ' ':
            case 's': {
                String srcRowStr = splitByOp[2].replaceAll("[\\(\\)p]", "");//remove extra parenthesis and rho character

                int srcRow = Integer.parseInt(srcRowStr);
                if (Matrix.ONE_INDEXED) {srcRow--;}
                m.swap(destRow, srcRow);
                break;
            }
            default:
                assert(false);
        }
    }



    // public static void main()
    // {
    //     String s = "1+2";
    //     System.out.println(Arrays.deepToString(parse(s)));
    //     Scanner sc = new Scanner(System.in);
    //     while (true)
    //     {
    //         try {
    //             System.out.println(Arrays.deepToString(parse(sc.nextLine())));
    //         } catch (IllegalStateException e) {
    //             System.out.println(e);
    //         }
            
    //     }
    // }
}
