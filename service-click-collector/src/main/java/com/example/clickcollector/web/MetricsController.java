package com.example.clickcollector.web;

import com.example.clickcollector.repository.ClickEventRepo;
import com.example.clickcollector.repository.GeoCount;
import com.example.clickcollector.web.dto.RateDto;
import com.example.clickcollector.web.dto.TopUrlDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {
    private final ClickEventRepo repo;

    public MetricsController(ClickEventRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/rate")
    public RateDto rate() {
        long since = Instant.now().minusSeconds(60).toEpochMilli();
        return new RateDto(repo.countSince(since));
    }

    @GetMapping("/geo")
    public List<GeoCount> geo() {
        return repo.byRegion();
    }

    @GetMapping("/top")
    public List<TopUrlDto> topUrls() {
        return repo.findTopUrls(PageRequest.of(0, 10)).stream()
                .map(a -> new TopUrlDto(a.getAlias(), a.getCnt()))
                .collect(Collectors.toList());
    }
}
