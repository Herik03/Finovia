package org.vaadin.example.application.services;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class APIConfig {

    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("API_KEY");
        
        Config cfg = Config.builder()
                .key(apiKey)
                .timeOut(10)
                .build();

        AlphaVantage.api().init(cfg);
    }
}