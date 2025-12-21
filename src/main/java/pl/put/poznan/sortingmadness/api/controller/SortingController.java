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

import java.util.List;

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

        List<String> data = request.getData();
        SortResult result = sortingService.sort(
                request.getAlgorithm(),
                data
        );

        return new SortResponse<String>(
                result.getAlgorithmName(),
                result.getExecutionTimeNano() / 1_000_000.0,
                data
        );
    }

    @RequestMapping("/api/sort/integers")
    @PostMapping
    public SortResponse<Integer> sortIntegers(@RequestBody SortRequest<Integer> request) {
        logger.debug("Odebrano zapytanie o posortowanie tabeli liczb");

        List<Integer> data = request.getData();
        SortResult result = sortingService.sort(
                request.getAlgorithm(),
                data
        );

        return new SortResponse<Integer>(
                result.getAlgorithmName(),
                result.getExecutionTimeNano() / 1_000_000.0,
                data
        );
    }
}
