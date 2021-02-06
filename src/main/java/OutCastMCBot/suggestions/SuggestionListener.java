package OutCastMCBot.suggestions;

import OutCastMCBot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

public class SuggestionListener {
    public static void run(PrivateMessageReceivedEvent e) {
        SuggestionList.getList().remove(e.getAuthor());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Confirm Suggestion").setColor(Constants.ticketcolor)
                .setDescription(e.getMessage().getContentRaw())
                .setFooter(Constants.serverip, Constants.iconurl);
        e.getChannel().sendMessage(eb.build()).queue(m -> {
            m.addReaction(Constants.checkmark).queue();
            m.addReaction(Constants.crossmark).queue();
        });
    }
}
