package pl.put.poznan.sortingmadness.cache;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CacheSmokeRunner implements CommandLineRunner {

    private final CacheService cacheService;

    public CacheSmokeRunner(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void run(String... args) {
        // Jednorazowy wpis testowy, żeby łatwo sprawdzić tabelę w H2
        String key = "smoke_test_key_do_not_use";
        cacheService.put(key, "{\"smoke\":true}", "{\"ok\":true}", Duration.ofMinutes(10));
    }
}
