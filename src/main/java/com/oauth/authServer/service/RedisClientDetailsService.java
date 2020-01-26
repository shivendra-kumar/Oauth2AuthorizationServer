package com.oauth.authServer.service;

import com.oauth.authServer.repository.CustomClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class RedisClientDetailsService implements ClientDetailsService {
    @Autowired
    private CustomClientDetailsRepository repository;
    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        if(repository.findById(s).isPresent()){
            return repository.findById(s).get();
        }
        return null;
    }
}
