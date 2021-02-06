package OutCastMCBot.applications;

import OutCastMCBot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;

public class ApplicationQuestionConfirmation {
    public static void run(Message m, PrivateMessageReactionAddEvent e) {
        User user = e.getUser();
        if (e.getReactionEmote().getEmoji().equals(Constants.checkmark)) {
            m.delete().queue();
            if (!ApplicationMap.getMap().containsKey(user)) return;
            ApplicationMap.getMap().get(user).add(m.getEmbeds().get(0).getDescription());
            if (ApplicationMap.getMap().get(user).size()==13) {
                ApplicationConfirmation.run(user, e.getChannel());
                return;
            }
            e.getChannel().sendMessage(
                    ApplicationMap.getQuestion(ApplicationMap.getMap().get(user).size()))
                    .queue(msg -> {
                        ApplicationMap.getMessages().get(user).add(msg.getId());
                    });
        }else if (e.getReactionEmote().getEmoji().equals(Constants.crossmark)) {
            m.delete().queue();
            e.getChannel().sendMessage(
                    ApplicationMap.getQuestion(ApplicationMap.getMap().get(user).size()))
                    .queue(msg -> {
                        ApplicationMap.getMessages().get(user).add(msg.getId());
                    });
        }
    }
}
