package OutCastMCBot.suggestions;

import OutCastMCBot.CommandInterface;
import OutCastMCBot.Constants;
import OutCastMCBot.applications.ApplicationMap;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class SuggestCommand implements CommandInterface {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent e) throws IOException {
        e.getMessage().addReaction(Constants.ticketemoji).queue();
        e.getMember().getUser().openPrivateChannel().queue(channel -> {
            User user = e.getMember().getUser();
            channel.sendMessage("**Enter your suggestion:**").queue();
            List<User> list = SuggestionList.getList();
            if (!list.contains(user)) list.add(user);
            ApplicationMap.getMap().removeAll(user);
        });
    }

    @Override
    public String getCommand() {
        return "suggest";
    }

    @Override
    public String getHelp() {
        return "Submit a suggestion\n**Usage:** `!suggest`";
    }
}
