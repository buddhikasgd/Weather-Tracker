package com.bud.weather.api.config.interceptor;

import com.bud.weather.api.exception.MissingHeaderException;
import com.bud.weather.api.exception.TooManyRequestsException;
import com.bud.weather.api.exception.ValidationException;
import com.bud.weather.api.service.RateLimitService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);
    private static final String HEADER_X_API_KEY= "X-api-key";
    private static final String HEADER_X_RATE_LIMIT_REMAINING= "X-Rate-Limit-Remaining";
    private static final String HEADER_X_RATE_LIMIT_RETRY_MINUTES= "X-Rate-Limit-Retry-After-Minutes";

    // TODO: These keys should move to property file or taken from a key service/provider or handle in api gateway itself
    private final Set<String> apiKeyHolder = new HashSet<>(
            Arrays.asList("WTR-KEY-1", "WTR-KEY-2", "WTR-KEY-3", "WTR-KEY-4", "WTR-KEY-5")
    );

    @Autowired
    private RateLimitService rateLimitService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String apiKey = request.getHeader(HEADER_X_API_KEY);
        logger.info("Api key sent by the client is {}", apiKey);
        validateApiKey(apiKey);

        return handleRateLimits(response, apiKey);
    }

    private boolean handleRateLimits(HttpServletResponse response, String apiKey) {
        Bucket bucket = rateLimitService.resolveBucket(apiKey);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.addHeader(HEADER_X_RATE_LIMIT_REMAINING, String.valueOf(probe.getRemainingTokens()));
            return true;
        } else {
            long waitForRefill = probe.getNanosToWaitForRefill() / 60000000000L;
            response.addHeader(HEADER_X_RATE_LIMIT_RETRY_MINUTES, String.valueOf(waitForRefill));
            throw new TooManyRequestsException("Rate limit has exceeded!! Please Try again in " + waitForRefill + " minutes");
        }
    }

    private void validateApiKey(String apiKey) {
        if (!StringUtils.hasLength(apiKey)) {
            throw new MissingHeaderException("Missing Header: " + HEADER_X_API_KEY);
        }
        if (!apiKeyHolder.contains(apiKey.toUpperCase())) {
            throw new ValidationException("Invalid Api Key");
        }
    }
}
