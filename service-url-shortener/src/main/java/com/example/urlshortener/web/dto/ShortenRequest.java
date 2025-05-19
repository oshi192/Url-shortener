package com.example.urlshortener.web.dto;

public record ShortenRequest(String longUrl, String customAlias) {
}
