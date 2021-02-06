package OutCastMCBot.applications;

import OutCastMCBot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ApplicationConfirmation {
    public static void run(User user, PrivateChannel channel) {
        channel.sendMessage(buildEmbed(user).build()).queue(m -> {
            m.addReaction(Constants.checkmark).queue();
            m.addReaction(Constants.crossmark).queue();
            ApplicationMap.getMessages().get(user).add(m.getId());
        });
    }

    public static void confirm(Message m, PrivateMessageReactionAddEvent e) {
        User user = e.getUser();
        if (e.getReactionEmote().getEmoji().equals(Constants.checkmark)) {
            ApplicationMap.removeOldMessages(user);
            e.getChannel().sendMessage("Your application has been submitted successfully.").queue();
            makeApplicationChannel(e.getJDA().getGuildById(Constants.guildID).getCategoryById(Constants.applicationCategoryID),
                    e.getJDA().getGuildById(Constants.guildID).getMember(e.getUser()), buildEmbed(e.getUser()));
            ApplicationMap.getMap().removeAll(e.getUser());
        }else if (e.getReactionEmote().getEmoji().equals(Constants.crossmark)) {
            ApplicationMap.removeOldMessages(user);
            e.getChannel().sendMessage("Your application has been cancelled.").queue();
            ApplicationMap.getMap().removeAll(e.getUser());
        }
    }

    public static EmbedBuilder buildEmbed(User user) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(String.format("%s's OutcastMC Staff Application", user.getAsTag()))
                .setColor(Constants.ticketcolor).setFooter(Constants.serverip, Constants.iconurl);
        ArrayList<String> answers = new ArrayList<>(ApplicationMap.getMap().get(user));
        for (int i=1; i<=12; ++i) {
            embed.addField(ApplicationMap.getQuestion(i), answers.get(i), false);
        }
        return embed;
    }

    public static void makeApplicationChannel(Category category, Member member, EmbedBuilder embed) {
        User user = member.getUser();
        category.createTextChannel(user.getName() + "-Application").queue(tc -> {
            tc.getManager().setTopic("Application - " + user.getId()).queue();
            tc.createPermissionOverride(member).setAllow(Permission.MESSAGE_READ).setDeny(Permission.MESSAGE_WRITE).queue();
            tc.putPermissionOverride(category.getGuild().getPublicRole()).setDeny(Permission.VIEW_CHANNEL).queue();
            tc.putPermissionOverride(category.getGuild().getRoleById("722875027132776542")).setAllow(Permission.MESSAGE_WRITE)
                    .setAllow(Permission.MESSAGE_READ).queue();
            tc.sendMessage("@everyone").queue(msg -> msg.delete().queue());
            tc.sendMessage(embed.build()).queue();
            ApplicationMap.getLockedchannels().add(tc.getId());
        });
    }

    public static boolean applicationExists(User user) {
        Category category = user.getJDA().getGuildById(Constants.guildID).getCategoryById(Constants.applicationCategoryID);
        for (TextChannel c : category.getTextChannels()) {
            if (c.getTopic()!=null && c.getTopic().contains(user.getId())) {
                return true;
            }
        }
        return false;
    }
}
