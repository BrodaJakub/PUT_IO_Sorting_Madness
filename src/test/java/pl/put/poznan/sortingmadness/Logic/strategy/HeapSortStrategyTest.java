package pl.put.poznan.sortingmadness.Logic.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortStrategyTest {

    private HeapSortStrategy heapSort;

    @BeforeEach
    void setUp() {
        heapSort = new HeapSortStrategy();
    }

    @Test
    void getName_shouldReturnHeapSort() {
        assertEquals("Heap Sort", heapSort.getName());
    }

    @Test
    void sort_shouldHandleEmptyList() {
        List<Integer> list = new ArrayList<>();
        heapSort.sort(list);
        assertTrue(list.isEmpty());
    }

    @Test
    void sort_shouldHandleSingleElement() {
        List<Integer> list = new ArrayList<>(Collections.singletonList(42));
        heapSort.sort(list);
        assertEquals(Collections.singletonList(42), list);
    }

    @Test
    void sort_shouldSortAscendingNumbers() {
        List<Integer> list = Arrays.asList(5, 2, 9, 1, 5, 6);
        heapSort.sort(list);
        assertEquals(Arrays.asList(1, 2, 5, 5, 6, 9), list);
    }

    @Test
    void sort_shouldSortAlreadySortedList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        heapSort.sort(list);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), list);
    }

    @Test
    void sort_shouldSortDescendingNumbers() {
        List<Integer> list = Arrays.asList(9, 7, 5, 3, 1);
        heapSort.sort(list);
        assertEquals(Arrays.asList(1, 3, 5, 7, 9), list);
    }

    @Test
    void sort_shouldSortWithDuplicates() {
        List<Integer> list = Arrays.asList(3, 1, 2, 3, 1);
        heapSort.sort(list);
        assertEquals(Arrays.asList(1, 1, 2, 3, 3), list);
    }
}
