package com.oauth.authServer.controller;

import com.oauth.authServer.entity.CustomClientDetails;
import com.oauth.authServer.repository.CustomClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ClientDetailsController {

    @Autowired
    private CustomClientDetailsRepository customClientDetailsRepository;

    @PostMapping("/createClient")
    public CustomClientDetails createNewClient(@RequestBody CustomClientDetails clientDetails){
        return customClientDetailsRepository.save(clientDetails);
    }

}
