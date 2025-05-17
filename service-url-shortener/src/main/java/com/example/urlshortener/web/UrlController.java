package com.example.urlshortener.web;

import com.example.urlshortener.service.UrlService;
import com.example.urlshortener.web.dto.ShortenRequest;
import com.example.urlshortener.web.dto.ShortenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UrlController {
    private final UrlService urlService;
    private final KafkaTemplate<String, Map<String,Object>> kafka;

    public UrlController(UrlService urlService,
                         KafkaTemplate<String, Map<String,Object>> kafka) {
        this.urlService = urlService;
        this.kafka = kafka;
    }

    @PostMapping("/shorten")
    public ShortenResponse shorten(@RequestBody ShortenRequest req) {
        var mapping = urlService.create(req.longUrl(), req.customAlias());
        return new ShortenResponse(mapping.getAlias());
    }

    @GetMapping("/{alias}")
    public void redirect(@PathVariable String alias,
                         HttpServletRequest req,
                         HttpServletResponse res) throws IOException {
        String dest = urlService.resolve(alias);
        kafka.send("clicks", Map.of(
                "alias", alias,
                "ua", req.getHeader("User-Agent"),
                "geo", req.getRemoteAddr(),
                "ts", Instant.now().toEpochMilli()
        ));
        res.sendRedirect(dest);
    }
}
