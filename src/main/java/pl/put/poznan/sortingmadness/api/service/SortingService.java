package pl.put.poznan.sortingmadness.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.put.poznan.sortingmadness.Logic.context.SortingContext;
import pl.put.poznan.sortingmadness.Logic.model.SortResult;
import pl.put.poznan.sortingmadness.Logic.strategy.*;
import pl.put.poznan.sortingmadness.api.dto.SortRequest;
import pl.put.poznan.sortingmadness.api.dto.SortResponse;
import pl.put.poznan.sortingmadness.cache.CacheKeyBuilder;
import pl.put.poznan.sortingmadness.cache.CacheService;

import java.time.Duration;
import java.util.*;

@Service
public class SortingService {

    private static final Logger logger = LoggerFactory.getLogger(SortingService.class);

    private final CacheService cacheService;
    private final CacheKeyBuilder cacheKeyBuilder;
    private final ObjectMapper objectMapper;

    // TTL w sekundach (możesz ustawić w application.properties)
    private final Duration ttl;

    // Czy kolejność algorithms ma wpływać na cache key?
    // Dla bezpieczeństwa ustawiamy true (kolejność ma znaczenie).
    private static final boolean ALGORITHMS_ORDER_MATTERS = true;

    private final Map<String, SortingStrategy> strategies = Map.of(
            "bubble", new BubbleSortStrategy(),
            "selection", new SelectionSortStrategy(),
            "insertion", new InsertionSortStrategy(),
            "merge", new MergeSortStrategy(),
            "quick", new QuickSortStrategy(),
            "heap", new HeapSortStrategy()
    );

    public SortingService(
            CacheService cacheService,
            CacheKeyBuilder cacheKeyBuilder,
            ObjectMapper objectMapper,
            @Value("${cache.ttl-seconds:86400}") long ttlSeconds // domyślnie 24h
    ) {
        this.cacheService = cacheService;
        this.cacheKeyBuilder = cacheKeyBuilder;
        this.objectMapper = objectMapper;
        this.ttl = Duration.ofSeconds(ttlSeconds);
    }

    public <T extends Comparable<T>> SortWithCacheResult<T> sortWithCache(String sortType, SortRequest<T> request, Class<T> elementClass) {

        if (request == null || !request.isValid()) {
            logger.debug("Odebrano zapytanie z brakiem algorytmów lub danych");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "algorithms and data tables must not be empty"
            );
        }

        String cacheKey = cacheKeyBuilder.buildKey(sortType, request.getAlgorithms(), request.getData(), ALGORITHMS_ORDER_MATTERS);

        // 1) HIT?
        Optional<String> cachedJsonOpt = cacheService.getResponseJsonIfPresent(cacheKey);
        if (cachedJsonOpt.isPresent()) {
            SortResponse<T> cachedResponse = deserializeSortResponse(cachedJsonOpt.get(), elementClass);
            return new SortWithCacheResult<>(cachedResponse, true);
        }

        // 2) MISS -> policz
        SortResponse<T> computed = computeSort(request);

        // 3) Zapisz do cache (request+response jako JSON)
        String requestJson = serialize(request);
        String responseJson = serialize(computed);
        cacheService.put(cacheKey, requestJson, responseJson, ttl);

        return new SortWithCacheResult<>(computed, false);
    }

    private <T extends Comparable<T>> SortResponse<T> computeSort(SortRequest<T> request) {

        List<T> data = request.getData();
        List<SortResponse.AlgorithmSortResult<T>> results = new ArrayList<>();

        for (String algorithm : request.getAlgorithms()) {

            SortingStrategy strategy = strategies.get(algorithm.toLowerCase(Locale.ROOT));
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

    private String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("JSON serialization failed", e);
        }
    }

    private <T extends Comparable<T>> SortResponse<T> deserializeSortResponse(String json, Class<T> elementClass) {
        try {
            JavaType type = objectMapper.getTypeFactory()
                    .constructParametricType(SortResponse.class, elementClass);
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new IllegalStateException("JSON deserialization failed (cached response)", e);
        }
    }

}
