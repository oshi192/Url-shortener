package com.example.urlshortener.web;

import com.example.urlshortener.service.UrlService;
import com.example.urlshortener.web.dto.ShortenRequest;
import com.example.urlshortener.web.dto.ShortenResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UrlController {
    private final UrlService urlService;
    private final KafkaTemplate<String, Map<String, Object>> kafka;

    public UrlController(UrlService urlService,
                         KafkaTemplate<String, Map<String, Object>> kafka) {
        this.urlService = urlService;
        this.kafka = kafka;
    }

    @PostMapping("/shorten")
    public ShortenResponse shorten(@RequestBody ShortenRequest req) {
        var mapping = urlService.create(req.longUrl(), req.customAlias());
        return new ShortenResponse(mapping.getAlias());
    }
}
