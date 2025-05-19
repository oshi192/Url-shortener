package com.example.clickcollector.service;

import com.example.clickcollector.model.ClickEvent;
import com.example.clickcollector.repository.ClickEventRepo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ClickListener {
    private final ClickEventRepo repo;

    public ClickListener(ClickEventRepo repo) {
        this.repo = repo;
    }

    @KafkaListener(topics = "clicks", groupId = "collector")
    public void onMessage(Map<String, Object> data) {
        var evt = new ClickEvent(
                (String) data.get("alias"),
                (String) data.get("ua"),
                (String) data.get("geo"),
                ((Number) data.get("ts")).longValue()
        );
        repo.save(evt);
    }
}
