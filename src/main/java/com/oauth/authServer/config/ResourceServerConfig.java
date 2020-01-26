package com.oauth.authServer.config;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

   @Autowired
   private RedissonClient client;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/createClient").permitAll()
                .antMatchers("/setLimit").permitAll()
                .anyRequest().authenticated().and()
                .addFilterAfter(rateLimitFilter(tokenStore), BasicAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable();
    }

    @Bean
    public Filter rateLimitFilter(TokenStore tokenStore){
        return new OncePerRequestFilter(){


            @Override
            protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
                HttpServletRequest request = (HttpServletRequest)servletRequest;
                String requestUri = request.getRequestURI();
                if(requestUri.contains("/createClient") || requestUri.contains("/setLimit") || requestUri.contains("/oauth/token")){
                    filterChain.doFilter(servletRequest,servletResponse);
                }
                else {

                    String[] authorization = request.getHeaders("Authorization").nextElement().split(" ");
                    String accessToken = authorization[1];
                    String clientId = tokenStore.readAuthentication(accessToken).getOAuth2Request().getClientId();
                    StringBuilder builder = new StringBuilder();
                    builder.append("ratelimit");
                    builder.append(":");
                    builder.append(clientId);
                    if(client.getRateLimiter(builder.toString()).tryAcquire()){
                        filterChain.doFilter(servletRequest,servletResponse);
                    }
                    else {
                        HttpServletResponse response = (HttpServletResponse) servletResponse;
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    }

                }
            }
            };


    }


}
