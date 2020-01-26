package com.oauth.authServer.controller;

import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RateLimitController {

    private static final String CLIENT_ID = "client_id";
    private static final String API_THROTTLING_LIMIT = "api_throttling_limit";
    private static final String RESET_INTERVAL_IN_MINUTES = "reset_interval_in_minutes";

    @Autowired
    private RedissonClient client;


    @PostMapping("/setLimit")
    public void setRateLimit(@RequestBody Map<String,Object> limit){
        String clientId = (String) limit.get(CLIENT_ID);
        int numberOfAllowedAPICalls = (int) limit.get(API_THROTTLING_LIMIT);
        int refreshIntervalinMinutes = (int) limit.get(RESET_INTERVAL_IN_MINUTES);
        StringBuilder builder = new StringBuilder();
        builder.append("ratelimit");
        builder.append(":");
        builder.append(clientId);
        client.getRateLimiter(builder.toString()).trySetRate(RateType.PER_CLIENT,numberOfAllowedAPICalls,refreshIntervalinMinutes,RateIntervalUnit.MINUTES);
    }
}
