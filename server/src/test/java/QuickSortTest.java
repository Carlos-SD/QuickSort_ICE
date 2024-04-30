
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
// Import the class you're testing:


import java.util.Arrays;

public class QuickSortTest{

    @Test
    public void testPrintStringSingleElement() {
        QuickSortI sorter = new QuickSortI();
        String input = "[1]";
        String expectedOutput = "[1]";
        String actualOutput = sorter.printString(input, null);
        assertEquals(expectedOutput, actualOutput);
    }
    @Test
    public void testSortAlreadySorted() {
        QuickSortI sorter = new QuickSortI();
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = Arrays.copyOf(arr, arr.length);
        sorter.sort(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);
    }
    @Test
    public void testSortReverseSorted() {
        QuickSortI sorter = new QuickSortI();
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        sorter.sort(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testPartitionEqualElements() {
        QuickSortI sorter = new QuickSortI();
        int[] arr = {3, 3, 3, 2, 1};
        int pivotIndex = sorter.partition(arr, 0, arr.length - 1);
        // All elements less than or equal to the pivot should be on the left side
        for (int i = 0; i < pivotIndex; i++) {
            assertTrue(arr[i] <= arr[pivotIndex]);
        }
    }

    @Test
    public void testSortEmptyArray() {
        QuickSortI sorter = new QuickSortI();
        int[] arr = {};
        int[] expected = {};
        sorter.sort(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testSortSingleElementArray() {
        QuickSortI sorter = new QuickSortI();
        int[] arr = {1};
        int[] expected = {1};
        sorter.sort(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testSortDuplicateElements() {
        QuickSortI sorter = new QuickSortI();
        int[] arr = {5, 3, 2, 5, 2, 1};
        int[] expected = {1, 2, 2, 3, 5, 5};
        sorter.sort(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testSortRandomArray() {
        QuickSortI sorter = new QuickSortI();
        int[] arr = {12, 4, 7, 3, 9, 8, 5};
        int[] expected = {3, 4, 5, 7, 8, 9, 12};
        sorter.sort(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testPartitionSingleElementArray() {
        QuickSortI sorter = new QuickSortI();
        int[] arr = {1};
        int pivotIndex = sorter.partition(arr, 0, arr.length - 1);
        assertEquals(0, pivotIndex); // Expecting pivot index 0 for single element array
    }
}

