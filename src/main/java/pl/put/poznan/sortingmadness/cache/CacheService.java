package pl.put.poznan.sortingmadness.cache;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
public class CacheService {

    private final CacheEntryRepository repository;

    public CacheService(CacheEntryRepository repository) {
        this.repository = repository;
    }


    @Transactional
    public Optional<String> getResponseJsonIfPresent(String cacheKey) {
        Optional<CacheEntry> entryOpt = repository.findByCacheKey(cacheKey);

        if (!entryOpt.isPresent()) {
            return Optional.empty();
        }

        CacheEntry entry = entryOpt.get();
        if (isExpired(entry.getExpiresAt())) {
            repository.delete(entry);
            return Optional.empty();
        }

        return Optional.of(entry.getResponseJson());
    }

    @Transactional
    public void put(String cacheKey, String requestJson, String responseJson, Duration ttl) {
        Instant now = Instant.now();
        Instant expiresAt = (ttl == null) ? null : now.plus(ttl);

        CacheEntry entry = repository.findByCacheKey(cacheKey)
                .orElseGet(() -> new CacheEntry(cacheKey, requestJson, responseJson, now, expiresAt));

        entry.setRequestJson(requestJson);
        entry.setResponseJson(responseJson);
        entry.setExpiresAt(expiresAt);

        repository.save(entry);
    }

    @Transactional
    public long deleteExpired() {
        return repository.deleteByExpiresAtBefore(Instant.now());
    }

    private boolean isExpired(Instant expiresAt) {
        return expiresAt != null && expiresAt.isBefore(Instant.now());
    }
}
