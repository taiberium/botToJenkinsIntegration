package com.hackathon.integration.model.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class CommandDto {
    @NotNull
    private final String command;
    @NotNull
    private final String job;
}
