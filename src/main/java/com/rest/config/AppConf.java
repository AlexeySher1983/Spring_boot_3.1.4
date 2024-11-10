package com.rest.config;


import com.rest.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.awt.*;

@Configuration
public class AppConf {

    @Bean
    public RestTemplate restTemplate (){
        return new RestTemplate();
    }



}
