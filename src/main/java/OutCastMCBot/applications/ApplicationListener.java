package OutCastMCBot.applications;

import OutCastMCBot.Constants;
import com.google.common.collect.Multimap;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

public class ApplicationListener {
    public static void run(PrivateMessageReceivedEvent e) {
        User user = e.getAuthor();
        ApplicationMap.removeOldMessages(user);
        if (ApplicationMap.getMap().get(user).size()==13) {
            ApplicationConfirmation.run(user, e.getChannel());
            return;
        }
        String msg = e.getMessage().getContentRaw();
        PrivateChannel channel = e.getChannel();
        if (msg.equalsIgnoreCase("cancel")) {
            channel.sendMessage("Your application has been cancelled.").queue();
            ApplicationMap.getMap().removeAll(user);
            return;
        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Constants.ticketcolor).setFooter(Constants.serverip, Constants.iconurl)
                .setTitle(ApplicationMap.getQuestion(ApplicationMap.getMap().get(user).size()))
                .setDescription(msg);
        channel.sendMessage(embed.build()).queue(m -> {
            m.addReaction(Constants.checkmark).queue();
            m.addReaction(Constants.crossmark).queue();
            ApplicationMap.getMessages().get(user).add(m.getId());
        });
    }
}
