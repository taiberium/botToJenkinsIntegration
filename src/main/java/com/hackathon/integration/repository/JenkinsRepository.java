package com.hackathon.integration.repository;


import com.offbytwo.jenkins.JenkinsServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
@RequiredArgsConstructor
public class JenkinsRepository {

    private final JenkinsServer jenkinsServer;

    public void execute(String jobName) {
        try {
            jenkinsServer.enableJob(jobName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
