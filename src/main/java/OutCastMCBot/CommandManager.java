package OutCastMCBot;

import OutCastMCBot.applications.*;
import OutCastMCBot.bugs.BugCommand;
import OutCastMCBot.commands.*;
import OutCastMCBot.invites.*;
import OutCastMCBot.tickets.*;
import OutCastMCBot.reports.*;
import OutCastMCBot.suggestions.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.*;

public class CommandManager {

    private final Map<String, CommandInterface> commands = new HashMap<>();

    CommandManager() {
        addCommand(new TicketMessage());
        addCommand(new TicketClose());
        addCommand(new TicketClaim());
        addCommand(new Help(this));
        addCommand(new ReportCommand());
        addCommand(new Announce());
        addCommand(new InviteCommand());
        addCommand(new InvitesLeaderboard());
        addCommand(new InviteBonus());
        addCommand(new WhoInvited());
        addCommand(new SuggestCommand());
        addCommand(new MemberCount());
        addCommand(new ApplyCommand());
        addCommand(new LockCommand());
        addCommand(new TicketAddCommand());
        addCommand(new BugCommand());
    }

    private void addCommand(CommandInterface c) {
        if (!commands.containsKey(c.getCommand())) {
            commands.put(c.getCommand(),c);
        }
    }

    public Collection<CommandInterface> getCommands() {
        return commands.values();
    }

    public CommandInterface getCommand(String name) {
        if (name==null) {
            return null;
        }
        return commands.get(name);
    }

    void run(GuildMessageReceivedEvent e) {
        final String message = e.getMessage().getContentRaw();
        if (!message.startsWith(Constants.prefix)) {
            // Reports check
            ReportListener.run(e);
            return;
        }
        final String[] msg = message.replaceFirst(Constants.prefix,"").split(" ");
        if (commands.containsKey(msg[0].toLowerCase())) {
            final List<String> args = Arrays.asList(msg).subList(1,msg.length);
            try {
                commands.get(msg[0].toLowerCase()).run(args, e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

}
