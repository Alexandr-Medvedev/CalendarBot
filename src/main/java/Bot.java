import java.time.LocalDate;
import java.time.YearMonth;
import java.util.regex.Pattern;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class Bot extends TelegramLongPollingBot {
    private static final String TOKEN = "";
    private static final String USERNAME = "";

    public Bot(DefaultBotOptions options) {
        super(options);
    }

    public String getBotToken() {
        return TOKEN;
    }

    public String getBotUsername() {
        return USERNAME;
    }

    public void onUpdateReceived(Update update) {
        try {
            if (update.getMessage() != null && update.getMessage().hasText()) {
                String chatId = update.getMessage().getChatId().toString();
                String textMessage = update.getMessage().getText();

                if (textMessage.equals("/start"))
                    execute(new SendMessage((String) chatId, "Hi! To choose calendar please use /calendar command"));
                else if (textMessage.equals("/calendar")) {
                    InlineKeyboardMarkup cal = new Calendar(YearMonth.now().getYear(), YearMonth.now().getMonthValue()).getCalendar();
                    SendMessage message = new SendMessage(chatId, "Please, choose the date");
                    message.setReplyMarkup(cal);
                    execute(message);
                }
            } else if (update.hasCallbackQuery()) {
                String callback = update.getCallbackQuery().getData();
                String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
                int msgId = update.getCallbackQuery().getMessage().getMessageId();

                if (Pattern.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$", callback) && !update.getCallbackQuery().getData().isEmpty()) {
                    //delete old calendar
                    DeleteMessage delMsg = new DeleteMessage();
                    delMsg.setChatId(chatId);
                    delMsg.setMessageId(msgId);
                    execute(delMsg);

                    //set new calendar
                    LocalDate date = LocalDate.parse(update.getCallbackQuery().getData());
                    InlineKeyboardMarkup cal = new Calendar(date.getYear(), date.getMonthValue()).getCalendar();
                    SendMessage message = new SendMessage(chatId, "Please, choose the date");
                    message.setReplyMarkup(cal);
                    execute(message);
                }
                else {
                    execute( new SendMessage(chatId, callback));
                }
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}