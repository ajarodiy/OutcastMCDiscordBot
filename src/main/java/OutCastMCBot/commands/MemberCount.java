package OutCastMCBot.commands;

import OutCastMCBot.CommandInterface;
import OutCastMCBot.Constants;
import OutCastMCBot.utils.CheckRole;
import OutCastMCBot.utils.Error;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class MemberCount implements CommandInterface {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent e) throws IOException {
        if (!CheckRole.hasRole(e.getMember(), Constants.roleOutcastTeamID)){
            Error.run(e.getChannel());return;
        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("<" + Constants.ticketemoji + ">  Member Count")
                .setFooter(Constants.serverip, Constants.iconurl)
                .setColor(Constants.ticketcolor);
        int users=0, bots=0;
        for (Member member : e.getGuild().getMembers()) {
            if (member.getUser().isBot())  bots++;
            else users++;
        }
        embed.setDescription(String.format("**Total Members:** %d\n**Users:** %d\n**Bots:** %d", bots+users, users, bots));
        e.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getCommand() {
        return "membercount";
    }

    @Override
    public String getHelp() {
        return "Get the member count of the server (Staff Only)\n**Usage:** `!membercount`";
    }
}
