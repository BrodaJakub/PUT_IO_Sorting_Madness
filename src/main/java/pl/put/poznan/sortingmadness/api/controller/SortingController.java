package pl.put.poznan.sortingmadness.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.api.dto.*;
import pl.put.poznan.sortingmadness.Logic.service.SortingService;
import pl.put.poznan.sortingmadness.Logic.model.SortResult;
import pl.put.poznan.sortingmadness.Logic.context.SortingContext;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.put.poznan.sortingmadness.app.Main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
public class SortingController {

    private final SortingService sortingService;
    final static Logger logger = LoggerFactory.getLogger(SortingController.class);

    public SortingController(SortingService sortingService) {
        this.sortingService = sortingService;
    }


    @RequestMapping("/api/sort/strings")
    @PostMapping
    public SortResponse<String> sortStrings(@RequestBody SortRequest<String> request) {
        logger.debug("Odebrano zapytanie o posortowanie tabeli stringów");

        if (!request.isValid()) {
            logger.debug("Odebrano zapytanie z brakiem algorytmów lub danych");

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "algorithms and data tables must not be empty"
            );
        }

        List<String> data = request.getData();
        List<SortResponse.AlgorithmSortResult<String>> results = new ArrayList<>();
        SortResponse<String> result = new SortResponse<String>();
        for (String algorithm : request.getAlgorithms()) {
            List<String> datac = new ArrayList<String>(data);
            SortResult sr = sortingService.sort(
                    algorithm,
                    datac
            );
            results.add(new SortResponse.AlgorithmSortResult<String>(sr.getAlgorithmName(), sr.getExecutionTimeNano(), datac));

        }
        return new SortResponse<String>(results);
    }

    @RequestMapping("/api/sort/integers")
    @PostMapping
    public SortResponse<Integer> sortIntegers(@RequestBody SortRequest<Integer> request) {
        logger.debug("Odebrano zapytanie o posortowanie tabeli liczb");

        if (!request.isValid()) {
            logger.debug("Odebrano zapytanie z brakiem algorytmów lub danych");

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "algorithms and data tables must not be empty"
            );
        }

        List<Integer> data = request.getData();
        List<SortResponse.AlgorithmSortResult<Integer>> results = new ArrayList<>();
        SortResponse<Integer> result = new SortResponse<Integer>();
        for (String algorithm : request.getAlgorithms()) {

            List<Integer> datac = new ArrayList<Integer>(data);
            SortResult sr = sortingService.sort(
                    algorithm,
                    datac
            );
            results.add(new SortResponse.AlgorithmSortResult<Integer>(sr.getAlgorithmName(), sr.getExecutionTimeNano(), datac));

        }
        return new SortResponse<Integer>(results);
    }
}
