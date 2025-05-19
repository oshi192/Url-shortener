package com.example.urlshortener.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "urls")
@Data
@NoArgsConstructor
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String alias;

    @Column(nullable = false)
    private String longUrl;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public UrlMapping(String alias, String longUrl) {
        this.alias = alias;
        this.longUrl = longUrl;
    }
}
