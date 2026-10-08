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
    public static final char OPEN_BRACKET_TOP = '┌';
    public static final char OPEN_BRACKET_MID = '│';
    public static final char OPEN_BRACKET_BOT = '└';
    public static final char OPEN_BRACKET_1_ROW = '[';
    public static final char CLOSE_BRACKET_TOP = '┐';
    public static final char CLOSE_BRACKET_MID = OPEN_BRACKET_MID;
    public static final char CLOSE_BRACKET_BOT = '┘';
    public static final char CLOSE_BRACKET_1_ROW = ']';
    public static final char SEP = OPEN_BRACKET_MID;
    public static final char RHO = 'ρ';
    public static final char LEFT_ARROW = '←';
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

    static {

    }

    public boolean isVector() {
        return false;
    }

    public static boolean isREF(Matrix m) {
        return false;
    }

    public static boolean isRREF(Matrix m) {
        return false;
    }

    public abstract void swap(int row1, int row2);
    public abstract void multiplyRow(Fraction c, int row);
    public abstract void multiplyRow(long c, int row);
    public abstract void addRow(int dest, Fraction c, int src);
    public abstract void addRow(int dest, long c, int src);
    public abstract void REF();
    public abstract void RREF();

    //=====Row op log methods=====
    /**
     * Adds the operation in plain text to the row op log
     * @param operation
     */
    protected void addToRowOpLog(String operation)
    {
        rowOpLog.add(operation);
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
    protected char getOpeningCharacter(int row)
    {
        return getEdgeCharacter(row, OPEN_BRACKET_TOP, OPEN_BRACKET_MID, OPEN_BRACKET_BOT, OPEN_BRACKET_1_ROW);
    }
    protected char getClosingCharacter(int row)
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
    private char getEdgeCharacter(int row, final char top, final char mid, final char bot, final char sing)
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
