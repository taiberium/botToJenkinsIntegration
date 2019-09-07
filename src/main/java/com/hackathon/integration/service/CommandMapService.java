package com.hackathon.integration.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CommandMapService {

    private static final Map<String, String> COMMAND_TO_JOB_MAP = new ConcurrentHashMap<>();

    public Map<String, String> getAll() {
        return COMMAND_TO_JOB_MAP;
    }

    public String getJobByCommand(String command) throws NoSuchElementException {
        String job = COMMAND_TO_JOB_MAP.get(command);
        return Optional.ofNullable(job).orElseThrow();
    }

    public String getCommandByJob(String job) throws NoSuchElementException {
        return COMMAND_TO_JOB_MAP.entrySet().stream()
                .filter(entry -> job.equalsIgnoreCase(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow();
    }

    public void save(String command, String job) {
        COMMAND_TO_JOB_MAP.put(command, job);
    }

    public void delete(String command) {
        COMMAND_TO_JOB_MAP.remove(command);
    }
}
