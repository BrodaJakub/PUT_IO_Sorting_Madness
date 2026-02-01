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
        String key = "smoke_test_key_do_not_use";

        if (cacheService.getResponseJsonIfPresent(key).isPresent()) {
            return;
        }

        cacheService.put(key, "{\"smoke\":true}", "{\"ok\":true}", Duration.ofMinutes(10));
    }
}
