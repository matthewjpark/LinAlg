import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
    public static Matrix m;
    public static Scanner scnr = new Scanner(System.in);
    public static void createMatrix() {
        String type;
        do {
            System.out.print("What type of matrix do you want to make? (A for augmented, C for coefficient): ");
            type = scnr.next();
            type = type.toUpperCase();
            if ((type.charAt(0) == 'A') || (type.charAt(0) == 'C')) {
                break;
            } else {
                System.out.println("Please enter only A or C.");
            }
        } while (true);
        

        if (type.charAt(0) == 'A') {
            System.out.println("Enter number of rows: ");
            int numRows = scnr.nextInt();
            System.out.println("Enter number of columns on the left side:");
            int numColsL = scnr.nextInt();
            System.out.println("Enter number of columns on the right side:");
            int numColsR = scnr.nextInt();
            Fraction[][] mArrLeft = new Fraction[numRows][numColsL];
            Fraction[][] mArrRight = new Fraction[numRows][numColsR];
            for (int i = 0; i < numRows; i++) {
                System.out.println("Enter contents of row " + (i + 1) + ":");
                String nextInput = null;
                try {
                    for (int j = 0; j < numColsL; j++) {
                        mArrLeft[i][j] = new Fraction(scnr.next());
                    }
                    for (int j = 0; j < numColsR; j++) {
                        mArrRight[i][j] = new Fraction(scnr.next());
                    }
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Couldn't parse your input " + nextInput + ": " + e.toString());
                    scnr.nextLine();//flush scanner
                    i--;
                    continue;
                }
                scnr.nextLine();
            }
            m = new AMatrix(mArrLeft, mArrRight);
        } else if (type.charAt(0) == 'C') {
            System.out.println("Enter number of rows: ");
            int numRows = scnr.nextInt();
            System.out.println("Enter number of columns:");
            int numCols = scnr.nextInt();
            scnr.nextLine();//flush scanner
            Fraction[][] mArr = new Fraction[numRows][numCols];
            for (int i = 0; i < numRows; i++) {
                System.out.println("Enter contents of row " + (i + 1) + ":");
                String nextInput = null;
                try {
                    for (int j = 0; j < numCols; j++) {
                        nextInput = scnr.next();
                        mArr[i][j] = new Fraction(nextInput);
                    }
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Couldn't parse your input " + nextInput + ": " + e.toString());
                    scnr.nextLine();//flush scanner
                    i--;
                    continue;
                }
                
            }
            m = new CMatrix(mArr);
        }
    }
	public static void main(String[] args) {
		createMatrix();
        System.out.println(m.toString());
        RowOpInterpreter manipulator = new RowOpInterpreter(m);
        while (scnr.hasNext())
        {
            try {
                manipulator.parse(scnr.nextLine());
                System.out.println(m.getLastRowOp());
                System.out.println(m.toString());
            } catch (NumberFormatException | /*IllegalStateException |*/ ArrayIndexOutOfBoundsException e) {
                System.out.println("Couldn't parse your input: " + e.toString());
            } catch (ArithmeticException e) {
                System.out.println(e.toString() + "(Note: the program has not ended)");
            }
            
        }
        m.printRowOpLog();
        System.out.println(m.toString());
        scnr.close();
	}
}
