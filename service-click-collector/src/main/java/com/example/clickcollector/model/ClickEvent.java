package com.example.clickcollector.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "click_events")
@Data
@NoArgsConstructor
public class ClickEvent {
    @Id
    @GeneratedValue
    private Long id;
    private String alias;
    private String ua;
    private String geo;
    private Long ts;

    public ClickEvent(String alias, String ua, String geo, Long ts) {
        this.alias = alias;
        this.ua = ua;
        this.geo = geo;
        this.ts = ts;
    }
}
