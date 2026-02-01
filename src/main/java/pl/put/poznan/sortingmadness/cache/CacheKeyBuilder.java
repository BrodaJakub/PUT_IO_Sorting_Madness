package pl.put.poznan.sortingmadness.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class CacheKeyBuilder {

    private final ObjectMapper objectMapper;

    public CacheKeyBuilder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

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

    private String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase(Locale.ROOT);
    }

    private String sha256Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return toHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }

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
