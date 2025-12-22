package pl.put.poznan.sortingmadness.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.Logic.strategy.*;
import pl.put.poznan.sortingmadness.api.dto.*;
import pl.put.poznan.sortingmadness.Logic.model.SortResult;
import pl.put.poznan.sortingmadness.Logic.context.SortingContext;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
public class SortingController {

    final static Logger logger = LoggerFactory.getLogger(SortingController.class);


    @RequestMapping("/api/sort/strings")
    @PostMapping
    public SortResponse<String> sortStrings(@RequestBody SortRequest<String> request) {
        logger.debug("Odebrano zapytanie o posortowanie tabeli stringów");

        return performSort(request);
    }

    @RequestMapping("/api/sort/integers")
    @PostMapping
    public SortResponse<Integer> sortIntegers(@RequestBody SortRequest<Integer> request) {
        logger.debug("Odebrano zapytanie o posortowanie tabeli liczb");


        return performSort(request);
    }

    public <T extends Comparable<T>> SortResponse<T> performSort(@RequestBody SortRequest<T> request) {

        if (!request.isValid()) {
            logger.debug("Odebrano zapytanie z brakiem algorytmów lub danych");

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "algorithms and data tables must not be empty"
            );
        }
        Map<String, SortingStrategy> strategies = Map.of(
                "bubble", new BubbleSortStrategy(),
                "selection", new SelectionSortStrategy(),
                "insertion", new InsertionSortStrategy(),
                "merge", new MergeSortStrategy(),
                "quick", new QuickSortStrategy(),
                "heap", new HeapSortStrategy()
        );
        List<T> data = request.getData();
        List<SortResponse.AlgorithmSortResult<T>> results = new ArrayList<>();
        SortResponse<T> result = new SortResponse<T>();
        for (String algorithm : request.getAlgorithms()) {


            SortingStrategy strategy = strategies.get(algorithm.toLowerCase());
            if (strategy == null) {
                logger.debug("Odebrano zapytanie z niewłaściwą nazwą algorytmu");

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid algorithm name, must be one of bubble, selection, insertion, merge, quick, heap"
                );
            }

            List<T> datac = new ArrayList<T>(data);

            SortingContext context = new SortingContext();
            context.setStrategy(strategy);

            SortResult sr = context.execute(datac);
            results.add(new SortResponse.AlgorithmSortResult<T>(sr.getAlgorithmName(), sr.getExecutionTimeNano(), datac));

        }
        return new SortResponse<T>(results);
    }
}
