package OutCastMCBot.bugs;

import OutCastMCBot.CommandInterface;
import OutCastMCBot.Constants;
import OutCastMCBot.applications.ApplicationMap;
import OutCastMCBot.suggestions.SuggestionList;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class BugCommand implements CommandInterface {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent e) throws IOException {
        e.getMessage().addReaction(Constants.ticketemoji).queue();
        e.getMember().getUser().openPrivateChannel().queue(channel -> {
            User user = e.getMember().getUser();
            channel.sendMessage("**Describe the encountered bug in detail:**").queue();
            List<User> list = BugList.getList();
            if (!list.contains(user)) list.add(user);
            ApplicationMap.getMap().removeAll(user);
            SuggestionList.getList().remove(user);
        });
    }

    @Override
    public String getCommand() {
        return "bug";
    }

    @Override
    public String getHelp() {
        return "Submit a bug report\n**Usage:** `!bug`";
    }
}
