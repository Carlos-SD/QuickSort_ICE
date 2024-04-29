import MathCal.*;
import com.zeroc.Ice.*;

public class Server {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {
            ObjectAdapter adapter =
                    communicator.createObjectAdapterWithEndpoints("SortAdapter", "tcp -h localhost -p 10000");

            // Crear una instancia del objeto QuickSorter
            QuickSorter sorter = new QuickSorter();

            // Crear un objeto de tipo Sorter para ICE y pasarlo al adapter
            Object servant = new SorterI(sorter);
            adapter.add(servant, Util.stringToIdentity("Sorter"));

            adapter.activate();
            communicator.waitForShutdown();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
