public class CMatrix extends Matrix {
    private Fraction[][] elems;

    /**
     * Initialize a {@code CMatrix} using the default indexing mode
     * @param m
     */
    public CMatrix(Fraction[][] m) {
        Matrix.ensureRectangularArray(m);
        elems = m;
    }

    @Override
    public int getNumRows() {
        return elems.length;
    }

    @Override
    public int getNumCols() {
        return elems[0].length;
    }

    @Override
    public Fraction[] getRow(int rowNum) {
        return elems[rowNum];
    }

    @Override
    public Fraction[] getCol(int colNum) {
        Fraction[] returnArr = new Fraction[elems.length];
        for (int i = 0; i < elems.length; i++)
        {
            returnArr[i] = elems[i][colNum];
        }
        return returnArr;
    }

    //TODO implement these
    // @Override
    // public void swap(int row1, int row2) {
    //     Fraction[] temp = elems[row1];
    //     elems[row1] = elems[row2];
    //     elems[row2] = temp;
    // }

    // @Override
    // public void multiplyRow(Fraction c, int row) {
    //     for (Fraction frac : elems[row])
    //     {
    //         frac.teq(c);
    //     }
    // }

    // @Override
    // public void multiplyRow(long c, int row) {
    //     for (Fraction frac : elems[row])
    //     {
    //         frac.teq(c);
    //     }
    // }


    /**
     * Internal add row method
     * @param dest
     * @param c
     * @param src
     */
    protected void addR(int dest, Fraction c, int src) {
        for (int col = 0; col < getNumCols(); col++) {
            Fraction scaledFraction = Fraction.multiply(elems[src][col], c);
            Fraction destF = elems[dest][col];
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

    public String toString()
    {
        int colLengths[] = getColLengths();
        int finalStringLength = 1 + 1 + 1;//opening bracket, closing bracket, newline
        for (int num : colLengths)
        {
            finalStringLength += num;
        }
        finalStringLength += getNumCols() - 1;//to account for the space between matrix elements
        finalStringLength *= getNumRows();
        StringBuilder sb = new StringBuilder(finalStringLength);
        for (int r = 0; r < getNumRows(); r++)
        {
            sb.append(getOpeningCharacter(r));
            //add the matrix numbers to the string
            for (int c = 0; c < getNumCols(); c++)
            {
                if (c != 0)
                {
                    sb.append(' ');
                }
                Fraction currFrac = elems[r][c];
                sb.append(currFrac.toCenterString(colLengths[c]));  
            }


            sb.append(getClosingCharacter(r));
            sb.append('\n');
        }
        String returnStr = sb.toString();
        assert(finalStringLength == returnStr.length());
        return returnStr;
    }

    //TODO Improve the complexity of this later using a heap
    /**
     * 
     * @return an array that contains the length fattest fraction of each column. Each index corresponds to a column.
     */
    private int[] getColLengths()
    {
        int[] colLengths = new int[getNumCols()];
        
        for (int col = 0; col < getNumCols(); col++)
        {
            int fattestCol = 0;
            for (int row = 0; row < getNumRows(); row++)
            {
                fattestCol = Math.max(fattestCol, elems[row][col].getLength());
            }
            colLengths[col] = fattestCol;
        }
        return colLengths;
    }

}
