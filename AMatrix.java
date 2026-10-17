/**
 * Augmented matrix class.
 * @author Matthew Park
 * @version v1
 */
public class AMatrix extends Matrix {
    private Fraction[][] left;
    private Fraction[][] right;

    /**
     * Initilize an augmented matrix. This conceptually holds two submatricies separated by a line.
     * @param left submatrix on the left side
     * @param right submatrix on the right side
     */
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
    public boolean REF(RowReduceStrategy strat) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean RREF(RowReduceStrategy strat) {
        switch (strat) {
            case ANYTHING_GOES:
                int numLV = 0;//number of leading variables - will update at the end of the loop. This also represents the current row number.
                for (int c = 0; (c < getNumColsLeft()) && (numLV < getNumRows()); c++)
                {
                    //find which row to use as the pivot entry and swap it if necessary
                    if (left[numLV][c].isZero())//don't use this row
                    {
                        //find which row to use based off the three criteria
                        //for _1RowNum, it uses the first row. For the others, it uses the last row
                        int _1RowNum = -1;
                        int intRowNum = -1;
                        int non0RowNum = -1;
                        for (int r = (numLV + 1); r < getNumRows(); r++)
                        {
                            Fraction curr = left[r][c];
                            if (curr.isOne()) {
                                _1RowNum = r;
                                break;
                            } else if (!curr.isZero()) {
                                if (curr.isInt()) {
                                    intRowNum = r;
                                } else {
                                    non0RowNum = r;
                                }
                            }
                        }
                        //if they're all -1, continue onto the next column without incrementing numLV
                        if (_1RowNum >= 0) {
                            swap(numLV, _1RowNum);
                        } else if (intRowNum >= 0) {
                            swap(numLV, intRowNum);
                        } else if (non0RowNum >= 0) {
                            swap(numLV, non0RowNum);
                        } else {
                            continue;
                        }
                    }//else, use this row (do nothing)

                    assert(!left[numLV][c].isZero());
                    if (!left[numLV][c].isOne()) {
                        //multiply the row so that it's one
                        Fraction reciprocal = left[numLV][c].reciprocal();
                        multiplyRow(reciprocal, numLV);
                    }

                    //make the other entries zero
                    for (int r = 0; r < getNumRows(); r++)
                    {
                        if ((r == c) || (left[r][c].isZero())) {continue;}//if doing a row op here would do nothing, skip it
                        addRow(r, left[r][c].swapedSign(), numLV);
                        if (isInconsistent(r)) {
                            System.out.println("Inconsistent row!");
                            return true;
                        }
                    }
                    numLV++;
                }
                return true;
            default:
                return false;
        }
    }
    //=====String methods=====
    @Override 
    public String toString()
    {
        int colLengths[] = getColLengths();
        int finalStringLength =
        (getNumRows() == 1) ? 
        Matrix.OPEN_BRACKET_1_ROW.length() + Matrix.CLOSE_BRACKET_1_ROW.length() + Matrix.SEP.length() + 1 :
        Matrix.OPEN_BRACKET_TOP.length() + Matrix.CLOSE_BRACKET_TOP.length() + Matrix.SEP.length() + 1;//opening bracket, closing bracket, newline, line separator
        for (int num : colLengths)
        {
            finalStringLength += num;
        }
        finalStringLength += Math.max(0, getNumColsLeft() - 1);//to account for the space between matrix elements
        finalStringLength += Math.max(0, right[0].length - 1);
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
        //consider removing finalStringLength because it's taking a disproportionate amount of effort for a tiny amount of time
        Utils.printRuntimeWarningIfFalse(finalStringLength == returnStr.length(), "Expected string length " + finalStringLength + " does not match actual string length " + returnStr.length());
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

    /**
     * Returns true if the zero-indexed row number is inconsistent (it is 0 on the left side and nonzero on the right side)
     * @param rowNum
     * @return
     */
    public boolean isInconsistent(int rowNum)
    {
        for (Fraction elem : left[rowNum])
        {
            if (!elem.isZero()) {
                return false;//if any element on the left side is not zero, return false
            }
        }
        //at this point, the left side should be all 0
        for (Fraction elem : right[rowNum])
        {
            if (!elem.isZero()) {
                return true;//if any elemnt on the right side is not zero, return false
            }
        }
        return false;//at this point, everything is 0, which means it's consistent. Return false.
    }
}
