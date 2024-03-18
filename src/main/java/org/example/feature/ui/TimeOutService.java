package org.example.feature.ui;

import org.example.feature.currency.Currency;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.example.feature.telegram.CurrencyTelegramBot;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class TimeOutService {
/*
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();


    public  String timeout(String text, long chatId, int timeId) {

        scheduledExecutorService.schedule(
                () -> sendText(text, chatId),
                timeId,
                TimeUnit.SECONDS
        );
    }

    private  void sendText(String text, long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

*/
}
