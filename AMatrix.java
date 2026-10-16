/**
 * Augmented matrix class.
 * @author Matthew Park
 * @version v1
 */
public class AMatrix extends Matrix {
    private Fraction[][] left;
    private Fraction[][] right;

    public AMatrix(Fraction[][] left, Fraction[][] right) {
        Matrix.ensureRectangularArray(left);
        Matrix.ensureRectangularArray(right);
        if (left.length != right.length) {throw new IllegalArgumentException("Left and right matricies have different lengths");}
        this.left = left;
        this.right = right;
    }

    //=====Getter methods=====
    @Override
    public int getNumRows() {
        assert(left.length == right.length);
        return left.length;
    }

    @Override
    public int getNumCols() {
        return getNumColsLeft() + right[0].length;
    }
    public int getNumColsLeft() {
        return left[0].length;
    }

    @Override
    public Fraction[] getRow(int rowNum) {
        throw new IllegalArgumentException();
    }

    @Override
    public Fraction[] getCol(int colNum) {
        assert(left.length == right.length);
        Fraction[] returnArr = new Fraction[left.length];
        if (colNum >= getNumColsLeft()) {
            for (int i = 0; i < right.length; i++)//doesn't matter whether u use left.length or right.length
            {
                returnArr[i] = right[i][colNum - getNumColsLeft()];
            }
        } else {
            for (int i = 0; i < left.length; i++)
            {
                returnArr[i] = left[i][colNum];
            }
        }
        return returnArr;
    }

    //=====Row Operation methods=====

    public void sw(int row1, int row2) {
        Fraction[] temp = left[row1];
        left[row1] = left[row2];
        left[row2] = temp;
        temp = right[row1];
        right[row1] = right[row2];
        right[row2] = temp;
    }

    public void multR(Fraction c, int row) {
        for (Fraction frac : left[row])
        {
            frac.teq(c);
        }
        for (Fraction frac : right[row])
        {
            frac.teq(c);
        }
    }

    public void addR(int dest, Fraction c, int src) {
        for (int col = 0; col < getNumColsLeft(); col++) {
            Fraction scaledFraction = Fraction.multiply(left[src][col], c);
            Fraction destF = left[dest][col];
            destF.peq(scaledFraction);
        }
        for (int col = 0; col < right[0].length; col++) {
            Fraction scaledFraction = Fraction.multiply(right[src][col], c);
            Fraction destF = right[dest][col];
            destF.peq(scaledFraction);
        }
    }

    @Override
    public void REF() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void RREF() {
        throw new UnsupportedOperationException();
    }
    //=====String methods=====
    @Override 
    public String toString()
    {
        int colLengths[] = getColLengths();
        //TODO handle the case where the matrix is 1 row
        int finalStringLength = Matrix.OPEN_BRACKET_TOP.length() + Matrix.CLOSE_BRACKET_TOP.length() + Matrix.SEP.length() + 1;//opening bracket, closing bracket, newline, line separator
        for (int num : colLengths)
        {
            finalStringLength += num;
        }
        finalStringLength += getNumCols() - 2;//to account for the space between matrix elements
        finalStringLength *= getNumRows();
        StringBuilder sb = new StringBuilder(finalStringLength);
        for (int r = 0; r < getNumRows(); r++)
        {
            sb.append(getOpeningCharacter(r));
            //add the matrix numbers to the string
            for (int c = 0; c < getNumColsLeft(); c++)
            {
                if (c != 0)
                {sb.append(' ');}
                Fraction currFrac = left[r][c];
                sb.append(currFrac.toCenterString(colLengths[c]));
            }
            sb.append(Matrix.SEP);
            for (int c = getNumColsLeft(); c < getNumCols(); c++) {
                if (c != getNumColsLeft())
                {sb.append(' ');}
                Fraction currFrac = right[r][c - getNumColsLeft()];
                sb.append(currFrac.toCenterString(colLengths[c]));
            }
            sb.append(getClosingCharacter(r));
            sb.append('\n');
        }
        String returnStr = sb.toString();
        assert(finalStringLength == returnStr.length());
        return returnStr;
    }

    //=====Other Methods
    private int[] getColLengths()
    {
        int[] colLengths = new int[getNumCols()];

        for (int col = 0; col < getNumColsLeft(); col++)
        {
            int fattestCol = 0;
            for (int row = 0; row < getNumRows(); row++)
            {
                fattestCol = Math.max(fattestCol, left[row][col].getLength());
            }
            colLengths[col] = fattestCol;
        }
        for (int col = getNumColsLeft(); col < getNumCols(); col++)
        {
            int fattestCol = 0;
            for (int row = 0; row < getNumRows(); row++)
            {
                fattestCol = Math.max(fattestCol, right[row][col - getNumColsLeft()].getLength());
            }
            colLengths[col] = fattestCol;
        }
        return colLengths;
    }
}
