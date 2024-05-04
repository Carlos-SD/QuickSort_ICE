import com.zeroc.Ice.*;
import Demo.PrinterPrx;
import com.zeroc.Ice.Exception;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Client {

    public static void main(String[] args) {
        int status = 0;

        try (Communicator communicator = Util.initialize(args, "client.cfg")) {
            // Create proxy objects for multiple servers
            PrinterPrx printer1 = getProxy(communicator, "sort.proxy");
            PrinterPrx printer2 = getProxy(communicator, "sort.proxy2");
            PrinterPrx printer3 = getProxy(communicator, "sort.proxy3");

            java.util.Scanner scanner = new java.util.Scanner(System.in);

            System.out.println("Enter number of elements (or -1 to exit):");
            int n = scanner.nextInt();

            while (n != -1) {
                int[] arr = new int[n];

                System.out.println("Enter the elements:");
                for (int i = 0; i < n; i++) {
                    arr[i] = scanner.nextInt();
                }

                // Calculate the size of each subarray
                int subArraySize = n / 3;
                int remainder = n % 3;

                // Divide the array into three subarrays
                int[] arr1 = Arrays.copyOfRange(arr, 0, subArraySize + remainder);
                int[] arr2 = Arrays.copyOfRange(arr, subArraySize + remainder, 2 * subArraySize + remainder);
                int[] arr3 = Arrays.copyOfRange(arr, 2 * subArraySize + remainder, n);

                // Send subarrays to servers asynchronously
                CompletableFuture<String> result1 = sortArrayAsync(printer1, arr1);
                CompletableFuture<String> result2 = sortArrayAsync(printer2, arr2);
                CompletableFuture<String> result3 = sortArrayAsync(printer3, arr3);

                // Combine results from all servers
                CompletableFuture<Void> allOf = CompletableFuture.allOf(result1, result2, result3);
                allOf.thenRun(() -> {
                    try {
                        // Get the results from CompletableFuture
                        String sortedArray1 = result1.get();
                        String sortedArray2 = result2.get();
                        String sortedArray3 = result3.get();

                        // Display combined results
                        System.out.println("Sorted array received from server 1: " + sortedArray1);
                        System.out.println("Sorted array received from server 2: " + sortedArray2);
                        System.out.println("Sorted array received from server 3: " + sortedArray3);
                        System.out.println("-------------------------------------");
                        // Combine results into a single array
                        String combinedArray = sortedArray1 + ", " + sortedArray2 + ", " + sortedArray3;

                        // Remove the leading "[" and trailing "]" characters from combinedArray
                        combinedArray = combinedArray.replaceAll("\\[|\\]", "");

                        // Split the combined array into individual elements
                        String[] combinedArrStrings = combinedArray.split(", ");

                        // Convert the array elements to integers
                        int[] combinedArr = Arrays.stream(combinedArrStrings)
                                .mapToInt(Integer::parseInt)
                                .toArray();

                        int combinedLength = combinedArr.length;
                        int mid = combinedLength / 2;

                        int[] subArray1 = Arrays.copyOfRange(combinedArr, 0, mid);
                        int[] subArray2 = Arrays.copyOfRange(combinedArr, mid, combinedLength);

                        // Send subarrays to servers asynchronously
                        CompletableFuture<String> result4 = sortArrayAsync(printer1, subArray1);
                        CompletableFuture<String> result5 = sortArrayAsync(printer2, subArray2);

                        // Combine results from the two servers
                        CompletableFuture<Void> allOf2 = CompletableFuture.allOf(result4, result5);
                        allOf2.thenRun(() -> {
                            try {
                                // Get the results from CompletableFuture
                                String sortedSubArray1 = result4.get();
                                String sortedSubArray2 = result5.get();

                                // Display combined results
                                System.out.println("Sorted subarray received from server 1: " + sortedSubArray1);
                                System.out.println("Sorted subarray received from server 2: " + sortedSubArray2);
                                System.out.println("-----------------------------------------");

                                String combinedArray2 = sortedSubArray1 + ", " + sortedSubArray2;

                                combinedArray2 = combinedArray2.replaceAll("\\[|\\]", "");

                                String[] combinedArrStrings2 = combinedArray2.split(", ");

                                int[] combinedArr2 = Arrays.stream(combinedArrStrings2)
                                        .mapToInt(Integer::parseInt)
                                        .toArray();

                                CompletableFuture<String> result6 = sortArrayAsync(printer1, combinedArr2);

                                String sortedSubArray3 = result6.get();

                                System.out.println("Sorted subarray received from server 1: " + sortedSubArray3);
                            } catch (InterruptedException | ExecutionException ex) {
                                throw new RuntimeException(ex);
                            }
                        }).get(); // Wait for all tasks to complete
                    } catch (InterruptedException | ExecutionException ex) {
                        throw new RuntimeException(ex);
                    }
                }).get(); // Wait for all tasks to complete

                System.out.println("Enter number of elements (or -1 to exit):");
                n = scanner.nextInt();
            }

        } catch (Exception e) {
            e.printStackTrace();
            status = 1;
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.exit(status);
    }


    // Helper method to create proxy object from configuration
    private static PrinterPrx getProxy(Communicator communicator, String configKey) {
        String proxyConfig = communicator.getProperties().getProperty(configKey);
        ObjectPrx base = communicator.stringToProxy(proxyConfig);
        return PrinterPrx.checkedCast(base);
    }

    // Method to sort array asynchronously
    private static CompletableFuture<String> sortArrayAsync(PrinterPrx printer, int[] arr) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Send the array to the server to be sorted
                return printer.printString(Arrays.toString(arr));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
