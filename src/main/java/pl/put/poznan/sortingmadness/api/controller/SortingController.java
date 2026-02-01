package pl.put.poznan.sortingmadness.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.put.poznan.sortingmadness.api.dto.SortRequest;
import pl.put.poznan.sortingmadness.api.dto.SortResponse;
import pl.put.poznan.sortingmadness.api.service.SortWithCacheResult;
import pl.put.poznan.sortingmadness.api.service.SortingService;

@RestController
public class SortingController {

    private static final Logger logger = LoggerFactory.getLogger(SortingController.class);

    private final SortingService sortingService;

    public SortingController(SortingService sortingService) {
        this.sortingService = sortingService;
    }

    @PostMapping("/api/sort/strings")
    public ResponseEntity<SortResponse<String>> sortStrings(@RequestBody SortRequest<String> request) {
        logger.debug("Odebrano zapytanie o posortowanie tabeli stringów");

        SortWithCacheResult<String> result = sortingService.sortWithCache("strings", request, String.class);

        return ResponseEntity.ok()
                .header("X-Cache", result.isCached() ? "HIT" : "MISS")
                .body(result.getResponse());
    }

    @PostMapping("/api/sort/integers")
    public ResponseEntity<SortResponse<Integer>> sortIntegers(@RequestBody SortRequest<Integer> request) {
        logger.debug("Odebrano zapytanie o posortowanie tabeli liczb");

        SortWithCacheResult<Integer> result = sortingService.sortWithCache("integers", request, Integer.class);

        return ResponseEntity.ok()
                .header("X-Cache", result.isCached() ? "HIT" : "MISS")
                .body(result.getResponse());
    }
}
