package OutCastMCBot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public interface CommandInterface {

    void run(List<String> args, GuildMessageReceivedEvent e) throws IOException;

    String getCommand();

    String getHelp();


}
