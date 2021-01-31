import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();

        DefaultBotOptions botOptions = new ApiContext().getInstance(DefaultBotOptions.class);

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        CalendarBot botCalendar = new CalendarBot(botOptions);
        try {
            telegramBotsApi.registerBot(botCalendar);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
