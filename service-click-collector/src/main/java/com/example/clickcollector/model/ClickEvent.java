package com.example.clickcollector.model;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "click_events")
@Data
@NoArgsConstructor
public class ClickEvent {
    @Id @GeneratedValue private Long id;
    private String alias;
    private String ua;
    private String geo;
    private Long ts;

    public ClickEvent(String alias, String ua, String geo, Long ts) {
        this.alias = alias; this.ua = ua; this.geo = geo; this.ts = ts;
    }
}
