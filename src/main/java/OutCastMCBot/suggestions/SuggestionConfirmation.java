package OutCastMCBot.suggestions;

import OutCastMCBot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;

public class SuggestionConfirmation {
    public static void run(Message m, PrivateMessageReactionAddEvent e) {
        if (e.getReactionEmote().getEmoji().equals(Constants.checkmark)) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(e.getUser().getAsTag())
                    .setDescription(m.getEmbeds().get(0).getDescription())
                    .setColor(Constants.ticketcolor)
                    .setFooter(Constants.serverip, Constants.iconurl);
            e.getJDA().getGuildById(Constants.guildID).getTextChannelById(Constants.suggestionChannelID)
                    .sendMessage(eb.build()).queue(msg -> {
                msg.addReaction(Constants.thumbsup).queue();
                msg.addReaction(Constants.thumbsdown).queue();
            });
            m.delete().queue();
            e.getChannel().sendMessage("The suggestion has been submitted successfully.").queue();
        }else if (e.getReactionEmote().getEmoji().equals(Constants.crossmark)) {
            m.delete().queue();
            e.getChannel().sendMessage("Suggestion has been cancelled.").queue();
        }
    }
}
