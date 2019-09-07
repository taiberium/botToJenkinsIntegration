package com.hackathon.integration.listener;

import com.hackathon.integration.service.ExecutorService;
import im.dlg.botsdk.Bot;
import im.dlg.botsdk.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.NoSuchElementException;

@Slf4j
@Component
@RequiredArgsConstructor
public class BotListener {

    private final Bot bot;
    private final ExecutorService service;

    @PostConstruct
    public void init() {
        bot.messaging().onMessage(this::handleMessage);
    }

    private void handleMessage(Message message) {
        logUserMessage(message);
        String chatCommand = message.getText();
        BotResponse botResponse = execute(chatCommand);
        bot.messaging().sendText(message.getSender(), botResponse.toString());
    }

    private void logUserMessage(Message message) {
        bot.users().get(message.getSender())
                .thenAccept(userOpt ->
                        userOpt.ifPresent(user ->
                                log.info("Got a message: {} from user: {}", message.getText(), user.getName())));
    }

    private BotResponse execute(String command) {
        try {
            service.executeChatCommand(command);
        } catch (NoSuchElementException e) {
            return BotResponse.NOT_FOUND;
        } catch (RuntimeException e) {
            log.error("Bot execution error", e);
            return BotResponse.ERROR;
        }
        return BotResponse.SUCCESS;
    }

    private enum BotResponse {
        SUCCESS, NOT_FOUND, ERROR
    }
}
