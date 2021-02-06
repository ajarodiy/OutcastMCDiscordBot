package OutCastMCBot.tickets;

import OutCastMCBot.CommandInterface;
import OutCastMCBot.Constants;
import OutCastMCBot.utils.Error;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TicketMessage implements CommandInterface {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent e) throws IOException {
        if (!e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            Error.run(e.getChannel()); return;
        }
        e.getMessage().delete().queue();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(":tickets: Support Tickets")
                .setDescription(Constants.ticketdescription).setColor(Constants.ticketcolor)
                .setFooter(Constants.serverip, Constants.iconurl);
        e.getChannel().sendMessage(embed.build()).queue(m -> m.addReaction(Constants.ticketemoji).queue());
    }

    @Override
    public String getCommand() {
        return "ticketmessage";
    }

    @Override
    public String getHelp() {
        return "Generate a tickets prompt (Staff Only)\n**Usage:** `!ticketmessage`";
    }
}
