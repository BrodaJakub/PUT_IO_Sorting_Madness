package pl.put.poznan.sortingmadness.Logic.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortStrategyTest {

    private QuickSortStrategy quickSort;

    @BeforeEach
    void setUp() {
        quickSort = new QuickSortStrategy();
    }

    @Test
    void getName_shouldReturnQuickSort() {
        assertEquals("Quick Sort", quickSort.getName());
    }

    @Test
    void sort_shouldHandleEmptyList() {
        List<Integer> list = new ArrayList<>();
        quickSort.sort(list);
        assertTrue(list.isEmpty());
    }

    @Test
    void sort_shouldHandleSingleElement() {
        List<Integer> list = new ArrayList<>(Collections.singletonList(42));
        quickSort.sort(list);
        assertEquals(Collections.singletonList(42), list);
    }

    @Test
    void sort_shouldSortAscendingNumbers() {
        List<Integer> list = Arrays.asList(5, 2, 9, 1, 5, 6);
        quickSort.sort(list);
        assertEquals(Arrays.asList(1, 2, 5, 5, 6, 9), list);
    }

    @Test
    void sort_shouldSortAlreadySortedList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        quickSort.sort(list);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), list);
    }

    @Test
    void sort_shouldSortDescendingNumbers() {
        List<Integer> list = Arrays.asList(9, 7, 5, 3, 1);
        quickSort.sort(list);
        assertEquals(Arrays.asList(1, 3, 5, 7, 9), list);
    }

    @Test
    void sort_shouldSortWithDuplicates() {
        List<Integer> list = Arrays.asList(3, 1, 2, 3, 1);
        quickSort.sort(list);
        assertEquals(Arrays.asList(1, 1, 2, 3, 3), list);
    }
}
