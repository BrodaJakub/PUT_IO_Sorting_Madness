package pl.put.poznan.sortingmadness.Logic.context;

import pl.put.poznan.sortingmadness.Logic.model.SortResult;
import pl.put.poznan.sortingmadness.Logic.strategy.SortingStrategy;

import java.util.List;

/**
 * Kontekst wzorca projektowego Strategy.
 * <p>
 * Klasa odpowiada za przechowywanie aktualnej strategii sortowania
 * oraz delegowanie do niej wykonania algorytmu.
 * </p>
 *
 * Strategia sortowania może być zmieniana dynamicznie za pomocą metody
 * {@link #setStrategy(SortingStrategy)}.
 *
 *
 */
public class SortingContext {

    /** Aktualnie ustawiona strategia sortowania */
    private SortingStrategy strategy;

    /**
     * Ustawia strategię sortowania.
     *
     * @param strategy obiekt implementujący {@link SortingStrategy}
     */
    public void setStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Wykonuje sortowanie przy użyciu aktualnie ustawionej strategii.
     *
     * @param data lista elementów do posortowania
     * @param <T> typ elementów, który musi implementować {@link Comparable}
     * @return wynik sortowania jako obiekt {@link SortResult}
     * @throws IllegalStateException jeśli strategia sortowania nie została ustawiona
     */
    public <T extends Comparable<T>> SortResult execute(List<T> data) {
        if (strategy == null) {
            throw new IllegalStateException("Nie ustawiono strategii sortowania");
        }
        return strategy.execute(data);
    }
}
