package com.bud.weather.api.service.impl;

import com.bud.weather.api.service.RateLimitService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("rateLimitService")
public class RateLimitServiceImpl implements RateLimitService {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();
    private final int NO_OF_WEATHER_REPORTS_PER_HOUR_PER_KEY = 5;
    private final int RATE_LIMIT_REFRESH_TIME_IN_HOURS = 1;

    @Override
    public Bucket resolveBucket(String apiKey) {
        return cache.computeIfAbsent(apiKey, this::createBucket);
    }

    private Bucket createBucket(String apiKey) {
        Bandwidth limit = Bandwidth.classic(
                NO_OF_WEATHER_REPORTS_PER_HOUR_PER_KEY,
                Refill.intervally(NO_OF_WEATHER_REPORTS_PER_HOUR_PER_KEY, Duration.ofHours(RATE_LIMIT_REFRESH_TIME_IN_HOURS)));
        return Bucket4j.builder()
                .addLimit(limit)
                .build();
    }
}
