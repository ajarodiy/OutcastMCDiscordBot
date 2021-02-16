package OutCastMCBot.bugs;

import OutCastMCBot.Constants;
import OutCastMCBot.suggestions.SuggestionList;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

public class BugListener {
    public static void run(PrivateMessageReceivedEvent e) {
        BugList.getList().remove(e.getAuthor());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Confirm Bug Report").setColor(Constants.ticketcolor)
                .setDescription(e.getMessage().getContentRaw())
                .setFooter(Constants.serverip, Constants.iconurl);
        e.getChannel().sendMessage(eb.build()).queue(m -> {
            m.addReaction(Constants.checkmark).queue();
            m.addReaction(Constants.crossmark).queue();
        });
    }
}
