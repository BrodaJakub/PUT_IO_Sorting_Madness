package pl.put.poznan.sortingmadness.cache;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "cache_entry",
        uniqueConstraints = @UniqueConstraint(name = "uk_cache_entry_cache_key", columnNames = "cache_key"),
        indexes = {
                @Index(name = "idx_cache_entry_expires_at", columnList = "expires_at")
        }
)
public class CacheEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cache_key", nullable = false, updatable = false, length = 64)
    private String cacheKey;

    @Lob
    @Column(name = "request_json", nullable = false)
    private String requestJson;

    @Lob
    @Column(name = "response_json", nullable = false)
    private String responseJson;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    protected CacheEntry() {
        // JPA
    }

    public CacheEntry(String cacheKey, String requestJson, String responseJson, Instant createdAt, Instant expiresAt) {
        this.cacheKey = cacheKey;
        this.requestJson = requestJson;
        this.responseJson = responseJson;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    @PrePersist
    void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public String getRequestJson() {
        return requestJson;
    }

    public String getResponseJson() {
        return responseJson;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}
