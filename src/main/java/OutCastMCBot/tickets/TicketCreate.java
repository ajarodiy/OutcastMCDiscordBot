package OutCastMCBot.tickets;

import OutCastMCBot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class TicketCreate {
    public static void run(Message m, GuildMessageReactionAddEvent e) {
        try {
            m.removeReaction(e.getReactionEmote().getEmote(), e.getUser()).queue();
            if (e.getReactionEmote().getEmote().getId().equals(Constants.serveremote)) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setDescription("_A member of staff will be with you shortly. Please explain in full detail why you require support._\n\n" +
                        "**USER :** " + e.getUser().getName() +
                        "\n**ESTIMATED TIME :** 10 Minutes\n\n" +
                        "Type **!close** to close the ticket.")
                        .setColor(Constants.ticketcolor).setTitle(":tickets:  OutcastMC Ticket")
                        .setFooter(Constants.serverip, Constants.iconurl);

                Category category = e.getGuild().getCategoryById(Constants.ticketcategoryid);
                for (TextChannel c : category.getTextChannels()) {
                    if (c.getTopic()!=null && c.getTopic().contains(e.getUserId())) {
                        e.getUser().openPrivateChannel().queue(channel -> channel.sendMessage("Please close your current ticket to open a new one.").queue());
                        return;
                    }
                }
                category.createTextChannel(e.getUser().getName() + "-Ticket").queue(tc -> {
                    tc.getManager().setTopic("Unclaimed | ticket - " + e.getUserId()).queue();
                    tc.createPermissionOverride(e.getMember()).setAllow(Permission.MESSAGE_WRITE).setAllow(Permission.MESSAGE_READ).queue();
                    tc.putPermissionOverride(e.getGuild().getPublicRole()).setDeny(Permission.VIEW_CHANNEL).queue();
//                    tc.sendMessage("@everyone").queue(msg -> msg.delete().queue());
                    tc.sendMessage(embed.build()).queue();
                });

                EmbedBuilder embed1 = new EmbedBuilder();

                embed1.setDescription(e.getUser().getName() + " has opened a ticket. React with <:OutcastMC:782035842293039124> to claim it.")
                        .setTitle(e.getUser().getName() + "'s Ticket")
                        .setColor(Constants.ticketcolor)
                        .setFooter(Constants.serverip, Constants.iconurl);


                e.getGuild().getTextChannelById(Constants.ticketrequestschannel).sendMessage(embed1.build()).queue(msg -> msg.addReaction(Constants.ticketemoji).queue());
                e.getGuild().getTextChannelById(Constants.ticketrequestschannel).sendMessage("@everyone").queue(msg -> msg.delete().queue());
            }
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
