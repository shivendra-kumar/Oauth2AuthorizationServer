package com.oauth.authServer.entity;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.io.Serializable;

@RedisHash("CustomClientDetails")
public class CustomClientDetails extends BaseClientDetails {
    @JsonProperty("client_id")
    @com.fasterxml.jackson.annotation.JsonProperty("client_id")
    @Id
    private String clientId;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    public String getClientId() {
        return clientId;
    }



}
