package pl.put.poznan.sortingmadness.api.controller;

import pl.put.poznan.sortingmadness.api.dto.*;
import pl.put.poznan.sortingmadness.Logic.service.SortingService;
import pl.put.poznan.sortingmadness.Logic.model.SortResult;
import pl.put.poznan.sortingmadness.Logic.context.SortingContext;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/sort")
public class SortingController {

    private final SortingService sortingService;

    public SortingController(SortingService sortingService) {
        this.sortingService = sortingService;
    }

    @PostMapping
    public SortResponse sort(@RequestBody SortRequest request) {

        SortResult result = sortingService.sort(
                request.getAlgorithm(),
                request.getData()
        );

        return new SortResponse(
                result.getAlgorithmName(),
                result.getExecutionTimeNano() / 1_000_000.0,
                result.getSortedArray()
        );
    }
}
