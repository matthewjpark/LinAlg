import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
    public static Matrix m;
    public static Scanner scnr = new Scanner(System.in);
    // /**
    //  * Prompt the user to enter the number of rows and repeat until the user enters a positive number
    //  * @return
    //  */
    // public static int promptNumRows()
    // {
    //     while (true) {
    //         System.out.println("Enter number of rows: ");
    //         int numRows = scnr.nextInt();
    //         if (numRows <= 0)
    //         {
    //             System.out.println("Number of rows must be at least 1!");
    //             System.uo
    //         }
    //     }
    // }
    /**
     * Create a {@code Matrix} using an interactive {@code Scanner} interface
     * @throws IllegalArgumentException when {@code args[0]} is not A or C (case-insensitive)
     */
    public static void createMatrix(String[] args) {
        char type;
        if ((args.length == 0)) {
            do {
                System.out.print("What type of matrix do you want to make? (A for augmented, C for coefficient): ");
                String typeStr = scnr.next();
                try {
                    type = typeStr.toUpperCase().charAt(0);
                    if ((type == 'A') || (type == 'C')) {
                        break;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {}
                System.out.println("Please enter only A or C.");//this line runs if the exception was caught or if the if statement was false
                
                // if ((type == 'A') || (type == 'C')) {
                //     break;
                // } else {
                //     System.out.println("Please enter only A or C.");
                // }
            } while (true);
        } else {
            type = args[0].toUpperCase().charAt(0);
            if ((type != 'A') && (type != 'C')) {
                throw new IllegalArgumentException("Invalid matrix type. Must be either A or C (case-insensitive).");
            }
        }
        

        if (type == 'A') {
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
            }
            scnr.nextLine();
            m = new AMatrix(mArrLeft, mArrRight);
        } else if (type == 'C') {
            System.out.println("Enter number of rows: ");
            int numRows = scnr.nextInt();
            System.out.println("Enter number of columns:");
            int numCols = scnr.nextInt();
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
    public static void createAMatrix() {
        createMatrix(new String[]{"A"});
    }
    public static void manual() {
        System.out.println("Type \"b\" to go back to the option selector.");
        System.out.println(m.toString());
        RowOpInterpreter manipulator = new RowOpInterpreter(m);
        while (scnr.hasNextLine())
        {
            try {
                String nextLn = scnr.nextLine();
                if (nextLn.equalsIgnoreCase("b")) {
                    break;
                }
                manipulator.parse(nextLn);
                // System.out.println(m.getLastRowOp());
                // System.out.println(m.toString());
            } catch (NumberFormatException | ArithmeticException | /*IllegalStateException |*/ ArrayIndexOutOfBoundsException e) {
                System.out.println("Couldn't parse your input \"" + e.toString() + "\": " + e.toString());
            }
            
        }
    }
    public static void foo() {
        ;
    }
    public static void modeSelect() {
        Runnable simpAny = () -> m.RREF(RowReduceStrategy.ANYTHING_GOES);
        Option[] commands = {
            new Option(Main::createAMatrix, "Create new augmented matrix"),
            new Option(Main::manual, "Perform row operations manually"),
            new Option(Main::foo, "foo"),
            new Option(simpAny, "Auto-RREF with strategy \"Anything goes\""),
            new Option(null, "Exit the program")//this must be the last option
        };
        while (true) {
            int maxDigitLength = String.valueOf(commands.length).length();
            for (int i = 0; i < commands.length; i++)
            {
                Option currCommand = commands[i];
                System.out.printf("%" + maxDigitLength + "d) %s\n", i, currCommand.message());
            }
            System.out.print("\nSelect an option: ");
            int option = scnr.nextInt();
            scnr.nextLine();
            if (option == (commands.length - 1)) {
                System.out.println("Exiting the program.");
                break;
            } else {
                Option selectedOption;
                try {
                    selectedOption = commands[option];
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid option. Please select a number from the possible options.");
                    continue;
                }
                selectedOption.function().run();//run the function associated with the option
                m.printRowOpLog();//print the final result
                System.out.println(m.toString());
            }
        }
    }
	public static void main(String[] args) {
		createMatrix(args);
        System.out.println(m.toString());
        modeSelect();
        //manual();
        scnr.close();
	}
}
