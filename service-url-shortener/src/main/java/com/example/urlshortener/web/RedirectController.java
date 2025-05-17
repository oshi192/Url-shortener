package com.example.urlshortener.web;

import com.example.urlshortener.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@RestController
public class RedirectController {
    private final UrlService urlService;
    private final org.springframework.kafka.core.KafkaTemplate<String,Map<String,Object>> kafka;

    public RedirectController(UrlService urlService,
                              org.springframework.kafka.core.KafkaTemplate<String,Map<String,Object>> kafka) {
        this.urlService = urlService;
        this.kafka = kafka;
    }

    @GetMapping("/{alias}")
    public void redirect(
            @PathVariable String alias,
            HttpServletRequest req,
            HttpServletResponse res
    ) throws IOException {
        // lookup
        String dest = urlService.resolve(alias);
        // emit click event
        kafka.send("clicks", Map.of(
                "alias", alias,
                "ua", req.getHeader("User-Agent"),
                "geo", req.getRemoteAddr(),
                "ts", Instant.now().toEpochMilli()
        ));
        // redirect with 302
        res.sendRedirect(dest);
    }
}
