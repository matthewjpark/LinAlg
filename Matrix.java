import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Matrix {
    protected RREFStatus rREFStatus;
    private ArrayList<String> rowOpLog = new ArrayList<String>();

    public abstract int getNumRows();
    public abstract int getNumCols();
    public abstract Fraction[] getRow(int rowNum);
    public abstract Fraction[] getCol(int colNum);
    public static final String OPEN_BRACKET_TOP = "┌";
    public static final String OPEN_BRACKET_MID = "│";
    public static final String OPEN_BRACKET_BOT = "└";
    public static final String OPEN_BRACKET_1_ROW = "[";
    public static final String CLOSE_BRACKET_TOP = "┐";
    public static final String CLOSE_BRACKET_MID = OPEN_BRACKET_MID;
    public static final String CLOSE_BRACKET_BOT = "┘";
    public static final String CLOSE_BRACKET_1_ROW = "]";
    public static final String SEP = OPEN_BRACKET_MID;
    public static final String RHO = "ρ";
    public static final String LEFT_ARROW = "←";
    public static final String ROW_LOG_OPEN_BRACKET = "[";
    public static final String ROW_LOG_CLOSE_BRACKET = "]";
    private static final HashMap<Character, Character> subscriptMap = new HashMap<Character, Character>(Map.of(
        '0', '₀',
        '1', '₁',
        '2', '₂',
        '3', '₃',
        '4', '₄',
        '5', '₅',
        '6', '₆',
        '7', '₇',
        '8', '₈',
        '9', '₉'
    ));
    public boolean isVector() {
        return false;
    }

    public static boolean isREF(Matrix m) {
        return false;
    }

    public static boolean isRREF(Matrix m) {
        return false;
    }
    /**
     * Treat these as private methods
     * @param row1
     * @param row2
     */
    // protected abstract void sw(int row1, int row2);
    // protected abstract void multiR(Fraction c, int row);
    protected abstract void addR(int dest, Fraction c, int src);
//TODO implement these
    // public final void swap(int row1, int row2);
    // public final void multiplyRow(Fraction c, int row);
    // public final void multiplyRow(long c, int row);
    /**
     * @param dest the row number of the destination
     * @param c the multiple of the source row you want to multiply by
     * @param src the row number you want to add to
     */
    public final void addRow(int dest, Fraction c, int src)
    {
        addR(dest, c, src);
        final String destRowStr = getRho(dest);//string that represents the destination row and rho symbol
        StringBuilder rowOp = new StringBuilder(destRowStr);
        rowOp.append(LEFT_ARROW);
        rowOp.append(destRowStr);
        boolean cIsNeg = c.isNegative();//if it's negative, 
        boolean cIsWhole = c.isWhole();//if it's not whole, use parenthesis
        //whole --> no parenthesis; not whole --> parenthesis
        //negative --> - instead of +
        //positive --> +
        if (cIsNeg)
        {
            rowOp.append('-');
        } else {
            rowOp.append('+');
        }
        Fraction abC = c.absOf();//absolute value of C
        String abCStr = abC.toString();
        if (cIsWhole)
        {
            rowOp.append(abCStr);
        } else {
            rowOp.append('(' + abCStr + ')');
        }
        rowOp.append(getRho(src));



        //addToRowOpLog(destRowStr + LEFT_ARROW + destRowStr + "+(" + c.toString() + ")");
    }
    public final void addRow(int dest, long c, int src)
    {
        addRow(dest, new Fraction(c), src);
    }
    public abstract void REF();
    public abstract void RREF();



    //=====Row op log methods=====
    /**
     * Adds the operation in plain text to the row op log.
     * @param operation the operation, not including [].
     */
    protected void addToRowOpLog(String operation)
    {
        rowOpLog.add(ROW_LOG_OPEN_BRACKET + operation + ROW_LOG_CLOSE_BRACKET);
    }
    public void printRowOpLog()
    {
        for (String entry : rowOpLog)
        {
            System.out.println(entry);
        }
    }
    public void clearRowOpLog()
    {
        rowOpLog.clear();
    }
    //=====character methods=====
    /**
     * Get the correct character corresponding to the opening of a matrix based on the row
     * @param row
     * @return
     * @throws IllegalArgumentException when the row is not in the matrix
     */
    protected String getOpeningCharacter(int row)
    {
        return getEdgeCharacter(row, OPEN_BRACKET_TOP, OPEN_BRACKET_MID, OPEN_BRACKET_BOT, OPEN_BRACKET_1_ROW);
    }
    protected String getClosingCharacter(int row)
    {
        return getEdgeCharacter(row, CLOSE_BRACKET_TOP, CLOSE_BRACKET_MID, CLOSE_BRACKET_BOT, CLOSE_BRACKET_1_ROW);
    }

    /**
     * Get the correct character corresponding to the edge of the matrix based on the row
     * @param row the row number
     * @param top the character to use if it's the first row
     * @param mid the character to use if it's neither the first nor last row
     * @param bot the character to use if it's the last row
     * @param sing the character to use if the matrix only has 1 row
     * @return
     */
    private String getEdgeCharacter(int row, final String top, final String mid, final String bot, final String sing)
    {
        if (row >= getNumRows() || row < 0)
        {throw new IndexOutOfBoundsException("Specified row does not exist");}

        if (row == 0)
        {
            if (getNumRows() == 1)
            {return sing;}
            //else
            return top;
        } else if (row == (getNumRows() - 1))
        {return bot;}
        else
        {return mid;}
    }
    public static char toSubscript(char input)
    {
        return subscriptMap.get(input);
    }
    public static char toSubscript(long input)//test for invalid inputs
    {
        return subscriptMap.get((char)(input + '0'));
    }
    /**
     * @param row a row number
     * @return a string that is the rho character + the row # as a subscript
     */
    public static String getRho(int row)
    {
        return RHO + toSubscript(row);
    }
    //=====Indexing methods=====
    /**
     * Convert a zero-indexed index to a one-indexed index
     * @return
     */
    public static int cToH(int cInd)
    {   return ++cInd;  }
    public static 

    /**
     * Makes sure the 2d array is a rectangular array and at least 1x1
     * @param arr
     * @throws IllegalArgumentException when it's not a rectangular array
     * @throws IndexOutOfBoundsException when the array is null (double chek if this is the case)
     */
    public static void ensureRectangularArray(Object[][] arr)
    {
        if (arr.length == 0) {return;}//an empty array is  valid
        final int rowLength = arr[0].length;
        for (int r = 0; r < arr.length; r++)
        {
            if (arr[r].length != rowLength) {throw new IllegalArgumentException("Jagged array");}
        }
        return;
    }
}
