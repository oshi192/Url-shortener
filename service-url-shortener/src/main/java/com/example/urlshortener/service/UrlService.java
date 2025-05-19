package com.example.urlshortener.service;

import com.example.urlshortener.model.UrlMapping;
import com.example.urlshortener.repository.UrlMappingRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class UrlService {
    private final UrlMappingRepository repo;

    public UrlService(UrlMappingRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "aliasToUrl", key = "#alias")
    public String resolve(String alias) {
        return repo.findByAlias(alias)
                .map(UrlMapping::getLongUrl)
                .orElseThrow(() -> new IllegalArgumentException("Alias not found"));
    }

    public UrlMapping create(String longUrl, String customAlias) {
        String alias = (customAlias != null && !customAlias.isBlank())
                ? customAlias
                : generateAlias(longUrl);

        return repo.findByAlias(alias)
                .orElseGet(() -> repo.save(new UrlMapping(alias, longUrl)));
    }

    private String generateAlias(String url) {
        try {
            var md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(url.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash).substring(0, 8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
