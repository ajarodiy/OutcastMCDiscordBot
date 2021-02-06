package OutCastMCBot.tickets;

import OutCastMCBot.CommandInterface;
import OutCastMCBot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class TicketClose implements CommandInterface {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent e) throws IOException {
//        System.out.println("working");
        if (e.getChannel().getTopic()!=null && e.getChannel().getTopic().contains("ticket") && e.getChannel().getName().contains("ticket")) {

            e.getMessage().delete().queue();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setDescription(Constants.closeconfirmationdescription).setTitle("Confirm").setColor(Constants.ticketcolor);
            e.getChannel().sendMessage(embed.build()).queue(msg -> {
                msg.addReaction(Constants.checkmark).queue();
                msg.addReaction(Constants.crossmark).queue();
            });
        }

        if (e.getChannel().getTopic()!=null && e.getChannel().getTopic().contains("Application") && e.getChannel().getName().contains("application")) {

            e.getMessage().delete().queue();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setDescription(Constants.applicationconfirmationdescription).setTitle("Confirm").setColor(Constants.ticketcolor);
            e.getChannel().sendMessage(embed.build()).queue(msg -> {
                msg.addReaction(Constants.checkmark).queue();
                msg.addReaction(Constants.crossmark).queue();
            });
        }
    }

    @Override
    public String getCommand() {
        return "close";
    }

    @Override
    public String getHelp() {
        return "Close your ticket\n**Usage:** `!close`";
    }
}
