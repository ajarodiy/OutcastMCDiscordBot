package OutCastMCBot.tickets;

import OutCastMCBot.CommandInterface;
import OutCastMCBot.Constants;
import OutCastMCBot.utils.CheckRole;
import OutCastMCBot.utils.Error;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TicketClaim implements CommandInterface {

    @Override
    public void run(List<String> args, GuildMessageReceivedEvent e) throws IOException {
        if (!CheckRole.hasRole(e.getMember(), Constants.roleOutcastTeamID)) {
            Error.run(e.getChannel());return;
        }
        if (e.getChannel().getTopic()==null || (!e.getChannel().getTopic().contains("ticket"))) return;
        String topic = e.getChannel().getTopic();
        if (topic.contains("Claimed by")) {
            e.getChannel().sendMessage("This ticket has already been claimed by " +
                    topic.substring(topic.indexOf("Claimed by")+11, topic.indexOf("| ticket"))).queue(message -> {
                message.delete().queueAfter(2, TimeUnit.SECONDS);
                e.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
            });
            return;
        }
        e.getMessage().delete().queue();
        e.getChannel().sendMessage("This ticket has been claimed by " + e.getMember().getAsMention()).queue();
        e.getChannel().getManager().setTopic(e.getChannel().getTopic()
                .replaceFirst("Unclaimed", "Claimed by " + e.getMember().getUser().getName())).queue();
    }

    @Override
    public String getCommand() {
        return "claim";
    }

    @Override
    public String getHelp() {
        return "Claim a ticket (Staff Only)\n**Usage:** `!claim`";
    }
}
