package org.example.feature.telegram;

import org.example.feature.currency.*;
import org.example.feature.currency.Currency;
import org.example.feature.telegram.command.StartCommand;
import org.example.feature.ui.PrettyCurrencyService;
import org.example.feature.ui.TimeOutService;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {

    // значення за замовчуванням
    //    bankID = 1 - Приватбанк, 2-НБУ, 3 - Монобанк
    //    prettyId = 2 - 2 знака после запятой
    //    timeId  - зміщення запуску в сек

    public static int bankId = 1;   // значення за замовчуванням
    public  static int prettyId = 3;  // значення за замовчуванням
    public  static int timeId = 10;  // значення за замовчуванням

    private ScheduledExecutorService scheduledExecutorService;

    private CurrencyService currencyService;
    private PrettyCurrencyService prettyCurrencyService;
    private TimeOutService timeOutService;

    public CurrencyTelegramBot() {

        if (bankId == 1) {
            currencyService = new PrivateBankCurrencyService();
        } else if (bankId == 2) {
            currencyService = new NBUCurrencyService();
        } else {
            currencyService = new MonoCurrencyService();
        }
        prettyCurrencyService = new PrettyCurrencyService();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        timeOutService = new TimeOutService();

        register(new StartCommand());
        // register(new HelpCommand());
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        if (update.hasCallbackQuery()) {

            String callbackQuery = update.getCallbackQuery().getData();
            Currency currency = Currency.valueOf(callbackQuery);
            // 3 рядки в інфо
            for (int i = 0; i <= 2; i++) {
                double currencyRateBuy = 0.0;

                if (i != 0) {
                    currencyRateBuy = currencyService.getRateBuy(currency, i);
                } else {
                    //////
                    long chatId1 = update.getCallbackQuery().getMessage().getChatId();
                    String ss = "Зміщення запуску: " + timeId;
                    sendText(ss, chatId1);
                    /////
                }
                String prettyText = prettyCurrencyService.convert(currencyRateBuy, currency, i, bankId, prettyId);

                long chatId = update.getCallbackQuery().getMessage().getChatId();

                SendMessage resMessage = new SendMessage();
                resMessage.setText(prettyText);
                resMessage.setChatId(Long.toString(chatId));
                scheduledExecutorService.schedule(
                        () -> sendText(prettyText, chatId),
                        timeId,
                        TimeUnit.SECONDS
                );
               /* try {
                    execute(resMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                */

            }
        }
    }

    private  void sendText(String text, long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return BotConstants.BOT_TOKEN;
    }


    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }


}

