package pl.put.poznan.sortingmadness.cache;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

/**
 * Serwis odpowiedzialny za obsługę pamięci podręcznej (cache) zapisanej w bazie danych.
 * <p>
 * Umożliwia pobieranie odpowiedzi na podstawie klucza cache, zapisywanie nowych wpisów
 * oraz usuwanie wpisów przeterminowanych. Wpisy cache mogą posiadać czas wygaśnięcia (TTL).
 * Jeśli czas wygaśnięcia jest ustawiony, wpis po jego przekroczeniu jest traktowany jako
 * nieaktualny i usuwany.
 * </p>
 */

@Service
public class CacheService {

    /**
     * Repozytorium służące do operacji na encji {@code CacheEntry}.
     */
    private final CacheEntryRepository repository;

    /**
     * Tworzy nową instancję serwisu cache z podanym repozytorium.
     *
     * @param repository repozytorium wpisów cache
     */
    public CacheService(CacheEntryRepository repository) {
        this.repository = repository;
    }


    /**
     * Pobiera odpowiedź w formacie JSON z cache dla podanego klucza, jeśli wpis istnieje i nie jest przeterminowany.
     * <p>
     * Jeżeli wpis nie istnieje, metoda zwraca {@link Optional#empty()}.
     * Jeżeli wpis istnieje, ale jest przeterminowany (czas wygaśnięcia minął),
     * wpis zostaje usunięty z bazy danych, a metoda zwraca {@link Optional#empty()}.
     * </p>
     *
     * @param cacheKey klucz identyfikujący wpis cache
     * @return {@link Optional} zawierający odpowiedź JSON, jeżeli wpis jest dostępny i aktualny;
     *         w przeciwnym razie {@link Optional#empty()}
     */
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

    /**
     * Zapisuje lub aktualizuje wpis cache dla podanego klucza.
     * <p>
     * Jeżeli wpis dla danego klucza już istnieje, jego pola zostaną nadpisane.
     * Jeśli wpis nie istnieje, zostanie utworzony nowy.
     * </p>
     * <p>
     * Parametr {@code ttl} określa czas życia wpisu (time-to-live). Jeżeli {@code ttl} jest {@code null},
     * wpis będzie traktowany jako nieprzeterminowany (brak daty wygaśnięcia).
     * </p>
     *
     * @param cacheKey klucz identyfikujący wpis cache
     * @param requestJson treść żądania w formacie JSON (do przechowywania / diagnostyki)
     * @param responseJson treść odpowiedzi w formacie JSON przechowywana w cache
     * @param ttl czas życia wpisu; {@code null} oznacza brak wygaśnięcia
     */
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

    /**
     * Usuwa wszystkie przeterminowane wpisy cache.
     *
     * @return liczba usuniętych wpisów cache
     */
    @Transactional
    public long deleteExpired() {
        return repository.deleteByExpiresAtBefore(Instant.now());
    }

    /**
     * Sprawdza, czy wpis cache jest przeterminowany na podstawie daty wygaśnięcia.
     * <p>
     * Jeśli {@code expiresAt} jest {@code null}, wpis jest traktowany jako nieprzeterminowany.
     * </p>
     *
     * @param expiresAt data i czas wygaśnięcia wpisu; {@code null} oznacza brak wygaśnięcia
     * @return {@code true} jeśli wpis ma ustawioną datę wygaśnięcia i jest ona w przeszłości;
     *         w przeciwnym razie {@code false}
     */
    private boolean isExpired(Instant expiresAt) {
        return expiresAt != null && expiresAt.isBefore(Instant.now());
    }
}
