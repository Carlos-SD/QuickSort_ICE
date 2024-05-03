import Demo.Printer;
import com.zeroc.Ice.Current;

class QuickSortI implements Printer {

        /**
     * This method takes a string s as input, which represents an array of integers separated by commas and enclosed in square brackets.
     * It converts the string to an array of integers, sorts the array using the QuickSort algorithm, and then converts the sorted array back to a string.
     * The sorted string is then returned.
     *
     * @param s the string to sort, which represents an array of integers separated by commas and enclosed in square brackets
     * @param current not used in the code
     * @return the sorted string
     */
    public String printString(String s, Current current) {
        // Convert the string to an array of integers
        String[] strArray = s.substring(1, s.length() - 1).split(", ");
        int[] arr = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            arr[i] = Integer.parseInt(strArray[i]);
        }

        // Sort the array using QuickSort
        sort(arr, 0, arr.length - 1);

        // Convert the sorted array back to a string
        StringBuilder sortedArray = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sortedArray.append(arr[i]);
            if (i < arr.length - 1) {
                sortedArray.append(", ");
            }
        }
        sortedArray.append("]");

        return sortedArray.toString();
    }

    /**
     * This method is a helper method for the QuickSort algorithm. It takes an array arr and two indices start and end as input.
     * It recursively sorts the array using the QuickSort algorithm.
     *
     * @param arr the array to sort
     * @param start the starting index of the array to sort
     * @param end the ending index of the array to sort
     */
    public void sort(int arr[], int start, int end) {
        if (start < end) {
            int pIndex = partition(arr, start, end);
            sort(arr, start, pIndex - 1);
            sort(arr, pIndex + 1, end);
        }
    }

    /**
     * This method is another helper method for the QuickSort algorithm. It takes an array arr and two indices start and end as input.
     * It partitions the array around a pivot element, which is chosen as the last element of the array. It returns the index of the pivot element after partitioning.
     *
     * @param arr the array to partition
     * @param start the starting index of the array to partition
     * @param end the ending index of the array to partition
     * @return the index of the pivot element after partitioning
     */
    public int partition(int arr[], int start, int end) {
        int pivot = arr[end];
        int pIndex = start;
        for (int i = start; i < end; i++) {
            if (arr[i] <= pivot) {
                swap(arr, i, pIndex);
                pIndex++;
            }
        }
        swap(arr, pIndex, end);
        return pIndex;
    }

    /**
     * This method swaps two elements in an array.
     *
     * @param arr the array containing the elements to swap
     * @param x the index of the first element to swap
     * @param y the index of the second element to swap
     */
    private void swap(int arr[], int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }
}
