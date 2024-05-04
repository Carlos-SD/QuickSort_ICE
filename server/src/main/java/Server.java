import com.zeroc.Ice.*;
import Demo.Printer;
import com.zeroc.Ice.Exception;

public class Server {
    /**
     * The main method initializes the application and sets up the communication adapter for the Printer object.
     *
     * @param args The command line arguments passed to the application.
     * @throws Exception If there is an error during initialization or execution of the application.
     */
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args, "server.cfg")) {
            /**
             * Creates an ObjectAdapter object with the name "SimplePrinterAdapter" and the endpoint "default -p 10000".
             */
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("SimplePrinterAdapter", "default -p 10003");
            /**
             * Creates a Printer object of type QuickSortI.
             */
            Printer printer = new QuickSortI();
            /**
             * Adds the Printer object to the ObjectAdapter with the identity "SimplePrinter".
             */
            adapter.add(printer, Util.stringToIdentity("SimplerSort3"));
            /**
             * Activates the ObjectAdapter.
             */
            adapter.activate();
            /**
             * Prints a message to the console indicating that the server is ready to receive requests.
             */
            System.out.println("Server is ready to receive requests...");
            /**
             * Waits for the Communicator to shut down.
             */
            communicator.waitForShutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}