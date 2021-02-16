package OutCastMCBot.events;

import OutCastMCBot.applications.ApplicationListener;
import OutCastMCBot.applications.ApplicationMap;
import OutCastMCBot.bugs.BugList;
import OutCastMCBot.bugs.BugListener;
import OutCastMCBot.suggestions.SuggestionList;
import OutCastMCBot.suggestions.SuggestionListener;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class PrivateMessage extends ListenerAdapter {
    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return;

        // Suggestion ?
        if (SuggestionList.getList().contains(e.getAuthor())) SuggestionListener.run(e);
        if (ApplicationMap.getMap().containsKey(e.getAuthor())) ApplicationListener.run(e);
        if (BugList.getList().contains(e.getAuthor())) BugListener.run(e);
    }
}
