package com.snow.service.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HomeService {

    private static final Logger logger = LoggerFactory.getLogger(HomeService.class);
    private static final String ACCPET_JSON = "application/json";

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "homeError")
    public String homeService(String name) {
        logger.info("Entering homeService() method, go to call service-home/home api, the name is: {}", name);

        return restTemplate.getForObject("http://service-home/home?name=" + name, String.class);
    }

    public String homeError(String name) {
        return "hi, " + name + ", sorry, error!";
    }

    public HttpEntity<String> setAcceptType(String type) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, type);
        return new HttpEntity<>(null, httpHeaders);
    }
}
