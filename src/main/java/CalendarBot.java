import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CalendarBot extends TelegramLongPollingBot{
    private static final String TOKEN = "";
    private static final String USERNAME = "";

    public CalendarBot(DefaultBotOptions options){
        super(options);
    }

    public String getBotToken(){
        return TOKEN;
    }

    public String getBotUsername(){
        return USERNAME;
    }

    public void onUpdateReceived(Update update){
        if(update.getMessage() != null && update.getMessage().hasText()){
            String chatId = update.getMessage().getChatId().toString();

            try{
                execute(new SendMessage((String) chatId,"Hi"));
            }
            catch (TelegramApiException e){
                e.printStackTrace();
            }

        }
    }
}
