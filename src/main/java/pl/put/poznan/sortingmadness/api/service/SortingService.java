package pl.put.poznan.sortingmadness.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.put.poznan.sortingmadness.Logic.context.SortingContext;
import pl.put.poznan.sortingmadness.Logic.model.SortResult;
import pl.put.poznan.sortingmadness.Logic.strategy.*;
import pl.put.poznan.sortingmadness.api.dto.SortRequest;
import pl.put.poznan.sortingmadness.api.dto.SortResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SortingService {

    private static final Logger logger = LoggerFactory.getLogger(SortingService.class);

    private final Map<String, SortingStrategy> strategies = Map.of(
            "bubble", new BubbleSortStrategy(),
            "selection", new SelectionSortStrategy(),
            "insertion", new InsertionSortStrategy(),
            "merge", new MergeSortStrategy(),
            "quick", new QuickSortStrategy(),
            "heap", new HeapSortStrategy()
    );

    public <T extends Comparable<T>> SortResponse<T> sort(SortRequest<T> request) {

        if (request == null || !request.isValid()) {
            logger.debug("Odebrano zapytanie z brakiem algorytmów lub danych");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "algorithms and data tables must not be empty"
            );
        }

        List<T> data = request.getData();
        List<SortResponse.AlgorithmSortResult<T>> results = new ArrayList<>();

        for (String algorithm : request.getAlgorithms()) {

            SortingStrategy strategy = strategies.get(algorithm.toLowerCase());
            if (strategy == null) {
                logger.debug("Odebrano zapytanie z niewłaściwą nazwą algorytmu");
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid algorithm name, must be one of bubble, selection, insertion, merge, quick, heap"
                );
            }

            List<T> datac = new ArrayList<>(data);

            SortingContext context = new SortingContext();
            context.setStrategy(strategy);

            SortResult sr = context.execute(datac);
            results.add(new SortResponse.AlgorithmSortResult<>(
                    sr.getAlgorithmName(),
                    sr.getExecutionTimeNano(),
                    datac
            ));
        }

        return new SortResponse<>(results);
    }
}
