package com.hackathon.integration.config;

import im.dlg.botsdk.Bot;
import im.dlg.botsdk.BotConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutionException;


@Configuration
@ConfigurationProperties(prefix = "bot")
@Getter
@Setter
public class BotConfiguration {

    private String host;
    private int port;
    private String token;

    @Bean(destroyMethod = "stop")
    public Bot bot() throws ExecutionException, InterruptedException {
        BotConfig botConfig = BotConfig.Builder.aBotConfig()
                .withHost(host)
                .withPort(port)
                .withToken(token)
                .build();

        return Bot.start(botConfig).get();
    }
}
