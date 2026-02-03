package pl.put.poznan.sortingmadness.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import pl.put.poznan.sortingmadness.api.dto.SortRequest;
import pl.put.poznan.sortingmadness.cache.CacheKeyBuilder;
import pl.put.poznan.sortingmadness.cache.CacheService;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SortingServiceTest {

    @Mock
    private CacheService cacheService;

    @Mock
    private CacheKeyBuilder cacheKeyBuilder;

    @Mock
    private ObjectMapper objectMapper;

    private SortingService sortingService;

    @BeforeEach
    void setup() {
        sortingService = new SortingService(
                cacheService,
                cacheKeyBuilder,
                objectMapper,
                60);
    }

    @Test
    void shouldThrowExceptionWhenRequestIsNull() {
        assertThrows(ResponseStatusException.class,
                () -> sortingService.sortWithCache("test", null, Integer.class));
    }

    @Test
    void shouldThrowExceptionWhenRequestIsInvalid() {
        SortRequest<Integer> request = mock(SortRequest.class);
        when(request.isValid()).thenReturn(false);

        assertThrows(ResponseStatusException.class,
                () -> sortingService.sortWithCache("test", request, Integer.class));
    }

    @Test
    void shouldComputeAndCacheResultWhenCacheMiss() throws Exception {
        SortRequest<Integer> request = mockValidRequest();

        when(cacheKeyBuilder.buildKey(any(), any(), any(), anyBoolean()))
                .thenReturn("key");
        when(cacheService.getResponseJsonIfPresent(any()))
                .thenReturn(Optional.empty());
        when(objectMapper.writeValueAsString(any()))
                .thenReturn("{}");

        var result = sortingService.sortWithCache("test", request, Integer.class);

        assertFalse(result.isCached());
        verify(cacheService).put(any(), any(), any(), any(Duration.class));
    }

    @Test
    void shouldThrowExceptionForInvalidAlgorithm() {
        SortRequest<Integer> request = new SortRequest<>(
                List.of("invalid"),
                List.of(1, 2, 3));

        when(cacheKeyBuilder.buildKey(any(), any(), any(), anyBoolean()))
                .thenReturn("key");
        when(cacheService.getResponseJsonIfPresent(any()))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> sortingService.sortWithCache("test", request, Integer.class));
    }

    @Test
    void shouldProcessMultipleAlgorithms() throws Exception {
        SortRequest<Integer> request = new SortRequest<>(
                List.of("quick", "heap"),
                List.of(3, 1, 2));

        when(cacheKeyBuilder.buildKey(any(), any(), any(), anyBoolean()))
                .thenReturn("key");
        when(cacheService.getResponseJsonIfPresent(any()))
                .thenReturn(Optional.empty());
        when(objectMapper.writeValueAsString(any()))
                .thenReturn("{}");

        var result = sortingService.sortWithCache("test", request, Integer.class);

        assertEquals(2, result.getResponse().getAlgorithmResults().size());
    }

    @Test
    void shouldBuildCacheKeyUsingBuilder() throws Exception {
        SortRequest<Integer> request = mockValidRequest();

        when(cacheKeyBuilder.buildKey(any(), any(), any(), anyBoolean()))
                .thenReturn("key");
        when(cacheService.getResponseJsonIfPresent(any()))
                .thenReturn(Optional.empty());

        doReturn("{}")
                .when(objectMapper)
                .writeValueAsString(any());

        sortingService.sortWithCache("type", request, Integer.class);

        verify(cacheKeyBuilder).buildKey(any(), any(), any(), eq(true));
    }

    @Test
    void shouldThrowExceptionWhenSerializationFails() throws Exception {
        SortRequest<Integer> request = mockValidRequest();

        when(cacheKeyBuilder.buildKey(any(), any(), any(), anyBoolean()))
                .thenReturn("key");
        when(cacheService.getResponseJsonIfPresent(any()))
                .thenReturn(Optional.empty());

        doThrow(new com.fasterxml.jackson.core.JsonProcessingException("fail") {
        })
                .when(objectMapper)
                .writeValueAsString(any());

        assertThrows(IllegalStateException.class,
                () -> sortingService.sortWithCache("test", request, Integer.class));
    }

    private SortRequest<Integer> mockValidRequest() {
        SortRequest<Integer> request = mock(SortRequest.class);
        when(request.isValid()).thenReturn(true);
        when(request.getAlgorithms()).thenReturn(List.of("quick"));
        when(request.getData()).thenReturn(List.of(3, 1, 2));
        return request;
    }
}
