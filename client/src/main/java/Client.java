import com.zeroc.Ice.*;
import Demo.PrinterPrx;
import com.zeroc.Ice.Exception;

import java.util.Arrays;

/**
 * This class contains the main method for a client program that sends an array of integers to a server to be sorted.
 * The client reads the number of elements and the elements themselves from the user, sends the array to the server,
 * receives the sorted array from the server, and displays the sorted array to the user.
 */

 public class Client {

    /**
     * The main method for the client program.
     * @param args The command-line arguments for the program.
     */
    public static void main(String[] args) {
        // Initialize the status variable to 0, which will be used to track the program's exit status
        int status = 0;

        try (Communicator communicator = Util.initialize(args)) {
            // Create a proxy object from the string "SimplePrinter:default -p 10000"
            ObjectPrx base = communicator.stringToProxy("SimplePrinter:default -p 10000");

            // Cast the base object to a PrinterPrx object
            PrinterPrx printer = PrinterPrx.checkedCast(base);

            // Check if the casting was successful, if not, throw an error
            if (printer == null) {
                throw new Error("Invalid proxy");
            }

            // Create a scanner object to read input from the user
            java.util.Scanner scanner = new java.util.Scanner(System.in);

            // Prompt the user to enter the number of elements (or -1 to exit)
            System.out.println("Enter number of elements (or -1 to exit):");

            // Read the number of elements from the user
            int n = scanner.nextInt();

            // Loop until the user enters -1
            while (n!= -1) {
                // Create an array of size n
                int[] arr = new int[n];

                // Prompt the user to enter the elements
                System.out.println("Enter the elements:");

                // Read the elements from the user and store them in the array
                for (int i = 0; i < n; i++) {
                    arr[i] = scanner.nextInt();
                }

                // Send the array to the server to be sorted
                String sortedArray = printer.printString(Arrays.toString(arr));

                // Display the sorted array received from the server
                System.out.println("Sorted array received from server: " + sortedArray);

                // Prompt the user to enter the number of elements again (or -1 to exit)
                System.out.println("Enter number of elements (or -1 to exit):");

                // Read the number of elements from the user
                n = scanner.nextInt();
            }

        } catch (Exception e) {
            // Catch any exceptions that occur during the execution of the try block
            e.printStackTrace();
            // Set the status to 1 if an exception occurs
            status = 1;
        }

        // Exit the program with the specified status
        System.exit(status);
    }
}
