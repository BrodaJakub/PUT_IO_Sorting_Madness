package pl.put.poznan.sortingmadness.Logic.model;

/**
 * Reprezentuje wynik wykonania algorytmu sortowania.
 * <p>
 * Klasa przechowuje podstawowe informacje dotyczące działania
 * algorytmu sortującego, takie jak jego nazwa oraz czas wykonania
 * mierzony w nanosekundach. Obiekt tej klasy może być wykorzystywany
 * do porównywania wydajności różnych algorytmów sortowania.
 * </p>
 */

public class SortResult {

    /**
     * Nazwa algorytmu sortującego.
     */
    private final String algorithmName;

    /**
     * Czas wykonania algorytmu wyrażony w nanosekundach.
     */
    private final long executionTimeNano;

    /**
     * Tworzy nowy obiekt {@code SortResult} z podaną nazwą algorytmu
     * oraz czasem jego wykonania.
     *
     * @param algorithmName nazwa algorytmu sortującego
     * @param executionTimeNano czas wykonania algorytmu w nanosekundach
     */
    public SortResult(String algorithmName, long executionTimeNano) {
        this.algorithmName = algorithmName;
        this.executionTimeNano = executionTimeNano;
    }

    /**
     * Zwraca nazwę algorytmu sortującego.
     *
     * @return nazwa algorytmu
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /**
     * Zwraca czas wykonania algorytmu sortującego.
     *
     * @return czas wykonania w nanosekundach
     */
    public long getExecutionTimeNano() {
        return executionTimeNano;
    }
}
