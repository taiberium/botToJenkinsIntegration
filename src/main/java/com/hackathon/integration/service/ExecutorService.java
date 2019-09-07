package com.hackathon.integration.service;

import com.hackathon.integration.repository.JenkinsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutorService {

    private final CommandMapService commandMapService;
    private final JenkinsRepository jenkinsRepository;

    public void executeChatCommand(String command) throws NoSuchElementException {
        var job = commandMapService.getJobByCommand(command);
        executeJob(job);
    }

    public void executeJob(String jobName) {
        jenkinsRepository.execute(jobName);
    }
}
