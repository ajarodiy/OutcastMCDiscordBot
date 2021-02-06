package OutCastMCBot.tickets;

import OutCastMCBot.Constants;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class TicketCloseConfirmation {
    public static void run(Message m, GuildMessageReactionAddEvent e) {
        if (e.getReactionEmote().getEmoji().equals(Constants.checkmark)) {
            e.retrieveMessage().queue(msg -> {
                msg.getTextChannel().delete().queue();
            });
        }else if (e.getReactionEmote().getEmoji().equals(Constants.crossmark)) {
            e.retrieveMessage().queue(msg -> msg.delete().queue());
        }
    }
}
