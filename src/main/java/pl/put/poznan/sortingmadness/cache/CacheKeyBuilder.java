package pl.put.poznan.sortingmadness.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Klasa odpowiedzialna za generowanie deterministycznych kluczy pamięci podręcznej (cache).
 * <p>
 * Klucz cache jest tworzony na podstawie zestawu parametrów opisujących żądanie,
 * takich jak typ sortowania, lista algorytmów oraz dane wejściowe.
 * W celu zapewnienia jednoznaczności oraz odporności na różnice formatowania,
 * parametry są najpierw normalizowane i serializowane do postaci JSON,
 * a następnie haszowane przy użyciu algorytmu SHA-256.
 * </p>
 */
public class CacheKeyBuilder {

    /**
     * Obiekt {@link ObjectMapper} wykorzystywany do serializacji danych do formatu JSON.
     */
    private final ObjectMapper objectMapper;

    /**
     * Tworzy nową instancję generatora kluczy cache.
     *
     * @param objectMapper mapper JSON używany do serializacji danych wejściowych
     */
    public CacheKeyBuilder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Buduje klucz cache na podstawie przekazanych parametrów żądania.
     * <p>
     * Parametry wejściowe są normalizowane (przycinanie białych znaków, zamiana na małe litery),
     * a następnie zapisywane w uporządkowanej strukturze danych.
     * Jeżeli kolejność algorytmów nie ma znaczenia, lista algorytmów jest sortowana,
     * co zapewnia identyczny klucz dla logicznie równoważnych zapytań.
     * </p>
     * <p>
     * Ostateczny klucz jest tworzony poprzez serializację danych do formatu JSON
     * oraz obliczenie skrótu SHA-256.
     * </p>
     *
     * @param sortType typ sortowania
     * @param algorithms lista nazw algorytmów sortujących
     * @param data dane wejściowe do sortowania
     * @param algorithmsOrderMatters informacja, czy kolejność algorytmów ma znaczenie
     * @return deterministyczny klucz cache w postaci ciągu znaków
     * @throws IllegalStateException jeżeli wystąpi błąd serializacji JSON lub brak obsługi algorytmu SHA-256
     */
    public String buildKey(String sortType, List<String> algorithms, List<?> data, boolean algorithmsOrderMatters) {
        Map<String, Object> canonical = new LinkedHashMap<>();
        canonical.put("sortType", normalize(sortType));

        List<String> normalizedAlgos = new ArrayList<>();
        if (algorithms != null) {
            for (String a : algorithms) {
                normalizedAlgos.add(normalize(a));
            }
        }

        if (!algorithmsOrderMatters) {
            Collections.sort(normalizedAlgos);
        }

        canonical.put("algorithms", normalizedAlgos);
        canonical.put("data", data == null ? Collections.emptyList() : data);

        try {
            String canonicalJson = objectMapper.writeValueAsString(canonical);
            return sha256Hex(canonicalJson);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to build cache key (JSON serialization error)", e);
        }
    }

    /**
     * Normalizuje ciąg znaków poprzez usunięcie białych znaków z początku i końca
     * oraz konwersję do małych liter.
     *
     * @param s ciąg znaków do normalizacji
     * @return znormalizowany ciąg znaków; pusty ciąg w przypadku {@code null}
     */
    private String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase(Locale.ROOT);
    }

    /**
     * Oblicza skrót SHA-256 dla podanego ciągu znaków i zwraca go w postaci szesnastkowej.
     *
     * @param input dane wejściowe do haszowania
     * @return skrót SHA-256 zapisany w postaci szesnastkowej
     * @throws IllegalStateException jeżeli algorytm SHA-256 nie jest dostępny
     */
    private String sha256Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return toHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }

    /**
     * Konwertuje tablicę bajtów na reprezentację szesnastkową.
     *
     * @param bytes tablica bajtów
     * @return ciąg znaków w postaci szesnastkowej
     */
    private String toHex(byte[] bytes) {
        char[] hex = "0123456789abcdef".toCharArray();
        char[] out = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            out[i * 2] = hex[v >>> 4];
            out[i * 2 + 1] = hex[v & 0x0F];
        }
        return new String(out);
    }
}
