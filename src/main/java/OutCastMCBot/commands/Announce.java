package OutCastMCBot.commands;

import OutCastMCBot.CommandInterface;
import OutCastMCBot.Constants;
import OutCastMCBot.utils.Error;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class Announce implements CommandInterface {

    @Override
    public void run(List<String> args, GuildMessageReceivedEvent e) throws IOException {
        if (!e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            Error.run(e.getChannel()); return;
        }
        StringBuilder msg = new StringBuilder();
        EmbedBuilder embed = new EmbedBuilder();
        if (args.get(0).startsWith("{")) {
            for (String word : args) msg.append(word + " ");
            embed.setTitle(msg.substring(1,msg.indexOf("}")))
                    .setThumbnail(Constants.iconurl)
                    .setDescription(msg.substring(msg.indexOf("}")+1))
                    .setColor(Constants.ticketcolor)
                    .setFooter(Constants.serverip, Constants.iconurl);

        }else{
            for (String word : args) msg.append(word + " ");
            embed.setTitle("<" + Constants.ticketemoji + ">  OutcastMC Announcement")
                    .setThumbnail(Constants.iconurl)
                    .setDescription(msg.toString())
                    .setColor(Constants.ticketcolor)
                    .setFooter(Constants.serverip, Constants.iconurl);
        }
        e.getMessage().delete().queue();
        e.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getCommand() {
        return "announce";
    }

    @Override
    public String getHelp() {
        return "Announce a message (Staff Only)\n**Usage:** `!announce {title} [message]`";
    }
}
