package com.hackathon.integration.config;

import com.offbytwo.jenkins.JenkinsServer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Configuration
@ConfigurationProperties(prefix = "jenkins")
@Getter
@Setter
public class JenkinsConfiguration {

    private String host;
    private int port;
    private String user;
    private String password;

    @Bean
    public JenkinsServer jenkinsServer() {
        URI uri = UriComponentsBuilder.newInstance().host(host).port(port).build().toUri();
        return new JenkinsServer(uri, user, password);
    }
}
