package OutCastMCBot.bugs;

import OutCastMCBot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;

public class BugConfirmation {
    public static void run(Message m, PrivateMessageReactionAddEvent e) {
        if (e.getReactionEmote().getEmoji().equals(Constants.checkmark)) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(e.getUser().getAsTag() + " Bug Report")
                    .setDescription(m.getEmbeds().get(0).getDescription())
                    .setColor(Constants.ticketcolor)
                    .setFooter(Constants.serverip, Constants.iconurl);
            e.getJDA().getGuildById(Constants.guildID).getTextChannelById(Constants.bugsChannelID)
                    .sendMessage(eb.build()).queue();
            m.delete().queue();
            e.getChannel().sendMessage("The bug report has been submitted successfully.").queue();
        }else if (e.getReactionEmote().getEmoji().equals(Constants.crossmark)) {
            m.delete().queue();
            e.getChannel().sendMessage("Bug report has been cancelled.").queue();
        }
    }
}
