package OutCastMCBot.events;

import OutCastMCBot.Constants;
import OutCastMCBot.applications.ApplicationConfirmation;
import OutCastMCBot.applications.ApplicationMap;
import OutCastMCBot.applications.ApplicationQuestionConfirmation;
import OutCastMCBot.suggestions.SuggestionConfirmation;
import OutCastMCBot.tickets.TicketCreate;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class PrivateReact extends ListenerAdapter {
    @Override
    public void onPrivateMessageReactionAdd(@NotNull PrivateMessageReactionAddEvent e) {
        if (e.getUser().isBot()) return;
        e.getChannel().retrieveMessageById(e.getMessageId()).queue(m -> {
            if (!m.getEmbeds().isEmpty()) {
                // Suggestions
                if (m.getEmbeds().get(0).getTitle().equals("Confirm Suggestion")) {
                    SuggestionConfirmation.run(m, e);
                }
                // Application
                try {
                    String title = m.getEmbeds().get(0).getTitle();
                    title = title.substring(title.indexOf(".")+2);
                    if (ApplicationMap.getQuestions().contains(title)) {
                        ApplicationQuestionConfirmation.run(m, e);
                    }
                }catch (Exception exception) {
                    exception.printStackTrace();
                }

                if (m.getEmbeds().get(0).getTitle().contains("'s OutcastMC Staff Application")) {
                    ApplicationConfirmation.confirm(m, e);
                }
            }
        });

    }
}
