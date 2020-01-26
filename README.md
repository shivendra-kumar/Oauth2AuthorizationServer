# Oauth2AuthorizationServer
## Description:
 Simple Oauth2 based Authorization server for creation of access tokens and their validation. Inbuilt RateLimiter for granular control of number of API calls per client.
 
## Technology Stack:
* *Spring Boot* as framework
* *Spring Security Oauth2* for client registration,generation and validation of access tokens
* *Spring Data Redis* for persistence of client details and access tokens.
* *Redisson* for implementation of API RateLimiter.
