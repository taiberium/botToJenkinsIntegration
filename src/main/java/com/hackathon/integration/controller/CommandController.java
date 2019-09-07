package com.hackathon.integration.controller;

import com.hackathon.integration.model.dto.CommandDto;
import com.hackathon.integration.service.CommandMapService;
import com.hackathon.integration.service.ExecutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("commands")
public class CommandController {

    private final CommandMapService commandMapService;
    private final ExecutorService executorService;

    @GetMapping
    public List<CommandDto> getAll() {
        return commandMapService.getAll().entrySet()
                .stream()
                .map(entry -> new CommandDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid CommandDto commandDto) {
        commandMapService.save(commandDto.getCommand(), commandDto.getJob());
    }

    @GetMapping("{command}")
    public CommandDto getByChatCommand(@PathVariable("command") String command) {
        var job = commandMapService.getJobByCommand(command);
        return new CommandDto(command, job);
    }

    @GetMapping("jobs/{job}")
    public CommandDto getByJobName(@PathVariable("job") String job) {
        var command = commandMapService.getCommandByJob(job);
        return new CommandDto(command, job);
    }

    @PostMapping("jobs/{job}")
    public void executeJob(@PathVariable("job") String job) {
        executorService.executeJob(job);
    }

    @PostMapping("{command}")
    public void executeCommand(@PathVariable("command") String command) {
        executorService.executeChatCommand(command);
    }

    @DeleteMapping("{command}")
    public void delete(@PathVariable("command") String command) {
        commandMapService.delete(command);
    }
}
