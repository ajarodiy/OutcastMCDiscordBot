package OutCastMCBot.tickets;

import OutCastMCBot.*;
import OutCastMCBot.Constants;
import OutCastMCBot.utils.CheckRole;
import OutCastMCBot.utils.Error;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TicketAddCommand implements CommandInterface {

    @Override
    public void run(List<String> args, GuildMessageReceivedEvent e) throws IOException {
        if (!CheckRole.hasRole(e.getMember(), Constants.roleOutcastTeamID)) {
            Error.run(e.getChannel());return;
        }

        if (!e.getChannel().getName().contains("ticket")) return;

        if (args.size() < 0 || !args.get(0).startsWith("<@")) {
            e.getChannel().sendMessage("Please mention a valid user.").queue();
            return;
        }

        String userid = args.get(0).replaceAll("<@","").replaceAll(">","").replaceAll("!","");
        Member member;

        try {
            member = e.getGuild().getMemberById(userid);
        }catch (Exception exception) {
            e.getChannel().sendMessage("Please mention a valid user.").queue();
            return;
        }

        e.getChannel().putPermissionOverride(member).setAllow(Permission.VIEW_CHANNEL).queue();
        e.getChannel().sendMessage(member.getAsMention() + " has been added to the ticket.").queue();

    }

    @Override
    public String getCommand() {
        return "add";
    }

    @Override
    public String getHelp() {
        return "Add another staff to the ticket (Staff Only)\n**Usage:** `!add {user}`";
    }
}
