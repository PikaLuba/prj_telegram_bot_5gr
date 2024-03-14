package org.example.feature.telegram;

import org.example.feature.currency.*;
import org.example.feature.telegram.command.StartCommand;
import org.example.feature.ui.PrettyCurrencyService;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {
    // значення за замовчуванням
    //    bankID = 1 - Приватбанк
    //    prettyId = 2 - 2 знака после запятой
    public static int bankId = 1;
    public  static int prettyId = 2;

    private CurrencyService currencyService;
    private PrettyCurrencyService prettyCurrencyService;

    public CurrencyTelegramBot() {
        if (bankId == 1) {
            currencyService = new PrivateBankCurrencyService();
        } else if (bankId == 2) {
            currencyService = new NBUCurrencyService();
        } else {
            currencyService = new MonoCurrencyService();
        }

        if (prettyId == 2) {
            prettyCurrencyService = new PrettyCurrencyService();
        }

        register(new StartCommand());
        // register(new HelpCommand());
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            String callbackQuery = update.getCallbackQuery().getData();

            Currency currency = Currency.valueOf(callbackQuery);

            for (int i = 0; i <= 2; i++) {
                double currencyRateBuy = 0.0;

                if (i != 0) {
                    currencyRateBuy = currencyService.getRateBuy(currency, i);
                }
                String prettyText = prettyCurrencyService.convert(currencyRateBuy, currency, i, bankId);

                SendMessage responseMessage = new SendMessage();
                responseMessage.setText(prettyText);

                long chatId = update.getCallbackQuery().getMessage().getChatId();
                responseMessage.setChatId(Long.toString(chatId));

                try {
                    execute(responseMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

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
}

