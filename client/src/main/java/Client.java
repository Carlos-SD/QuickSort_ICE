import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        // Inicializar ICE
        Ice.Communicator communicator = null;
        try {
            communicator = Ice.Util.initialize(args);

            // Obtener el proxy del objeto remoto
            Ice.ObjectPrx proxy = communicator.stringToProxy("Sorter:tcp -h localhost -p 10000");

            // Convertir el proxy al tipo adecuado
            Demo.SorterPrx sorter = Demo.SorterPrx.checkedCast(proxy);

            if (sorter == null) {
                throw new Error("Invalid proxy");
            }

            // Solicitar al usuario que ingrese los datos a ordenar
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese los datos a ordenar separados por espacios: ");
            String input = scanner.nextLine();

            // Convertir la entrada del usuario en un array de enteros
            String[] dataStr = input.split("\\s+");
            int[] data = new int[dataStr.length];
            for (int i = 0; i < dataStr.length; i++) {
                data[i] = Integer.parseInt(dataStr[i]);
            }

            // Llamar al método remoto para ordenar los datos
            int[] sortedData = sorter.sort(data);

            // Mostrar los datos ordenados
            System.out.print("Datos ordenados: ");
            for (int num : sortedData) {
                System.out.print(num + " ");
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Apagar ICE
            if (communicator != null) {
                try {
                    communicator.destroy();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
