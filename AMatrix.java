public class AMatrix extends Matrix {
    @SuppressWarnings("unused") private CMatrix left;
    @SuppressWarnings("unused") private CMatrix right;

    public AMatrix(Fraction[][] left, Fraction[][] right) {
    }

    @Override
    public int getNumRows() {
        return 0;
    }

    @Override
    public int getNumCols() {
        return 0;
    }

    @Override
    public Fraction[] getRow(int rowNum) {
        return null;
    }

    @Override
    public Fraction[] getCol(int colNum) {
        return null;
    }

    @Override
    public void swap(int row1, int row2) {
    }

    @Override
    public void multiplyRow(Fraction c, int row) {
    }

    @Override
    public void multiplyRow(long c, int row) {
    }

    @Override
    public void addRow(int dest, Fraction c, int src) {
    }

    @Override
    public void addRow(int dest, long c, int src) {
    }

    @Override
    public void REF() {
    }

    @Override
    public void RREF() {
    }
}
