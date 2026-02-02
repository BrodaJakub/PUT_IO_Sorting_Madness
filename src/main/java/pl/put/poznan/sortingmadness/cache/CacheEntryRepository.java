package pl.put.poznan.sortingmadness.cache;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface CacheEntryRepository extends JpaRepository<CacheEntry, Long> {

    Optional<CacheEntry> findByCacheKey(String cacheKey);

    long deleteByExpiresAtBefore(Instant now);
}
